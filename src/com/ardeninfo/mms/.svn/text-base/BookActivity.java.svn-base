package com.wzx.andapp.shh;


import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wzx.andapp.book.BookAdapter;
import com.wzx.andapp.book.BookContentManager;
import com.wzx.andapp.book.BookLayout;

public class BookActivity extends Activity {
	/** Called when the activity is first created. */
	String filepath = "/sdcard/test.txt";
	private ProgressDialog pd;
	private boolean start = false;
	BookLayout bk = null;
	BookAdapter ba = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = BookActivity.this.getIntent();
		if (intent.hasExtra("filepath")) {
			filepath = intent.getStringExtra("filepath");
		}
		bk = new BookLayout(BookActivity.this);
		ba = new BookAdapter(BookActivity.this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		load_data();
	}

	private void load_data() {
		pd = ProgressDialog.show(this,
				getResources().getString(R.string.load_content_title),
				getResources().getString(R.string.load_content_msg), true,
				false);

		Thread thread = new MyRunnable();
		thread.start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (start) {
				return;
			}
			pd.dismiss();
			BookContentManager bcm = BookContentManager
					.getInstance(BookActivity.this);
			bk.setPageAdapter(bcm);
			start = true;
			setContentView(bk);
		}
	};

	class MyRunnable extends Thread {
		public void run() {
			try {
				BookContentManager bcm = BookContentManager
						.getInstance(BookActivity.this);
				bcm.clear();
				bcm.handler = handler;
				bcm.openbook(filepath);
				bk.setTotalPageNum(bcm.getCount());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.a_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		LayoutInflater inflater = LayoutInflater.from(BookActivity.this);
		final View textEntryView = inflater.inflate(
				R.layout.alert_dialog_text_entry, null);

		final EditText pageno_etext = (EditText) textEntryView
				.findViewById(R.id.username_value);

		new AlertDialog.Builder(BookActivity.this)
				.setTitle(
						String.format(
								getResources().getString(
										R.string.jump_tip_title),
								String.valueOf(BookContentManager.getInstance(
										BookActivity.this).getCount())))
				.setView(textEntryView)
				.setPositiveButton(getResources().getString(R.string.ok_btn),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								int indexPage = 0;
								try {
									indexPage = Integer.parseInt(pageno_etext
											.getText().toString()) - 1;
									bk.setIndexPage(indexPage);
									bk.updatePageView();
									// }
								} catch (Exception e) {
									Toast.makeText(
											BookActivity.this,
											String.format(
													getResources()
															.getString(
																	R.string.input_error_toast),
													pageno_etext.getText()
															.toString()),
											Toast.LENGTH_LONG).show();
								}
							}
						})
				.setNegativeButton(
						getResources().getString(R.string.cancel_btn),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Toast.makeText(
								// BookActivity.this,
								// "你选择了确定取消！", Toast.LENGTH_SHORT)
								// .show();
							}
						}).show();
		return true;
	}

}