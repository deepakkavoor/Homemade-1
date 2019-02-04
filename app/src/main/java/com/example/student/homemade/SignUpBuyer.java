package com.example.student.homemade;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpBuyer extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            //"(?=.*[0-9])" +               //at least 1 digit
            "(?=.*[a-z])" +               //at least 1 lower case letter
            "(?=.*[A-Z])" +               //at least 1 upper case letter
            //"(?=.*[a-zA-Z])" +            //any letter
            "(?=.*[@#$%^&+=])" +          //at least 1 special character
            //"(?=\\S+$)" +                 //no white space
            //".{4,}" +                     //at least 4 characters
            "$");

    public EditText textInputEmail;
    public EditText textInputUsername;
    public EditText textInputPassword;
    private Typeface myFont;
    private TextView headText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_buyer);


        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/PlayfairDisplay_Black.ttf");
        headText = findViewById(R.id.head_text);
        headText.setTypeface(myFont);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getText().toString().trim();

        if(emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        }

        //Given below is a predefined pattern that validates email address; emailInput string should be passed into matcher; returns true if they match
        //Keep the cursor on EMAIL_ADDRESS below and click on "ctrl + b" to get declaration.
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Invalid email address. Pls enter a valid one.");
            return false;
        }

        else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = textInputUsername.getText().toString().trim();

        if(usernameInput.isEmpty()) {
            textInputUsername.setError("Field cannot be empty");
            return false;
        }

        if(usernameInput.length() > 15) {
            textInputUsername.setError("Field cannot have greater than 15 characters");
            return false;
        }

        else {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getText().toString().trim();

        if(passwordInput.isEmpty()) {
            textInputPassword.setError("Field cannot be empty");
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak. Must have atleast one small, one capital letter and one special character.");
            return false;
        }
        else {
            textInputPassword.setError(null);
            return true;
        }
    }


    public void confirmInput(View v) {
        if(!validateEmail() | !validatePassword() |!validateUsername()) {
            return;
        }

        String input = "Email: " + textInputEmail.getText().toString();
        input += "\n";
        input += "Username: " + textInputUsername.getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }


}

