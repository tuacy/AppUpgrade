package com.tuacy.upgradelibrary;

import android.content.Context;

import com.tuacy.upgradelibrary.bean.UpgradeBean;

public interface IUpgradeCheck {

	void onCheckStart();

	void onFindNewVersion(Context context, UpgradeBean packageInfo);

	void onAlreadyLatestVersion();

	void onCheckFailure();

	void onCheckEnd();
}
