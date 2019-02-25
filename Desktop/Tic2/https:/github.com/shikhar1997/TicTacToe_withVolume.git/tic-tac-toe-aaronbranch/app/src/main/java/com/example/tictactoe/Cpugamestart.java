package com.example.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Cpugamestart extends AppCompatActivity
{

    //The Main variable name of the screen view
    private Game_View Myscreenview;

    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if (extras != null)
        {
            difficulty = extras.getInt("gameid");
        }

        Myscreenview = new Game_View(this,false,difficulty);

        //Setting what is being displayed on the screen as my Game_View
        setContentView(Myscreenview);
    }
}
