package com.example.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.media.* ;
import android.content.* ;
import android.view.KeyEvent ;

public class MainActivity extends Activity
{

    private static MainActivity instance;

    final   AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService( Context.AUDIO_SERVICE );


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Making the window take up the whole screen and removing the title from the top of the screen
        getWindow().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN ) ;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        instance = this;

        button_to_next_screen();
        //Myscreenview = new Game_View(this);

        //Setting what is being displayed on the screen as my Game_View
        //setContentView(Myscreenview);

    }

    private void button_to_next_screen()
    {

        Button button_two_player_game =  (Button)(findViewById(R.id.btn2Player));

        Button button_single_player_game = (Button)(findViewById(R.id.btn1Player));

    //    Button Volume = (Button)(findViewById(R.id.btnSettings)) ; remove this if not needed

        button_two_player_game.setOnClickListener(new View.OnClickListener(){

            @Override                               // Anonymous Class
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TwoplayerActivity.class));
            }
        });

        button_single_player_game.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Cpugame.class));
            }
        });

//        Volume.setOnClickListener ( new View.OnClickListener() // create a class Volume separately if required.
//        {
//
//            @Override                               // Anonymous Class
//            public void onClick(View v)
//            {
//                startActivity(new Intent(MainActivity.this,Volume.class));
//            }
//
//        } );




        Button volUp = (Button) findViewById(R.id.upButton);

        volUp.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

//To increase media player volume
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
            }
        });

        Button volDown = (Button) findViewById(R.id.downButton);

        volDown.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

//To decrease media player volume
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            }
        });



    }


    @Override

    public boolean dispatchKeyEvent(KeyEvent event)

    {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        switch (keyCode)
        {

            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN)
                {
                    audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                }
                return true;

                case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN)
                {
                    audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                }
                return true;

            default:
                return super.dispatchKeyEvent(event);

        }

    }




    public static MainActivity getInstance()
    {
        return instance;
    }

    public void return_screen()
    {

        startActivity(new Intent(MainActivity.this,MainActivity.class));

        setContentView(R.layout.activity_main);

    }

}
