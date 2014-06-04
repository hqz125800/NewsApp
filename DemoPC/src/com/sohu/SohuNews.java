package com.sohu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.sohu.bean.NewsBean;
import com.sohu.db.ConnectionManager;

/**
 * 用于对搜狐网站上的新闻进行抓取
 */
public class SohuNews {

	public static String SERVERIP = "192.168.1.106";
	public static final int SERVERPORT = 54321;
	private Parser parser = null; // 用于分析网页的分析器。
	private List newsList = new ArrayList(); // 暂存新闻的List；
	private NewsBean bean = new NewsBean();
	private ConnectionManager manager = null; // 数据库连接管理器。
	private PreparedStatement pstmt = null;
	public String newsTitle = null;
	public String newsPic = null;
	public String newsDate = null;
	public String newsContent = null;
	public String newsauthor = null;
	public String filename = null;
	public String picDescription = null;

	public SohuNews() {
	}

	/**
	 * 获得一条完整的新闻。
	 * 
	 * @param newsBean
	 * @return
	 */
	public List getNewsList(final NewsBean newsBean) {
		List list = new ArrayList();
		String newstitle = newsBean.getNewsTitle();
		String newsauthor = newsBean.getNewsAuthor();
		String newscontent = newsBean.getNewsContent();
		String newsdate = newsBean.getNewsDate();
		list.add(newstitle);
		list.add(newsauthor);
		list.add(newscontent);
		list.add(newsdate);
		return list;
	}

	/**
	 * 设置新闻对象，让新闻对象里有新闻数据
	 * 
	 * @param newsTitle
	 *            新闻标题
	 * @param newsauthor
	 *            新闻作者
	 * @param newsContent
	 *            新闻内容
	 * @param newsDate
	 *            新闻日期
	 * @param url
	 *            新闻链接
	 */
	public void setNews(String newsTitle, String newsauthor,
			String newsContent, String newsDate, String url, String newsPic,
			String picDescription) {
		bean.setNewsTitle(newsTitle);
		bean.setNewsAuthor(newsauthor);
		bean.setNewsContent(newsContent);
		bean.setNewsDate(newsDate);
		bean.setNewsURL(url);
		bean.setNewsPic(newsPic);
		bean.setPicDescription(picDescription);

	}

	/**
	 * 该方法用于将新闻添加到数据库中。
	 */
	protected void newsToDataBase(final String table) {

		// 建立一个线程用来执行将新闻插入到数据库中。
		Thread thread = new Thread(new Runnable() {

			public void run() {
				boolean sucess = saveToDB(bean, table);
				if (sucess != false) {
					System.out.println("插入数据失败");
				}
			}
		});
		thread.start();
	}

	/**
	 * 将新闻插入到数据库中
	 * 
	 * @param bean
	 * @return
	 */
	public boolean saveToDB(NewsBean bean, String table) {
		boolean flag = true;
		UUID newsId = UUID.randomUUID();
		String sql = "insert into "
				+ table
				+ "(newstitle,newsauthor,newscontent,newsurl,newsdate,newspic,newsid,newpicDescription) values(?,?,?,?,?,?,?,?)";
		manager = new ConnectionManager();
		String titleLength = bean.getNewsTitle();
		if (titleLength.length() > 60) { // 标题太长的新闻不要。
			return flag;
		}
		try {
			pstmt = manager.getConnection().prepareStatement(sql);
			pstmt.setString(1, bean.getNewsTitle());
			pstmt.setString(2, bean.getNewsAuthor());
			pstmt.setString(3, bean.getNewsContent());
			pstmt.setString(4, bean.getNewsURL());
			pstmt.setString(5, bean.getNewsDate());
			pstmt.setString(6, bean.getNewsPic());
			pstmt.setString(7, newsId.toString().replace("-", ""));
			pstmt.setString(8, bean.getPicDescription());
			flag = pstmt.execute();

		} catch (SQLException ex) {
			Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null,
					ex);
		} finally {
			try {
				pstmt.close();
				manager.close();
			} catch (SQLException ex) {
				Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE,
						null, ex);
			}

		}

		String sql_sqlite = "INSERT INTO "
				+ table
				+ "(`newspic`, `newsid`, `newstitle`, `newsauthor`, `newscontent`, `newsurl`, `newsdate`)VALUES('"
				+ bean.getNewsPic() + "', '"
				+ newsId.toString().replace("-", "") + "','"
				+ bean.getNewsTitle() + "','" + bean.getNewsAuthor() + "','"
				+ newsContent.replaceAll("\r\n", "<br>") + "','"
				+ bean.getNewsURL() + "','" + bean.getNewsDate() + "')";
		Socket socket = null;
		try {
			socket = new Socket(SERVERIP, SERVERPORT);

			PrintWriter writer = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())));
			writer.println(sql_sqlite);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer.close();
			reader.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获得新闻的标题
	 * 
	 * @param titleFilter
	 * @param parser
	 * @return
	 */
	private String getTitle(NodeFilter titleFilter, Parser parser) {
		String titleName = "";
		try {

			NodeList titleNodeList = (NodeList) parser.parse(titleFilter);
			for (int i = 0; i < titleNodeList.size(); i++) {
				HeadingTag title = (HeadingTag) titleNodeList.elementAt(i);
				titleName = title.getStringText();
			}

		} catch (ParserException ex) {
			Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return titleName;
	}

	/**
	 * 获得新闻的责任编辑，也就是作者。
	 * 
	 * @param newsauthorFilter
	 * @param parser
	 * @return
	 */
	private String getNewsAuthor(NodeFilter newsauthorFilter, Parser parser) {
		String newsAuthor = "";
		try {
			NodeList authorList = (NodeList) parser.parse(newsauthorFilter);
			for (int i = 0; i < authorList.size(); i++) {
				Span authorSpan = (Span) authorList.elementAt(i);
				newsAuthor = authorSpan.getStringText();
			}

		} catch (ParserException ex) {
			Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return newsAuthor;

	}

	/*
	 * 获得新闻的日期
	 */
	private String getNewsDate(NodeFilter dateFilter, Parser parser) {
		String newsDate = null;
		try {
			NodeList dateList = (NodeList) parser.parse(dateFilter);
			for (int i = 0; i < dateList.size(); i++) {
				Div dateTag = (Div) dateList.elementAt(i);
				newsDate = dateTag.getStringText();
			}
		} catch (ParserException ex) {
			Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		return newsDate;
	}

	/**
	 * 获取新闻的内容
	 * 
	 * @param newsContentFilter
	 * @param parser
	 * @return content 新闻内容
	 */
	private String getNewsContent(NodeFilter newsContentFilter, Parser parser) {
		String content = null;
		StringBuilder builder = new StringBuilder();

		try {
			NodeList newsContentList = (NodeList) parser
					.parse(newsContentFilter);
			for (int i = 0; i < newsContentList.size(); i++) {
				Div newsContenTag = (Div) newsContentList.elementAt(i);
				builder = builder.append(newsContenTag.getStringText());
			}
			content = builder.toString(); // 转换为String 类型。
			if (content != null) {
				parser.reset();
				parser = Parser.createParser(content, "gb2312");
				StringBean sb = new StringBean();
				sb.setCollapse(true);
				parser.visitAllNodesWith(sb);
				content = sb.getStrings();
				// 处理冗余信息
				if (content.indexOf("新闻加点料") >= 0) {
					content = content.substring(0, content.indexOf("新闻加点料"));// 应该是如果有新闻加点料,那就处理新闻加点料,没有在处理这个
				}
				if (content.indexOf("点击", 200) >= 0) {
					content = content.substring(0, content.indexOf("点击", 200));
				}
				if (content.indexOf("http://") >= 0) {
					content = content.substring(0, content.indexOf("http://"));
				}
				if (content.indexOf("相关阅读", 200) >= 0) {
					content = content
							.substring(0, content.indexOf("相关阅读", 200));
				}
				if (content.indexOf("相关新闻", 200) >= 0) {
					content = content
							.substring(0, content.indexOf("相关新闻", 200));
				}

			} else {
				System.out.println("没有得到新闻内容！");
			}

		} catch (ParserException ex) {
			Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		// System.out.println(content);
		return content;
	}

	private String getImage(NodeFilter imageFilter, Parser parser)
			throws Exception {

		StringBuilder builder = new StringBuilder();
		String newsPics = "";
		try {
			NodeList imageList = (NodeList) parser.parse(imageFilter);
			for (int i = 0; i < imageList.size(); i++) {
				TableTag newsContenTag = (TableTag) imageList.elementAt(i);
				int start = newsContenTag.getStringText()
						.indexOf("<img src=\"");
				int end = newsContenTag.getStringText().indexOf("\" alt=");
				String urls = newsContenTag.getStringText().substring(
						start + "<img src=\"".length(), end);

				int ends = urls.indexOf(".jpg");
				filename = urls.substring(ends - 9);// 命名规则
				URL url = new URL(urls);
				// 打开连接
				URLConnection con = url.openConnection();
				// 设置请求超时为5s
				con.setConnectTimeout(5 * 1000);
				// 输入流
				InputStream is = con.getInputStream();

				// 1K的数据缓冲
				byte[] bs = new byte[1024];
				// 读取到的数据长度
				int len;
				// 输出的文件流
				File sf = new File(".\\resource\\image\\");
				if (!sf.exists()) {
					sf.mkdirs();
				}
				OutputStream os = new FileOutputStream(sf.getPath() + "\\"
						+ filename);
				newsPics = newsPics + urls + "," + filename + ",";
				// 开始读取
				while ((len = is.read(bs)) != -1) {
					os.write(bs, 0, len);
				}
				// 完毕，关闭所有链接
				os.close();
				is.close();

			}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newsPics;

	}

	private String getPicDescription(NodeFilter imageFilter, Parser parser)
			throws Exception {
		StringBuilder builder = new StringBuilder();
		String picDescription = "";

		NodeList imageList = (NodeList) parser.parse(imageFilter);
		for (int i = 0; i < imageList.size(); i++) {
			TableTag newsContenTag = (TableTag) imageList.elementAt(i);
			builder = builder.append(newsContenTag.getStringText());
			picDescription = builder.toString(); // 转换为String 类型。
			if (picDescription != null) {
				parser.reset();
				parser = Parser.createParser(picDescription, "gb2312");
				StringBean sb = new StringBean();
				sb.setCollapse(true);
				parser.visitAllNodesWith(sb);
				picDescription = sb.getStrings();
			}
		}

		return picDescription;

	}

	/**
	 * 对新闻URL进行解析提取新闻，同时将新闻插入到数据库中。
	 * 
	 * @param url
	 *            新闻连接。
	 * @throws Exception
	 */
	public void parser(String url, String table) throws Exception {
		try {
			parser = new Parser(url);

			NodeFilter titleFilter = new TagNameFilter("h1");
			NodeFilter contentFilter = new AndFilter(new TagNameFilter("div"),
					new HasAttributeFilter("id", "contentText")); // 2010 版
			NodeFilter imageFilter1 = new AndFilter(new TagNameFilter("table"),
					new HasAttributeFilter("class", "tableImg"));// 图片过滤器1
			NodeFilter imageFilter2 = new AndFilter(new TagNameFilter("table"),
					new HasAttributeFilter("align", "center"));// 图片过滤器2
			NodeFilter imageFilter = new OrFilter(imageFilter1, imageFilter2);
			NodeFilter newsdateFilter = new AndFilter(new TagNameFilter("div"),
					new HasAttributeFilter("class", "time"));
			NodeFilter newsauthorFilter = new AndFilter(new TagNameFilter(
					"span"), new HasAttributeFilter("class", "editer"));

			newsTitle = getTitle(titleFilter, parser);
			System.out.println(newsTitle + "\n");
			parser.reset(); // 记得每次用完parser后，要重置一次parser。要不然就得不到我们想要的内容了。

			newsDate = getNewsDate(newsdateFilter, parser);
			System.out.println(newsDate + "\n");
			parser.reset();

			newsContent = getNewsContent(contentFilter, parser);
			System.out.println(newsContent); // 输出新闻的内容，查看是否符合要求
			parser.reset();

			newsauthor = getNewsAuthor(newsauthorFilter, parser);
			System.out.println(newsauthor + "\n");
			parser.reset();

			newsPic = getImage(imageFilter, parser);
			parser.reset();

			// 得到图片的相应文字
			picDescription = getPicDescription(imageFilter, parser);// .replaceAll("\r\n",
																	// ";");//让所有图片的文字按分号隔开
			parser.reset();

			newsContent = newsContent.replace(picDescription, "");

			// 先设置新闻对象，让新闻对象里有新闻内容。
			setNews(newsTitle, newsauthor, newsContent, newsDate, url, newsPic,
					picDescription);
			// 将新闻添加到数据中。
			this.newsToDataBase(table);// (为什么执行失败)

		} catch (ParserException ex) {
			Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

}