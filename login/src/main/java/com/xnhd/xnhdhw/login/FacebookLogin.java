package com.xnhd.xnhdhw.login;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class FacebookLogin extends Login
{
    private static final String TAG         = "FacebookLogin";

    private CallbackManager callbackManager;

    public FacebookLogin(Activity activity, LoginListener loginListener)
    {
        Log.e(TAG, "FacebookLogin");

        mActivity   = activity;
        mListener   = loginListener;

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        // App code
                        Log.e(TAG, loginResult.getAccessToken().toString());
                        Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT);

                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                        Log.e(TAG, isLoggedIn ? "Login true" : "Login False");

                        if (mListener != null)
                        {
                            mListener.loginSuccess();
                        }
                    }

                    @Override
                    public void onCancel()
                    {
                        // App code
                        Log.e(TAG, "onCancel");
                        Toast.makeText(mActivity, "取消登录", Toast.LENGTH_SHORT);

                        if (mListener != null)
                        {
                            mListener.loginCancel();
                        }
                    }

                    @Override
                    public void onError(FacebookException exception)
                    {
                        // App code
                        Log.e(TAG, exception.getMessage());
                        Toast.makeText(mActivity, "登录失败:" + exception.getMessage(), Toast.LENGTH_SHORT);

                        if (mListener != null)
                        {
                            mListener.loginFailed();
                        }
                    }
                });

        login();
    }

    @Override
    public void onPreActivityResult(int requestCode, int resultCode, Intent data)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void login()
    {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired())
        {
            Log.i(TAG, "账号已登录");
            if (mListener != null)
            {
                mListener.loginSuccess();
            }
        }
        else
        {
            LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("public_profile", "email"));
        }
    }

    @Override
    public void logOut()
    {
        LoginManager.getInstance().logOut();
        if (mListener != null)
        {
            mListener.logoutSuccess();
        }
    }

    @Override
    public boolean isLogin()
    {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }

    @Override
    public String getId()
    {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired())
        {
            return accessToken.getUserId();
        }

        return "";
    }

    @Override
    public String getToken()
    {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired())
        {
            return accessToken.getToken();
        }

        return "";
    }
}
