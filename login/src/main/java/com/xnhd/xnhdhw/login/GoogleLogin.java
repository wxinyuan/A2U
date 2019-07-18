package com.xnhd.xnhdhw.login;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GoogleLogin extends Login
{
    private static final String TAG                     = "GoogleLogin";
    private static final int    RC_SIGN_IN              = 9002;

    private GoogleSignInClient      mGoogleSignInClient = null;
    private  GoogleSignInAccount    mAccount            = null;

    public GoogleLogin(Activity activity, LoginListener loginListener)
    {
        Log.i(TAG, "GoogleLogin");

        mActivity   = activity;
        mListener   = loginListener;
        //Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(mActivity, gso);

        login();
    }

    @Override
    public void onPostActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else
        {
            if (mListener != null)
            {
                mListener.loginFailed();
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try
        {
            mAccount = completedTask.getResult(ApiException.class);

            if (mListener != null)
            {
                String strInfo = "";
                strInfo += "Id:" + mAccount.getId() + "\n";
                strInfo += "token:" + mAccount.getIdToken() + "\n";
                strInfo += "DisplayName:" + mAccount.getDisplayName() + "\n";
                strInfo += "Email:" + mAccount.getEmail();

                Log.e(TAG, strInfo);
                mListener.loginSuccess();
            }
        }
        catch (ApiException e)
        {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());

            if (mListener != null)
            {
                mListener.loginFailed();
            }
        }
    }

    private void login()
    {
        //Toast.makeText(this, "signIn", Toast.LENGTH_SHORT).show();
        if (mActivity == null)
        {
            if (mListener != null)
            {
                mListener.loginFailed();
            }
            return;
        }

        mAccount = GoogleSignIn.getLastSignedInAccount(mActivity);
        if (mAccount == null)
        {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        else
        {
            Log.i(TAG, "账号已登录");
            if (mListener != null)
            {
                mListener.loginSuccess();
            }
            String strInfo = "";
            strInfo += "Id:" + mAccount.getId() + "\n";
            strInfo += "token:" + mAccount.getIdToken() + "\n";
            strInfo += "DisplayName:" + mAccount.getDisplayName() + "\n";
            strInfo += "Email:" + mAccount.getEmail();

            Log.e(TAG, strInfo);
        }
    }

    @Override
    public void logOut()
    {
        Toast.makeText(mActivity, "signOut", Toast.LENGTH_SHORT).show();

        mGoogleSignInClient.signOut().addOnCompleteListener(mActivity, new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(Task<Void> task)
            {
                mAccount = null;
                Log.d(TAG, "账号退出");
                Toast.makeText(mActivity, "账号退出", Toast.LENGTH_SHORT);

                if (mListener != null)
                {
                    mListener.logoutSuccess();
                }
            }
        });
    }

    @Override
    public boolean isLogin()
    {
        return mAccount != null;
    }

    @Override
    public String getId()
    {
        if (mAccount == null)
        {
            return  "";
        }

        return mAccount.getId();
    }

    @Override
    public String getToken()
    {
        if (mAccount == null)
        {
            return  "";
        }

        return mAccount.getIdToken();
    }
}
