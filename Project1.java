package com.example.strinh2.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import static android.R.id.input;

public class Project1 extends AppCompatActivity  {

    public final int ACTIVITY_RESULT = 1;

    Spinner converterSpinner;
    Spinner bitSpinner;
    TextView scoreView;

    int score;
    int numQuestions;
    Random randomizer;

    /**
     *  Create the first activity and set up it's variables and widgets.
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project1);

        //Generate the string array to pass into the first spinner.
        //String[] values = new String[] {"Hex to Decimal", "Decimal to Unsigned Hex", "Decimal to Signed Hex"};

        //Set up the first spinner
        converterSpinner = (Spinner) findViewById(R.id.converter);
        bitSpinner = (Spinner) findViewById(R.id.bits);
        scoreView = (TextView) findViewById(R.id.scoreText);
        score = 0;
        numQuestions = 0;
        randomizer = new Random();
    }

    /** Called when the user selects the go button */
    public void goToQuestion(View view) {
        Toast.makeText(getApplicationContext(),"button pressed",Toast.LENGTH_SHORT).show();
        String questionString = converterSpinner.getSelectedItem().toString();

        //intent.putExtra("converterString", questionString);

        //int randomType = randomizer.nextInt(1);

        Intent intent;
        //Send the user to different activities depending on which question type they chose
        if(questionString.equals("Hex to Decimal")){
            intent = new Intent(this, QuestionActivity.class);
        }
        else if(questionString.equals("Decimal to Unsigned Hex")){
            intent = new Intent(this, DecimalToHex.class);
            intent.putExtra("type", 0);     //0 for unsigned
        }
        else if(questionString.equals("Decimal to Signed Hex")){
            intent = new Intent(this, DecimalToHex.class);
            intent.putExtra("type", 1);   //1 for signed
        }
        else{
            Toast.makeText(getApplicationContext(),"The option doesn't match any of the dropdown menu items",Toast.LENGTH_SHORT).show();
            intent = new Intent(this, QuestionActivity.class);
        }
        intent.putExtra("scoreInt", score);
        intent.putExtra("numQuestions", numQuestions);

        startActivityForResult(intent, ACTIVITY_RESULT);

    }

    /** Gets called when the application returns to the main screen **/
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        //If we returned successfully, update the score
        if (requestCode == ACTIVITY_RESULT) {
            //String address = data.getExtras().getString(UpdateActivity.RETVAL);

            //Update the tip values
            Intent intent = data;
            score = intent.getIntExtra("scoreInt", 0);
            numQuestions = intent.getIntExtra("numQuestions", numQuestions);
            scoreView.setText("Score: " + score + "/" + numQuestions);


        }

    }
}
