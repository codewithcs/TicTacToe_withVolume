package com.example.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupMenu;

public class Game_View extends View
{

    //Background images
    private Bitmap Background;

    //Pause button and stuff for menu

    private Bitmap pause,resume,backtomenu;

    boolean menuopen;


    //X and O and white square that they go on
    private Bitmap Xbitmap,Obitmap,whitesquare;

    //Dimensions of the screen
    private DisplayMetrics myscreen;

    private int screen_height,screen_width;

    //array to keep track of x's and o's on board
    private String []board;

    //array that hols the positions the X's and O's will be printed out on
    private static int []Xboard_places = {50,400,750,50,400,750,50,400,750};

    private static int []Yboard_places = {670,670,670,1020,1020,1020,1370,1370,1370};

    //Players
    private int player;

    //Counter that count the wins of both players
    private int win_player1,win_player2;

    //Paint for end of game text and win counter text
    private Paint redline,blackpaint;

    //Setting the Ai and seeing if its 2 player or v ai
    private ComputerAi MyAi;
    private boolean twoplayer_or_Ai;

    //variable that records if game is won or not
    boolean game_running = true;

    public Game_View(Context context,boolean twoplayer,int ai_diff)
    {
        super(context);

        //Initializing the dimensions of the screen
        myscreen = new DisplayMetrics();

        ( (Activity) getContext() ).getWindowManager().getDefaultDisplay().getMetrics(myscreen);

        screen_height = myscreen.heightPixels;

        screen_width = myscreen.widthPixels;

        //Loading the Images of the background in drawables as bitmaps and resizing the bitmap to fill the screen
        Background = BitmapFactory.decodeResource(getResources(),R.drawable.simplebackground);

        //rescaling the background
        Background = Bitmap.createScaledBitmap(Background,screen_width,screen_height,true);

        //Loading the Images of the white square,X and O in drawables as bitmaps
        Xbitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ximage2);
        Obitmap = BitmapFactory.decodeResource(getResources(),R.drawable.oimage2);
        whitesquare = BitmapFactory.decodeResource(getResources(),R.drawable.whitesquare);

        //rescaling of the white square,X's and O's
        Xbitmap = Bitmap.createScaledBitmap(Xbitmap,300,300,true);
        Obitmap = Bitmap.createScaledBitmap(Obitmap,300,300,true);
        whitesquare = Bitmap.createScaledBitmap(whitesquare,300,300,true);

        //Loading the image of pause icon in drawables as bitmaps ,then rescaling it
        pause = BitmapFactory.decodeResource(getResources(),R.drawable.menubutton);
        pause = Bitmap.createScaledBitmap(pause,100,100,true);

        resume = BitmapFactory.decodeResource(getResources(),R.drawable.resumebutton);
        resume = Bitmap.createScaledBitmap(resume,300,300,true);

        backtomenu = BitmapFactory.decodeResource(getResources(),R.drawable.backtomenubutton);
        backtomenu = Bitmap.createScaledBitmap(backtomenu,500,500,true);

        //Boolean that tells the draw function when to draw the menu on the screen
        menuopen = false;

        //Initializing the end text paint
        redline = new Paint();
        redline.setColor(Color.RED);
        redline.setTextSize(100);

        //Initializing the win counter text paint
        blackpaint = new Paint();
        blackpaint.setColor(Color.WHITE);
        blackpaint.setTextSize(80);
        blackpaint.setStrokeWidth(10);

        //Initializing the board
        board = new String[9];

        for(int i = 0; i < 9; i++)
        {
            board[i] = "";
        }

        //Initializing the player wins
        win_player1 = 0;
        win_player2 = 0;

        //Initializing the whether it is v ai or 2 player
        twoplayer_or_Ai = twoplayer;

        //Initializing the ai if it is single player
        if(!twoplayer_or_Ai){
            MyAi = new ComputerAi(ai_diff);
        }

        //Initializing the board
        player = 1;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        //drawing background on canvas
        canvas.drawBitmap(Background, 0,0,null);

        canvas.drawBitmap(pause, screen_width - 125,25,null);

        //drawing the win counters at the top of the canvas
        if(twoplayer_or_Ai){
            canvas.drawText("Player 1: " + win_player1 ,50,100,blackpaint);
            canvas.drawText("Player 2: " + win_player2 ,550,100,blackpaint);
        }
        else{
            canvas.drawText("Player: " + win_player1 ,50,100,blackpaint);
            canvas.drawText("Computer: " + win_player2 ,500,100,blackpaint);
        }


        //iterates through the board array and draws the necessary changes to the board
        for(int a = 0 ; a < 9; a++)
        {

            canvas.drawBitmap(whitesquare, Xboard_places[a],Yboard_places[a],null);

            if (board[a].equals("X"))
            {
                canvas.drawBitmap(Xbitmap, Xboard_places[a],Yboard_places[a],null);
            }
            if (board[a].equals("O")) {
                canvas.drawBitmap(Obitmap, Xboard_places[a], Yboard_places[a], null);
            }
        }

        //checks win for X's
        //Both check wins called if through but game on hold by changing game_running to false
        if(checkwin(board,"X",canvas))
        {
            game_running = false;

            if(twoplayer_or_Ai){
                canvas.drawText( "Game won by player1",(int)(screen_width/2) - 400,670-200,blackpaint);
            }
            else{
                canvas.drawText( "Game won by player",(int)(screen_width/2) - 400,670-200,blackpaint);
            }

        }

        //checks win for O's and prints out the relavent text depending on whether player 2 is Ai or not
        if(checkwin(board,"O",canvas)){
            game_running = false;

            if(twoplayer_or_Ai){
                canvas.drawText( "Game won by player2",(int)(screen_width/2) - 400,670-200,blackpaint);
            }
            else{
                canvas.drawText("Game won by CPU" ,(int)(screen_width/2) - 350,670-200,blackpaint);
            }

        }

        //if both the check win functions come back false then the check draw function runs
        //if board is full isdraw will be true and game will end
        if(isdraw(board) && game_running ){
            game_running = false;

            canvas.drawText("Game is a draw" ,(int)(screen_width/2) - 350,670-200,blackpaint);
        }

        //if pause button is clicked
        if(menuopen){
            canvas.drawBitmap(resume,(screen_width/2)-140,(screen_height/2),null);
            canvas.drawBitmap(backtomenu,(screen_width/2)-230,(screen_height/2)-400,null);
        }

        //swaps the turn but only if the game is running
        if(game_running && !twoplayer_or_Ai && !menuopen)
        {
            turn_swap();
        }
    }

    //Sets up on click listener for the whole screen
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        //this if function only takes in the down click of the mouse/finger and then sends it to the game
        //mechanics function provided the game is still running if not it will reset the game board
        //so the user can play again
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(game_running){

                //passes the location of the click to the game_mechanics function
                game_mechanics((int)event.getX(),(int)event.getY());
            }
            else{
                resetboard();
            }
        }

        return super.onTouchEvent(event);
    }

    //clears board and reinitialises the Ai and set game running back to true
    //game resumes from the beginning again, also sets starting player back to one
    private void resetboard()
    {

        player = 1;

        for(int i = 0; i < 9; i++){
            board[i] = "";
        }

        this.invalidate();

        if(!twoplayer_or_Ai){
            MyAi = new ComputerAi(5);
        }

        game_running = true;
    }

    private void turn_swap(){
        if(player == 2) {
            MyAi.computerplays(board);
            this.invalidate();
            player = 1;
        }
    }

    //function was taken from github https://gist.github.com/xaviablaza-zz/3844825
    //this is the only function taken from github
    private boolean checkwin(String[] board,String turn_counter,Canvas winningcanvas ) {

        String temp = turn_counter + turn_counter + turn_counter;

        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = board[2] + board[4] + board[6];
                    break;
            }

            if (line.equals(temp)) {

                draw_winning_line(winningcanvas, a);

                if(turn_counter == "X" && game_running){
                    win_player1++;
                }
                if(turn_counter == "O" && game_running){
                    win_player2++;
                }
                return true;
            }
        }

        return false;
    }

    //checks to see if all spaces in the array arent null, if all are filled it ends game
    //does this by iterating through array and checking it
    private boolean isdraw(String[] isdraw_board){
        int space = 0;

        for(int a = 0; a < 9; a++){
            if(isdraw_board[a] == ""){
                space++;
            }
        }

        if(space > 0){
            return false;
        }

        return true;
    }

    //After being passed the location of the click it checks to see if the click was on one of the white
    //squares and if that square is blank,if so places the piece of that player on the square if not nothing happens
    //and the person must choose a different square
    //Also adds to checker to see if player is clicking on menu button

    private void game_mechanics(int x,int y)
    {
        if(x > screen_width - 125 && x < screen_width - 25 && y > 25 && y < 125){
            this.invalidate();
            menuopen = true;
        }
        else if(menuopen && x > (screen_width/2)-140 && x < (screen_width/2)+160 && y > (screen_height/2) && y < (screen_height/2)+300){
            menuopen = false;
            this.invalidate();
        }else if(menuopen && x > (screen_width/2)-230 && x < (screen_width/2)+70 && y >(screen_height/2)-400 && y < (screen_height/2)-100){
            MainActivity.getInstance().return_screen();
        }
        else {
            if (player == 1 && !menuopen) {
                if (x > 50 && x < 350 && y > 670 && y < 970 && board[0] == "") {
                    board[0] = "X";
                    this.invalidate();
                    player = 2;
                }
                if (x > 400 && x < 700 && y > 670 && y < 970 && board[1] == "") {
                    board[1] = "X";
                    this.invalidate();
                    player = 2;
                }
                if (x > 750 && x < 1050 && y > 670 && y < 970 && board[2] == "") {
                    board[2] = "X";
                    this.invalidate();
                    player = 2;
                }
                if (x > 50 && x < 350 && y > 1020 && y < 1320 && board[3] == "") {
                    board[3] = "X";
                    this.invalidate();
                    player = 2;
                }
                if (x > 400 && x < 700 && y > 1020 && y < 1320 && board[4] == "") {
                    board[4] = "X";
                    this.invalidate();
                    player = 2;
                }
                if (x > 750 && x < 1050 && y > 1020 && y < 1320 && board[5] == "") {
                    board[5] = "X";
                    this.invalidate();
                    player = 2;
                }
                if (x > 50 && x < 350 && y > 1370 && y < 1670 && board[6] == "") {
                    board[6] = "X";
                    this.invalidate();
                    player = 2;
                }
                if (x > 400 && x < 700 && y > 1370 && y < 1670 && board[7] == "") {
                    board[7] = "X";
                    this.invalidate();
                    player = 2;
                }
                if (x > 750 && x < 1050 && y > 1370 && y < 1670 && board[8] == "") {
                    board[8] = "X";
                    this.invalidate();
                    player = 2;
                }
            }

            if (player == 2 && !menuopen) {
                if (x > 50 && x < 350 && y > 670 && y < 970 && board[0] == "") {
                    board[0] = "O";
                    this.invalidate();
                    player = 1;
                }
                if (x > 400 && x < 700 && y > 670 && y < 970 && board[1] == "") {
                    board[1] = "O";
                    this.invalidate();
                    player = 1;
                }
                if (x > 750 && x < 1050 && y > 670 && y < 970 && board[2] == "") {
                    board[2] = "O";
                    this.invalidate();
                    player = 1;
                }
                if (x > 50 && x < 350 && y > 1020 && y < 1320 && board[3] == "") {
                    board[3] = "O";
                    this.invalidate();
                    player = 1;
                }
                if (x > 400 && x < 700 && y > 1020 && y < 1320 && board[4] == "") {
                    board[4] = "O";
                    this.invalidate();
                    player = 1;
                }
                if (x > 750 && x < 1050 && y > 1020 && y < 1320 && board[5] == "") {
                    board[5] = "O";
                    this.invalidate();
                    player = 1;
                }
                if (x > 50 && x < 350 && y > 1370 && y < 1670 && board[6] == "") {
                    board[6] = "O";
                    this.invalidate();
                    player = 1;
                }
                if (x > 400 && x < 700 && y > 1370 && y < 1670 && board[7] == "") {
                    board[7] = "O";
                    this.invalidate();
                    player = 1;
                }
                if (x > 750 && x < 1050 && y > 1370 && y < 1670 && board[8] == "") {
                    board[8] = "O";
                    this.invalidate();
                    player = 1;
                }
            }
        }

    }

    //draws a red line all the three squares on where the game was won
    //it is passed a from the function checkwin and it refers to a case
    //this case tells where the line should be drawn
    private void draw_winning_line(Canvas winning_line,int a)
    {

        redline.setStrokeWidth(20);

        if(a == 0){
            winning_line.drawLine(50,670+150,750+300,670+150,redline);
        }
        else if(a ==1 )
        {
            winning_line.drawLine(50,1020+150,750+300,1020+150,redline);
        }
        else if(a ==2 )
        {
            winning_line.drawLine(50,1370+150,750+300,1370+150,redline);
        }
        else if(a ==3 )
        {
            winning_line.drawLine(50+150,670,50+150,1370+300,redline);
        }
        else if(a ==4 )
        {
            winning_line.drawLine(400+150,670,400+150,1370+300,redline);
        }
        else if(a ==5 )
        {
            winning_line.drawLine(750+150,670,750+150,1370+300,redline);
        }
        else if ( a ==6 )
        {
            winning_line.drawLine(50,670,750+300,1370+300,redline);
        }
        else
            {
            winning_line.drawLine(50,1370+300,750+300,670,redline);
        }

        this.invalidate();

    }

}
