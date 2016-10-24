package zef.andrade.cs4530.mvcbattleship;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zandrade on 10/12/2016.
 */
public class TransitionScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int winnerPlayerID = -1;
        int nextTurnPlayerID = -1;

        if (getIntent().hasExtra(MainActivity.NEXT_TURN_PLAYER_ID)) {
            nextTurnPlayerID = getIntent().getExtras().getInt(MainActivity.NEXT_TURN_PLAYER_ID);
        }
        else {
            winnerPlayerID = getIntent().getExtras().getInt(MainActivity.WINNER_PLAYER_ID);
        }
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView infoText = new TextView(this);
        if (nextTurnPlayerID > -1) {
            infoText.setText("It is now player " + (nextTurnPlayerID + 1) + "'s turn");
            infoText.setBackgroundColor(Color.GREEN);
            infoText.setTextSize(20);
        }
        else {
            infoText.setText("Player " + (winnerPlayerID + 1) + " won the game");
            infoText.setBackgroundColor(Color.RED);
            infoText.setTextSize(50);
        }

        infoText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        rootLayout.addView(infoText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 90));

        Button okButton = new Button(this);
        okButton.setText("Ok");
        rootLayout.addView(okButton,  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 10));
        okButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
        });
      setContentView(rootLayout);
    }
}
