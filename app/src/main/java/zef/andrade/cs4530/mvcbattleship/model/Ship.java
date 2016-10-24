package zef.andrade.cs4530.mvcbattleship.model;

import java.util.ArrayList;
import java.util.List;

import zef.andrade.cs4530.mvcbattleship.GameView;

/**
 * Created by zandrade on 10/12/2016.
 */
public class Ship {
    private List<Cell> mShipCells;

    public Ship(List<Integer> indices) {
        mShipCells = new ArrayList<Cell>();
        for (Integer index : indices) {
            Cell shipCell = new Cell();
            shipCell.setState(Cell.CellState.SHIP);
            shipCell.setIndex(index);
            shipCell.setColor(GameView.SHIP_COLOR);
            mShipCells.add(shipCell);
        }
    }

    public Cell.CellState shipHitOrMiss (int x, int y) {
        int index = y + x * GameView.numRows;
        for (Cell shipCell : mShipCells) {
            // TODO: Cant hit the same cell twice
            if (shipCell.getIndex() == index) {
                shipCell.setState(Cell.CellState.HIT);
                shipCell.setColor(GameView.HIT_COLOR);
                return Cell.CellState.HIT;
            }
        }
        return Cell.CellState.MISS;
    }
}
