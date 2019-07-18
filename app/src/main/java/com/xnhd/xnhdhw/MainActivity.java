package com.xnhd.xnhdhw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.unity3d.player.UnityPlayerActivity;
import com.xnhd.xnhdhw.login.LoginManager;
import com.xnhd.xnhdhw.pay.PayManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends UnityPlayerActivity
{
    private static final String TAG         = "MainActivity";

    private boolean bEnableGooglePay        = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        LoginManager.getInstance().Init(this);

        if (bEnableGooglePay)
        {
            List<String> skuList = new ArrayList<>();
            skuList.add("com.xnhd.xnhdhw.1");
            skuList.add("com.xnhd.xnhdhw.2");
            skuList.add("com.xnhd.xnhdhw.3");
            skuList.add("com.xnhd.xnhdhw.4");
            skuList.add("com.xnhd.xnhdhw.5");
            skuList.add("com.xnhd.xnhdhw.6");

            PayManager.getInstance().Init(this, skuList);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Log.i(TAG, "onResume");

        if (bEnableGooglePay)
        {
            //PayManager.getInstance().RefreshUnConsumePurchase();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        LoginManager.getInstance().onPreActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        LoginManager.getInstance().onPostActivityResult(requestCode, resultCode, data);
    }

    public void  login(int loginType)
    {
        LoginManager.LoginType type = LoginManager.LoginType.valueOf(loginType);
        LoginManager.getInstance().Login(type, new LoginListenerImpl(this));
    }

    public void logout()
    {
        LoginManager.getInstance().Logout();
    }

    public void  pay()
    {
        Log.i(TAG, "pay");

        if (bEnableGooglePay)
        {
            PayManager.getInstance().Buy(5);
        }
    }
}
