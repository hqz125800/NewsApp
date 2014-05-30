package com.example.gridlayouttest;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Tab extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //��Layout�����ListView
        ListView list = (ListView) findViewById(R.id.ListView01);
        
        //���ɶ�̬���飬��������
        ArrayList<HashMap<String, Object>> listItem 
        	= new ArrayList<HashMap<String, Object>>();
        
		try {
			String sql = "select newstitle from top";
			Cursor cursor = MainActivity.database.rawQuery(sql, null);
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();

				do{
	        	HashMap<String, Object> map = new HashMap<String, Object>();
	        	map.put("icon", R.drawable.ee);//ͼ����Դ��ID
	        	map.put("title", cursor.getString(cursor.getColumnIndex("newstitle")));
	        	map.put("msg", "��ظ�����Ŀǰû�ж��¹ʱ�̬");
	        	map.put("time", "5��20�� ");
	        	listItem.add(map);
				}while(cursor.moveToNext());

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
				Intent xinwen = new Intent(Tab.this,Neirong.class);
				startActivity(xinwen);

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
  	
  	//�����˵���Ӧ����
  	@Override
  	public boolean onContextItemSelected(MenuItem item) {
  		setTitle("����˳����˵�����ĵ�"+item.getItemId()+"����Ŀ"); 
  		return super.onContextItemSelected(item);
  	}
  }