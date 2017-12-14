package main.android51;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import chess.Grid;

/**
 * Created by John Freese on 12/13/2017.
 */

public class PlayRecorded extends Activity {
    public static final String RECORDED_GAMES = "RECORDED_GAMES";

    ArrayList<HomeScreen.Game> games;
    ArrayList<Grid.Space[]> moves;
    int count;
    char current_player;
    GridLayout gl;
    ImageView[][] views;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recorded_game_activity);

        Grid.init();

        gl = findViewById(R.id.r_g_chess);
        ArrayList<ImageView> iview = new ArrayList<>();
        for (int i = 0; i < gl.getChildCount(); i++) {
            if (gl.getChildAt(i) instanceof  ImageView) {
                iview.add((ImageView) gl.getChildAt(i));
            }
        }

        views = new ImageView[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                views[i][j] = iview.get(8*i + j);
            }
        }

        Intent recordedList = getIntent();
        moves = (ArrayList<Grid.Space[]>) recordedList.getSerializableExtra(RecordedList.GAME);
        games = (ArrayList<HomeScreen.Game>) recordedList.getSerializableExtra(RecordedList.GAMES);
        count = 0;
        current_player = 'w';
    }

    public void next_move_clicked(View v) {
        if (count >= moves.size()) {
            Toast.makeText(getApplicationContext(), "No More Moves.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            move_piece(moves.get(count));
            if (current_player == 'w') {
                current_player = 'b';
            }
            else {
                current_player = 'w';
            }
            count++;
        }
    }

    private ImageView mapSpaceToImage(int row, int column) {
//        Log.i("INFO", "row: " + row + " column: " + column +
//                "\n8- " + (8-row) + " c-97 " + (column-97));
        return views[8 - row][column - 97];
    }

    public void quit_clicked(View v) {
        Intent recorded_games_activity = new Intent(this, RecordedList.class);
        recorded_games_activity.putExtra(RECORDED_GAMES, games);
        startActivity(recorded_games_activity);
    }

    void move_piece(Grid.Space[] move) {
        Grid.Space start = move[0];
        Grid.Space destination = move[1];

        mapSpaceToImage(destination.row, destination.column).setImageDrawable(mapSpaceToImage(start.row, start.column).getDrawable());
        mapSpaceToImage(start.row, start.column).setImageDrawable(null);

        Grid.movePiece(start, destination, current_player,false);
    }
}
