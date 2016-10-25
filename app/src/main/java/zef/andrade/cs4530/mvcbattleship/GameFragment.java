package zef.andrade.cs4530.mvcbattleship;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import zef.andrade.cs4530.mvcbattleship.model.Cell;
import zef.andrade.cs4530.mvcbattleship.model.Game;
import zef.andrade.cs4530.mvcbattleship.model.Grid;
import zef.andrade.cs4530.mvcbattleship.model.Player;

/**
 * Created by zandrade on 10/12/2016.
 */
public class GameFragment extends Fragment implements GameCellView.OnCellTouchedListener, Player.OnMissileFiredListener {

    public interface OnMissileResultListener {
        public void OnMissileResult();
    }

    public interface OnTransitionScreenListener {
        public void OnNewTurn();
    }

    public static GameFragment newInstance() {
        //TODO: Insert Parameters into saved instance state
        return new GameFragment();
    }

    private Game mGame;

    private GameView mPlayerView;
    private GameView mOpponentView;
    private OnMissileResultListener mOnMissileResultListener;
    private OnTransitionScreenListener mOnTransitionScreenListener;
    private Timer timer = new Timer();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout rootLayout = new LinearLayout(getActivity());
        rootLayout.setOrientation(LinearLayout.HORIZONTAL);

        mPlayerView = new GameView(getActivity());
        mPlayerView.setVisibility(View.VISIBLE);
        mPlayerView.setBackgroundColor(Color.BLACK);

        mOpponentView = new GameView(getActivity());
        mOpponentView.setVisibility(View.VISIBLE);
        mOpponentView.setBackgroundColor(Color.WHITE);

        if (mGame == null) {
            displayEmptyGameCells(mPlayerView);
            displayEmptyGameCells(mOpponentView);
        } else {
            loadGame();
            setupOpponentViewTouchListeners();
            setupMissileFiredListeners();
            // display transition screen on turn over or end game
            if ((mGame.getTurnOver() || mGame.getGameOver()) && !mGame.getWinnerAnnounced()) {
                timer.schedule(new TransitionScreenTask(), 1500);
            }
        }


        rootLayout.addView(mPlayerView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        rootLayout.addView(mOpponentView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        return rootLayout;
    }

    public void setGame(Game game) {
        mGame = game;
    }

    public Game getGame() {
        return mGame;
    }

    public void setOnMissileResultListener(OnMissileResultListener onMissileResultListener) {
        mOnMissileResultListener = onMissileResultListener;
    }

    public void setOnTransitionScreenListener(OnTransitionScreenListener onTransitionScreenListener) {
        mOnTransitionScreenListener = onTransitionScreenListener;
    }

    // initialization code
    private void displayEmptyGameCells(GameView view) {
        for (int i = 0; i < GameView.numRows; i++) {
            for (int j = 0; j < GameView.numCols; j++) {
                GameCellView c = new GameCellView(getActivity(), j, i);
                view.addCell(j, i, c);
                view.setCellColor(j, i, GameView.WATER_COLOR);
                view.addView(c);
            }
        }
    }

    private void setupOpponentViewTouchListeners() {
        for (int i = 0; i < GameView.numRows; i++) {
            for (int j = 0; j < GameView.numCols; j++) {
                GameCellView gameCellView = mOpponentView.getCell(j, i);
                gameCellView.setOnCellTouchedListener(this);
            }
        }
    }

    private void setupMissileFiredListeners() {
        Player player1 = mGame.getCurrentPlayer();
        Player player2 = mGame.getOpponentPlayer();
        player1.setOnMissileFiredListener(this);
        player2.setOnMissileFiredListener(this);
    }

    public void loadGame() {
        Grid playerGrid = mGame.getCurrentPlayer().getGrid();
        List<Cell> playerCells = playerGrid.getGridCells();
        mPlayerView = new GameView(getActivity());
        mPlayerView.setBackgroundColor(Color.BLACK);
        for (Cell gridCell : playerCells) {
            GameCellView cell = new GameCellView(getActivity(), gridCell.getIndex());
            mPlayerView.addCell(gridCell.getRow(), gridCell.getColumn(), cell);
            mPlayerView.setCellColor(gridCell.getRow(), gridCell.getColumn(), gridCell.getColor());
            mPlayerView.addView(cell);
        }

        Grid opponentGrid = mGame.getOpponentPlayer().getGrid();
        List<Cell> opponentCells = opponentGrid.getGridCells();
        mOpponentView = new GameView(getActivity());
        mOpponentView.setBackgroundColor(Color.WHITE);
        for (Cell gridCell : opponentCells) {
            GameCellView cell = new GameCellView(getActivity(), gridCell.getIndex());
            mOpponentView.addCell(gridCell.getColumn(), gridCell.getRow(), cell);
            // dont show the ship of opponent
            if (gridCell.getColor() == GameView.SHIP_COLOR) {
                mOpponentView.setCellColor(gridCell.getColumn(), gridCell.getRow(), GameView.WATER_COLOR);
            }
            else {
                mOpponentView.setCellColor(gridCell.getColumn(), gridCell.getRow(), gridCell.getColor());
            }
            mOpponentView.addView(cell);
        }
    }

    @Override
    public void onCellTouched(int x, int y) {
        if (mGame != null && !mGame.getGameOver() && !mGame.getTurnOver()) {
            Player player = mGame.getCurrentPlayer();
            player.shootMissileAt(x, y);
        }
    }

    @Override
    public void OnMissileFired(int x, int y) {
        Player opponentPlayer = mGame.getOpponentPlayer();
        opponentPlayer.missileShotAt(x, y);
    }

    @Override
    public void OnMissileResult(int x, int y, Cell.CellState state) {
        int index = y + x * GameView.numRows;
        Cell cell = mGame.getOpponentPlayer().getGrid().getGridCells().get(index);
        cell.setState(state);
        if (state == Cell.CellState.MISS) {
            cell.setColor(GameView.MISS_COLOR);
        }
        else {
            cell.setColor(GameView.HIT_COLOR);
            mGame.getCurrentPlayer().setScore(mGame.getCurrentPlayer().getScore() + 1);
            mGame.updateGameOver();
        }
        mGame.setTurnOver(true);

        // update the fragment
        mOnMissileResultListener.OnMissileResult();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (!mGame.getGameOver()) {
                mGame.setTurnOver(false);
                Player currentPlayer = mGame.getCurrentPlayer();
                mGame.setCurrentPlayer(mGame.getOpponentPlayer());
                mGame.setOpponentPlayer(currentPlayer);
            }
            else {
                mGame.setWinnerAnnounced(true);
            }
           mOnTransitionScreenListener.OnNewTurn();
        }
    }

    class TransitionScreenTask extends TimerTask
    {
        public void run() {
            Intent intent = new Intent(getActivity(), TransitionScreenActivity.class);
            if (mGame.getGameOver()) {
                intent.putExtra(MainActivity.WINNER_PLAYER_ID, mGame.getPlayerWinnerID());
            }
            else {
                intent.putExtra(MainActivity.NEXT_TURN_PLAYER_ID, mGame.getOpponentPlayer().getPlayerID());
            }
            startActivityForResult(intent, 1);
        }
    }
}
