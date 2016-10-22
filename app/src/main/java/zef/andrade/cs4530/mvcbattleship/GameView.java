package zef.andrade.cs4530.mvcbattleship;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zandrade on 10/12/2016.
 */
public class GameView extends ViewGroup {

    // [row][col]
    public static final int numRows = 10;
    public static final int numCols = 10;
    public static final int WATER_COLOR = Color.BLUE;
    public static final int SHIP_COLOR = Color.GRAY;
    public static final int MISS_COLOR = Color.WHITE;
    public static final int HIT_COLOR = Color.RED;


    private GameCellView[][] mGameCells = new GameCellView[numRows][numCols];

    public GameView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int margin = numRows/2;
        int bottomMargin, rightMargin;

        for (int childIndex = 0; childIndex < getChildCount(); childIndex++) {
            mGameCells[childIndex / numRows][childIndex % numCols] = (GameCellView) getChildAt(childIndex);
        }
        for (int childIndex = 0; childIndex < getChildCount(); childIndex++) {
            View child = getChildAt(childIndex);
            Rect childLayout = new Rect();

            int actualWidth = getMeasuredWidth() / 10;
            int actualHeight = getMeasuredHeight() / 10;

            childLayout.left = (childIndex % numCols) * actualWidth;
            childLayout.top = (childIndex / numRows) * actualHeight;
            childLayout.right = ((childIndex % numCols) * actualWidth) + actualWidth;
            childLayout.bottom = ((childIndex / numRows) * actualHeight) + actualHeight;

            if (childIndex/numRows == (numRows-1)) {
                bottomMargin = 0;
            }
            else {
                bottomMargin = margin;
            }
            if (childIndex % numCols == (numCols-1)) {
                rightMargin = 0;
            }
            else {
                rightMargin = margin;
            }

            child.layout(childLayout.left + margin, childLayout.top + margin,childLayout.right - rightMargin,childLayout.bottom - bottomMargin);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int width = Math.max(widthSpec, getSuggestedMinimumWidth());
        int height = Math.max(heightSpec, getSuggestedMinimumHeight());

        int childState = 0;
        for (int childIndex = 0; childIndex < getChildCount(); childIndex++) {
            View child = getChildAt(childIndex);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, childState),resolveSizeAndState(height, heightMeasureSpec, childState));
    }

    public void setCellColor(int x, int y, int color) {
        mGameCells[y][x].setBackgroundColor(color);
    }

    public void addCell(int x, int y, GameCellView cell) {
        mGameCells[y][x] = cell;
    }
}
