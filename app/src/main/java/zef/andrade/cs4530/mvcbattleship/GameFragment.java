package zef.andrade.cs4530.mvcbattleship;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import zef.andrade.cs4530.mvcbattleship.model.Cell;
import zef.andrade.cs4530.mvcbattleship.model.Game;
import zef.andrade.cs4530.mvcbattleship.model.Grid;

/**
 * Created by zandrade on 10/12/2016.
 */
public class GameFragment extends Fragment {
    public static GameFragment newInstance() {
        //TODO: Insert Parameters into saved instance state
        return new GameFragment();
    }
    private Game mGame;

    private GameView mPlayerView;
    private GameView mOpponentView;

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
            displayGameCells(mPlayerView);
            displayGameCells(mOpponentView);
        }
        else {
            loadGame();
        }
        rootLayout.addView(mPlayerView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        rootLayout.addView(mOpponentView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        return rootLayout;
    }

    public void setGame(Game game) {
        mGame = game;
    }

    private void displayGameCells(GameView view) {
        for (int i = 0; i < GameView.numRows; i++) {
            for (int j = 0; j < GameView.numCols; j++) {
                GameCellView c = new GameCellView(getActivity(), j, i);
                view.addCell(j, i, c);
                view.setCellColor(j, i, GameView.WATER_COLOR);
                view.addView(c);
            }
        }
    }

    public void loadGame() {
        Grid playerGrid = mGame.getCurrentPlayerGrid();
        List<Cell> playerCells = playerGrid.getGridCells();
        mPlayerView = new GameView(getActivity());
        mPlayerView.setBackgroundColor(Color.BLACK);
        for (Cell gridCell : playerCells) {
            GameCellView cell = new GameCellView(getActivity(), gridCell.getIndex());
            mPlayerView.addCell(gridCell.getRow(), gridCell.getColumn(), cell);
            mPlayerView.setCellColor(gridCell.getRow(), gridCell.getColumn(), gridCell.getColor());
            mPlayerView.addView(cell);
        }

        Grid opponentGrid = mGame.getOpponentPlayerGrid();
        List<Cell> opponentCells = opponentGrid.getGridCells();
        mOpponentView = new GameView(getActivity());
        mOpponentView.setBackgroundColor(Color.WHITE);
        for (Cell gridCell : opponentCells) {
            GameCellView cell = new GameCellView(getActivity(), gridCell.getIndex());
            mOpponentView.addCell(gridCell.getColumn(), gridCell.getRow(), cell);
            mOpponentView.setCellColor(gridCell.getColumn(), gridCell.getRow(), gridCell.getColor());
            mOpponentView.addView(cell);
        }
    }
}
