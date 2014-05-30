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
		
//		String sqltest = "INSERT INTO top(`newspic`, `newsid`, `newstitle`, `newsauthor`, `newscontent`, `newsurl`, `newsdate`)VALUES('', 'b9a9413fa9bc49e69ac7d5f2f908e599','评论：“反腐规律”失效凸显铁律回归','(责任编辑：un649)','　　喜欢用民间总结出来的各种“反腐规律”分析中国反腐现实的人，可能越来越看不懂中国的反腐败了，因为那些“反腐规律”在现实面前失效了，根本解释不了当下的反腐现实。<br>　　比如，按照一些人总结出来的“反腐规律”，一个官员（尤其是高官）退休了，等于就是“安全着陆”，即使有问题，也不会追究了。但中央纪委的消息打破了这个“规律”：湖南省政协原党组副书记、副主席阳宝华涉嫌严重违纪违法，目前正接受组织调查。而到6月19日，阳宝华正式退休恰满一年。此前已有多位退休高官被调查。只要屁股不干净，退休照样会被追究，人退休了，问题不会自动清零和洗白，掌权时权力留下的痕迹不会被抹去，没有可以免于调查和追究的豁免权。<br>　　再多的“反腐规律”，都比不上一个基本的反腐常识：天网恢恢疏而不漏，只要滥用权力乱伸手了，都会付出代价，不管什么时候，不管你是什么人，不管你是多大的官。反腐并没有其他的规律可循，唯一的铁的规律就是“伸手必被捉”。<br>　　专家喜欢总结各种“反腐规律”，热衷于从以往纪委查处的案例中寻找一些可以分析走势和预测未来的规律，从而对将要发生的事情作出判断。人们总结出了很多听起来似乎很有道理的规律：比如腐败的59岁现象，哪些岗位是“腐败高危岗位”，党报上的公开报道可以预测官员政治生命，刑不上某级别、官做到某个级别就安全了，网上传闻被官方辟谣后就代表这个官员“安全过关”了。还有，纪委干部一般都很安全，自己人不查自己人。<br>　　种种“反腐规律”，是人们根据往日经验和既有案例总结出的认知，这些规律在一定时期内也确实有一定的预测能力，能解释一些现象，其背后却反映了人们对反腐的不信任：不信法律，不信“腐败必被捉”这个铁律，而是相信有很多凌驾于法律之上的因素会影响反腐败。人们不是根据一个官员的行为和法律去判断反腐走向，而是根据权力的潜规则和人治的思维去总结和判断：只要在《新闻联播》和党报上出现了，就是安全的；只要官做到一定的级别，上面顾忌影响也不会查处他。<br>　　也就是说，这些规律都是根据“人治”思维总结的，而没有法治反腐的影子，不相信会完全依据法律去查处贪官，法治之外有无数看得见看不见的手在操纵法律。人们以前根据这些反腐规律去判断，也能屡试不爽。那些“反腐规律”的有效和灵验，反衬着法治这个铁律的乏力。<br>　　不过，在当下这场“以治标为治本赢得时间”的反腐风暴中，过去那些规律都失效了。比如，从党报机关报的报道绝看不出官员的命运了，今年3月，中央纪委宣布江西省副省长姚木根涉嫌严重违纪违法接受组织调查时，舆论很是惊讶，因为在姚落马的当天，当地机关报还刊发了署名为“省人民政府副省长姚木根”的文章。副省长被查，当地官媒竟然一点都不知道。<br>　　“不明真相”的公众早就养成了一个习惯，就是通过党报和电视上的消息来判断官员涉贪被查传闻的真假，将党报上领导的排名和露面当作政治的风向标。如果网上有传闻称某个官员受到调查了，人们会去看机关报，看这个人近期有没有在报纸上出现、电视上露面，有没有在活动中公开出现。如果出现了，就说明那些传闻可能是假的，如果没有露面，可能就有问题了。可从南京前市长季建业到江西前副省长姚木根的被查，这个规律完全失效。<br>　　人们也以为，如果网传某个官员被查，但官方迅速辟谣称这官员没问题，过去都认为这个官员“安全过关”了。可从刘铁男被查，到近期北京市公安局公安交通管理局原局长宋建国落马，证明这个规律也不靠谱―两年前宋建国曾被传涉嫌腐败被调查，可官方很快辟谣，两年后传闻成真。这是一件好事，“反腐规律”是人治思维下扭曲的现象，必须排除种种干扰，回归“伸手必被捉”这个唯一的反腐常识和吏治铁律。<br>','http://star.news.sohu.com/20140529/n400178323.shtml','2014-05-29 05:15:31')";
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
		        mBundle.putString("Data",TOP);//压入数据  
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
