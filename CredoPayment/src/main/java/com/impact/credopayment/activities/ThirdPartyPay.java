package com.impact.credopayment.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.impact.credopayment.Api.JSONSchema.*;
import com.impact.credopayment.Transactions;
import com.impact.credopayment.R;
import com.impact.credopayment.adapters.*;
import com.impact.credopayment.model.CustomItem;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class ThirdPartyPay extends AppCompatActivity implements View.OnTouchListener {

    Transactions transactions;
    private ArrayList<CustomItem> customItem, cardTypeList;
    Spinner paymentOptions, cardType;
    EditText cardExpiry, cvv, cardNumber;
    double amount = 3000.00;
    TextView balance, email;
    String orderCurrency = "NGN";
    private Button cardPay;
    private static String transRef = "12345678900" + Calendar.getInstance().getTime().toString();
    private static final String customerEmail = "joseph@nugitech.com";
    private static final String customerName = "Joe Altidore";
    private static final String customerPhone = "09091234567";
    String paymentSlug;

    LinearLayout logoLL;
    ImageView loadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credo_third_party_pay);

        init();
        try {
            transactions = new Transactions("pk_demo-7BckFQAPo5nftWQDABrHi3kb8moGAr.qBgeBfvVGH-d", "sk_demo-Dj1rcY2rxNpmJgYwmhylIy7RpoS7Zy.ygUca22uTg-d");
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            transactions = null;
            e.printStackTrace();
            Log.d("INITIALIZATION ERROR", e.getMessage());
        }

        if (transactions != null){
            initiatePay();
        }

    }

    String cn, em, ey, cv;

    @SuppressLint("DefaultLocale")
    private void init() {
        logoLL = findViewById(R.id.logoLL);
        loadingView = findViewById(R.id.loading);
        cardPay = findViewById(R.id.cardPay);
        cardPay.setText(String.format("Pay %s %.2f", orderCurrency,  amount));
        paymentOptions = findViewById(R.id.paymentOptions);
        cardType = findViewById(R.id.cardType);
        customItem = new ArrayList<>();
        cardTypeList = new ArrayList<>();

        balance = findViewById(R.id.balance);
        email = findViewById(R.id.email);

        email.setText(customerEmail);
        balance.setText(String.format("%s %.2f", orderCurrency,  amount));

        cardTypeList.add(new CustomItem("Visa"));
        cardTypeList.add(new CustomItem("Master Card"));
        cardTypeList.add(new CustomItem("Verve"));

        customItem.add(new CustomItem("Card"));
        customItem.add(new CustomItem("Bank"));
        customItem.add(new CustomItem("USSD"));

        cardNumber = findViewById(R.id.cardNumber);
        cvv = findViewById(R.id.cardCVV);
        cardExpiry = findViewById(R.id.cardExpiry);
        cardExpiry.addTextChangedListener(new TextWatcher() {
            boolean del = false;
            int currentLength = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                currentLength = cardExpiry.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(cardExpiry.length() == 2 && currentLength < cardExpiry.length())
                    cardExpiry.append("/");
                if(cardExpiry.length() == 5){
                    cardExpiry.setFocusable(false);
                    cardNumber.setFocusable(false);
                    cvv.setFocusable(true);
                }
            }
        });

        cardExpiry.setOnTouchListener(this);
        cardNumber.setOnTouchListener(this);



        CustomAdapter adapter = new CustomAdapter(this, customItem);
        CustomAdapterCardType adapter2 = new CustomAdapterCardType(this, cardTypeList);

        cardType.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
        paymentOptions.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void cardPay(View view) {
        verify();
    }

    public void pay(){
        transRef = "";
        do {
            transRef = transRef + String.format("%x", new Random().nextInt(0x10));
        }while(transRef.length() < 25);

        transRef = transRef.replace(" ", "");
        Log.d("TRANsREF", transRef);
        transactions.thirdPartyPay(amount, orderCurrency, cn, em, ey, cv, transRef, customerEmail, customerName, customerPhone, paymentSlug, new Transactions.ThirdPartyCallBack() {
            @Override
            public void onSuccess(ThirdPartySchema schema) {
                if(schema != null && schema.getStatus() != null && schema.getStatus().equalsIgnoreCase("success")){
                    loadingV(false);
                    startActivity(new Intent(ThirdPartyPay.this, SuccessActivity.class).putExtra("status", "success"));
                    finish();
                }else{
                    loadingV(false);
                    startActivity(new Intent(ThirdPartyPay.this, SuccessActivity.class).putExtra("status", "failed"));
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                loadingV(false);
                startActivity(new Intent(ThirdPartyPay.this, SuccessActivity.class).putExtra("status", "network"));
                finish();
            }
        });
    }

    public void verify(){
        if(transactions != null && paymentSlug != null){
            cn = cardNumber.getText().toString();
            if(cardExpiry.length() > 2) {
                em = cardExpiry.getText().toString().split("/")[0];
                ey = cardExpiry.getText().toString().split("/")[1];
            }else{
                Toast.makeText(this, "wrong card expiry format", Toast.LENGTH_SHORT).show();
                return;
            }
            cv = cvv.getText().toString();
            if(cn.isEmpty() || em.isEmpty()||ey.isEmpty() || cv.isEmpty()){
                Toast.makeText(this, "no empty input fields allowed", Toast.LENGTH_SHORT).show();
                return;
            }
            loadingV(true);
            transactions.verifyCardNumber(cn, orderCurrency, paymentSlug, new Transactions.VerifyCardCallBack() {
                @Override
                public void onSuccess(VerifyCardSchema schema) {
                    if(schema != null && schema.getGatewayRecommendation() != null && schema.getGatewayRecommendation().equalsIgnoreCase("PROCEED")){
                        pay();
                    }else{
                        new AlertDialog.Builder(ThirdPartyPay.this).setMessage("Invalid card number. please try again")
                                .setPositiveButton("dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    new AlertDialog.Builder(ThirdPartyPay.this).setMessage("a network error occurred during request. try again")
                            .setPositiveButton("dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
            });
        }else{
            Toast.makeText(this, "initialization error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void initiatePay(){
        loadingV(true);
        Toast.makeText(this, "Initiating payment...", Toast.LENGTH_SHORT).show();
        transactions.initiatePayment(amount, orderCurrency, "https://google.com", "1234567890", "CARD", customerName, customerEmail, customerPhone, new Transactions.InitiatePaymentCallBack(){
            @Override
            public void onSuccess(InitiateSchema schema) {
                if(schema != null && schema.getPaymentSlug() != null && !schema.getPaymentSlug().isEmpty()){
                    paymentSlug=schema.getPaymentSlug();
                    Toast.makeText(ThirdPartyPay.this, "Initiating payment success", Toast.LENGTH_SHORT).show();
                    loadingV(false);
                }else{
                    loadingV(false);
                    Toast.makeText(ThirdPartyPay.this, "Initiating payment failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ThirdPartyPay.this, "oops! a network error terminated request", Toast.LENGTH_SHORT).show();
                loadingV(false);
            }
        });

    }

    private void loadingV(boolean b) {
        if(b){
            loadingView.setVisibility(View.VISIBLE);
            logoLL.setVisibility(View.INVISIBLE);
        }else{
            loadingView.setVisibility(View.GONE);
            logoLL.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (id == R.id.cardExpiry) {
            cardExpiry.setFocusable(true);
            cardExpiry.setFocusableInTouchMode(true);
            cardExpiry.setText("");
        } else if (id == R.id.cardNumber) {
            cardNumber.setFocusable(true);
            cardNumber.setFocusableInTouchMode(true);
        } else if (id == R.id.cardCVV) {
            cvv.setFocusable(true);
            cvv.setFocusableInTouchMode(true);
        }
        return false;
    }
}