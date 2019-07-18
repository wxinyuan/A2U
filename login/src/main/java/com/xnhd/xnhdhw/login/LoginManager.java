package com.xnhd.xnhdhw.login;

import android.app.Activity;
import android.content.Intent;

public class LoginManager
{
    private static final String TAG         = "LoginManager";

    public enum LoginType
    {
        NULL(0),
        GOOGLE(1),
        FACEBOOK(2),
        VISITOR(3);

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
                    return NULL;
                case 1:
                    return GOOGLE;
                case 2:
                    return FACEBOOK;
                case 3:
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

    private Activity mActivity;

    private Login mLogin;

    private static final LoginManager ourInstance = new LoginManager();

    public static LoginManager getInstance() {
        return ourInstance;
    }

    private LoginManager()
    {}

    public void Init(Activity activity)
    {
        mActivity = activity;
    }

    public void Login(LoginType loginType, LoginListener loginListener)
    {
        /*
        if (mLogin != null)
        {
            Log.e(TAG, "账号已登陆,请注销后再登陆");
            return;
        }
        */

        if (loginType == LoginType.GOOGLE)
        {
            mLogin = new GoogleLogin(mActivity, loginListener);
        }
        else if (loginType == LoginType.FACEBOOK)
        {
            mLogin = new FacebookLogin(mActivity, loginListener);
        }
        else if (loginType == LoginType.VISITOR)
        {

        }
    }

    public void onPreActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (mLogin != null)
        {
            mLogin.onPreActivityResult(requestCode, resultCode, data);
        }
    }

    public void onPostActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (mLogin != null)
        {
            mLogin.onPostActivityResult(requestCode, resultCode, data);
        }
    }

    public void Logout()
    {
        if (mLogin != null)
        {
            mLogin.logOut();
        }
    }

    public String getId()
    {
        if (mLogin != null)
        {
            return  mLogin.getId();
        }

        return "";
    }

    public String getToken()
    {
        if (mLogin != null)
        {
            return  mLogin.getToken();
        }

        return "";
    }
}
