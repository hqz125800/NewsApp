package com.example.gridlayouttest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Tab extends Activity {
	
	private static final String FILE_DIR = "./newsAPP/";
	ArrayList<String> newsids = new ArrayList<String>();
	ImageView imageView;


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //��Layout�����ListView
        ListView list = (ListView) findViewById(R.id.ListView01);
        
        //���ɶ�̬���飬��������
        ArrayList<HashMap<String, Object>> listItem 
        	= new ArrayList<HashMap<String, Object>>();
        Bundle bundle = getIntent().getExtras();    
        String table=bundle.getString("Data");//��������
		try {
			String sql = "select newstitle,newsdate,newsid,newspic from "+table;
			final Cursor cursor = MainActivity.database.rawQuery(sql, null);
			if (cursor.getCount() > 0) {
				cursor.moveToLast();

				if(newsids!=null)
				{
					newsids.clear();
				}
				int i = 0;
				do{
	        	HashMap<String, Object> map = new HashMap<String, Object>();
	        	
	        	File myCaptureFile = new File(Content.ALBUM_PATH + cursor.getString(
						cursor.getColumnIndex("newspic"))
						.split(",")[1]);
	        	
				if(myCaptureFile.exists())
				{
					 Bitmap bm = BitmapFactory.decodeFile(Content.ALBUM_PATH + cursor.getString(
								cursor.getColumnIndex("newspic"))
								.split(",")[1]);
					 Drawable drawable =new BitmapDrawable(bm);
					 map.put("icon",drawable);//ͼ����Դ��ID
				
				}
	        	map.put("title", cursor.getString(cursor.getColumnIndex("newstitle")));
	        	map.put("msg", "");
	        	map.put("time", cursor.getString(cursor.getColumnIndex("newsdate")));
	        	newsids.add(cursor.getString(cursor.getColumnIndex("newsid")));
	        	if (!cursor.getString(cursor.getColumnIndex("newspic")).equals("")){


	        	listItem.add(map);

	        	}
				}while(cursor.moveToPrevious());

			} else {
		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}


      
        //������������Item�Ͷ�̬�����Ӧ��Ԫ��
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//����Դ 
            R.layout.list_items,//ListItem��XMLʵ��
            //��̬������ImageItem��Ӧ������        
            new String[] {"icon","title","msg", "time"}, 
            //ImageItem��XML�ļ������һ��ImageView,����TextView ID
            new int[] {R.id.icon,R.id.title,R.id.msg,R.id.time}
        );
        //��Ӳ�����ʾ
        list.setAdapter(listItemAdapter);
        
        //��ӵ��
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent content = new Intent(Tab.this,Content.class);
				Bundle mBundle = new Bundle();  
		        mBundle.putString("Data",newsids.get(arg2));//ѹ������  
		        content.putExtras(mBundle);
				startActivity(content);

	         ;

			}});

        
        //��ӳ������
          list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
  			
  			@Override
  			public void onCreateContextMenu(ContextMenu menu, View v,
  											ContextMenuInfo menuInfo) {
  				menu.setHeaderTitle("�����˵�-ContextMenu");   
  				menu.add(0, 0, 0, "���������˵�0");
  				menu.add(0, 1, 0, "���������˵�1");   
  			}
  		}); 
      }
	
	private void storeInSD(Bitmap bitmap,String filename) {
		File file = new File(FILE_DIR);
		if (!file.exists()) {
			file.mkdir();
		}
		File imageFile = new File(file, filename);
		try {
			imageFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(CompressFormat.JPEG, 50, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  	
  	//�����˵���Ӧ����
  	@Override
  	public boolean onContextItemSelected(MenuItem item) {
  		setTitle("����˳����˵�����ĵ�"+item.getItemId()+"����Ŀ"); 
  		return super.onContextItemSelected(item);
  	}
  }