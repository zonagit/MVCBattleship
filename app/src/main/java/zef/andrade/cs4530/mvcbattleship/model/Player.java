package zef.andrade.cs4530.mvcbattleship.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zef.andrade.cs4530.mvcbattleship.GameView;

/**
 * Created by zandrade on 10/12/2016.
 */
public class Player {

    public interface OnMissileFiredListener {
        public void OnMissileFired(Player player, int x, int y);
        public void OnMissileResult(Player player, int x, int y, Cell.CellState state);
    }

    public interface OnMissileResultListener {

    }

    private int mPlayerID;
    private int mScore;
    private int mMissilesFired;
    private Grid mGrid;
    private OnMissileFiredListener mOnMissileFiredListener;

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

    public void setOnMissileFiredListener(OnMissileFiredListener onMissileFiredListener) {
        mOnMissileFiredListener = onMissileFiredListener;
    }

    // this player shoots at cell x,y
    public void shootMissileAt(int x, int y) {
        if (mOnMissileFiredListener != null) {
            mMissilesFired ++;
            mOnMissileFiredListener.OnMissileFired(this, x, y);
        }
    }

    // this player was shot at cell x,y
    public void missileShotAt(int x,int y) {
        // check if it was a hit or a miss
        Cell.CellState state = Cell.CellState.MISS;
        List<Ship> ships = mGrid.getShips();
        for (Ship ship : ships) {
           if (ship.shipHitOrMiss(x,y) == Cell.CellState.HIT) {
               state = Cell.CellState.HIT;
               break;
           }
        }
        // return the result of the shot to the fragment
        mOnMissileFiredListener.OnMissileResult(this, x, y, state);
    }
}
