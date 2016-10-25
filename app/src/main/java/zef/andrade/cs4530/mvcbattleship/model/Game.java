package zef.andrade.cs4530.mvcbattleship.model;

/**
 * Created by zandrade on 10/12/2016.
 */
public class Game {
    public static final int mNumShipCells = 5 + 4 + 3 + 3 + 2;

    private boolean mGameOver;
    private Player mCurrentPlayer;
    private Player mOpponentPlayer;
    private int mPlayerWinnerID;
    private boolean mTurnOver;
    private boolean mWinnerAnnounced;

    public Game() {
        mCurrentPlayer = new Player(0);
        mOpponentPlayer = new Player(1);
        mPlayerWinnerID = -1;
        mGameOver = false;
        mTurnOver = false;
        mWinnerAnnounced = false;
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

    public Player getPlayerByID(int id) {
        if (mCurrentPlayer.getPlayerID() == id) {
            return mCurrentPlayer;
        }
        else {
            return mOpponentPlayer;
        }
    }
    public void setCurrentPlayer (Player currentPlayer) {
        mCurrentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setOpponentPlayer (Player opponentPlayer) {
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

    public void updateGameOver() {
        if (mCurrentPlayer.getScore() == mNumShipCells) {
            mGameOver = true;
            mPlayerWinnerID = mCurrentPlayer.getPlayerID();
        }
    }
}
