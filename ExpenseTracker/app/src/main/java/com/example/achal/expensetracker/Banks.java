package com.example.achal.expensetracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Banks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks);
    }

    public void goToRBC(View view) {
        openURL("https://www.rbcroyalbank.com/ways-to-bank/online-banking/index.html");
    }

    public void goToTD(View view) {
        openURL("https://www.td.com/ca/en/personal-banking/");
    }

    public void goToScotia(View view) {
        openURL("https://www.scotiaonline.scotiabank.com/online/authentication/authentication.bns");
    }

    public void openURL (String uri) {
        Uri url = Uri.parse(uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, url);
        startActivity(intent);
    }
}
