package com.example.b0206610.examplebilling.com.example.b0206610.examplebilling.billing;

public interface BillingProvider {
    BillingManager getBillingManager();
    boolean isPremiumPurchased();
    boolean isGoldMonthlySubscribed();
    boolean isTankFull();
    boolean isGoldYearlySubscribed();
}
