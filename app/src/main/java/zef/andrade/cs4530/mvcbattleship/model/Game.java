package zef.andrade.cs4530.mvcbattleship.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zandrade on 10/12/2016.
 */
public class Game {
    public static final int mNumShipCells = 5 + 4 + 3 + 3 + 2;

    private List<Player> mPlayers;
    private boolean mGameOver;
    private Player mCurrentPlayer;
    private Player mOpponentPlayer;
    private int mPlayerWinnerID;
    private Grid mCurrentPlayerGrid;
    private Grid mOpponentPlayerGrid;
    private boolean mTurnOver;
    private boolean mWinnerAnnounced;

    public Game() {
        mPlayers = new ArrayList<Player>();
        mPlayers.add(new Player(0));
        mPlayers.add(new Player(1));
        mCurrentPlayer = mPlayers.get(0);
        mOpponentPlayer = mPlayers.get(1);
        mPlayerWinnerID = -1;
        mGameOver = false;
        mTurnOver = false;
        mWinnerAnnounced = false;
        mCurrentPlayerGrid = mCurrentPlayer.getGrid();
        mOpponentPlayerGrid = mOpponentPlayer.getGrid();

    }

    public Player getPlayer(int index) {
        return mPlayers.get(index);
    }

    public void setGameOver(boolean gameOver) {
        mGameOver = gameOver;
    }

    public boolean getGameOver() {
        return mGameOver;
    }

    public void setTurnOver(boolean turnOver) {
        mTurnOver = turnOver;
    }

    public void setWinnerAnnounced(boolean winnerAnnounced) {
        mWinnerAnnounced = winnerAnnounced;
    }

    public boolean getWinnerAnnounced() {
        return mWinnerAnnounced;
    }

    public boolean getTurnOver() {
        return mTurnOver;
    }

    public void setCurrentPlayer (Player currentPlayer) {
        mCurrentPlayerGrid = currentPlayer.getGrid();
        mCurrentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setOpponentPlayer (Player opponentPlayer) {
        mOpponentPlayerGrid = opponentPlayer.getGrid();
        mOpponentPlayer = opponentPlayer;
    }

    public Player getOpponentPlayer() {
        return mOpponentPlayer;
    }

    public void setPlayerWinnerID (int winnerID) {
        mPlayerWinnerID = winnerID;
    }

    public int getPlayerWinnerID() {
        return mPlayerWinnerID;
    }

    public Grid getCurrentPlayerGrid() {
        return mCurrentPlayerGrid;
    }

    public Grid getOpponentPlayerGrid() {
        return mOpponentPlayerGrid;
    }

    public void updateGameOver() {
        if (mCurrentPlayer.getScore() == mNumShipCells) {
            mGameOver = true;
            mPlayerWinnerID = mCurrentPlayer.getPlayerID();
        }
    }
}
