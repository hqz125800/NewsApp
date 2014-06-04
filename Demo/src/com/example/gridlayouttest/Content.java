package com.example.gridlayouttest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class Content extends Activity {

	TextView title;
	TextView date;
	TextView content;
	ImageView imageView;

	Bitmap bitmap;

	final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/newsapp/";

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 关闭

				imageView.setImageBitmap(bitmap);

				break;
			}
		}
	};

	/**
	 * 保存方法
	 * 
	 * @throws IOException
	 */
	public void saveBitmap(String picName, Bitmap bm)  {

		File dirFile = new File(ALBUM_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ALBUM_PATH + picName);

		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(
					new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);

		title = (TextView) findViewById(R.id.title1);
		date = (TextView) findViewById(R.id.msg1);
		content = (TextView) findViewById(R.id.content1);
		imageView = (ImageView) findViewById(R.id.icon1);
		Bundle bundle = getIntent().getExtras();
		String newsid = bundle.getString("Data");// 读出数据
		String sql = "select newstitle,newsdate,newsauthor,newscontent,newspic from top where newsid='"
				+ newsid + "'";
		final Cursor cursor = MainActivity.database.rawQuery(sql, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			title.setText(cursor.getString(cursor.getColumnIndex("newstitle")));
			date.setText(cursor.getString(cursor.getColumnIndex("newsauthor"))
					+ "  "
					+ cursor.getString(cursor.getColumnIndex("newsdate")));
			content.setText(Html.fromHtml(cursor.getString(cursor
					.getColumnIndex("newscontent"))));
			File myCaptureFile = new File(ALBUM_PATH + cursor.getString(
					cursor.getColumnIndex("newspic"))
					.split(",")[1]);
			if(myCaptureFile.exists())
			{
				 Bitmap bm = BitmapFactory.decodeFile(Content.ALBUM_PATH + cursor.getString(
							cursor.getColumnIndex("newspic"))
							.split(",")[1]);
				 imageView.setImageBitmap(bm);
			}else if (!cursor.getString(cursor.getColumnIndex("newspic")).equals("")) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// 这里下载数据
						try {
							URL url = new URL(cursor.getString(
									cursor.getColumnIndex("newspic"))
									.split(",")[0]);
							HttpURLConnection conn = (HttpURLConnection) url
									.openConnection();
							conn.setDoInput(true);
							conn.connect();
							InputStream inputStream = conn.getInputStream();
							bitmap = BitmapFactory.decodeStream(inputStream);

							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
							saveBitmap(
									cursor.getString(
											cursor.getColumnIndex("newspic"))
											.split(",")[1], bitmap);

						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();

			}else{
				
			}

		}
	}

}