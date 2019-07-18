package com.xnhd.xnhdhw;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.xnhd.xnhdhw.login.LoginListener;
import com.xnhd.xnhdhw.login.LoginManager;

public class LoginListenerImpl implements LoginListener
{
    private Activity mActivity;
    public LoginListenerImpl(Activity activity) { mActivity = activity; }
    @Override
    public void loginSuccess()
    {
        String id       = LoginManager.getInstance().getId();
        String token    = LoginManager.getInstance().getToken();

        Log.e("LoginListenerImpl", "loginSuccess id:" + id);
        Log.e("LoginListenerImpl","loginSuccess token:" + token);

        Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT);

        Unity3DCallback.doLoginResultCallback(ErrorCode.SUCCESS, token, id, "",  "");
    }

    @Override
    public void loginFailed()
    {
        Log.e("LoginListenerImpl","loginFailed");

        Toast.makeText(mActivity, "登录失败", Toast.LENGTH_SHORT);

        Unity3DCallback.doLoginResultCallback(ErrorCode.ERROR_LOGIN_FAIL, "", "", "", "");
    }

    @Override
    public void loginCancel()
    {
        Log.e("LoginListenerImpl","loginCancel");

        Toast.makeText(mActivity, "取消登录", Toast.LENGTH_SHORT);
    }

    @Override
    public void logoutSuccess()
    {
        Log.e("LoginListenerImpl","logoutSuccess");

        Toast.makeText(mActivity, "退出登录", Toast.LENGTH_SHORT);

        Unity3DCallback.doLogoutResultCallback(ErrorCode.SUCCESS);
    }
}
