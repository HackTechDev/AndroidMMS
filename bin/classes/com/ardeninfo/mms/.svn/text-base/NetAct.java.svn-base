package com.wzx.andapp.shh;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

public class NetAct extends Activity {
	private TextView textView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		textView.setBackgroundColor(0xffffffff);
		textView.setTextColor(0xff0000ff);
		textView.setTextSize(15.0f);
		textView.setScrollBarStyle(TextView.SCROLLBARS_OUTSIDE_OVERLAY);

		ScrollView scrollView = new ScrollView(this);
		scrollView.addView(textView);
		setContentView(scrollView);
		getLocalHost();
		getWifiInfo();
		initNetworkInfo();
	}

	private void getLocalHost() {
		try {
			InetAddress iAdd = InetAddress.getLocalHost();
			String line = "";
			String hostName = iAdd.getHostName();
			if (hostName != null) {
				InetAddress[] adds = InetAddress.getAllByName(hostName);
				for (int i = 0; i < adds.length; i++) {
					iAdd = adds[i];
					line = "HostName=" + iAdd.getHostName() + "\n";
					textView.append(line);
					line = "CanonicalHostName=" + iAdd.getCanonicalHostName()
							+ "\n";
					textView.append(line);
					line = "HostAddress=" + iAdd.getHostAddress() + "\n";
					textView.append(line);
					textView.append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void getWifiInfo() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		textView.append("WifiInfo:\n");
		// textView.append("HiddenSSID=" + info.getHiddenSSID() + "\n");
		// textView.append("IpAddress=" + info.getIpAddress() + "\n");
		// textView.append("LinkSpeed=" + info.getLinkSpeed() + "\n");
		// textView.append("NetworkId=" + info.getNetworkId() + "\n");
		// textView.append("Rssi=" + info.getRssi() + "\n");
		// textView.append("SSID=" + info.getSSID() + "\n");
		// textView.append("MacAddress=" + info.getMacAddress() + "\n");
		// textView.append("getBSSID=" + info.getBSSID() + "\n");
		// textView.append("getSupplicantState=" + info.getSupplicantState() +
		// "\n");
		if (null != info) {
			textView.append("WifiInfo.toString=" + info.toString() + "\n");
		} else {
			textView.append("WifiInfo.toString=no info\n");
		}
	}

	public void initNetworkInfo() {
		ConnectivityManager mag = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// 此处输出当前可用网络
		textView.append("\nActive:\n");
		NetworkInfo info = mag.getActiveNetworkInfo();
		// textView.append("ExtraInfo=" + info.getExtraInfo() + "\n");
		// textView.append("SubtypeName=" + info.getSubtypeName() + "\n");
		// textView.append("TypeName=" + info.getTypeName() + "\n");
		// textView.append("getReason=" + info.getReason() + "\n");
		if (null != info) {
			textView.append("NetworkInfo.toString=" + info.toString() + "\n");
		} else {
			textView.append("NetworkInfo.toString=no info\n");
		}
		textView.append("\nWifi:\n");
		NetworkInfo wifiInfo = mag
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		// textView.append("ExtraInfo=" + wifiInfo.getExtraInfo() + "\n");
		// textView.append("SubtypeName=" + wifiInfo.getSubtypeName() + "\n");
		// textView.append("TypeName=" + wifiInfo.getTypeName() + "\n");
		if (null != wifiInfo) {
			textView.append("wifiInfo.toString=" + wifiInfo.toString() + "\n");
		} else {
			textView.append("wifiInfo.toString=no info\n");
		}
		NetworkInfo mobInfo = mag
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		textView.append("\nMobile:\n");
		// textView.append("ExtraInfo=" + mobInfo.getExtraInfo() + "\n");
		// textView.append("SubtypeName=" + mobInfo.getSubtypeName() + "\n");
		// textView.append("TypeName=" + mobInfo.getTypeName() + "\n");
		if (null != mobInfo) {
			textView.append("mobInfo.toString=" + mobInfo.toString() + "\n");
		} else {
			textView.append("mobInfo.toString=no info\n");
		}

		// WifiManager wifiManager = (WifiManager)
		// getSystemService(Context.WIFI_SERVICE);
		// HttpClient httpClient = new DefaultHttpClient();
		// if (!wifiManager.isWifiEnabled()) {
		Uri uri = Uri.parse("content://telephony/carriers/preferapn"); // 获取当前正在使用的APN接入点
		Cursor mCursor = this.getContentResolver().query(uri, null, null, null,
				null);
		String[] apnCols = mCursor.getColumnNames();
		textView.append(Arrays.toString(mCursor.getColumnNames()) + "\n");
		if (mCursor != null) {
			mCursor.moveToNext(); // 游标移至第一条记录，当然也只有一条
			for (String col : apnCols) {
				textView.append(col + ":" + mCursor.getColumnIndex(col) + ":"
						+ mCursor.getString(mCursor.getColumnIndex(col)) + "\n");
			}

			String proxyStr = mCursor
					.getString(mCursor.getColumnIndex("proxy"));
			int port = mCursor.getInt(mCursor.getColumnIndex("port"));
			if (proxyStr != null && proxyStr.trim().length() > 0) {
				InetSocketAddress proxyAddress = new InetSocketAddress(
						proxyStr, port);
			}
			// }
		}

	}

	public void getProxy() {
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		HttpClient httpClient = new DefaultHttpClient();
		if (!wifiManager.isWifiEnabled()) {
			Uri uri = Uri.parse("content://telephony/carriers/preferapn"); // 获取当前正在使用的APN接入点
			Cursor mCursor = this.getContentResolver().query(uri, null, null,
					null, null);
			if (mCursor != null) {
				mCursor.moveToNext(); // 游标移至第一条记录，当然也只有一条
				String proxyStr = mCursor.getString(mCursor
						.getColumnIndex("proxy"));
				if (proxyStr != null && proxyStr.trim().length() > 0) {
					HttpHost proxy = new HttpHost(proxyStr, 80);
					httpClient.getParams().setParameter(
							ConnRouteParams.DEFAULT_PROXY, proxy);
				}
			}
		}
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				20 * 1000);
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 20 * 1000);
		HttpGet httpGet = new HttpGet("wrapGetUrl()");
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedInputStream bis = new BufferedInputStream(content);
				StringBuilder builder = new StringBuilder();
				int b;
				while ((b = bis.read()) != -1) {
					builder.append((char) b);
				}
				String resultStr = builder.toString();
				Log.v("result", resultStr);
			}
		} catch (Exception e) {
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	public void postProxy() {
		int version = 3;
		Class versionClass = VERSION.class;
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		String manufacturer = "";
		String device = "";
		String networkOperatorName = tm.getNetworkOperatorName();
		String IMEI = tm.getDeviceId();
		try {
			Field field = versionClass.getField("SDK_INT");
			version = (Integer) field.get(new VERSION());
			Class buildClass = Build.class;
			Field manu_field = buildClass.getField("MANUFACTURER");
			manufacturer = (String) manu_field.get(new android.os.Build())
					+ " ";
			Field deviceField = buildClass.getField("DEVICE");
			device = (String) deviceField.get(new Build());
		} catch (Exception e) {

		}
		HttpClient httpclient = new DefaultHttpClient();
		try {
			if (!wifiManager.isWifiEnabled()) {
				Uri uri = Uri.parse("content://telephony/carriers/preferapn");
				Cursor mCursor = this.getContentResolver().query(uri, null,
						null, null, null);
				if (mCursor != null) {
					mCursor.moveToNext();
					String proxyStr = mCursor.getString(mCursor
							.getColumnIndex("proxy"));
					if (proxyStr != null && proxyStr.trim().length() > 0) {
						HttpHost proxy = new HttpHost(proxyStr, 80);
						httpclient.getParams().setParameter(
								ConnRouteParams.DEFAULT_PROXY, proxy);
					}
				}
			}
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
					20 * 1000);
			HttpConnectionParams
					.setSoTimeout(httpclient.getParams(), 20 * 1000);
			HttpPost httppost = new HttpPost("YOUR_POST_URL");
			List nameValuePairs = new ArrayList();
			nameValuePairs.add(new BasicNameValuePair("os", "Android"));
			nameValuePairs.add(new BasicNameValuePair("os_version", String
					.valueOf(version)));
			nameValuePairs.add(new BasicNameValuePair("device", manufacturer
					+ device));
			// nameValuePairs.add(new BasicNameValuePair("uuid", md5(IMEI)));
			nameValuePairs.add(new BasicNameValuePair("network",
					networkOperatorName));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
					HTTP.UTF_8));
			HttpResponse response;
			response = httpclient.execute(httppost);
			httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 200) {
				// Toast.makeText(this, R.string.updatesucceed,
				// Toast.LENGTH_SHORT)
				// .show();
			} else {
				// Toast.makeText(this, R.string.updatefailed,
				// Toast.LENGTH_SHORT)
				// .show();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// updateFlag = true;
			httpclient.getConnectionManager().shutdown();
		}

	}

	/**
	 * 获取未安装的APK信息
	 * 
	 * @param context
	 * @param archiveFilePath
	 *            APK文件的路径。如：/sdcard/download/XX.apk
	 */
	public void getUninatllApkInfo(Context context, String archiveFilePath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			String appName = pm.getApplicationLabel(appInfo).toString();
			String packageName = appInfo.packageName;
			Drawable icon = pm.getApplicationIcon(appInfo);
		}
		// List<PackageInfo> packs =
		// getPackageManager().getInstalledPackages(0);
	}

	/**
	 * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
	 * 
	 * @param actionUrl
	 * @param params
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public static String post(String actionUrl, Map<String, String> params,
			Map<String, File> files) throws IOException {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// 发送文件数据
		if (files != null) {
			int i = 0;
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file"
						+ (i++) + "\"; filename=\"" + file.getKey() + "\""
						+ LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(LINEND.getBytes());
			}
		}

		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();

		// 得到响应码
		int res = conn.getResponseCode();
		InputStream in = null;
		if (res == 200) {
			in = conn.getInputStream();
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
		}
		return in == null ? null : in.toString();
	}
}