package zef.andrade.cs4530.mvcbattleship.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zandrade on 10/12/2016.
 */
public class Game {
    private List<Player> mPlayers;
    private boolean mGameOver;
    private int mPlayerTurnID;
    private int mPlayerWinnerID;
    private Grid mCurrentPlayerGrid;
    private Grid mOpponentPlayerGrid;

    public Game() {
        mPlayers = new ArrayList<Player>();
        mPlayers.add(new Player(1));
        mPlayers.add(new Player(2));
        mPlayerTurnID = 1;
        mPlayerWinnerID = -1;
        mGameOver = false;
        mCurrentPlayerGrid = mPlayers.get(0).getGrid();
        mOpponentPlayerGrid = mPlayers.get(1).getGrid();

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

    public void setPlayerTurnID (int playerTurnID) {
        mPlayerTurnID = playerTurnID;
    }

    public int getPlayerTurnID() {
        return mPlayerTurnID;
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
}
