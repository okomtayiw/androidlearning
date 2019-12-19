package dprdjepara.multisolusi.info.dprdjepara;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
//import android.os.Message;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;
import dprdjepara.multisolusi.info.dprdjepara.ui.FileDownloader;

//import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.pdf.PdfRenderer;

import static android.R.attr.path;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.core.content.FileProvider;

public class PreviewActivity extends Activity {

	private ServerRequest server;
	private String Imei, Username, BarisPerHalaman, Role, responsejenis, id_dokumen, jenispreview;
	private LinearLayout LLDetailDokumen;
	private WebView WVDetailDokumen;
	//private PDFView pdfView;
	private Integer pageNumber = 1;
	private ProgressDialog mProgressDialog ;
	private String fileName,fileType;
	private ImageView iv_pdf;
	private int currentPage=0;
	private Button bt_previous,bt_next;
	Matrix matrix = new Matrix();
	Float scale=1f;
	ScaleGestureDetector SGD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		Username = getIntent().getStringExtra("username");
		Role = getIntent().getStringExtra("role");
		BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
		//jenispreview = getIntent().getStringExtra("jenispreview");
		server = new ServerRequest();
		initView();

	}
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scale = scale * detector.getScaleFactor();
			scale = Math.max(0.1f, Math.min(scale,5f));
			matrix.setScale(scale,scale);
			iv_pdf.setImageMatrix(matrix);
			return true;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		SGD.onTouchEvent(event);
		return true;
	}
	private void initView() {
		//TextView tv_judul =(TextView) findViewById(R.id.tv_judul);
		//tv_judul.setText("-"+getIntent().getStringExtra("jenispreview")+"-");
		WVDetailDokumen = (WebView) findViewById(R.id.WVDetailDokumen);
		//pdfView = (PDFView) findViewById(R.id.pdfview);
		iv_pdf =(ImageView) findViewById(R.id.iv_pdf);
		//final ImageView zoom = (ImageView) findViewById(R.id.imageView);
		//final Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
		//iv_pdf.startAnimation(zoomAnimation);
		SGD = new ScaleGestureDetector(this, new ScaleListener());

		bt_previous=(Button) findViewById(R.id.bt_previous);
		bt_next=(Button) findViewById(R.id.bt_next);
		bt_next.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				currentPage++;
				tampilpdf(fileName);
			}
		});
		bt_previous.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				currentPage--;
				tampilpdf(fileName);
			}
		});
		fileType = getIntent().getStringExtra("filetype");
		fileType=fileType.substring(fileType.length()-4,fileType.length()-1);
		String urlnya,namafilenya;
		urlnya="";namafilenya="";
		File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "DPRDJepara");if (!f.exists()) {f.mkdirs();}
		File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara", "Lampiran");if (!f1.exists()) {f1.mkdirs();}
		File f2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara", "Perda");if (!f2.exists()) {f2.mkdirs();}

		if (getIntent().getStringExtra("jenispreview").trim().equals("dokumen")) {
			urlnya=server.urlDisplaydokumen + getIntent().getStringExtra("id");
			namafilenya=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara/Lampiran/"+getIntent().getStringExtra("id")+"_"+getIntent().getStringExtra("filename");
		} else {
			urlnya=server.urlDisplayedokumen + getIntent().getStringExtra("id");
			namafilenya=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara/Perda/"+getIntent().getStringExtra("id")+"_"+getIntent().getStringExtra("filename");

		}

		if (fileType != "pdf") {
			WVDetailDokumen.getSettings().setJavaScriptEnabled(true); // enable javascript
			WVDetailDokumen.getSettings().setSupportZoom(true);
			WVDetailDokumen.getSettings().setBuiltInZoomControls(true);
			WVDetailDokumen.getSettings().setDisplayZoomControls(true);
			//WVDetailDokumen.loadUrl(server.urlDisplaydokumen + getIntent().getStringExtra("id"));
			iv_pdf.setVisibility(GONE);
			bt_previous.setVisibility(GONE);
			bt_next.setVisibility(GONE);
			WVDetailDokumen.setVisibility(VISIBLE);
		} else {
			iv_pdf.setVisibility(VISIBLE);
			bt_previous.setVisibility(VISIBLE);
			bt_next.setVisibility(VISIBLE);
			WVDetailDokumen.setVisibility(GONE);
		}

		mProgressDialog = new ProgressDialog(PreviewActivity.this);
		mProgressDialog.setMessage("Downloading...");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);

		final DownloadFile downloadTask = new DownloadFile(PreviewActivity.this);
		downloadTask.execute(urlnya,namafilenya);

		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				downloadTask.cancel(true);
			}
		});

	}
	private void tampilgambar(String filenya) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		File file = new File(filenya);

		MimeTypeMap mime = MimeTypeMap.getSingleton();
		String ext = file.getName().substring(file.getName().indexOf(".") + 1);
		String type = mime.getMimeTypeFromExtension(ext);

		Uri path = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);
		intent.setDataAndType(path, type);

		startActivity(intent);

	}

	private void tampilpdf(String filenya) {
		try {
			int REQ_WIDTH = iv_pdf.getWidth();
			int REQ_HEIGHT = iv_pdf.getHeight();
			Bitmap bitmap = Bitmap.createBitmap(REQ_WIDTH,REQ_HEIGHT, Bitmap.Config.ARGB_4444);
			File file = new File(filenya);
			PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
			if(currentPage <0) {
				currentPage=0;
			} else if(currentPage>renderer.getPageCount()) {
				currentPage=renderer.getPageCount() - 1;
			}
			Matrix m =iv_pdf.getImageMatrix();
			Rect rect = new Rect(0,0,REQ_WIDTH,REQ_HEIGHT);
			renderer.openPage(currentPage).render(bitmap,rect,m,PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
			iv_pdf.setImageMatrix(m);
			iv_pdf.setImageBitmap(bitmap);
			iv_pdf.invalidate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*
		File pdfFile = new File(
				filenya);
		Uri path = Uri.fromFile(pdfFile);
		Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
		pdfIntent.setDataAndType(path, "application/pdf");
		pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		try{
			startActivity(pdfIntent);
		}catch(ActivityNotFoundException e){
			Toast.makeText(PreviewActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
		}
		*/
	}
	private class DownloadFile extends AsyncTask<String, Integer, String> {
		private Context context;
		private PowerManager.WakeLock mWakeLock;

		public DownloadFile(Context context) {
			this.context = context;
		}
		@Override
		protected String doInBackground(String... strings) {
			fileName = strings[1];  // -> maven.pdf
			InputStream input = null;
			OutputStream output = null;
			HttpURLConnection connection = null;
			try {
				URL url = new URL(strings[0]);
				connection = (HttpURLConnection) url.openConnection();
				connection.connect();
				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return "Server returned HTTP " + connection.getResponseCode()
							+ " " + connection.getResponseMessage();
				}
				int fileLength = connection.getContentLength();
				input = connection.getInputStream();

				output = new FileOutputStream(fileName);

				byte data[] = new byte[4096];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					// allow canceling with back button
					if (isCancelled()) {
						input.close();
						return null;
					}
					total += count;
					// publishing the progress....
					if (fileLength > 0) // only if total length is known
						publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}
			} catch (Exception e) {
				return e.toString();
			} finally {
				try {
					if (output != null)
						output.close();
					if (input != null)
						input.close();
				} catch (IOException ignored) {
				}

				if (connection != null)
					connection.disconnect();
			}
			return null;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// take CPU lock to prevent CPU from going off if the user
			// presses the power button during download
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
					getClass().getName());
			mWakeLock.acquire();
			mProgressDialog.show();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
			// if we get here, length is known, now set indeterminate to false
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMax(100);
			mProgressDialog.setProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			mWakeLock.release();
			mProgressDialog.dismiss();
			if (result != null)
				Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
			else {
				Toast.makeText(context, fileName + " downloaded", Toast.LENGTH_SHORT).show();
				if (fileType != "pdf")
					tampilgambar(fileName);
				else tampilpdf(fileName);

			}
		}

	}



}