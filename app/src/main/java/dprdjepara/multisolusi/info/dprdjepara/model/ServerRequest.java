package dprdjepara.multisolusi.info.dprdjepara.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import com.bumptech.glide.load.Key;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.MimeTypeMap;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import android.content.Intent;

import androidx.core.app.ActivityCompat;

public class ServerRequest {
	private final static String TAG = "ServerRequest";
	public static String serverUri = "http://jpr01.multisolusi.info/index.php?r=mobile/";
	//public static String serverUri = "http://192.168.1.50/jpr01/m";
	//public static String serverUri = "http://192.168.43.15/jpr01/m";
	//public static String serverUri = "http://192.168.201.25/jpr01/m";

	public static final String urlAdminlog = "adminlog";
	public static final String urlChattawal = "chattawal";
	public static final String urlDisplayaccount = "displayaccount";
	public static final String urlDisplaydokumen = "displaydokumen&id_dokumen=";
	public static final String urlDisplayedokumen = "displayedokumen";
	public static final String urlDisplayprofile = "displayprofile";
	public static final String urlDownloadapk = "downloadapk";
	public static final String urlDownloadinformasi = "downloadinformasi&id=";
	public static final String urlGetprofile = "getprofile";
	public static final String urlJumlahbarisedokumen = "jumlahbarisedokumen";
	public static final String urlJumlahbarisinformasi = "jumlahbarisinformasi";
	public static final String urlJumlahbarisrapat = "jumlahbarisrapat";
	public static final String urlListdokumen = "listdokumen&id_rapat=";
	public static final String urlListedokumen = "listedokumen";
	public static final String urlListinformasi = "listinformasi";
	public static final String urlListrapat = "listrapat";
	public static final String urlListrapat2 = "listrapat2";
	public static final String urlListtgl = "listtgl";
	public static final String urlLogin = "login";
	public static final String urlNoti = "noti";
	public static final String urlReadchat = "readchatt";
	public static final String urlReadchat2 = "readchatt2";
	public static final String urlSendchat = "sendchatt";
	public static final String urlSendchat2 = "sendchatt2";
	public static final String urlSetprofile = "setprofile";
	public static final String urlUndangan = "displayundangan&id=";
	public static final String urlUpdatelogoaccount = "updatelogoaccount";

	private static char[] hexDigitsMD5 = "0123456789abcdef".toCharArray();

	public ServerRequest() {
		super();
	}

	/* Mengirimkan GET request 	 */
	public static String sendGetRequestR(String param){
		StringBuilder stringBuilder = new StringBuilder();
		try {
			URL url = new URL(serverUri+param);
			Log.d("halo : ",url.toString());
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(false);
			huc.setConnectTimeout(3000);
			huc.connect();
			InputStream is = huc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while((line = reader.readLine()) != null){
				stringBuilder.append(line+"\n");
			}
			is.close();


		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
		}

		return stringBuilder.toString();
	}
	public static String sendGetRequestR2(String param){
		StringBuilder stringBuilder = new StringBuilder();
		URL url;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(serverUri+param);

			urlConnection = (HttpURLConnection) url
					.openConnection();

			InputStream in = urlConnection.getInputStream();

			InputStreamReader isw = new InputStreamReader(in);

			int data = isw.read();
			while (data != -1) {
				char current = (char) data;
				data = isw.read();
				Log.d("ServerRequest33 -> ",String.valueOf(current));
			}
//			BufferedReader reader = new BufferedReader(new InputStreamReader(isw));
//			String line = null;
//			while((line = reader.readLine()) != null){
//				Log.d("ServerRequest3 -> ","ke 4");
//				stringBuilder.append(line+"\n");
//			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return "";
	}
	public static String sendGetRequestRF(String param){
		StringBuilder stringBuilder = new StringBuilder();
		try {
			URL url = new URL(param);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(false);
			huc.setConnectTimeout(3000);
			huc.connect();
			InputStream is = huc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while((line = reader.readLine()) != null){
				stringBuilder.append(line+"\n");
			}
			is.close();

		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
		}
		return stringBuilder.toString();
	}

	public static String getUniqueIMEIId(Context context) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return "";
			}
			String imei = telephonyManager.getDeviceId();
			Log.e("imei", "=" + imei);
			if (imei != null && !imei.isEmpty()) {
				return imei;
			} else {
				return android.os.Build.SERIAL;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "not_found";
	}
	public static String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
		String response = "";
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(serverUri + requestURL).openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, Key.STRING_CHARSET_NAME));
			writer.write(getPostDataString(postDataParams));
			writer.flush();
			writer.close();
			os.close();
			if (conn.getResponseCode() == 200) {
				return new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine();
			}
			return "Error Registering";
		} catch (Exception e) {
			e.printStackTrace();
			return response;
		}
	}

	private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			result.append(URLEncoder.encode((String) entry.getKey(), Key.STRING_CHARSET_NAME));
			result.append("=");
			result.append(URLEncoder.encode((String) entry.getValue(), Key.STRING_CHARSET_NAME));
		}
		return result.toString();
	}
	public static byte[] createChecksum(String filename) throws Exception {
		InputStream fis =  new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}

	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public String getMD5Checksum(String filename) {
		String result = "";
		try{
			byte[] b = createChecksum(filename);


			for (int i=0; i < b.length; i++) {
				result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}