package com.example.student.homemade;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartPage extends AppCompatActivity {

    private TextView welcomeText;
    private TextView descriptionText;
    private Typeface myFont;

    private Button buttonBuyer;
    private Button buttonSeller;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/PlayfairDisplay_Black.ttf");
        welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setTypeface(myFont);
        descriptionText = findViewById(R.id.description_text);


        buttonBuyer = findViewById(R.id.buyer_button);
        buttonBuyer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSignUpActivity_Buyer();
            }
        });

        buttonSeller = findViewById(R.id.seller_button);
        buttonSeller.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSignUpActivity_Seller();
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }

    public void openSignUpActivity_Buyer() {
        Intent intent = new Intent(this, SignUpBuyer.class);
        startActivity(intent);
    }

    public void openSignUpActivity_Seller() {
        Intent intent = new Intent(this, SignUpSeller.class);
        startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
