package main.android51;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Space;

import java.util.ArrayList;

import chess.Grid;

public class RecordedList extends Activity {
    public static final String GAME = "GAME";
    public static final String GAMES = "GAMES";

    ArrayList<HomeScreen.Game> games;
    private ListView lv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recorded_list_activity);

        lv = findViewById(R.id.game_list);

        try {
            Intent home_screen = getIntent();
            games = (ArrayList<HomeScreen.Game>) home_screen.getSerializableExtra(HomeScreen.RECORDED_GAMES);
            ArrayAdapter<HomeScreen.Game> arrayAdapter = new ArrayAdapter<HomeScreen.Game>(
                    this,
                    android.R.layout.simple_list_item_1,
                    games
            );

            lv.setAdapter(arrayAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startGame(i);
                }
            });
        }
        catch (Exception e) {

        }
    }

    public void startGame(int i) {
        Intent recorded_list_activity = new Intent(this, PlayRecorded.class);
        ArrayList<Grid.Space[]> r_game = games.get(i).moves;
        recorded_list_activity.putExtra(GAME, r_game);
        recorded_list_activity.putExtra(GAMES, games);
        startActivity(recorded_list_activity);
    }
}
