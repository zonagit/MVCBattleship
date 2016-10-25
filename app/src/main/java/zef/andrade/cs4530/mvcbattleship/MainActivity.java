package zef.andrade.cs4530.mvcbattleship;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import zef.andrade.cs4530.mvcbattleship.model.Game;
import zef.andrade.cs4530.mvcbattleship.model.Player;

public class MainActivity extends AppCompatActivity implements GameListFragment.OnGameSelectedListener,
                                                               GameFragment.OnMissileResultListener,
                                                               GameFragment.OnTransitionScreenListener  {

    public static final String NEXT_TURN_PLAYER_ID = "NextTurnPlayerID";
    public static final String WINNER_PLAYER_ID = "WinnerPlayerID";
    private static final String GAME_FRAGMENT = "GameFragment";
    private static final String GAME_LIST_FRAGMENT = "GameListFragment";
    private static final String GAME_LIST_FILENAME = "GameList.txt";
    private static final String GAME_SELECTED_FILENAME = "SelectedGame.txt";

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

        Button deleteSelectedGameButton = new Button(this);
        deleteSelectedGameButton.setText("Delete Selected Game");
        deleteSelectedGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // remove the selected game from the Fragment List.
                mGameListFragment.removeGameFromList();
                refreshGameListFragment();
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
        mGameListLayout.addView(deleteSelectedGameButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mGameListLayout.addView(mGameListFrame);

        // do layout differently if tablet/phone
        if (isTablet(getResources())) {
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
            mGameFragment.setOnMissileResultListener(this);
            mGameFragment.setOnTransitionScreenListener(this);

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

        // refresh game fragment
        refreshGameFragment();
    }


    @Override
    public void OnMissileResult() {
        refreshGameFragment();
        if (isTablet(getResources())) {
            refreshGameListFragment();
        }
    }


    private void refreshGameFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mGameFragment = (GameFragment) getSupportFragmentManager().findFragmentByTag(GAME_FRAGMENT);
        transaction.detach(mGameFragment);
        transaction.attach(mGameFragment);
        transaction.commit();
    }

    private void refreshGameListFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mGameListFragment = (GameListFragment) getSupportFragmentManager().findFragmentByTag(GAME_LIST_FRAGMENT);
        transaction.detach(mGameListFragment);
        transaction.attach(mGameListFragment);
        transaction.commit();
    }

    @Override
    public void OnNewTurn() {
        refreshGameFragment();
        if (isTablet(getResources())) {
            refreshGameListFragment();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        // Save gamelist and selected game index
        File filesDir = getFilesDir();
        Gson gson = new Gson();

        try {
            List<Game> games =  mGameListFragment.getGames();
            // reset listeners (otherwise Gson will get in infinite loop due to circular references
            // in the tree)
            for (Game game : games) {
                game.getOpponentPlayer().setOnMissileFiredListener(null);
                game.getCurrentPlayer().setOnMissileFiredListener(null);
            }
            if (mGameFragment.getGame() != null) {
                Game game = mGameFragment.getGame();
                game.getCurrentPlayer().setOnMissileFiredListener(null);
                game.getOpponentPlayer().setOnMissileFiredListener(null);
                games.set(mGameListFragment.getIndexSelectedGame(), game);
            }
            String jsonGameList = gson.toJson(games);
            File file = new File(filesDir, GAME_LIST_FILENAME);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(jsonGameList);
            writer.close();

            file = new File(filesDir, GAME_SELECTED_FILENAME);
            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);

            writer.write(mGameListFragment.getIndexSelectedGame() + "");
            writer.close();
        }
        catch (Exception e) {
            Log.e("Error writing file on pause","Error writing one of the files " + GAME_LIST_FILENAME + " or " + GAME_SELECTED_FILENAME + " Error: " + e.getMessage());
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        //load game list and selected game index
        File filesDir = getFilesDir();
        Gson gson = new Gson();

        try {
            File file = new File(filesDir,GAME_LIST_FILENAME);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String jsonGameList = reader.readLine();
            Type collectionType = new TypeToken<ArrayList<Game>>() {}.getType();
            List<Game> games = gson.fromJson(jsonGameList, collectionType);
            reader.close();

            file = new File(filesDir,GAME_SELECTED_FILENAME);
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            int indexGameSelected = Integer.parseInt(reader.readLine());
            mGameListFragment.setIndexSelectedGame(indexGameSelected);
            if (games != null && mGameFragment.getGame() != null) {
                games.set(indexGameSelected, mGameFragment.getGame());
            }
            if (games != null) {
                mGameListFragment.setGames(games);
            }
            else {
                mGameListFragment.setGames(new ArrayList<Game>());
            }
           // mGameFragment.setGame(games.get(indexGameSelected));
            reader.close();
        }
        catch (Exception e) {
            Log.e("Error reading file on resume","Error reading one of the files " + GAME_LIST_FILENAME + " or " + GAME_SELECTED_FILENAME + " Error: " + e.getMessage());
        }
    }
}
