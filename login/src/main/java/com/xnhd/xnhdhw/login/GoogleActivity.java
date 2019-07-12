package com.xnhd.xnhdhw.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.xnhd.xnhdhw.ErrorCode;
import com.xnhd.xnhdhw.Unity3DCallback;

public class GoogleActivity extends Activity
{
    private static final String TAG         = "LoginActivity";
    private static final int    RC_SIGN_IN  = 9002;

    private Activity            mActivity;
    private GoogleSignInClient  mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate");

        mActivity = this;

        //Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        onSignIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try
        {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            updateUI(account);
        }
        catch (ApiException e)
        {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void onSignIn()
    {
        //Toast.makeText(this, "signIn", Toast.LENGTH_SHORT).show();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null)
        {
            updateUI(account);
        }
        else
        {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    private void onSignOut()
    {
        Toast.makeText(this, "signOut", Toast.LENGTH_SHORT).show();

        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(Task<Void> task)
            {
                Log.d(TAG, "账号退出");
                Toast.makeText(mActivity, "账号退出", Toast.LENGTH_SHORT);
                Unity3DCallback.doLogoutResultCallback(ErrorCode.SUCCESS);
            }
        });
    }

    private void  updateUI(GoogleSignInAccount account)
    {
        if (account != null)
        {
            String strInfo = "";
            strInfo += "Id:" + account.getId() + "\n";
            strInfo += "DisplayName:" + account.getDisplayName() + "\n";
            strInfo += "Email:" + account.getEmail();

            Log.e(TAG, strInfo);

            Unity3DCallback.doLoginResultCallback(ErrorCode.SUCCESS, account.getIdToken(), account.getId(), account.getDisplayName(),  account.getEmail());
        }
        else
        {
            Log.e(TAG, "登录失败");
            Unity3DCallback.doLoginResultCallback(ErrorCode.ERROR_LOGIN_FAIL, "", "", "", "");
        }
        this.finish();
    }
}
