package zef.andrade.cs4530.mvcbattleship;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zef.andrade.cs4530.mvcbattleship.model.Game;

/**
 * Created by zandrade on 10/12/2016.
 */
public class GameListFragment extends Fragment implements ListAdapter {
    private static final int ITEM_COLOR = Color.GREEN;
    private static final int SELECTED_COLOR = Color.CYAN;

    public static GameListFragment newInstance() {
        //TODO: Insert Parameters into saved instance state
        return new GameListFragment();
    }

    private Button mNewGameButton;
    private ListView mGameListView;

    private List<Game> mGames = new ArrayList<Game>();
    private int mIndexSelectedGame = 0;
    private OnGameSelectedListener mOnGameSelectedListener;

    public interface OnGameSelectedListener  {
        public void onGameSelected(GameListFragment gameListFragment, Game game);
    }

    public OnGameSelectedListener getOnGameSelectedListener() {
        return mOnGameSelectedListener;
    }

    public void setOnGameSelectedListener(OnGameSelectedListener onGameSelectedListener) {
        mOnGameSelectedListener = onGameSelectedListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout rootLayout = new LinearLayout(getActivity());
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        mGameListView = new ListView(getActivity());
        rootLayout.addView(mGameListView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
        mGameListView.setAdapter(this);
        mGameListView.setBackgroundColor(Color.GREEN);
        mGameListView.setDividerHeight(20);
        mGameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mIndexSelectedGame = i;
                for (int childIndex = 0; childIndex <  mGameListView.getChildCount(); childIndex++)
                {
                    mGameListView.getChildAt(childIndex).setBackgroundColor(ITEM_COLOR);
                }
                view.setBackgroundColor(SELECTED_COLOR);
                mOnGameSelectedListener.onGameSelected(GameListFragment.this, mGames.get(i));
            }
        });
        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootLayout;
    }

    public void addGameToList(Game newGame) {
        mGames.add(newGame);
        mGameListView.invalidateViews();
    }

    // ListAdapter interface methods
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return mGames.size();
    }

    @Override
    public Object getItem(int i) {
        return mGames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Game game = mGames.get(i);
        TextView listView = new TextView(getActivity());

        if (i != mIndexSelectedGame) {
            listView.setBackgroundColor(ITEM_COLOR);
        } else {
            listView.setBackgroundColor(SELECTED_COLOR);
        }

        String listViewText = "Game " + i + ": ";

        if (game.getGameOver()) {
            listViewText += " Completed. \n Player " + (game.getPlayerWinnerID()+1) + " won \n";
        }
        else {
            listViewText += " InProgress. \n Player  " + (game.getCurrentPlayer().getPlayerID()+1) + " turn \n";
        }
        listViewText += "Player 1 Shots: " + game.getPlayer(0).getMissilesFired();
        listViewText += " Hits: " + game.getPlayer(0).getScore() + "\n";
        listViewText += "Player 2 Shots: " + game.getPlayer(1).getMissilesFired();
        listViewText += " Hits: " + game.getPlayer(1).getScore() + "\n";

        listView.setText(listViewText);
        return listView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return (getCount() == 0);
    }
}
