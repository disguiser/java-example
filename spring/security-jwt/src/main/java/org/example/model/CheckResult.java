package org.example.model;

import io.jsonwebtoken.Claims;

/**
 * 验证信息
 */
public class CheckResult {
	private boolean success;

	private String errMsg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
