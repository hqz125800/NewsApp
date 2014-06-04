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
	private TextView tt;
	private TextView cj;
	private TextView js;
	private TextView yl;
	private TextView ly;
	private TextView ty;
	private TextView ss;
	private TextView gd;
	
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
		
//		String sqltest = "INSERT INTO top(`newspic`, `newsid`, `newstitle`, `newsauthor`, `newscontent`, `newsurl`, `newsdate`)VALUES('', 'b9a9413fa9bc49e69ac7d5f2f908e599','ÆÀÂÛ£º¡°·´¸¯¹æÂÉ¡±Ê§Ð§Í¹ÏÔÌúÂÉ»Ø¹é','(ÔðÈÎ±à¼­£ºun649)','¡¡¡¡Ï²»¶ÓÃÃñ¼ä×Ü½á³öÀ´µÄ¸÷ÖÖ¡°·´¸¯¹æÂÉ¡±·ÖÎöÖÐ¹ú·´¸¯ÏÖÊµµÄÈË£¬¿ÉÄÜÔ½À´Ô½¿´²»¶®ÖÐ¹úµÄ·´¸¯°ÜÁË£¬ÒòÎªÄÇÐ©¡°·´¸¯¹æÂÉ¡±ÔÚÏÖÊµÃæÇ°Ê§Ð§ÁË£¬¸ù±¾½âÊÍ²»ÁËµ±ÏÂµÄ·´¸¯ÏÖÊµ¡£<br>¡¡¡¡±ÈÈç£¬°´ÕÕÒ»Ð©ÈË×Ü½á³öÀ´µÄ¡°·´¸¯¹æÂÉ¡±£¬Ò»¸ö¹ÙÔ±£¨ÓÈÆäÊÇ¸ß¹Ù£©ÍËÐÝÁË£¬µÈÓÚ¾ÍÊÇ¡°°²È«×ÅÂ½¡±£¬¼´Ê¹ÓÐÎÊÌâ£¬Ò²²»»á×·¾¿ÁË¡£µ«ÖÐÑë¼ÍÎ¯µÄÏûÏ¢´òÆÆÁËÕâ¸ö¡°¹æÂÉ¡±£ººþÄÏÊ¡ÕþÐ­Ô­µ³×é¸±Êé¼Ç¡¢¸±Ö÷Ï¯Ñô±¦»ªÉæÏÓÑÏÖØÎ¥¼ÍÎ¥·¨£¬Ä¿Ç°Õý½ÓÊÜ×éÖ¯µ÷²é¡£¶øµ½6ÔÂ19ÈÕ£¬Ñô±¦»ªÕýÊ½ÍËÐÝÇ¡ÂúÒ»Äê¡£´ËÇ°ÒÑÓÐ¶àÎ»ÍËÐÝ¸ß¹Ù±»µ÷²é¡£Ö»ÒªÆ¨¹É²»¸É¾»£¬ÍËÐÝÕÕÑù»á±»×·¾¿£¬ÈËÍËÐÝÁË£¬ÎÊÌâ²»»á×Ô¶¯ÇåÁãºÍÏ´°×£¬ÕÆÈ¨Ê±È¨Á¦ÁôÏÂµÄºÛ¼£²»»á±»Ä¨È¥£¬Ã»ÓÐ¿ÉÒÔÃâÓÚµ÷²éºÍ×·¾¿µÄ»íÃâÈ¨¡£<br>¡¡¡¡ÔÙ¶àµÄ¡°·´¸¯¹æÂÉ¡±£¬¶¼±È²»ÉÏÒ»¸ö»ù±¾µÄ·´¸¯³£Ê¶£ºÌìÍø»Ö»ÖÊè¶ø²»Â©£¬Ö»ÒªÀÄÓÃÈ¨Á¦ÂÒÉìÊÖÁË£¬¶¼»á¸¶³ö´ú¼Û£¬²»¹ÜÊ²Ã´Ê±ºò£¬²»¹ÜÄãÊÇÊ²Ã´ÈË£¬²»¹ÜÄãÊÇ¶à´óµÄ¹Ù¡£·´¸¯²¢Ã»ÓÐÆäËûµÄ¹æÂÉ¿ÉÑ­£¬Î¨Ò»µÄÌúµÄ¹æÂÉ¾ÍÊÇ¡°ÉìÊÖ±Ø±»×½¡±¡£<br>¡¡¡¡×¨¼ÒÏ²»¶×Ü½á¸÷ÖÖ¡°·´¸¯¹æÂÉ¡±£¬ÈÈÖÔÓÚ´ÓÒÔÍù¼ÍÎ¯²é´¦µÄ°¸ÀýÖÐÑ°ÕÒÒ»Ð©¿ÉÒÔ·ÖÎö×ßÊÆºÍÔ¤²âÎ´À´µÄ¹æÂÉ£¬´Ó¶ø¶Ô½«Òª·¢ÉúµÄÊÂÇé×÷³öÅÐ¶Ï¡£ÈËÃÇ×Ü½á³öÁËºÜ¶àÌýÆðÀ´ËÆºõºÜÓÐµÀÀíµÄ¹æÂÉ£º±ÈÈç¸¯°ÜµÄ59ËêÏÖÏó£¬ÄÄÐ©¸ÚÎ»ÊÇ¡°¸¯°Ü¸ßÎ£¸ÚÎ»¡±£¬µ³±¨ÉÏµÄ¹«¿ª±¨µÀ¿ÉÒÔÔ¤²â¹ÙÔ±ÕþÖÎÉúÃü£¬ÐÌ²»ÉÏÄ³¼¶±ð¡¢¹Ù×öµ½Ä³¸ö¼¶±ð¾Í°²È«ÁË£¬ÍøÉÏ´«ÎÅ±»¹Ù·½±ÙÒ¥ºó¾Í´ú±íÕâ¸ö¹ÙÔ±¡°°²È«¹ý¹Ø¡±ÁË¡£»¹ÓÐ£¬¼ÍÎ¯¸É²¿Ò»°ã¶¼ºÜ°²È«£¬×Ô¼ºÈË²»²é×Ô¼ºÈË¡£<br>¡¡¡¡ÖÖÖÖ¡°·´¸¯¹æÂÉ¡±£¬ÊÇÈËÃÇ¸ù¾ÝÍùÈÕ¾­ÑéºÍ¼ÈÓÐ°¸Àý×Ü½á³öµÄÈÏÖª£¬ÕâÐ©¹æÂÉÔÚÒ»¶¨Ê±ÆÚÄÚÒ²È·ÊµÓÐÒ»¶¨µÄÔ¤²âÄÜÁ¦£¬ÄÜ½âÊÍÒ»Ð©ÏÖÏó£¬Æä±³ºóÈ´·´Ó³ÁËÈËÃÇ¶Ô·´¸¯µÄ²»ÐÅÈÎ£º²»ÐÅ·¨ÂÉ£¬²»ÐÅ¡°¸¯°Ü±Ø±»×½¡±Õâ¸öÌúÂÉ£¬¶øÊÇÏàÐÅÓÐºÜ¶àÁè¼ÝÓÚ·¨ÂÉÖ®ÉÏµÄÒòËØ»áÓ°Ïì·´¸¯°Ü¡£ÈËÃÇ²»ÊÇ¸ù¾ÝÒ»¸ö¹ÙÔ±µÄÐÐÎªºÍ·¨ÂÉÈ¥ÅÐ¶Ï·´¸¯×ßÏò£¬¶øÊÇ¸ù¾ÝÈ¨Á¦µÄÇ±¹æÔòºÍÈËÖÎµÄË¼Î¬È¥×Ü½áºÍÅÐ¶Ï£ºÖ»ÒªÔÚ¡¶ÐÂÎÅÁª²¥¡·ºÍµ³±¨ÉÏ³öÏÖÁË£¬¾ÍÊÇ°²È«µÄ£»Ö»Òª¹Ù×öµ½Ò»¶¨µÄ¼¶±ð£¬ÉÏÃæ¹Ë¼ÉÓ°ÏìÒ²²»»á²é´¦Ëû¡£<br>¡¡¡¡Ò²¾ÍÊÇËµ£¬ÕâÐ©¹æÂÉ¶¼ÊÇ¸ù¾Ý¡°ÈËÖÎ¡±Ë¼Î¬×Ü½áµÄ£¬¶øÃ»ÓÐ·¨ÖÎ·´¸¯µÄÓ°×Ó£¬²»ÏàÐÅ»áÍêÈ«ÒÀ¾Ý·¨ÂÉÈ¥²é´¦Ì°¹Ù£¬·¨ÖÎÖ®ÍâÓÐÎÞÊý¿´µÃ¼û¿´²»¼ûµÄÊÖÔÚ²Ù×Ý·¨ÂÉ¡£ÈËÃÇÒÔÇ°¸ù¾ÝÕâÐ©·´¸¯¹æÂÉÈ¥ÅÐ¶Ï£¬Ò²ÄÜÂÅÊÔ²»Ë¬¡£ÄÇÐ©¡°·´¸¯¹æÂÉ¡±µÄÓÐÐ§ºÍÁéÑé£¬·´³Ä×Å·¨ÖÎÕâ¸öÌúÂÉµÄ·¦Á¦¡£<br>¡¡¡¡²»¹ý£¬ÔÚµ±ÏÂÕâ³¡¡°ÒÔÖÎ±êÎªÖÎ±¾Ó®µÃÊ±¼ä¡±µÄ·´¸¯·ç±©ÖÐ£¬¹ýÈ¥ÄÇÐ©¹æÂÉ¶¼Ê§Ð§ÁË¡£±ÈÈç£¬´Óµ³±¨»ú¹Ø±¨µÄ±¨µÀ¾ø¿´²»³ö¹ÙÔ±µÄÃüÔËÁË£¬½ñÄê3ÔÂ£¬ÖÐÑë¼ÍÎ¯Ðû²¼½­Î÷Ê¡¸±Ê¡³¤Ò¦Ä¾¸ùÉæÏÓÑÏÖØÎ¥¼ÍÎ¥·¨½ÓÊÜ×éÖ¯µ÷²éÊ±£¬ÓßÂÛºÜÊÇ¾ªÑÈ£¬ÒòÎªÔÚÒ¦ÂäÂíµÄµ±Ìì£¬µ±µØ»ú¹Ø±¨»¹¿¯·¢ÁËÊðÃûÎª¡°Ê¡ÈËÃñÕþ¸®¸±Ê¡³¤Ò¦Ä¾¸ù¡±µÄÎÄÕÂ¡£¸±Ê¡³¤±»²é£¬µ±µØ¹ÙÃ½¾¹È»Ò»µã¶¼²»ÖªµÀ¡£<br>¡¡¡¡¡°²»Ã÷ÕæÏà¡±µÄ¹«ÖÚÔç¾ÍÑø³ÉÁËÒ»¸öÏ°¹ß£¬¾ÍÊÇÍ¨¹ýµ³±¨ºÍµçÊÓÉÏµÄÏûÏ¢À´ÅÐ¶Ï¹ÙÔ±ÉæÌ°±»²é´«ÎÅµÄÕæ¼Ù£¬½«µ³±¨ÉÏÁìµ¼µÄÅÅÃûºÍÂ¶Ãæµ±×÷ÕþÖÎµÄ·çÏò±ê¡£Èç¹ûÍøÉÏÓÐ´«ÎÅ³ÆÄ³¸ö¹ÙÔ±ÊÜµ½µ÷²éÁË£¬ÈËÃÇ»áÈ¥¿´»ú¹Ø±¨£¬¿´Õâ¸öÈË½üÆÚÓÐÃ»ÓÐÔÚ±¨Ö½ÉÏ³öÏÖ¡¢µçÊÓÉÏÂ¶Ãæ£¬ÓÐÃ»ÓÐÔÚ»î¶¯ÖÐ¹«¿ª³öÏÖ¡£Èç¹û³öÏÖÁË£¬¾ÍËµÃ÷ÄÇÐ©´«ÎÅ¿ÉÄÜÊÇ¼ÙµÄ£¬Èç¹ûÃ»ÓÐÂ¶Ãæ£¬¿ÉÄÜ¾ÍÓÐÎÊÌâÁË¡£¿É´ÓÄÏ¾©Ç°ÊÐ³¤¼¾½¨Òµµ½½­Î÷Ç°¸±Ê¡³¤Ò¦Ä¾¸ùµÄ±»²é£¬Õâ¸ö¹æÂÉÍêÈ«Ê§Ð§¡£<br>¡¡¡¡ÈËÃÇÒ²ÒÔÎª£¬Èç¹ûÍø´«Ä³¸ö¹ÙÔ±±»²é£¬µ«¹Ù·½Ñ¸ËÙ±ÙÒ¥³ÆÕâ¹ÙÔ±Ã»ÎÊÌâ£¬¹ýÈ¥¶¼ÈÏÎªÕâ¸ö¹ÙÔ±¡°°²È«¹ý¹Ø¡±ÁË¡£¿É´ÓÁõÌúÄÐ±»²é£¬µ½½üÆÚ±±¾©ÊÐ¹«°²¾Ö¹«°²½»Í¨¹ÜÀí¾ÖÔ­¾Ö³¤ËÎ½¨¹úÂäÂí£¬Ö¤Ã÷Õâ¸ö¹æÂÉÒ²²»¿¿Æ×¨DÁ½ÄêÇ°ËÎ½¨¹úÔø±»´«ÉæÏÓ¸¯°Ü±»µ÷²é£¬¿É¹Ù·½ºÜ¿ì±ÙÒ¥£¬Á½Äêºó´«ÎÅ³ÉÕæ¡£ÕâÊÇÒ»¼þºÃÊÂ£¬¡°·´¸¯¹æÂÉ¡±ÊÇÈËÖÎË¼Î¬ÏÂÅ¤ÇúµÄÏÖÏó£¬±ØÐëÅÅ³ýÖÖÖÖ¸ÉÈÅ£¬»Ø¹é¡°ÉìÊÖ±Ø±»×½¡±Õâ¸öÎ¨Ò»µÄ·´¸¯³£Ê¶ºÍÀôÖÎÌúÂÉ¡£<br>','http://star.news.sohu.com/20140529/n400178323.shtml','2014-05-29 05:15:31')";
//		MainActivity.database.execSQL(sqltest);
		this.startService(new Intent(this, SocketService.class));  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tablelayout);
		tt = (TextView)findViewById(R.id.textView1);
		cj = (TextView)findViewById(R.id.textView2);
		js = (TextView)findViewById(R.id.textView3);
		yl = (TextView)findViewById(R.id.textView4);
		ly = (TextView)findViewById(R.id.textView5);
		ty = (TextView)findViewById(R.id.textView6);
		ss = (TextView)findViewById(R.id.textView7);
		gd = (TextView)findViewById(R.id.textView8);
		tt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,Tab.class);
				Bundle mBundle = new Bundle();  
		        mBundle.putString("Data",TOP);//Ñ¹ÈëÊý¾Ý  
		        intent.putExtras(mBundle);
				startActivity(intent);
			}
		});
		
		cj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,Tab.class);
				Bundle mBundle = new Bundle();  
		        mBundle.putString("Data",FINANCE);//Ñ¹ÈëÊý¾Ý  
		        intent.putExtras(mBundle);
				startActivity(intent);
			}
		});
	
	
	js.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,Tab.class);
			Bundle mBundle = new Bundle();  
	        mBundle.putString("Data",MILITARY);//Ñ¹ÈëÊý¾Ý  
	        intent.putExtras(mBundle);
			startActivity(intent);
		}
	});
	
	yl.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,Tab.class);
			Bundle mBundle = new Bundle();  
	        mBundle.putString("Data",YULE);//Ñ¹ÈëÊý¾Ý  
	        intent.putExtras(mBundle);
			startActivity(intent);
		}
	});
	ly.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,Tab.class);
			Bundle mBundle = new Bundle();  
	        mBundle.putString("Data",TRAVEL);//Ñ¹ÈëÊý¾Ý  
	        intent.putExtras(mBundle);
			startActivity(intent);
		}
	});
	ty.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,Tab.class);
			Bundle mBundle = new Bundle();  
	        mBundle.putString("Data",TIYU);//Ñ¹ÈëÊý¾Ý  
	        intent.putExtras(mBundle);
			startActivity(intent);
		}
	});
	ss.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,Tab.class);
			Bundle mBundle = new Bundle();  
	        mBundle.putString("Data",FASHION);//Ñ¹ÈëÊý¾Ý  
	        intent.putExtras(mBundle);
			startActivity(intent);
		}
	});
	gd.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,Tab.class);
			Bundle mBundle = new Bundle();  
	        mBundle.putString("Data",MORE);//Ñ¹ÈëÊý¾Ý  
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
