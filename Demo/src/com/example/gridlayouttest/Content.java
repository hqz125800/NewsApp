package com.example.gridlayouttest;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Content extends Activity {
	
	TextView title;
	TextView date;
	TextView content;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        
        title = (TextView)findViewById(R.id.title1);
        date = (TextView)findViewById(R.id.msg1);
        content = (TextView)findViewById(R.id.content1);
        Bundle bundle = getIntent().getExtras();    
        String newsid=bundle.getString("Data");//¶Á³öÊý¾Ý
        String sql = "select newstitle,newsdate,newsauthor,newscontent from top where newsid='"+newsid+"'";
        Cursor cursor = MainActivity.database.rawQuery(sql, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			title.setText(cursor.getString(cursor.getColumnIndex("newstitle")));
			date.setText(cursor.getString(cursor.getColumnIndex("newsauthor"))+"  "+cursor.getString(cursor.getColumnIndex("newsdate")));
			content.setText(Html.fromHtml(cursor.getString(cursor.getColumnIndex("newscontent"))));
		}
	}
}