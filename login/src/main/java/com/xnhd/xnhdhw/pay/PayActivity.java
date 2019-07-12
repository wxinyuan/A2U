package com.xnhd.xnhdhw.pay;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayActivity extends Activity implements PurchasesUpdatedListener
{
    private static final String TAG         = "PayActivity";

    private static final String BASE_64_ENCODED_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtV9aRLyryZR0BXYq9ebZ5P75dzZM7uawTatLNrjde7q/8tds1oElafiU3fuBeQo3uqVuT/xAE3Ho5vNaKvkgTBIbOJH6BLymAxD8a6s17sn+/AxFqhRBFrJW6pW0YsCVRg3cx3T3w6w2Lf8ZmDD9yJ2pIyokHfej4GU/EqNV+7H0HIXXt48/pm9U2sh48gajVRCsda7NKhbR/8KiepJAm3fY0TSfEsaBDoIdKg9g/Wc+b7D7x/MMmUIMjXkjXdTtH8P6fi0P1Npx0+07aGhG8NXhYX7cXnC2E/KsuiS4u8LB894OD+sdsYkKCSSeFts0QQ4l/ZQvo9doOs/C8SG99QIDAQAB";
    private Activity mActivity;

    private BillingClient       mBillingClient;
    private List<SkuDetails>    mSkuDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "####################");

        mActivity = this;

        mBillingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult)
            {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
                {
                    // The BillingClient is ready. You can query purchases here.
                    refreshUnConsumePurchase();

                    onGetGoods();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Log.d(TAG, "####onResume####");

        refreshUnConsumePurchase();
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases)
    {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null)
        {
            for (Purchase purchase : purchases)
            {
                handlePurchase(purchase);
            }
        }
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED)
        {
            // Handle an error caused by a user cancelling the purchase flow.
        }
        else {
            // Handle any other error codes.
        }
    }

    //补单操作
    private void refreshUnConsumePurchase()
    {
        Purchase.PurchasesResult purchasesResult =  mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
        if (purchasesResult != null)
        {
            List<Purchase> purchaseList = purchasesResult.getPurchasesList();
            if (purchaseList == null)
            {
                Log.e(TAG, "没有需要补单的商品");
                return;
            }

            for (int i = 0; i < purchaseList.size(); ++i)
            {
                Purchase purchase = purchaseList.get(i);
                String strRet = purchase.isAcknowledged() ? "true" : "false";
                String msg = "####" + purchase.getSku() + " isAcknowledged:" + strRet;
                Log.e(TAG, msg);
                handlePurchase(purchase);
            }
        }
    }

        void postPay(String orderno, String productid, String packageName, String purchaseTime, String purchaseState,
                 String purchaseToken, String exinfo, String token) {

//        Log.i(TAG, "####orderno:" + orderno);
//        params.put("orderno", orderno);
//        params.put("productid", productid);
//        params.put("packageName", packageName);
//        params.put("paytime", purchaseTime);
//        params.put("status", purchaseState);
//        params.put("purchaseToken", purchaseToken);
//        params.put("exinfo", exinfo);
//        params.put("token", token);
//        client.post(Constant.PAY_URL, params, new TextHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String content) {
//                try {
//                    if (!TextUtils.isEmpty(content)) {
//                        JSONObject json = new JSONObject(content);
//                        Log.i(TAG , json.toString());
//
//                        int errno = json.getInt("errno");
//                        String errmsg = json.getString("errmsg");
//                        if (errno == 0) {
//                            Map<String, String> data = new HashMap<String, String>();
//                            data.put("orderid", "orderid");
//                            data.put("amount", "amount");
//                            data.put("currencyType", "googlepay");
//                            data.put("payment", "payment");
//
//                            //payView.onPayResult(true, errmsg, data);
//                        } else {
//                            Log.i(TAG, "进入发货阶段-失败");
//                            //payView.onPayResult(false, errmsg, null);
//                        }
//                    } else {
//                        Log.i(TAG, "返回为空");
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
//            {
//                Log.e(TAG, responseString);
//            }
//        });
    }

    void handlePurchase(Purchase purchase)
    {
        if (purchase == null)
        {
            return;
        }

        if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature()))
        {
            Log.i(TAG, "Got a purchase: " + purchase + "; but signature is bad. Skipping...");
            return;
        }

        int state = purchase.getPurchaseState();
        if (state == Purchase.PurchaseState.PURCHASED)
        {
            Log.d(TAG, "state == Purchase.PurchaseState.PURCHASED");
            // Grant entitlement to the user.
            String productid            = "1";
            String strOrderId           = purchase.getOrderId();
            String packageName          = purchase.getPackageName();
            String sku                  = purchase.getSku();
            long purchaseTime           = purchase.getPurchaseTime();
            String purchaseToken        = purchase.getPurchaseToken();
            String purchasePayLoad      = purchase.getDeveloperPayload();
            String purchaseSignature    = purchase.getSignature();

            //String token  = Md5.md5(secret + exinfo + orderno + productid);

            postPay(strOrderId, productid, packageName, String.valueOf(purchaseTime), String.valueOf(state), purchaseToken, "", "");

            // Acknowledge the purchase if it hasn't already been acknowledged.
            if (!purchase.isAcknowledged())
            {
                /*
                Log.d(TAG, "****!purchase.isAcknowledged()****");
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();

                mBillingClient.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListener()
                {
                    @Override
                    public void onAcknowledgePurchaseResponse(BillingResult billingResult)
                    {
                        billingResult.getResponseCode();
                        Log.d(TAG, "onAcknowledgePurchaseResponse(BillingResult billingResult)");
                    }
                });
                */
                Log.d(TAG, "purchaseToken:" + purchaseToken + " purchasePayLoad:" + purchasePayLoad);
                ConsumeParams consumeParams = ConsumeParams.newBuilder().setPurchaseToken(purchaseToken).build();
                mBillingClient.consumeAsync(consumeParams, new ConsumeResponseListener()
                {
                    @Override
                    public void onConsumeResponse(BillingResult billingResult, String outToken)
                    {
                        int responseCode = billingResult.getResponseCode();
                        if (responseCode == BillingClient.BillingResponseCode.OK)
                        {
                            Log.d(TAG, "消耗成功");
                            Toast.makeText(mActivity, "消耗成功", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Log.e(TAG, "消耗失败:" + outToken + " DebugMessage:" + billingResult.getDebugMessage());
                            Toast.makeText(mActivity, "消耗失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                Log.d(TAG, "====purchase.isAcknowledged()====");
            }
        }
        else if (state == Purchase.PurchaseState.UNSPECIFIED_STATE)
        {
            Log.d(TAG, "state == Purchase.PurchaseState.UNSPECIFIED_STATE");
        }
        else if (state == Purchase.PurchaseState.PENDING)
        {
            Log.d(TAG, "state == Purchase.PurchaseState.PENDING");

            // Here you can confirm to the user that they've started the pending
            // purchase, and to complete it, they should follow instructions that
            // are given to them. You can also choose to remind the user in the
            // future to complete the purchase if you detect that it is still
            // pending.
        }
    }

        /**
     * Verifies that the purchase was signed correctly for this developer's public key.
     * <p>Note: It's strongly recommended to perform such check on your backend since hackers can
     * replace this method with "constant true" if they decompile/rebuild your app.
     * </p>
     */
    private boolean verifyValidSignature(String signedData, String signature)
    {
        try
        {
            return com.xnhd.xnhdhw.pay.Security.verifyPurchase(BASE_64_ENCODED_PUBLIC_KEY, signedData, signature);
        }
        catch (IOException e) {
            Log.e(TAG, "Got an exception trying to validate a purchase: " + e);
            return false;
        }
    }

    public void onGetGoods()
    {
        Log.d(TAG, "onGetGoods Btn");

        List<String> skuList = new ArrayList<>();
        skuList.add("com.xnhd.xnhdhw.1");
        skuList.add("com.xnhd.xnhdhw.2");
        skuList.add("com.xnhd.xnhdhw.3");
        skuList.add("com.xnhd.xnhdhw.4");
        skuList.add("com.xnhd.xnhdhw.5");
        //skuList.add("pear");
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        mBillingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        // Process the result.
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                            Log.e(TAG, "$$$$:" + skuDetailsList.size() + " DebugMessage:" + billingResult.getDebugMessage());
                            //mStatusTextView.setText("商品个数:" + skuDetailsList.size());
                            String strGoods = "";
                            for (SkuDetails skuDetails : skuDetailsList) {
                                String sku = skuDetails.getSku();
                                String price = skuDetails.getPrice();
                                Log.e(TAG, "####" + sku + ":" + price);

                                mSkuDetailList.add(skuDetails);

                                strGoods += "商品名:" + skuDetails.getSku() + "价格:" + skuDetails.getPrice() + "\n";
                            }
                            //mStatusTextView.setText(strGoods);

                            onPay();
                        }
                        else
                        {
                            //mStatusTextView.setText("商品个数返回失败");
                            Log.e(TAG, "#################billingResult Error:" + billingResult.getResponseCode() + " " + billingResult.getDebugMessage());
                        }
                    }
                });
    }

    private void onPay()
    {
        if (mSkuDetailList == null || mSkuDetailList.size() == 0)
        {
            //Log.d(TAG, "onPay()");
            Toast.makeText(this, "商品列表为空,请先获取商品列表,再购买", Toast.LENGTH_SHORT).show();
            return;
        }

        SkuDetails sku = mSkuDetailList.get(1);
        Log.d("TAG", "sku:" + sku.getSku() + " " + sku.getPrice());
        //initiatePurchaseFlow(mSkuDetailList.get(0), this);
        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(sku)
                .build();
        BillingResult billingResult = mBillingClient.launchBillingFlow(this, flowParams);
        int responseCode = billingResult.getResponseCode();
        if (responseCode == BillingClient.BillingResponseCode.OK)
        {
            Log.d(TAG, "/** Success */");
        }
        else if (responseCode == BillingClient.BillingResponseCode.USER_CANCELED)
        {
            Log.d(TAG, "/** User pressed back or canceled a dialog */");
        }
        else if (responseCode == BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE)
        {
            Log.e(TAG, "/** Network connection is down */");
        }
        else if (responseCode == BillingClient.BillingResponseCode.BILLING_UNAVAILABLE)
        {
            Log.e(TAG, "/** Billing API version is not supported for the type requested */");
        }
        else if (responseCode == BillingClient.BillingResponseCode.ITEM_UNAVAILABLE)
        {
            Log.e(TAG, "/** Requested product is not available for purchase */");
        }
        else if (responseCode == BillingClient.BillingResponseCode.DEVELOPER_ERROR)
        {
            Log.e(TAG, "Invalid arguments provided to the API.");
        }
        else if (responseCode == BillingClient.BillingResponseCode.ERROR)
        {
            Log.e(TAG, "/** Fatal error during the API action */");
        }
        else if (responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED)
        {
            Log.e(TAG, "/** Failure to purchase since item is already owned */");
            Toast.makeText(this, "该商品已经购买", Toast.LENGTH_SHORT).show();
        }
        else if (responseCode == BillingClient.BillingResponseCode.ITEM_NOT_OWNED)
        {
            Log.e(TAG, "/** Failure to consume since item is not owned */");
        }
        else
        {
            Log.e(TAG, "Other Error");
        }
    }
    /**
     * Start a purchase or subscription replace flow
     */
    public void initiatePurchaseFlow(final SkuDetails skuDetails, final Activity activity) {
        Runnable purchaseFlowRequest = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Launching in-app purchase flow. Replace old SKU? " + (skuDetails != null));
                BillingFlowParams purchaseParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
                mBillingClient.launchBillingFlow(activity, purchaseParams);
            }
        };

        //executeServiceRequest(purchaseFlowRequest);
    }
}
