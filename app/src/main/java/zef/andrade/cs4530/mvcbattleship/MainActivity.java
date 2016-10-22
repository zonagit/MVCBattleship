package zef.andrade.cs4530.mvcbattleship;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import zef.andrade.cs4530.mvcbattleship.model.Game;

public class MainActivity extends AppCompatActivity implements GameListFragment.OnGameSelectedListener {

    private static final String GAME_FRAGMENT = "GameFragment";
    private static final String GAME_LIST_FRAGMENT = "GameListFragment";

    private GameFragment mGameFragment;
    private GameListFragment mGameListFragment;
    private FrameLayout mGameListFrame;
    private FrameLayout mGameFrame;
    private LinearLayout mGameListLayout;
    private LinearLayout mGameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.HORIZONTAL);
        setContentView(rootLayout);

        //New Game button
        Button newGameButton = new Button(this);
        newGameButton.setText("New Game");

        newGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Game newGame = new Game();
                // Add the game to the Fragment List.
                mGameListFragment.addGameToList(newGame);
            }
        });

        // Game List and Game fragment/buttons layouts
        mGameListLayout = new LinearLayout(this);
        mGameListLayout.setOrientation(LinearLayout.VERTICAL);
        mGameLayout = new LinearLayout(this);
        mGameLayout.setOrientation(LinearLayout.VERTICAL);

        mGameListFrame = new FrameLayout(this);
        mGameListFrame.setId(10);
        mGameListFrame.setBackgroundColor(Color.GRAY);

        mGameFrame = new FrameLayout(this);
        mGameFrame.setId(11);
        mGameFrame.setBackgroundColor(Color.BLACK);

        mGameListLayout.addView(newGameButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mGameListLayout.addView(mGameListFrame);

        // do layout differently if tablet/phone
        if (isTablet(getResources())) {
          // mGameLayout.addView(mGameFrame, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));

            rootLayout.addView(mGameListLayout, new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,20));
            rootLayout.addView(mGameFrame, new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,80));
        }
        else {
           // when in phone mode we need a back button to go back to the GameList screen;
           Button backButton = new Button(this);
           backButton.setText("Back");
           backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Back to Game List screen: Show the Game List and hide the Game Screen
                   mGameListLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                   mGameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0));

                   onPause();
                }
            });
           mGameLayout.addView(backButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
           mGameLayout.addView(mGameFrame);

           rootLayout.addView(mGameListLayout, new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1));
           rootLayout.addView(mGameLayout, new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,0));
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mGameListFragment = (GameListFragment) getSupportFragmentManager().findFragmentByTag(GAME_LIST_FRAGMENT);
        mGameFragment = (GameFragment) getSupportFragmentManager().findFragmentByTag(GAME_FRAGMENT);
        if (mGameListFragment == null) {
            mGameListFragment = GameListFragment.newInstance();
            mGameListFragment.setOnGameSelectedListener(this);
            mGameFragment = GameFragment.newInstance();

            transaction.add(mGameListFrame.getId(), mGameListFragment, GAME_LIST_FRAGMENT);
            transaction.add(mGameFrame.getId(), mGameFragment, GAME_FRAGMENT);
        }
        else {
            transaction.replace(mGameListFrame.getId(), mGameListFragment, GAME_LIST_FRAGMENT);
            transaction.replace(mGameFrame.getId(), mGameFragment, GAME_FRAGMENT);
        }

        transaction.commit();
    }

    private boolean isTablet(Resources resources) {
        int layout = resources.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        return ((layout == Configuration.SCREENLAYOUT_SIZE_LARGE) || (layout == Configuration.SCREENLAYOUT_SIZE_XLARGE));
    }

    @Override
    public void onGameSelected(GameListFragment gameListFragment, Game game) {
        // if phone switch to Game Screen view
        if (!isTablet(getResources())) {
            mGameListLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0));
            mGameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        }
        // Load game
        mGameFragment.setGame(game);

        //refresh fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mGameFragment = (GameFragment) getSupportFragmentManager().findFragmentByTag(GAME_FRAGMENT);
        transaction.detach(mGameFragment);
        transaction.attach(mGameFragment);
        transaction.commit();
    }
}
