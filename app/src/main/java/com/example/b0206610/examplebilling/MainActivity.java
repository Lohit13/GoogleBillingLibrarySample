package com.example.b0206610.examplebilling;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.example.b0206610.examplebilling.com.example.b0206610.examplebilling.billing.BillingConstants;
import com.example.b0206610.examplebilling.com.example.b0206610.examplebilling.billing.BillingManager;

public class MainActivity extends AppCompatActivity {

    private Button consumeBtn, purchaseBtn, purchaseBtnOne, purchaseBtnSub;
    private TextView TbuyCount, Tonetimebuy, Tsubscription;

    private BillingManager mBillingManager;

    private Controller mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        consumeBtn = findViewById(R.id.consumeBtn);
        purchaseBtn = findViewById(R.id.purchaseBtn);
        purchaseBtnOne = findViewById(R.id.purchaseBtnOne);
        purchaseBtnSub = findViewById(R.id.purchaseBtnSub);
        TbuyCount = findViewById(R.id.buyCount);
        Tonetimebuy = findViewById(R.id.oneTimeBuy);
        Tsubscription = findViewById(R.id.subscription);

        consumeBtn.setEnabled(false);

        mController = new Controller(this);

        // Create and initialize BillingManager which talks to BillingLibrary
        mBillingManager = new BillingManager(this, mController.getUpdateListener());

        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchaseBtnClicked();
            }
        });

        purchaseBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add func here
            }
        });

        purchaseBtnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add func here
            }
        });

        // Refresh UI for the first time
        showRefreshedUi();

    }

    public BillingManager getBillingManager() {
        return mBillingManager;
    }

    public void consumeBtnClicked(View view){
        if (mController.getMultiple_buy_qty() > 0){
            mController.useMultipleBuy();
        }
    }

    // Start purchase flow
    public void purchaseBtnClicked(){
        Log.d("CUSTOM", "ONEONEONE");
        mBillingManager.initiatePurchaseFlow(BillingConstants.MULTIPLE_BUY, null, BillingClient.SkuType.INAPP);
        Log.d("CUSTOM", "TWOTWOTWO");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showRefreshedUi() {
        TbuyCount.setText(String.valueOf(mController.getMultiple_buy_qty()));

        if (mController.getOne_time_buy()){
            Tonetimebuy.setText("true");
        }
        else{
            Tonetimebuy.setText("false");
        }

        if (mController.getSubscription()){
            Tsubscription.setText("true");
        }
        else{
            Tsubscription.setText("false");
        }

    }

}
