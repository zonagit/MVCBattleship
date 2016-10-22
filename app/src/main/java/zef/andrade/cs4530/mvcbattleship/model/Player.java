package zef.andrade.cs4530.mvcbattleship.model;

import java.util.HashMap;
import java.util.Map;

import zef.andrade.cs4530.mvcbattleship.GameView;

/**
 * Created by zandrade on 10/12/2016.
 */
public class Player {
    private int mPlayerID;
    private int mScore;
    private int mMissilesFired;
    private Grid mGrid;

    public Player(int playerID) {
        mPlayerID = playerID;
        mScore = 0;
        mMissilesFired = 0;
        mGrid = new Grid();
    }

    public void setPlayerID(int playerID) {
        mPlayerID = playerID;
    }

    public int getPlayerID() {
        return mPlayerID;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public int getScore() {
        return mScore;
    }

    public void setMissilesFired (int missilesFired) {
        mMissilesFired = missilesFired;
    }

    public int getMissilesFired() {
        return mMissilesFired;
    }

    public Grid getGrid() {
        return mGrid;
    }
}
