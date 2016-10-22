package zef.andrade.cs4530.mvcbattleship.model;

import zef.andrade.cs4530.mvcbattleship.GameView;

/**
 * Created by zandrade on 10/12/2016.
 */
public class Cell {
    private int mIndex;
    private int mColor;
    private CellState eState;

    public enum CellState {
        WATER(1),
        SHIP(2),
        HIT(3);

        CellState(int i) {
        }
    };

    public void setIndex(int index) {
        mIndex = index;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }

    public void setState(CellState state) {
        eState = state;
    }

    public CellState getState() {
        return eState;
    }

    public int getRow() {
        int row = mIndex / GameView.numRows;
        return row;

    }

    public int getColumn() {
        int column = mIndex % GameView.numCols;
        return column;
    }
}
