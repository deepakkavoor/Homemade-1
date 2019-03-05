package com.example.student.homemade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditConsumerDetails extends AppCompatActivity {

    EditText editName,editAddress,editcontact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_consumer_details);
    }
}
