package com.lastminute84.connect3;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int YELLOW_PLAYER = 1;
    private static final int RED_PLAYER = 10;

    private int activePlayer = YELLOW_PLAYER;
    private ImageView[][] counters;
    private int row0, row1, row2, column0, column1, column2, diagonal0, diagonal1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counters = new ImageView[3][3];
        //Get imageViews
        counters[0][0] = (ImageView) findViewById(R.id.img00);
        counters[0][1] = (ImageView) findViewById(R.id.img01);
        counters[0][2] = (ImageView) findViewById(R.id.img02);
        counters[1][0] = (ImageView) findViewById(R.id.img10);
        counters[1][1] = (ImageView) findViewById(R.id.img11);
        counters[1][2] = (ImageView) findViewById(R.id.img12);
        counters[2][0] = (ImageView) findViewById(R.id.img20);
        counters[2][1] = (ImageView) findViewById(R.id.img21);
        counters[2][2] = (ImageView) findViewById(R.id.img22);

    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        //Bring the counter off the screen to the top
        counter.setTranslationY(-1000f);
        //Change source of image to the next player's counter
        if(activePlayer == YELLOW_PLAYER) {
            counter.setImageResource(R.drawable.yellow);
            counter.setTag(YELLOW_PLAYER);
        } else {
            counter.setImageResource(R.drawable.red);
            counter.setTag(RED_PLAYER);
        }
        //Animate back the image to its place
        counter.animate().translationYBy(1000f). setDuration(900);

        checkWinner();

        //Change player
        if(activePlayer == YELLOW_PLAYER) {
            activePlayer = RED_PLAYER;
        } else {
            activePlayer = YELLOW_PLAYER;
        }
    }

    private void checkWinner() {
        boolean redWinner = false;
        boolean yellowWinner = false;
        //Create integers for all the possible winner rows and diagonals
        //diagonal0 goes top-left to bottom-right, diagonal1 goes top-right to bottom-left
        row0 = 0;
        row1 = 0;
        row2 = 0;
        column0 = 0;
        column1 = 0;
        column2 = 0;
        diagonal0= 0;
        diagonal1 = 0;
        int currentCounter;

        //Loop through the matrix
        for(int row = 0; row < counters.length; row++) {
            for(int column = 0; column < counters[row].length; column++) {
                if(counters[row][column].getTag() != null) {
                    Log.d("COUNTER", counters[row][column].getTag().toString());
                    currentCounter = Integer.parseInt(counters[row][column].getTag().toString());
                    if(row == 0) {
                        row0 += currentCounter;
                        if(column == 0) {
                            column0 += currentCounter;
                            diagonal0 += currentCounter;
                        } else if(column == 1) {
                            column1 += currentCounter;
                        } else {
                            column2 += currentCounter;
                            diagonal1 += currentCounter;
                        }
                    } else if(row == 1) {
                        row1 += currentCounter;
                        if(column == 0) {
                            column0 += currentCounter;
                        } else if(column == 1) {
                            column1 += currentCounter;
                            diagonal0 += currentCounter;
                            diagonal1 += currentCounter;
                        } else {
                            column2 += currentCounter;
                        }
                    } else {
                        row2 += currentCounter;
                        if(column == 0) {
                            column0 += currentCounter;
                            diagonal1 += currentCounter;
                        } else if(column == 1) {
                            column1 += currentCounter;
                        } else {
                            column2 += currentCounter;
                            diagonal0 += currentCounter;
                        }
                    }
                }
            }
        }

        Log.d("ROW0", row0 + "");
        Log.d("ROW1", row1 + "");
        Log.d("ROW2", row2 + "");
        Log.d("COL0", column0 + "");
        Log.d("COL1", column1 + "");
        Log.d("COL2", column2 + "");
        Log.d("DIA0", diagonal0 + "");
        Log.d("DIA1", diagonal1 + "");

        if(row0 == 3 || row1 == 3 || row2 == 3 || column0 == 3 || column1 == 3 || column2 == 3 || diagonal0 == 3 || diagonal1 == 3) {
            showGameOverDialog("The winner is the yellow player");
        } else if(row0 == 30 || row1 == 30 || row2 == 30 || column0 == 30 || column1 == 30 || column2 == 30 || diagonal0 == 30 || diagonal1 == 30) {
            showGameOverDialog("The winner is the red player");
        }

    }

    /**
     * Show dialog with winner colour
     * @param msg
     */
    private void showGameOverDialog(String msg) {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
        dialog.setTitle("GAME OVER");
        dialog.setMessage(msg);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
            }
        });
        dialog.show();
    }

    private void reset() {
        counters[0][0].setImageDrawable(null);
        counters[0][0].setTag(null);
        counters[0][1].setImageDrawable(null);
        counters[0][1].setTag(null);
        counters[0][2].setImageDrawable(null);
        counters[0][2].setTag(null);
        counters[1][0].setImageDrawable(null);
        counters[1][0].setTag(null);
        counters[1][1].setImageDrawable(null);
        counters[1][1].setTag(null);
        counters[1][2].setImageDrawable(null);
        counters[1][2].setTag(null);
        counters[2][0].setImageDrawable(null);
        counters[2][0].setTag(null);
        counters[2][1].setImageDrawable(null);
        counters[2][1].setTag(null);
        counters[2][2].setImageDrawable(null);
        counters[2][2].setTag(null);

    }
}
