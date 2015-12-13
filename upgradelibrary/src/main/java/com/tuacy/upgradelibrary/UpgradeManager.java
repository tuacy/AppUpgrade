package com.tuacy.upgradelibrary;

import android.content.Context;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class UpgradeManager {

	public static void checkUpgradeInfo(Context context, String url) {
		try {
			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
			SAXParser parser = saxFactory.newSAXParser();
			InputStream is = context.getAssets().open("upgrade.xml");
			List<UpgradeBean> upgradeBeanList = new ArrayList<>();
			SAXUpgradeParseHandler handle = new SAXUpgradeParseHandler(upgradeBeanList);
			parser.parse(is, handle);
			is.close();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void downloadUpgradeApp(String url) {

	}
}
