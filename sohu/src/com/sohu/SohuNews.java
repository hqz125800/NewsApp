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
 * @author guanminglin <guanminglin@gmail.com>
 */
public class SohuNews {

    private static final String SERVERIP = "192.168.63.2";
	private static final int SERVERPORT = 54321;
	private Parser parser = null;   //用于分析网页的分析器。
    private List newsList = new ArrayList();    //暂存新闻的List；
    private NewsBean bean = new NewsBean();
    private ConnectionManager manager = null;    //数据库连接管理器。
    private PreparedStatement pstmt = null;
    public String newsTitle = null;
    public String newsPic = null;
    public String newsDate = null;
    public String newsContent = null;
    public String newsauthor = null;
    public String filename = null;

    public SohuNews() {
    }

    /**
     * 获得一条完整的新闻。
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
     *  设置新闻对象，让新闻对象里有新闻数据
     * @param newsTitle 新闻标题
     * @param newsauthor  新闻作者
     * @param newsContent 新闻内容
     * @param newsDate  新闻日期
     * @param url  新闻链接
     */
    public void setNews(String newsTitle, String newsauthor, String newsContent, String newsDate, String url,String newsPic) {
        bean.setNewsTitle(newsTitle);
        bean.setNewsAuthor(newsauthor);
        bean.setNewsContent(newsContent);
        bean.setNewsDate(newsDate);
        bean.setNewsURL(url);
        bean.setNewsPic(newsPic);
    }

    /**
     * 该方法用于将新闻添加到数据库中。
     */
    protected void newsToDataBase(final String table) {

        //建立一个线程用来执行将新闻插入到数据库中。
        Thread thread = new Thread(new Runnable() {

            public void run() {
                boolean sucess = saveToDB(bean,table);
                if (sucess != false) {
                    System.out.println("插入数据失败");
                }
            }
        });
        thread.start();
    }

    /**
     * 将新闻插入到数据库中
     * @param bean
     * @return
     */
    public boolean saveToDB(NewsBean bean,String table) {
        boolean flag = true;
        UUID newsId = UUID.randomUUID(); 
        String sql = "insert into "+table+"(newstitle,newsauthor,newscontent,newsurl,newsdate,newspic,newsid) values(?,?,?,?,?,?,?)";
        manager = new ConnectionManager();
        String titleLength = bean.getNewsTitle();
        if (titleLength.length() > 60) {  //标题太长的新闻不要。
            return flag;
        }
        try {
            pstmt = manager.getConnection().prepareStatement(sql);
//            pstmt.setString(1, new String(bean.getNewsTitle().getBytes("gb2312"),"utf-8"));
//            pstmt.setString(2, new String(bean.getNewsAuthor().getBytes("gb2312"),"utf-8"));
//            pstmt.setString(3, new String(bean.getNewsContent().getBytes("gb2312"),"utf-8"));
//            pstmt.setString(4, new String(bean.getNewsURL().getBytes("gb2312"),"utf-8"));
            pstmt.setString(1, bean.getNewsTitle());
            pstmt.setString(2, bean.getNewsAuthor());
            pstmt.setString(3, bean.getNewsContent());
            pstmt.setString(4, bean.getNewsURL());
            pstmt.setString(5, bean.getNewsDate());
            pstmt.setString(6,  bean.getNewsPic());
            
            pstmt.setString(7,  newsId.toString().replace("-", ""));
            flag = pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pstmt.close();
                manager.close();
            } catch (SQLException ex) {
                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        String sql_sqlite = "INSERT INTO "+table+"(`newspic`, `newsid`, `newstitle`, `newsauthor`, `newscontent`, `newsurl`, `newsdate`)VALUES('"+bean.getNewsPic()+"', '"+newsId.toString().replace("-", "")+"','"+bean.getNewsTitle()+"','"+bean.getNewsAuthor()+"','"+newsContent.replaceAll("\r\n", "<br>")+"','"+bean.getNewsURL()+"','"+bean.getNewsDate()+"')";
		Socket socket = null;
		try {
			socket = new Socket(SERVERIP, SERVERPORT);

			PrintWriter writer = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())));
			writer.println(sql_sqlite);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			//mReceivedMsg = reader.readLine();
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
            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
        }
        return titleName;
    }

    /**
     * 获得新闻的责任编辑，也就是作者。
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
            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newsDate;
    }

    /**
     * 获取新闻的内容
     * @param newsContentFilter
     * @param parser
     * @return  content 新闻内容
     */
    private String getNewsContent(NodeFilter newsContentFilter, Parser parser) {
        String content = null;
        StringBuilder builder = new StringBuilder();


        try {
            NodeList newsContentList = (NodeList) parser.parse(newsContentFilter);
            for (int i = 0; i < newsContentList.size(); i++) {
            	Div newsContenTag = (Div) newsContentList.elementAt(i);
                builder = builder.append(newsContenTag.getStringText());
            }
            content = builder.toString();  //转换为String 类型。
            if (content != null) {
                parser.reset();
                parser = Parser.createParser(content, "gb2312");
                StringBean sb = new StringBean();
                sb.setCollapse(true);
                parser.visitAllNodesWith(sb);
                content = sb.getStrings();
                //处理冗余信息
                if(content.indexOf("新闻加点料")>=0){
                content =content.substring(0,content.indexOf("新闻加点料"));//应该是如果有新闻加点料,那就处理新闻加点料,没有在处理这个
                }
                if(content.indexOf("点击",200)>=0){
                	content =content.substring(0,content.indexOf("点击",200));
                }
                if(content.indexOf("http://")>=0){
                	content =content.substring(0,content.indexOf("http://"));
                }
                if(content.indexOf("相关阅读",200)>=0){
                	content =content.substring(0,content.indexOf("相关阅读",200));
                }
                if(content.indexOf("相关新闻",200)>=0){
                	content =content.substring(0,content.indexOf("相关新闻",200));
                }

//                String s = "\";} else{ document.getElementById('TurnAD444').innerHTML = \"\";} } showTurnAD444(intTurnAD444); }catch(e){}";
              //在这处理页面
//                    content = content.replaceAll("\\\".*[a-z].*\\}", "");
//                    content = content.replace("[我来说两句]", "");//过滤内容内不可用的信息
                


            } else {
                System.out.println("没有得到新闻内容！");
            }

        } catch (ParserException ex) {
            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println(content);
        return content;
    }
    


    
    private String getImage(NodeFilter imageFilter, Parser parser) throws Exception{
    	
        StringBuilder builder = new StringBuilder();
        String newsPics = "";



            
			try {
				NodeList imageList = (NodeList) parser.parse(imageFilter);
	            for (int i = 0; i < imageList.size(); i++) {
	            	TableTag newsContenTag = (TableTag) imageList.elementAt(i);
	            	int start = newsContenTag.getStringText().indexOf("<img src=\"");
	            	int end = newsContenTag.getStringText().indexOf("\" alt=");
	            	String urls = newsContenTag.getStringText().substring(start+"<img src=\"".length(),end);
//	            	int starts = urls.indexOf("Img");
	            	int ends = urls.indexOf(".jpg");
	            	filename = urls.substring(ends-9);//命名规则
	            	 URL url = new URL(urls);  
	                 // 打开连接  
	                 URLConnection con = url.openConnection();  
	                 //设置请求超时为5s  
	                 con.setConnectTimeout(5*1000);  
	                 // 输入流  
	                 InputStream is = con.getInputStream();  

	                 // 1K的数据缓冲  
	                 byte[] bs = new byte[1024];  
	                 // 读取到的数据长度  
	                 int len;  
	                 // 输出的文件流  
	                File sf=new File(".\\image\\");  
	                if(!sf.exists()){  
	                    sf.mkdirs();  
	                }  
	                OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
	                newsPics = newsPics + filename + ",";
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

    /**
     * 根据提供的URL，获取此URL对应网页所有的纯文本信息，次方法得到的信息不是很纯，
     *常常会得到我们不想要的数据。不过如果你只是想得到某个URL 里的所有纯文本信息，该方法还是很好用的。
     * @param url 提供的URL链接
     * @return RL对应网页的纯文本信息
     * @throws ParserException
     * @deprecated 该方法被 getNewsContent()替代。
     */
    @Deprecated
    public String getText(String url) throws ParserException {
        StringBean sb = new StringBean();

        //设置不需要得到页面所包含的链接信息
        sb.setLinks(false);
        //设置将不间断空格由正规空格所替代
        sb.setReplaceNonBreakingSpaces(true);
        //设置将一序列空格由一个单一空格所代替
        sb.setCollapse(true);
        //传入要解析的URL
        sb.setURL(url);

        //返回解析后的网页纯文本信息
        return sb.getStrings();
    }

    /**
     * 对新闻URL进行解析提取新闻，同时将新闻插入到数据库中。
     * @param url 新闻连接。
     * @throws Exception 
     */
    public void parser(String url,String table) throws Exception {
        try {
            parser = new Parser(url);
            
            NodeFilter titleFilter = new TagNameFilter("h1");
            NodeFilter contentFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "contentText")); //2010 版
            NodeFilter imageFilter1 = new AndFilter(new TagNameFilter("table"), new HasAttributeFilter("class", "tableImg"));//图片过滤器1
            NodeFilter imageFilter2 = new AndFilter(new TagNameFilter("table"), new HasAttributeFilter("align", "center"));//图片过滤器2
            NodeFilter imageFilter = new OrFilter(imageFilter1,imageFilter2);

            //NodeFilter videotextFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "sele-now top-pager-current"));
            //NodeFilter subTitleFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "sele-con top-pager-options"));
            NodeFilter newsdateFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "time"));
            NodeFilter newsauthorFilter = new AndFilter(new TagNameFilter("span"), new HasAttributeFilter("class", "editer"));
            
            //得到的是图片的名字

            
            
            newsTitle = getTitle(titleFilter, parser);
            System.out.println(newsTitle+"\n");
            parser.reset();   //记得每次用完parser后，要重置一次parser。要不然就得不到我们想要的内容了。
            
            
            newsDate = getNewsDate(newsdateFilter, parser);
            System.out.println(newsDate+"\n");
            parser.reset();
            
            
            newsContent = getNewsContent(contentFilter, parser);
            System.out.println(newsContent);   //输出新闻的内容，查看是否符合要求
            parser.reset(); 
            
            
            //String subTitle = getNewsContent(subTitleFilter, parser);
            //System.out.println(subTitle);   //输出新闻的内容，查看是否符合要求
            //parser.reset();
            //newsContent.replace(subTitle, " ");//把subTitle去除
            //System.out.println(newsContent);         
            
            newsauthor = getNewsAuthor(newsauthorFilter, parser);
            System.out.println(newsauthor+"\n");
            parser.reset();  
            
            newsPic = getImage(imageFilter, parser);
            parser.reset();  
            
            
            
            
            //先设置新闻对象，让新闻对象里有新闻内容。
            setNews(newsTitle, newsauthor, newsContent, newsDate, url, newsPic);
            //将新闻添加到数据中。
            this.newsToDataBase(table);//(为什么执行失败)

        } catch (ParserException ex) {
            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    //单个文件测试网页
//    public static void main(String[] args) throws Exception {
//        SohuNews news = new SohuNews();
//        news.parser("http://sports.sohu.com/20140522/n399886225.shtml");//创建一个变量,实现拖拽
//    }
}