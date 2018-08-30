package com.example.b0206610.examplebilling;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.example.b0206610.examplebilling.com.example.b0206610.examplebilling.billing.BillingManager;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;

public class Controller {

    int multiple_buy_qty;
    boolean one_time_buy, subscription;

    private static final int MULTIPLE_BUY_MAX = 5;

    private MainActivity mActivity;

    private final UpdateListener mUpdateListener;

    public Controller(MainActivity activity){
        mUpdateListener = new UpdateListener();
        mActivity = activity;
        loadData();
    }

    public UpdateListener getUpdateListener() {
        return mUpdateListener;
    }

    public int getMultiple_buy_qty(){
        return multiple_buy_qty;
    }

    public boolean getOne_time_buy(){
        return one_time_buy;
    }

    public boolean getSubscription(){
        return subscription;
    }

    public void useMultipleBuy() {
        multiple_buy_qty--;
        saveData();
        Log.d(TAG, "Tank is now: " + multiple_buy_qty);
    }


    /**
     * Handler to billing updates
     */
    private class UpdateListener implements BillingManager.BillingUpdatesListener {
        @Override
        public void onBillingClientSetupFinished() {

        }

        @Override
        public void onConsumeFinished(String token, @BillingClient.BillingResponse int result) {
            Log.d(TAG, "Consumption finished. Purchase token: " + token + ", result: " + result);

            // Note: We know this is the SKU_GAS, because it's the only one we consume, so we don't
            // check if token corresponding to the expected sku was consumed.
            // If you have more than one sku, you probably need to validate that the token matches
            // the SKU you expect.
            // It could be done by maintaining a map (updating it every time you call consumeAsync)
            // of all tokens into SKUs which were scheduled to be consumed and then looking through
            // it here to check which SKU corresponds to a consumed token.
            if (result == BillingClient.BillingResponse.OK) {
                // Successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                Log.d(TAG, "Consumption successful. Provisioning.");
                multiple_buy_qty = multiple_buy_qty == MULTIPLE_BUY_MAX ? MULTIPLE_BUY_MAX : multiple_buy_qty + 1;
                saveData();
                //mActivity.alert(R.string.alert_fill_gas, mTank);
            } else {
                //mActivity.alert(R.string.alert_error_consuming, result);
            }

            mActivity.showRefreshedUi();
            Log.d(TAG, "End consumption flow.");
        }

        @Override
        public void onPurchasesUpdated(List<Purchase> purchaseList) {
            subscription = false;

            for (Purchase purchase : purchaseList) {
                switch (purchase.getSku()) {
                    case "onetime":
                        Log.d(TAG, "Bought one time purchase");
                        one_time_buy = true;
                        break;
                    case "multiple":
                        Log.d(TAG, "We have gas. Consuming it.");
                        // We should consume the purchase and fill up the tank once it was consumed
                        mActivity.getBillingManager().consumeAsync(purchase.getPurchaseToken());
                        break;
                    case "subscription":
                        subscription = true;
                        break;
                }
            }

            mActivity.showRefreshedUi();
        }
    }


    /**
     * Save current tank level to disc
     *
     * Note: In a real application, we recommend you save data in a secure way to
     * prevent tampering.
     * For simplicity in this sample, we simply store the data using a
     * SharedPreferences.
     */
    private void saveData() {
        SharedPreferences.Editor spe = mActivity.getPreferences(MODE_PRIVATE).edit();
        spe.putBoolean("onetimebuy", one_time_buy);
        spe.putBoolean("subscription", subscription);
        spe.putInt("multiplebuy", multiple_buy_qty);
        spe.apply();
        Log.d(TAG, "Saved data: multiplebuy = " + String.valueOf(multiple_buy_qty));
    }

    private void loadData() {
        SharedPreferences sp = mActivity.getPreferences(MODE_PRIVATE);
        one_time_buy = sp.getBoolean("onetimebuy", false);
        subscription = sp.getBoolean("subscription", false);
        multiple_buy_qty = sp.getInt("multiplebuy", 0);
        Log.d(TAG, "Loaded data: multiplebuy = " + String.valueOf(multiple_buy_qty));
    }

}
