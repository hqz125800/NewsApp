package com.example.gridlayouttest;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
//Download by http://www.codefans.net
public class MainActivity extends Activity {


	private static final String MORE = "more";
	private static final String FASHION = "fashion";
	private static final String FINANCE = "finance";
	private static final String TIYU = "tiyu";
	private static final String YULE = "yule";
	private static final String TRAVEL = "travel";
	private static final String MILITARY = "military";
	private static final String TOP = "top";
	private static final String[] tags = {TOP,MILITARY,TRAVEL,YULE,TIYU,FINANCE,FASHION,MORE}; 
	private TextView textView;
	private TextView textView0;
	static SQLiteDatabase database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataBaseHelper myDbHelper = new DataBaseHelper(this);

		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

			database = myDbHelper.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}
		
//		String sqltest = "INSERT INTO top(`newspic`, `newsid`, `newstitle`, `newsauthor`, `newscontent`, `newsurl`, `newsdate`)VALUES('', 'b9a9413fa9bc49e69ac7d5f2f908e599','���ۣ����������ɡ�ʧЧ͹�����ɻع�','(���α༭��un649)','����ϲ��������ܽ�����ĸ��֡��������ɡ������й�������ʵ���ˣ�����Խ��Խ�������й��ķ������ˣ���Ϊ��Щ���������ɡ�����ʵ��ǰʧЧ�ˣ��������Ͳ��˵��µķ�����ʵ��<br>�������磬����һЩ���ܽ�����ġ��������ɡ���һ����Ա�������Ǹ߹٣������ˣ����ھ��ǡ���ȫ��½������ʹ�����⣬Ҳ����׷���ˡ��������ί����Ϣ��������������ɡ�������ʡ��Эԭ���鸱��ǡ�����ϯ��������������Υ��Υ����Ŀǰ��������֯���顣����6��19�գ���������ʽ����ǡ��һ�ꡣ��ǰ���ж�λ���ݸ߹ٱ����顣ֻҪƨ�ɲ��ɾ������������ᱻ׷�����������ˣ����ⲻ���Զ������ϴ�ף���ȨʱȨ�����µĺۼ����ᱻĨȥ��û�п������ڵ����׷���Ļ���Ȩ��<br>�����ٶ�ġ��������ɡ������Ȳ���һ�������ķ�����ʶ�������ֻ������©��ֻҪ����Ȩ���������ˣ����Ḷ�����ۣ�����ʲôʱ�򣬲�������ʲô�ˣ��������Ƕ��Ĺ١�������û�������Ĺ��ɿ�ѭ��Ψһ�����Ĺ��ɾ��ǡ����ֱر�׽����<br>����ר��ϲ���ܽ���֡��������ɡ��������ڴ�������ί�鴦�İ�����Ѱ��һЩ���Է������ƺ�Ԥ��δ���Ĺ��ɣ��Ӷ��Խ�Ҫ���������������жϡ������ܽ���˺ܶ��������ƺ����е���Ĺ��ɣ����縯�ܵ�59��������Щ��λ�ǡ����ܸ�Σ��λ���������ϵĹ�����������Ԥ���Ա�����������̲���ĳ���𡢹�����ĳ������Ͱ�ȫ�ˣ����ϴ��ű��ٷ���ҥ��ʹ��������Ա����ȫ���ء��ˡ����У���ί�ɲ�һ�㶼�ܰ�ȫ���Լ��˲����Լ��ˡ�<br>�������֡��������ɡ��������Ǹ������վ���ͼ��а����ܽ������֪����Щ������һ��ʱ����Ҳȷʵ��һ����Ԥ���������ܽ���һЩ�����䱳��ȴ��ӳ�����ǶԷ����Ĳ����Σ����ŷ��ɣ����š����ܱر�׽��������ɣ����������кܶ�����ڷ���֮�ϵ����ػ�Ӱ�췴���ܡ����ǲ��Ǹ���һ����Ա����Ϊ�ͷ���ȥ�жϷ������򣬶��Ǹ���Ȩ����Ǳ��������ε�˼άȥ�ܽ���жϣ�ֻҪ�ڡ������������͵����ϳ����ˣ����ǰ�ȫ�ģ�ֻҪ������һ���ļ�������˼�Ӱ��Ҳ����鴦����<br>����Ҳ����˵����Щ���ɶ��Ǹ��ݡ����Ρ�˼ά�ܽ�ģ���û�з��η�����Ӱ�ӣ������Ż���ȫ���ݷ���ȥ�鴦̰�٣�����֮�����������ü������������ڲ��ݷ��ɡ�������ǰ������Щ��������ȥ�жϣ�Ҳ�����Բ�ˬ����Щ���������ɡ�����Ч�����飬�����ŷ���������ɵķ�����<br>�����������ڵ����ⳡ�����α�Ϊ�α�Ӯ��ʱ�䡱�ķ����籩�У���ȥ��Щ���ɶ�ʧЧ�ˡ����磬�ӵ������ر��ı�������������Ա�������ˣ�����3�£������ί��������ʡ��ʡ��Ҧľ����������Υ��Υ��������֯����ʱ�����ۺ��Ǿ��ȣ���Ϊ��Ҧ����ĵ��죬���ػ��ر�������������Ϊ��ʡ����������ʡ��Ҧľ���������¡���ʡ�����飬���ع�ý��Ȼһ�㶼��֪����<br>�������������ࡱ�Ĺ������������һ��ϰ�ߣ�����ͨ�������͵����ϵ���Ϣ���жϹ�Ա��̰���鴫�ŵ���٣����������쵼��������¶�浱�����εķ���ꡣ��������д��ų�ĳ����Ա�ܵ������ˣ����ǻ�ȥ�����ر���������˽�����û���ڱ�ֽ�ϳ��֡�������¶�棬��û���ڻ�й������֡���������ˣ���˵����Щ���ſ����Ǽٵģ����û��¶�棬���ܾ��������ˡ��ɴ��Ͼ�ǰ�г�����ҵ������ǰ��ʡ��Ҧľ���ı��飬���������ȫʧЧ��<br>��������Ҳ��Ϊ���������ĳ����Ա���飬���ٷ�Ѹ�ٱ�ҥ�����Աû���⣬��ȥ����Ϊ�����Ա����ȫ���ء��ˡ��ɴ������б��飬�����ڱ����й����ֹ�����ͨ�����ԭ�ֳ��ν�������֤���������Ҳ�����רD����ǰ�ν������������Ӹ��ܱ����飬�ɹٷ��ܿ��ҥ��������ų��档����һ�����£����������ɡ�������˼ά��Ť�������󣬱����ų����ָ��ţ��ع顰���ֱر�׽�����Ψһ�ķ�����ʶ���������ɡ�<br>','http://star.news.sohu.com/20140529/n400178323.shtml','2014-05-29 05:15:31')";
//		MainActivity.database.execSQL(sqltest);
		this.startService(new Intent(this, SocketService.class));  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tablelayout);
		textView = (TextView)findViewById(R.id.textView1);
		textView0 = (TextView)findViewById(R.id.textView2);
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,Tab.class);
				startActivity(intent);
			}
		});
		
		textView0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,Tab.class);
				Bundle mBundle = new Bundle();  
		        mBundle.putString("Data",TOP);//ѹ������  
		        intent.putExtras(mBundle);
				startActivity(intent);
			}
		});
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.stopService(new Intent(this, SocketService.class));  
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
