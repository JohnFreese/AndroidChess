package main.android51;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

/**
 * Created by John Freese on 12/9/2017.
 */

public class HomeScreen extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
    }

    public void play_button_click(View v){
        Intent mainActivity = new Intent(this, ChessGame.class);
        startActivity(mainActivity);
    }
}
