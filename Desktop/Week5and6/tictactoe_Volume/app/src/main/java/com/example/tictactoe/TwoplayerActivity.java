package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TwoplayerActivity extends AppCompatActivity
{

    //The Main variable name of the screen view
    private Game_View Myscreenview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Myscreenview = new Game_View(this,true,0);

        //Setting what is being displayed on the screen as my Game_View
        setContentView(Myscreenview);
    }
}
