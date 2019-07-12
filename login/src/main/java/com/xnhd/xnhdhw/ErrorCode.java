package com.xnhd.xnhdhw;

public enum ErrorCode {
	
	SUCCESS(0), ERROR_CANCEL(-1), ERROR_FAIL(-2), ERROR_FAIL_DETECT(-3),
	ERROR_LOGIN_CANCEL(-1), ERROR_LOGIN_FAIL(-2),
	ERROR_PAY_CANCEL(-1), ERROR_PAY_FAIL(-2), ERROR_PAY_ORDER_SUBMIT(-3),
	ERROR_QUIT_GAME(3), ERROR_QUIT_GAME_0(4), ERROR_CONTINUE_GAME(5),
	ERROR_SIGNOFF(6);
	
	private int index;
	
	private ErrorCode(int index) {
        this.index = index;
    }
	
	public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
