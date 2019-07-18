package com.xnhd.xnhdhw.login;

public interface LoginListener
{
    public void loginSuccess();

    public void loginFailed();

    public void loginCancel();

    public void logoutSuccess();
}
