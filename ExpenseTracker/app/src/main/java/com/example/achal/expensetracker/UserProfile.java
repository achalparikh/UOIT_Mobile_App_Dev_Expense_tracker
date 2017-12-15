package com.example.achal.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {
    private String name, email;
    private EditText nameVal, emailVal;
    private TextView navName, navEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        nameVal = findViewById(R.id.fullName);
        emailVal = findViewById(R.id.userEmail);


    }
//Add user info into the Nav Drawer
    public void addInfo(View view) {

        name = nameVal.getText().toString();
        email = emailVal.getText().toString();

        Toast.makeText(this, "Welcome " + name, Toast.LENGTH_LONG).show();
        Intent result = new Intent();
        result.putExtra("name", name);
        result.putExtra("email", email);
        setResult(1);
        finish();
    }
}
