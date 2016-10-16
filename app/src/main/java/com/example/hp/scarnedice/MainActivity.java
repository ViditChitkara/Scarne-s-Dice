package com.example.hp.scarnedice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private static int playerOverallScore=0;
    private static int playerTurnScore=0;
    private static int computerOverallScore=0;
    private static int computerTurnScore=0;
    private ImageView imageView;
    private Button holdButton,rollButton;
    protected Button resetButton;
    private TextView overAllScore,turnScore,turn;
    private final int timer=1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView=(ImageView)findViewById(R.id.imageView);
        holdButton=(Button)findViewById(R.id.holdButton);
        rollButton=(Button)findViewById(R.id.rollButton);
        resetButton=(Button)findViewById(R.id.resetButton);
        overAllScore=(TextView) findViewById(R.id.overAllTotal);
        turnScore=(TextView) findViewById(R.id.turnTotal);
        turn=(TextView) findViewById(R.id.userTurn);
    }
    public void onRoll(View view)
    {
        int diceRoll=getRandomDiceRoll();
        changeImage(diceRoll);
        controlPlayerTurn(diceRoll);
    }
    public void controlPlayerTurn(int diceRoll)
    {
        if(diceRoll==1)
        {
            holdButton.setEnabled(false);
            rollButton.setEnabled(false);
            Toast.makeText(this,"You got a 1!",Toast.LENGTH_SHORT).show();
           Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeToComputerTurn();
                }
            },timer);

        }
        else {
            playerTurnScore+=diceRoll;
            displayTurnTotal(playerTurnScore);
        }
    }
    public void displayTurnTotal(int score)
    {
        turnScore.setText("TURN SCORE: "+score);
    }
    public void changeToComputerTurn()
    {
        holdButton.setEnabled(false);
        rollButton.setEnabled(false);
        if(playerOverallScore>=100)
        {
            holdButton.setEnabled(false);
            rollButton.setEnabled(false);
            Toast.makeText(this,"YOU WON",Toast.LENGTH_SHORT).show();
        }
        else
        {
            playerTurnScore=0;
            displayTurnTotal(computerTurnScore);
            turn.setText("COMPUTER'S TURN");

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
                }
            },timer);
        }
    }
    public void computerTurn()
    {
        int score=getRandomDiceRoll();
            turn.setText("COMPUTER ROLLS");
            computerTurnScore+=score;
        displayTurnTotal(computerTurnScore);
        changeImage(score);
        if(score==1)
        {
            changeToPlayerTurn();
            Toast.makeText(this,"Computer got a 1!",Toast.LENGTH_SHORT).show();
        }
        if(score!=1 && computerTurnScore<=10)
        {
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
                }
            },timer);
        }
        else if(computerTurnScore>10)
        {
            computerOverallScore+=computerTurnScore;

            turn.setText("COMPUTER HOLDS");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    overAllScore.setText("Your score: " +playerOverallScore+" Computer score: "+computerOverallScore);
                    changeToPlayerTurn();
                }
            },timer);

        }

    }

    public void changeToPlayerTurn()
    {

        if(computerOverallScore>=100)
        {
            Toast.makeText(this,"COMPUTER WON",Toast.LENGTH_SHORT).show();
        }
        else
        {
            holdButton.setEnabled(true);
            rollButton.setEnabled(true);
            computerTurnScore=0;
            displayTurnTotal(playerTurnScore);
            turn.setText("PLAYER TURN");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public int getRandomDiceRoll()
    {
        Random random=new Random();
        int num=random.nextInt(6);
        return num+1;
    }

    public void changeImage(int diceRoll)
    {
        switch (diceRoll)
        {
            case 1:
              imageView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.dice5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.dice6);
                break;
                default:
                    imageView.setImageResource(R.drawable.dice1);
        }
    }
    public void onReset(View view){
        playerOverallScore=0;
        playerTurnScore=0;
        computerOverallScore=0;
        computerTurnScore=0;
        overAllScore.setText("Your score: 0 Computer score: 0");
        turnScore.setText("TURN SCORE: 0");
        turn.setText("PLAYER TURN");
        changeImage(0);
        holdButton.setEnabled(true);
        rollButton.setEnabled(true);
    }
    public void onHold(View view){
        playerOverallScore+=playerTurnScore;
        overAllScore.setText("Your score: " +playerOverallScore+" Computer score: "+computerOverallScore);
        changeToComputerTurn();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
