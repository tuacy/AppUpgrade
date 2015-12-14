package com.tuacy.upgradelibrary;

import android.util.Log;

import com.tuacy.upgradelibrary.bean.UpgradeBean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Stack;

public class UpgradeSAXHandler extends DefaultHandler {

	private static final String TAG_FILE         = "file";
	private static final String TAG_VERSION_CODE = "versionCode";
	private static final String TAG_VERSION_NAME = "versionName";
	private static final String TAG_PACKAGE_NAME = "packageName";
	private static final String TAG_FILE_NAME    = "fileName";
	private static final String TAG_FILE_LENGTH  = "fileLength";
	private static final String TAG_FILE_URL     = "fileUrl";
	private static final String TAG_DESCRIPTIONS = "descriptions";
	private static final String TAG_DESCRIPTION  = "description";

	private final String            TAG              = "UpgradeSAXHandler";
	private       Stack<String>     mStack           = new Stack<String>();
	private       List<UpgradeBean> mUpgradeBeanList = null;
	private       UpgradeBean       mUpgradeBean     = null;
	private       String            mDescLanguage    = null;

	public UpgradeSAXHandler(List<UpgradeBean> upgradeBeanList) {
		mUpgradeBeanList = upgradeBeanList;
	}

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		mStack.push(qName);
		if (localName.equalsIgnoreCase(TAG_FILE)) {
			mUpgradeBean = new UpgradeBean();
		}

		Log.d(TAG, "Element qName    : " + qName);
		Log.d(TAG, "Element localName: " + localName);
		Log.d(TAG, "Element uri      : " + uri);
		for (int i = 0; i < attributes.getLength(); i++) {
			Log.d(TAG, "  attribute qName    : " + attributes.getQName(i));
			Log.d(TAG, "  attribute localName: " + attributes.getLocalName(i));
			Log.d(TAG, "  attribute value    : " + attributes.getValue(i));
			Log.d(TAG, "  attribute uri      : " + attributes.getURI(i));
			if (TAG_DESCRIPTION.equals(qName)) {
				mDescLanguage = attributes.getValue(i);
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String tag = mStack.peek();
		if (null != mUpgradeBean) {
			return;
		}
		String content = new String(ch, start, length);
		Log.d(TAG, content);
		switch (tag) {
			case TAG_VERSION_CODE:
				mUpgradeBean.setVersionCode(Integer.parseInt(content));
				break;
			case TAG_VERSION_NAME:
				mUpgradeBean.setVersionName(content);
				break;
			case TAG_PACKAGE_NAME:
				mUpgradeBean.setPackageName(content);
				break;
			case TAG_FILE_NAME:
				mUpgradeBean.setFileName(content);
				break;
			case TAG_FILE_LENGTH:
				mUpgradeBean.setFileLength(Long.parseLong(content));
				break;
			case TAG_FILE_URL:
				mUpgradeBean.setFileUrl(content);
				break;
			case TAG_DESCRIPTIONS:
				if (null != mDescLanguage) {
					mUpgradeBean.addDescription(mDescLanguage, content);
				}
				break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		mStack.pop();
		mDescLanguage = null;
		if (localName.equalsIgnoreCase(TAG_FILE)) {
			if (null != mUpgradeBean) {
				mUpgradeBeanList.add(mUpgradeBean);
			}
		}
	}

}
