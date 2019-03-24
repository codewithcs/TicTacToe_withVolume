package com.example.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cpugame extends AppCompatActivity {


    private int game_difficulty = 0;

    public int getGame_difficulty() {
        return game_difficulty;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.level_selection_screen);

        button_to_cpu_game();
    }

    private void button_to_cpu_game(){

        Button easy =  (Button)(findViewById(R.id.btnEasy));
        Button medium = (Button)(findViewById(R.id.btnNormal));
        Button hard = (Button)(findViewById(R.id.btnHard));
        Button impossible = (Button)(findViewById(R.id.btnImpossible));


        easy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                game_difficulty = 0;
                Intent intent = new Intent(getBaseContext(), Cpugamestart.class);
                intent.putExtra("gameid",game_difficulty);
                startActivity(intent);
            }
        });
        medium.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                game_difficulty = 1;
                Intent intent = new Intent(getBaseContext(), Cpugamestart.class);
                intent.putExtra("gameid",game_difficulty);
                startActivity(intent);
            }
        });
        hard.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                game_difficulty = 3;
                Intent intent = new Intent(getBaseContext(), Cpugamestart.class);
                intent.putExtra("gameid",game_difficulty);
                startActivity(intent);
            }
        });
        impossible.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                game_difficulty = 5;
                Intent intent = new Intent(getBaseContext(), Cpugamestart.class);
                intent.putExtra("gameid",game_difficulty);
                startActivity(intent);
            }
        });
    }
}
