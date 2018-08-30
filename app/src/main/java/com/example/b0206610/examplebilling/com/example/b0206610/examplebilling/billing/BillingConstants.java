package com.example.b0206610.examplebilling.com.example.b0206610.examplebilling.billing;

import com.android.billingclient.api.BillingClient;

import java.util.Arrays;
import java.util.List;

public class BillingConstants {

    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    public static final String ONE_TIME_BUY = "onetime";
    public static final String MULTIPLE_BUY = "android.test.purchased";

    // SKU for our subscription (infinite gas)
    public static final String SUBSCRIPTION1 = "subscription";

    private static final String[] IN_APP_SKUS = {MULTIPLE_BUY, ONE_TIME_BUY};
    private static final String[] SUBSCRIPTIONS_SKUS = {SUBSCRIPTION1};

    private BillingConstants(){}

    /**
     * Returns the list of all SKUs for the billing type specified
     */
    public static final List<String> getSkuList(@BillingClient.SkuType String billingType) {
        return (billingType == BillingClient.SkuType.INAPP) ? Arrays.asList(IN_APP_SKUS) : Arrays.asList(SUBSCRIPTIONS_SKUS);
    }

}
