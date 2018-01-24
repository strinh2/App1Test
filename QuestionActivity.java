package com.example.strinh2.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {



    TextView question, scoreText, answerText;;
    EditText unsignedText, signedText;
    Button returnButton, nextButton, checkButton;

    int score, numQuestions;
    String currValue;
    Random randomizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Get parameters passed in from main activity
        Intent intent = getIntent();
        score = intent.getIntExtra("scoreInt", 0);
        numQuestions = intent.getIntExtra("numQuestions", 0);

        question =  (TextView) findViewById(R.id.question);
        scoreText =  (TextView) findViewById(R.id.scoreText);
        answerText =  (TextView) findViewById(R.id.answerText);
        returnButton =  (Button) findViewById(R.id.prevButton);
        nextButton =  (Button) findViewById(R.id.nextButton);
        checkButton =  (Button) findViewById(R.id.checkAnswersButton);
        unsignedText = (EditText) findViewById(R.id.unsignedAnswer);
        signedText = (EditText) findViewById(R.id.signedAnswer);


        //Randomize the question
        randomizer = new Random();
        resetQuestion();

    }

    public void resetScreen(View view){
        resetQuestion();
    }

    public void checkAnswers(View view){

        try {
            //Reveal everything

            nextButton.setVisibility(View.VISIBLE);
            returnButton.setVisibility(View.VISIBLE);

            //Calculate answers and check to see if they're the right ones.
            String signedString = signedText.getText().toString();
            String unsignedString = unsignedText.getText().toString();

            int signedInput = Integer.parseInt(signedString);
            int unsignedInput = Integer.parseInt(unsignedString);
            int currAnswerU = Integer.parseInt(currValue, 16);
            int currAnswerS = currAnswerU;
            if (currAnswerU > 127) {  //Calculate the signed value if the answer is negative
                currAnswerS = currAnswerU - 256;
            }
            if (unsignedInput == currAnswerU && signedInput == currAnswerS) {
                answerText.setText("Congratulations, that was correct!");
                answerText.setVisibility(View.VISIBLE);
                score++;
                scoreText.setText("Score: " + score + "/" + numQuestions);

            } else {  //If the user got the answer wrong
                answerText.setText("The correct answer is for signed is: " + currAnswerS + "\n" + "The correct answer is for unsigned is: " + currAnswerU);
                answerText.setVisibility(View.VISIBLE);
            }
            numQuestions++;
            scoreText.setText("Score: " + score + "/" + numQuestions);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"ERROR: This is not a valid numerical input! Try again",Toast.LENGTH_LONG).show();
        }
    }

    public void returnToMain(View view) {
        Toast.makeText(getApplicationContext(),"Going to main screen",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.putExtra("scoreInt", score);
        intent.putExtra("numQuestions", numQuestions);
        //Set result and finish the activity
        setResult(QuestionActivity.RESULT_OK, intent);
        finish();
    }

    //Don't start a new activity when asking for a new similar question. Just reset the fields and update the score
    public void resetQuestion(){
        int randomNum1 = randomizer.nextInt(16); //Get a random number from 0 - 15;
        int randomNum2 = randomizer.nextInt(16);
        String str1 = "";
        String str2 = "";

        if(randomNum1 == 10){
            str1 = "A";
        }
        else if(randomNum1 == 11){
            str1 = "B";
        }
        else if(randomNum1 == 12){
            str1 = "C";
        }
        else if(randomNum1 == 13){
            str1 = "D";
        }
        else if(randomNum1 == 14){
            str1 = "E";
        }
        else if(randomNum1 == 15){
            str1 = "F";
        }
        else{
            str1 += randomNum1;
        }

        if(randomNum2 == 10){
            str2 = "A";
        }
        else if(randomNum2 == 11){
            str2 = "B";
        }
        else if(randomNum2 == 12){
            str2 = "C";
        }
        else if(randomNum2 == 13){
            str2 = "D";
        }
        else if(randomNum2 == 14){
            str2 = "E";
        }
        else if(randomNum2 == 15){
            str2 = "F";
        }
        else{
            str2 += randomNum2;
        }

        currValue = str1+str2;  //Reset currValue completely, but keep it separate from the text so we can check our answers.
        question.setText("0x" + currValue);
        signedText.setText("");
        unsignedText.setText("");
        answerText.setText("");
        scoreText.setText("Score: " + score + "/" + numQuestions);

        //Make sure the appropriate widgets are invisible in the case of a refresh.
        answerText.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
        returnButton.setVisibility(View.INVISIBLE);

    }

    public void onBackPressed() {
        //Pass the values inputted back to the main state
        Toast.makeText(getApplicationContext(),"onBackPressed, returning to main...",Toast.LENGTH_SHORT).show();


        Intent intent = new Intent();
        intent.putExtra("scoreInt", score);
        intent.putExtra("numQuestions", numQuestions);


        //Set result and finish the activity
        setResult(QuestionActivity.RESULT_OK, intent);
        finish();

    }
}
