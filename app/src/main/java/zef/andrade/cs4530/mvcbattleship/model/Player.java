package zef.andrade.cs4530.mvcbattleship.model;

import java.util.List;

/**
 * Created by zandrade on 10/12/2016.
 */
public class Player {

    public interface OnMissileFiredListener {
        public void OnMissileFired(int x, int y);
        public void OnMissileResult(int x, int y, Cell.CellState state);
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


    public int getPlayerID() {
        return mPlayerID;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public int getScore() {
        return mScore;
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
            mOnMissileFiredListener.OnMissileFired(x, y);
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
        mOnMissileFiredListener.OnMissileResult(x, y, state);
    }
}
