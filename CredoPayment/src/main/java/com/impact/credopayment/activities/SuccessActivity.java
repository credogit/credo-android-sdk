package com.impact.credopayment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.impact.credopayment.R;

import java.util.Locale;

public class SuccessActivity extends AppCompatActivity {

    RelativeLayout successTv, failTV;
    Button redirectBT;
    TextView redirectTv;
    TextView successTextView;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        init();
        loadView();
        runnable.run();

    }
    private void init(){
        successTv = findViewById(R.id.successTV);
        failTV = findViewById(R.id.failTV);
        redirectBT = findViewById(R.id.redirectBT);
        redirectTv = findViewById(R.id.redirectTV);
        successTv = findViewById(R.id.successTV);
        n = 5;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            --n;
            redirectLogic(n);

        }
    };

    private void redirectLogic(int n) {
        if(n == 0){
            startActivity(new Intent(SuccessActivity.this, ThirdPartyPay.class));
            finish();
        }else{
            redirectTv.setText(String.format(Locale.getDefault(), "Redirectiong in %ds...", n));
            new Handler().postDelayed(runnable, 1000);
        }
    }

    private void loadView(){
        switch (getIntent().getStringExtra("status")){
            case "success":
                loadV(true);
                successTextView.setText("Payment\nSuccessful");
                break;
            case "failed":
                loadV(false);
                successTextView.setText("Payment\nFailed");
                break;
            case "network":
                loadV(false);
                successTextView.setText("Payment\nFailed");
                showToast();
        }
    }

    private void showToast() {
        Toast.makeText(getApplicationContext(), "caused by network failure", Toast.LENGTH_SHORT).show();
    }

    private void loadV(boolean b) {
        if(b){
            successTv.setVisibility(View.VISIBLE);
            failTV.setVisibility(View.GONE);
        }else{
            successTv.setVisibility(View.GONE);
            failTV.setVisibility(View.VISIBLE);
        }
    }

    public void redirect(View view) {

    }
}