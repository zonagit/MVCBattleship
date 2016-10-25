package zef.andrade.cs4530.mvcbattleship;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zandrade on 10/14/2016.
 */
public class GameCellView extends View {

    public interface OnCellTouchedListener {
        public void onCellTouched(int x, int y);
    }

    private int mGridIndex;
    private int mRow,mColumn;
    private OnCellTouchedListener mOnCellTouchedListener;

    public GameCellView(Context context, int gridIndex) {
        super(context);

        setBackgroundColor(Color.BLUE);

        mGridIndex = gridIndex;

        mRow = mGridIndex / GameView.numRows;
        mColumn = mGridIndex % GameView.numCols;
    }

    public GameCellView(Context context, int column, int row) {
        super(context);

        setBackgroundColor(Color.BLUE);

        mColumn = column;
        mRow = row;

        mGridIndex = mColumn + GameView.numRows* mRow;
    }

    public void setOnCellTouchedListener (OnCellTouchedListener onCellTouchedListener) {
        mOnCellTouchedListener = onCellTouchedListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mOnCellTouchedListener != null) {
            mOnCellTouchedListener.onCellTouched(mRow, mColumn);
        }
        return super.onTouchEvent(event);
    }

    public void setGridIndex(int gridIndex) {
        mGridIndex = gridIndex;
    }

    public int getGridIndex() {
        return mGridIndex;
    }
}
