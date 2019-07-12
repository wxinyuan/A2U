package com.xnhd.xnhdhw.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.xnhd.xnhdhw.ErrorCode;
import com.xnhd.xnhdhw.Unity3DCallback;

import java.util.Arrays;

public class FacebookActivity extends Activity
{
    private static final String TAG         = "FacebookActivity";

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "###########");

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                        //Log.e(TAG, isLoggedIn ? "Login true" : "Login False");
                        if (isLoggedIn)
                        {
                            updateUI(accessToken);
                        }
                        else
                        {
                            updateUI(null);
                        }
                        finish();
                    }

                    @Override
                    public void onCancel()
                    {
                        // App code
                        Log.e(TAG, "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception)
                    {
                        // App code
                        Log.e(TAG, exception.getMessage());
                        finish();
                    }
                });

        login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void login()
    {
        LoginManager.getInstance().logOut();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired())
        {
            updateUI(accessToken);
            finish();
        }
        else
        {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }
    }

    private void  updateUI(AccessToken account)
    {
        if (account != null)
        {
            Log.e(TAG, "登录成功");
            String strInfo = "";
            strInfo += account.toString();
            strInfo += account.getApplicationId();
            strInfo += account.getToken();
            strInfo += account.getUserId();

            Log.e(TAG, strInfo);

            Unity3DCallback.doLoginResultCallback(ErrorCode.SUCCESS, account.getToken(), account.getUserId(), account.getApplicationId(),  account.getUserId());
        }
        else
        {
            Log.e(TAG, "登录失败");
        }
        this.finish();
    }
}
