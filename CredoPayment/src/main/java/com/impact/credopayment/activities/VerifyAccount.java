package com.impact.credopayment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.impact.credopayment.Transactions;
import com.impact.credopayment.R;
import com.impact.credopayment.adapters.CustomAdapter;
import com.impact.credopayment.model.CustomItem;

import java.util.ArrayList;

public class VerifyAccount extends AppCompatActivity {
    Transactions transactions;
    private ArrayList<CustomItem> customItem;
    Spinner paymentOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        init();
    }

    public void init(){
        paymentOptions = findViewById(R.id.paymentOptions);
        customItem = new ArrayList<>();

        customItem.add(new CustomItem("Card"));
        customItem.add(new CustomItem("Bank"));
        customItem.add(new CustomItem("USSD"));

        CustomAdapter adapter = new CustomAdapter(this, customItem);

        paymentOptions.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void verifyAccount(View view) {
        startActivity(new Intent(VerifyAccount.this, SuccessActivity.class));
    }


}