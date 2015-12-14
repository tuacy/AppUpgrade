package com.tuacy.upgradelibrary.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeBean {

	private int    mVersionCode;
	private String mVersionName;
	private String mPackageName;
	private String mFileName;
	private long   mFileLength;
	private String mFileUrl;
	private Map<String, List<String>> mDescriptions = new HashMap<>();

	public int getVersionCode() {
		return mVersionCode;
	}

	public void setVersionCode(int versionCode) {
		this.mVersionCode = versionCode;
	}

	public Map<String, List<String>> getDescriptions() {
		return mDescriptions;
	}

	public void setDescriptions(Map<String, List<String>> descriptions) {
		this.mDescriptions = descriptions;
	}

	public String getFileUrl() {
		return mFileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.mFileUrl = fileUrl;
	}

	public long getFileLength() {
		return mFileLength;
	}

	public void setFileLength(long fileLength) {
		this.mFileLength = fileLength;
	}

	public String getFileName() {
		return mFileName;
	}

	public void setFileName(String fileName) {
		this.mFileName = fileName;
	}

	public String getVersionName() {
		return mVersionName;
	}

	public void setVersionName(String versionName) {
		this.mVersionName = versionName;
	}

	public String getPackageName() {
		return mPackageName;
	}

	public void setPackageName(String packageName) {
		this.mPackageName = packageName;
	}

	public void addDescription(String key, String value) {
		List<String> keyList = mDescriptions.get(key);
		if (null == keyList) {
			keyList = new ArrayList<String>();
			mDescriptions.put(key, keyList);
		}
		if (!keyList.contains(value)) {
			keyList.add(value);
		}
	}
}
