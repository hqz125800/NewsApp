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
	
	ArrayList<String> newsids = new ArrayList<String>();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定Layout里面的ListView
        ListView list = (ListView) findViewById(R.id.ListView01);
        
        //生成动态数组，加入数据
        ArrayList<HashMap<String, Object>> listItem 
        	= new ArrayList<HashMap<String, Object>>();
        Bundle bundle = getIntent().getExtras();    
        String table=bundle.getString("Data");//读出数据
		try {
			String sql = "select newstitle,newsdate,newsid from "+table;
			Cursor cursor = MainActivity.database.rawQuery(sql, null);
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();

				if(newsids!=null)
				{
					newsids.clear();
				}
				do{
	        	HashMap<String, Object> map = new HashMap<String, Object>();
	        	map.put("icon", R.drawable.ee);//图像资源的ID
	        	map.put("title", cursor.getString(cursor.getColumnIndex("newstitle")));
	        	map.put("msg", "");
	        	map.put("time", cursor.getString(cursor.getColumnIndex("newsdate")));
	        	newsids.add(cursor.getString(cursor.getColumnIndex("newsid")));
	        	listItem.add(map);
				}while(cursor.moveToNext());

			} else {
		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}


      
        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
            R.layout.list_items,//ListItem的XML实现
            //动态数组与ImageItem对应的子项        
            new String[] {"icon","title","msg", "time"}, 
            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
            new int[] {R.id.icon,R.id.title,R.id.msg,R.id.time}
        );
       
        //添加并且显示
        list.setAdapter(listItemAdapter);
        
        //添加点击
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent content = new Intent(Tab.this,Content.class);
				Bundle mBundle = new Bundle();  
		        mBundle.putString("Data",newsids.get(arg2));//压入数据  
		        content.putExtras(mBundle);
				startActivity(content);

	         ;

			}});

        
        //添加长按点击
          list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
  			
  			@Override
  			public void onCreateContextMenu(ContextMenu menu, View v,
  											ContextMenuInfo menuInfo) {
  				menu.setHeaderTitle("长按菜单-ContextMenu");   
  				menu.add(0, 0, 0, "弹出长按菜单0");
  				menu.add(0, 1, 0, "弹出长按菜单1");   
  			}
  		}); 
      }
  	
  	//长按菜单响应函数
  	@Override
  	public boolean onContextItemSelected(MenuItem item) {
  		setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目"); 
  		return super.onContextItemSelected(item);
  	}
  }