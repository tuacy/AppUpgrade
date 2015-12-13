package com.tuacy.upgradelibrary;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class SAXUpgradeParseHandler extends DefaultHandler {

	private final String TAG_FILE         = "file";
	private final String TAG_VERSION_CODE = "versionCode";
	private final String TAG_VERSION_NAME = "versionName";
	private final String TAG_PACKAGE_NAME = "packageName";
	private final String TAG_FILE_NAME    = "fileName";
	private final String TAG_FILE_LENGTH  = "fileLength";
	private final String TAG_FILE_URL     = "fileUrl";
	private final String TAG_DESCRIPTIONS = "descriptions";

	private final int STATE_DEFAULT      = -1;
	private final int STATE_FILE         = 0;
	private final int STATE_VERSION_CODE = 1;
	private final int STATE_VERSION_NAME = 2;
	private final int STATE_PACKAGE_NAME = 3;
	private final int STATE_FILE_NAME    = 4;
	private final int STATE_FILE_LENGTH  = 5;
	private final int STATE_FILE_URL     = 6;
	private final int STATE_DESCRIPTIONS = 7;


	private List<UpgradeBean> mUpgradeBeanList;
	private UpgradeBean       mUpgradeBean;
	private int               mCurrentState;

	public SAXUpgradeParseHandler(List<UpgradeBean> upgradeBeanList) {
		mUpgradeBeanList = upgradeBeanList;
		Log.d("vae_tag", "SAXUpgradeParseHandler");
	}

	@Override
	public void startDocument() throws SAXException {
		Log.d("vae_tag", "startDocument");
	}

	@Override
	public void endDocument() throws SAXException {
		Log.d("vae_tag", "endDocument");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		Log.d("vae_tag", "Element qName    : " + qName);
		Log.d("vae_tag", "Element localName: " + localName);
		Log.d("vae_tag", "Element uri      : " + uri);
		for (int i = 0; i < attributes.getLength(); i++) {
			Log.d("vae_tag", "  attribute qName    : " + attributes.getQName(i));
			Log.d("vae_tag", "  attribute localName: " + attributes.getLocalName(i));
			Log.d("vae_tag", "  attribute value    : " + attributes.getValue(i));
			Log.d("vae_tag", "  attribute uri      : " + attributes.getURI(i));
		}
		if (localName.equalsIgnoreCase(TAG_FILE)) {
			mUpgradeBean = new UpgradeBean();
			mCurrentState = STATE_FILE;
		} else if (localName.equalsIgnoreCase(TAG_VERSION_CODE)) {
			mCurrentState = STATE_VERSION_CODE;
		} else if (localName.equalsIgnoreCase(TAG_VERSION_NAME)) {
			mCurrentState = STATE_VERSION_NAME;
		} else if (localName.equalsIgnoreCase(TAG_PACKAGE_NAME)) {
			mCurrentState = STATE_PACKAGE_NAME;
		} else if (localName.equalsIgnoreCase(TAG_FILE_NAME)) {
			mCurrentState = STATE_FILE_NAME;
		} else if (localName.equalsIgnoreCase(TAG_FILE_LENGTH)) {
			mCurrentState = STATE_FILE_LENGTH;
		} else if (localName.equalsIgnoreCase(TAG_FILE_URL)) {
			mCurrentState = STATE_FILE_URL;
		} else if (localName.equalsIgnoreCase(TAG_DESCRIPTIONS)) {
			mCurrentState = STATE_DESCRIPTIONS;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase(TAG_FILE)) {
			mUpgradeBeanList.add(mUpgradeBean);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String content = new String(ch, start, length);
		Log.d("vae_tag", content);
		switch (mCurrentState) {
			case STATE_VERSION_CODE:
				break;
			case STATE_VERSION_NAME:
				break;
			case STATE_PACKAGE_NAME:
				break;
			case STATE_FILE_NAME:
				break;
			case STATE_FILE_LENGTH:
				break;
			case STATE_FILE_URL:
				break;
			case STATE_DESCRIPTIONS:
				break;
		}
		mCurrentState = STATE_DEFAULT;
	}
}
