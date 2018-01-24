package com.example.strinh2.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class DecimalToHex extends AppCompatActivity {


    TextView question, scoreText, answerText;;
    EditText unsignedText, signedText;
    Button returnButton, nextButton, checkButton;
    RadioButton radio1, radio2, radio3;
    Spinner spin1, spin2;

    int score, questionType, currValue, numQuestions;
    Random randomizer;
    boolean smallSelected, largeSelected, answerSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decimal_to_hex);

        //Get parameters passed in from main activity
        Intent intent = getIntent();
        score = intent.getIntExtra("scoreInt", 0);
        numQuestions= intent.getIntExtra("numQuestions", 0);
        questionType = intent.getIntExtra("type", 0);

        question =  (TextView) findViewById(R.id.question);
        scoreText =  (TextView) findViewById(R.id.scoreText);
        answerText =  (TextView) findViewById(R.id.answerText);
        returnButton =  (Button) findViewById(R.id.prevButton);
        nextButton =  (Button) findViewById(R.id.nextButton);
        checkButton =  (Button) findViewById(R.id.checkAnswersButton);
        radio1 = (RadioButton) findViewById(R.id.radioButton1);
        radio2 = (RadioButton) findViewById(R.id.radioButton2);
        radio3 =  (RadioButton) findViewById(R.id.radioButton3);
        spin1 = (Spinner) findViewById(R.id.spinner1);
        spin2 = (Spinner) findViewById(R.id.spinner2);

        //Randomize the question
        randomizer = new Random();
        smallSelected = false;
        largeSelected = false;
        answerSelected = false;
        resetQuestion();
    }

    public void resetScreen(View view){
        resetQuestion();
    }

    public void onRadioClicked(View view){
        boolean on = ((RadioButton) view).isChecked();
        int current = view.getId();

        switch(current) {
            case (R.id.radioButton1):
                if (on) {
                    answerSelected = true;
                    smallSelected = false;
                    largeSelected = false;
                }
                break;
            case (R.id.radioButton2):
                if (on) {
                    answerSelected = false;
                    smallSelected = true;
                    largeSelected = false;

                }
                break;
            case (R.id.radioButton3):
                if (on) {
                    answerSelected = false;
                    smallSelected = false;
                    largeSelected = true;
                }

        }
    }

    public void checkAnswers(View view){
        //Reveal everything
        nextButton.setVisibility(View.VISIBLE);
        returnButton.setVisibility(View.VISIBLE);


        //First determine the question type
        if(questionType == 0) {  //Unsigned conversions
            if(currValue < 0){ //If the currValue is negative
                if(smallSelected == true){
                    answerText.setText("Congratulations, that was correct!");
                    answerText.setVisibility(View.VISIBLE);
                    score++;
                    scoreText.setText("Score: " + score + "/" + numQuestions);
                }
                else{
                    answerText.setText("The correct answer is that the value is too small");
                    answerText.setVisibility(View.VISIBLE);
                }
            }
            else if(currValue > 256){  //If the currValue is too large
                if(largeSelected == true){
                    answerText.setText("Congratulations, that was correct!");
                    answerText.setVisibility(View.VISIBLE);
                    score++;
                    scoreText.setText("Score: " + score + "/" + numQuestions);
                }
                else{
                    answerText.setText("The correct answer is that the value is too large");
                    answerText.setVisibility(View.VISIBLE);
                }
            }
            else{  //If the currValue is in range

                String str1 = spin1.getSelectedItem().toString();
                String str2 = spin2.getSelectedItem().toString();
                String input = str1+str2;

                int givenAnswer = Integer.valueOf(input, 16);
                String correctHex = Integer.toHexString(currValue);
                int length = correctHex.length();
                correctHex = correctHex.substring(length-2); //Compensate for the appended values in java's toHexString method.
                correctHex = correctHex.toLowerCase();
                input = input.toLowerCase();
                if(givenAnswer == currValue){
                    answerText.setText("Congratulations, that was correct!");
                    answerText.setVisibility(View.VISIBLE);
                    score++;
                    scoreText.setText("Score: " + score + "/" + numQuestions);
                }
                else{
                    answerText.setText("The correct answer is " + correctHex);
                    answerText.setVisibility(View.VISIBLE);
                }
            }
            numQuestions++;
            scoreText.setText("Score: " + score + "/" + numQuestions);
        }
        else if(questionType == 1){  //Signed conversions
            if(currValue < -127){ //If the currValue is less than the minimum 8-bit signed value
                if(smallSelected == true){
                    answerText.setText("Congratulations, that was correct!");
                    answerText.setVisibility(View.VISIBLE);
                    score++;
                    scoreText.setText("Score: " + score + "/" + numQuestions);
                }
                else{
                    answerText.setText("The correct answer is that the value is too small");
                    answerText.setVisibility(View.VISIBLE);
                }
            }
            else if(currValue > 127){  //If the currValue is larger than Tmax
                if(largeSelected == true){
                    answerText.setText("Congratulations, that was correct!");
                    answerText.setVisibility(View.VISIBLE);
                    score++;
                    scoreText.setText("Score: " + score + "/" + numQuestions);
                }
                else{
                    answerText.setText("The correct answer is that the value is too large");
                    answerText.setVisibility(View.VISIBLE);
                }
            }
            else{  //If the currValue is in range, do the same thing as with unsigned

                String str1 = spin1.getSelectedItem().toString();
                String str2 = spin2.getSelectedItem().toString();
                String input = str1+str2;

                int givenAnswer = Integer.valueOf(input, 16);
                String correctHex = Integer.toHexString(currValue);
                int length = correctHex.length();
                correctHex = correctHex.substring(length-2); //Compensate for the appended values in java's toHexString method.
                correctHex = correctHex.toLowerCase();
                input = input.toLowerCase();
                if(input.equals(correctHex)){
                    answerText.setText("Congratulations, that was correct!");
                    answerText.setVisibility(View.VISIBLE);
                    score++;
                }
                else{
                    answerText.setText("The correct answer is " + correctHex);
                    answerText.setVisibility(View.VISIBLE);
                }
            }
            numQuestions++;
            scoreText.setText("Score: " + score + "/" + numQuestions);
        }
        else{
            Toast.makeText(getApplicationContext(),"An Error occurred while determining the questiontype",Toast.LENGTH_SHORT).show();
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
        String str = "Something went Wrong with th questionType randomizer ";

        int r = randomizer.nextInt(400+400) - 400; //We should generate numbers out of range

        if(questionType == 0){ //This is an unsigned conversion
            //r = randomizer.nextInt(257); //Get a random number from 0 - 256;
            str = "In unsigned, what is the 8-bit hex conversion for " + r;
        }
        else if(questionType == 1){  //This is a signed conversion
            //r = randomizer.nextInt(128+128)-128; //Get a random number from -127 - 127;
            str = "In two's complement, what is the 8-bit hex value for " + r;
        }
        //Toast.makeText(getApplicationContext(),"The Question should be: " + str,Toast.LENGTH_SHORT).show();

        currValue = r;
        question.setText(str);
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
