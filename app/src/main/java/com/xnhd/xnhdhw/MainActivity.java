package com.xnhd.xnhdhw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.xnhd.xnhdhw.login.FacebookActivity;
import com.xnhd.xnhdhw.login.GoogleActivity;
import com.unity3d.player.UnityPlayerActivity;
import com.xnhd.xnhdhw.pay.PayManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends UnityPlayerActivity
{
    private static final String TAG         = "MainActivity";

    enum LoginType
    {
        GOOGLE(0),
        FACEBOOK(1),
        VISITOR(2);

        private int value = 0;

        private LoginType(int value)
        {
            this.value = value;
        }

        public static LoginType valueOf(int value)
        {
            switch (value)
            {
                case 0:
                    return GOOGLE;
                case 1:
                    return FACEBOOK;
                case 2:
                    return VISITOR;
                default:
                    return null;
            }
        }

        public int value()
        {
            return this.value;
        }
    };

    private boolean bEnableGooglePay        = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        if (bEnableGooglePay)
        {
            List<String> skuList = new ArrayList<>();
            skuList.add("com.xnhd.xnhdhw.1");
            skuList.add("com.xnhd.xnhdhw.2");
            skuList.add("com.xnhd.xnhdhw.3");
            skuList.add("com.xnhd.xnhdhw.4");
            skuList.add("com.xnhd.xnhdhw.5");
            skuList.add("com.xnhd.xnhdhw.6");

            //PayManager.getInstance().Init(this, skuList);
            PayManager.getInstance().Init(this, skuList);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (bEnableGooglePay)
        {
            //PayManager.getInstance().RefreshUnConsumePurchase();
        }
    }

    public void  login(int loginType)
    {
        LoginType type = LoginType.valueOf(loginType);
        if (type == LoginType.GOOGLE)
        {
            Intent intent = new Intent(this, GoogleActivity.class);
            startActivity(intent);
        }
        else if (type == LoginType.FACEBOOK)
        {
            Intent intent = new Intent(this, FacebookActivity.class);
            startActivity(intent);
        }
        else if (type == LoginType.VISITOR)
        {
            Log.e(TAG, "游客登录还未实现");
        }
        else
        {
            Log.e(TAG, "账号登录还未实现");
        }
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
