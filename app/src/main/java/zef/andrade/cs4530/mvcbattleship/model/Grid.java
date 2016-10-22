package zef.andrade.cs4530.mvcbattleship.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import zef.andrade.cs4530.mvcbattleship.GameView;

/**
 * Created by zandrade on 10/12/2016.
 */
public class Grid {
    private List<Ship> mShips;
    private List<Cell> mGridCells;

    private Random randomGenerator;
    private final int mTotalCells = GameView.numCols * GameView.numRows;

    public Grid() {
        mShips = new ArrayList<Ship>();
        mGridCells = new ArrayList<Cell>();
        for (int index = 0; index< mTotalCells; index++) {
            Cell cell = new Cell();
            cell.setIndex(index);
            cell.setColor(GameView.WATER_COLOR);
            cell.setState(Cell.CellState.WATER);
            mGridCells.add(cell);
        }
        placeShipsRandomlyOnGrid();
    }

    public List<Cell> getGridCells() {
        return mGridCells;
    }

    // place 5 ships of different size randomly in the grid
    // try larger ships first to decrease collision chances
    public void placeShipsRandomlyOnGrid() {
        mShips.add(createShip(5));
        mShips.add(createShip(4));
        mShips.add(createShip(3));
        mShips.add(createShip(3));
        mShips.add(createShip(2));
    }

    private Ship createShip(int size) {
        Ship ship;
        List<Integer> shipCells = new ArrayList<Integer>();

        int startIndex, randomDirection;
        // try until we get the requested number of cells
        while (shipCells.size() < size) {
            randomGenerator = new Random(System.currentTimeMillis());
            shipCells = new ArrayList<Integer>();
            startIndex = randomGenerator.nextInt(mTotalCells);
            // if 0 ship goes right (row) if 1 goes down (column) in the grid
            randomDirection = randomGenerator.nextInt(2);
            if (randomDirection == 0) {
                shipCells = getRowShip(size, startIndex);
            }
            else {
                shipCells = getColumnShip(size, startIndex);
            }
        }
        ship = new Ship(shipCells);
        // mark those cells as ship cells and update the corresponding
        // grid cells
        for (Integer shipCell : shipCells) {
            Cell cell = mGridCells.get(shipCell);
            cell.setColor(GameView.SHIP_COLOR);
            cell.setState(Cell.CellState.SHIP);
        }
        return ship;
    }

    private List<Integer> getRowShip(int size,int startIndex) {
        List<Integer> shipCells = new ArrayList<Integer>();
        int cellIndex = startIndex;
        int row = cellIndex / GameView.numRows;
        for (int i=0; i < size; i++) {
            if ( cellIndex >= mTotalCells || cellIndex/GameView.numRows != row || (mGridCells.get(cellIndex).getState() == Cell.CellState.SHIP)) {
                return shipCells;
            }
            shipCells.add(cellIndex);
            cellIndex += 1;
        }
        return shipCells;
    }

    private List<Integer> getColumnShip(int size,int startIndex) {
        List<Integer> shipCells = new ArrayList<Integer>();
        int cellIndex = startIndex;
        int column = cellIndex % GameView.numCols;
        for (int i=0; i < size; i++) {
            if (cellIndex >= mTotalCells || cellIndex%GameView.numCols != column || (mGridCells.get(cellIndex).getState() == Cell.CellState.SHIP)) {
                return shipCells;
            }
            shipCells.add(cellIndex);
            cellIndex += GameView.numCols;
        }
        return shipCells;
    }
}
