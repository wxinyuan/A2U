package com.xnhd.xnhdhw.login;

import android.app.Activity;
import android.content.Intent;

public class Login
{
    protected Activity mActivity   = null;
    protected LoginListener             mListener   = null;
    protected LoginManager.LoginType    mLoginType  = LoginManager.LoginType.NULL;

    public LoginManager.LoginType getType() { return  mLoginType; }

    public boolean isLogin() { return  false; }

    public void logOut() {}

    public void onPreActivityResult(int requestCode, int resultCode, Intent data) {}

    public void onPostActivityResult(int requestCode, int resultCode, Intent data) {}

    public String getId() { return  ""; }

    public String getToken() { return  ""; }
}

