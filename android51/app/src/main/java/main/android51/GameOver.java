package main.android51;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import chess.Grid;

public class GameOver extends Activity {
    public static final String GAME_NAME = "GAME_NAME";
    public static final String SAVED_GAME = "SAVED_GAME";

    TextView winner;
    EditText game_name;
    ArrayList<Grid.Space[]> moves;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_activity);

        Intent mainActivity = getIntent();

        winner = findViewById(R.id.winner_text);
        winner.setText(mainActivity.getStringExtra(ChessGame.WINNER));

        try {
            moves = (ArrayList<Grid.Space[]>) mainActivity.getSerializableExtra(ChessGame.GAME_MOVES);
        }
        catch (Exception e) {

        }

        game_name = findViewById(R.id.recorded_game_name);
    }

    public void add_game(View view) {
        String name = game_name.getEditableText().toString();
        if (moves == null || game_name.equals("")) {
            return;
        }

        Intent home_screen = new Intent(this, HomeScreen.class);
        home_screen.putExtra(GAME_NAME, name);
        home_screen.putExtra(SAVED_GAME, moves);
        startActivity(home_screen);
    }

    public void cancel(View view) {
        Intent home_screen = new Intent(this, HomeScreen.class);
        startActivity(home_screen);
    }
}