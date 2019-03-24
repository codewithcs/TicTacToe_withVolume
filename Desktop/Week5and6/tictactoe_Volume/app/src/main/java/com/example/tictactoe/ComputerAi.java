package com.example.tictactoe;


public class ComputerAi {

    private int [] score = new int[9];
    private  int Maxlevel;
    private String[] temp_board = new String[9];
    private  int pos_at_zero = 0;

    public ComputerAi(int diff_level) {

        Maxlevel = diff_level;

        for (int a = 0; a < 8; a++) {
            score[a] = 0;
        }
    }

    public void computerplays(String[] Computers_board) {

        for (int a = 0; a < 9; a++) {
            score[a] = 0;
        }

        move(minimax_attempt(Computers_board),Computers_board);
    }

    private int minimax_attempt(String[] board_look) {

        for(int z = 0; z < 9; z++) {
            temp_board[z] = board_look[z];
        }

        minimax_checker(temp_board,"O",0);

        int temp_pos = 0,temp_score = -10000000;

        for (int a = 0; a < 9; a++) {
            if(score[a] >= temp_score && board_look[a] != "X" && board_look[a] != "O") {

                temp_pos = a;
                temp_score = score[a];
            }
        }
        return temp_pos;

    }

    private void minimax_checker(String board_look[],String turn , int level) {

        for (int a = 0; a < 9; a++) {
            if(board_look[a]!="X" && board_look[a]!="O") {

                board_look[a] = turn;

                if(level == 0) {
                    pos_at_zero = a;
                }

                if(checkwin(board_look,turn)) {
                    if(turn == "X" ) {
                        score[pos_at_zero] = score[pos_at_zero] - 10 ;
                        if(level == 1) {
                            score[pos_at_zero] = score[pos_at_zero] - 1000 ;
                        }
                    }
                    else {
                        score[pos_at_zero] = score[pos_at_zero] + 10;
                        if(level == 0) {
                            score[pos_at_zero] = score[pos_at_zero] + 1000 ;
                        }

                    }
                }else if(level <= Maxlevel){
                    if(turn == "X"){
                        minimax_checker(board_look,"O" , (level + 1));
                    }
                    else {
                        minimax_checker(board_look,"X" , (level + 1));
                    }
                }
                board_look[a] = String.valueOf(a+1);
            }
        }
    }

    private void move(int position,String[] board) {
        board[position] = "O";
    }

    private boolean checkwin(String[] board,String turn_counter) {

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
                return true;
            }
        }

        return false;
    }
}



