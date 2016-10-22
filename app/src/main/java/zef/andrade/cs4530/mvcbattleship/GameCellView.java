package zef.andrade.cs4530.mvcbattleship;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.View;

/**
 * Created by zandrade on 10/14/2016.
 */
public class GameCellView extends View {

    private int mGridIndex;
    private int mRow,mColumn;

    public GameCellView(Context context, int gridIndex) {
        super(context);

        setBackgroundColor(Color.BLUE);

        mGridIndex = gridIndex;
    }

    public GameCellView(Context context, int column, int row) {
        super(context);

        setBackgroundColor(Color.BLUE);

        mColumn = column;
        mRow = row;

        mGridIndex = mColumn* GameView.numRows* mRow;
    }

    public void setGridIndex(int gridIndex) {
        mGridIndex = gridIndex;
    }

    public int getGridIndex() {
        return mGridIndex;
    }
}
