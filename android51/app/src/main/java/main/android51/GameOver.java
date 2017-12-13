package main.android51;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class GameOver extends Activity {
    TextView winner;
    EditText game_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_activity);

        Intent mainActivity = getIntent();

        winner = findViewById(R.id.winner_text);
        winner.setText(mainActivity.getStringExtra(ChessGame.WINNER));

        game_name = findViewById(R.id.recorded_game_name);
    }

    public void add_game(View view) {

    }

    public void cancel(View view) {
        Intent home_screen = new Intent(this, HomeScreen.class);
        startActivity(home_screen);
    }
}