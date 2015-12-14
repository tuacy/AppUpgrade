package com.tuacy.upgradelibrary;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.tuacy.upgradelibrary.bean.UpgradeBean;
import com.tuacy.upgradelibrary.exception.UpgradeException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class UpgradeManager {

	private static final int    CONNECT_TIME_OUT = 3000;
	private static final int    READ_TIME_OUT    = 5000;
	private static final String METHOD           = "GET";

	public void checkUpgradeInfo(Context context, String url) {
		try {
			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
			SAXParser parser = saxFactory.newSAXParser();
			InputStream is = context.getAssets().open("upgrade.xml");
			List<UpgradeBean> upgradeBeanList = new ArrayList<>();
			UpgradeSAXHandler handle = new UpgradeSAXHandler(upgradeBeanList);
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

	public void checkUpgrade(@NonNull final Context context, @NonNull final String upgradeXmlUrl, final @NonNull IUpgradeCheck callback)
		throws UpgradeException {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new UpgradeException("current thread not UI thread (must call in UI thread)");
		}
		new UpgradeAsyncTask(context, callback).execute(upgradeXmlUrl);
	}

	private List<UpgradeBean> getUpgradeList(String xmlUrl) {
		InputStream xmlIn = null;
		List<UpgradeBean> upgradeList = null;
		try {
			URL upgradeXmlUrl = new URL(xmlUrl);
			HttpURLConnection conn = (HttpURLConnection) upgradeXmlUrl.openConnection();
			conn.setConnectTimeout(CONNECT_TIME_OUT);
			conn.setRequestMethod(METHOD);
			conn.setReadTimeout(READ_TIME_OUT);
			xmlIn = conn.getInputStream();
			upgradeList = parseUpgradeXml(xmlIn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (xmlIn != null) {
				try {
					xmlIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return upgradeList;
	}

	private List<UpgradeBean> parseUpgradeXml(InputStream xml) throws IOException, SAXException, ParserConfigurationException {

		List<UpgradeBean> updateInfoList = new ArrayList<UpgradeBean>();

		Reader reader = new InputStreamReader(xml, "UTF-8");
		InputSource is = new InputSource(reader);
		is.setEncoding("UTF-8");

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();

		parser.parse(is, new UpgradeSAXHandler(updateInfoList));

		return updateInfoList;
	}

	private UpgradeBean getUpgradeInfo(Context context, List<UpgradeBean> upgradeList) {

		if (null == upgradeList) {
			return null;
		}

		UpgradeBean maxVersionUpgradeInfo = null;
		int curVersionCode = AppUtils.getVersionCode(context);
		for (UpgradeBean upgradeInfo : upgradeList) {
			if (upgradeInfo.getVersionCode() > curVersionCode) {
				if (maxVersionUpgradeInfo == null || (upgradeInfo.getVersionCode() > maxVersionUpgradeInfo.getVersionCode())) {
					maxVersionUpgradeInfo = upgradeInfo;
				}
			}
		}

		return maxVersionUpgradeInfo;
	}

	class UpgradeAsyncTask extends AsyncTask<String, Integer, List<UpgradeBean>> {

		private Context       mContext;
		private IUpgradeCheck mCallBack;

		public UpgradeAsyncTask(Context context, IUpgradeCheck callback) {
			mContext = context;
			mCallBack = callback;
		}

		@Override
		protected void onPreExecute() {
			mCallBack.onCheckStart();
		}

		@Override
		protected void onPostExecute(List<UpgradeBean> result) {
			UpgradeBean upgradeInfo = getUpgradeInfo(mContext, result);
		}

		@Override
		protected List<UpgradeBean> doInBackground(String... params) {
			String url = params[0];
			return getUpgradeList(url);
		}
	}
}
