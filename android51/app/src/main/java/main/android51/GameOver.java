package main.android51;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import chess.*;

public class GameOver extends Activity {
    
	public final static String EXTRA_RECORDED = "EXTRA_RECORDED";

    RecordedGame thisGame;

    Button saveB, homeB;
	TextView winner;
    EditText game_name;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_activity);
		
		final Intent homeScreen =  new Intent(this, HomeScreen.class);
        Intent mainActivity = getIntent();
		
		final String winner = mainGame.getStringExtra(MainGame.EXTRA_WINNER);
        thisGame = (RecordedGame)mainGame.getSerializableExtra(MainGame.EXTRA_RECORDED);

        winner = (TextView)findViewById(R.id.winner_text);
        winner.setText(mainActivity.getStringExtra(ChessGame.WINNER));

        game_name = findViewById(R.id.recorded_game_name);
		
		saveB = (Button)findViewById(R.id.saveB);
        saveB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(gameName.getText().toString().equals("")){
                    thisGame.setName("Untitled");
                }else{
                    thisGame.setName(gameName.getText().toString());
                }
                try {homeScreen.removeExtra(EXTRA_RECORDED);
                }catch(Exception e){}
                homeScreen.putExtra(EXTRA_RECORDED, thisGame);
                startActivity(homeScreen);
            }
        });
		
        homeB = (Button)findViewById(R.id.homeB);
        homeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeScreen);
            }
        });	
    }

    public void add_game(View view) {
    }

    public void cancel(View view) {
        Intent home_screen = new Intent(this, HomeScreen.class);
        startActivity(home_screen);
    }
}