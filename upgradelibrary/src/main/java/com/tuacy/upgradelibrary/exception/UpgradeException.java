package com.tuacy.upgradelibrary.exception;

public class UpgradeException extends Exception {

	private static final long serialVersionUID = -8031365274814652823L;

	public UpgradeException(String detailMessage) {
		super(detailMessage);
	}

	public UpgradeException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public UpgradeException(Throwable throwable) {
		super(throwable);
	}
}
