package com.sohu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
 * 鐢ㄤ簬瀵规悳鐙愮綉绔欎笂鐨勬柊闂昏繘琛屾姄鍙�
 * @author guanminglin <guanminglin@gmail.com>
 */
public class SohuNews {

    private Parser parser = null;   //鐢ㄤ簬鍒嗘瀽缃戦〉鐨勫垎鏋愬櫒銆�
    private List newsList = new ArrayList();    //鏆傚瓨鏂伴椈鐨凩ist锛�
    private NewsBean bean = new NewsBean();
    private ConnectionManager manager = null;    //鏁版嵁搴撹繛鎺ョ鐞嗗櫒銆�
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
     * 鑾峰緱涓�鏉″畬鏁寸殑鏂伴椈銆�
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
     *  璁剧疆鏂伴椈瀵硅薄锛岃鏂伴椈瀵硅薄閲屾湁鏂伴椈鏁版嵁
     * @param newsTitle 鏂伴椈鏍囬
     * @param newsauthor  鏂伴椈浣滆��
     * @param newsContent 鏂伴椈鍐呭
     * @param newsDate  鏂伴椈鏃ユ湡
     * @param url  鏂伴椈閾炬帴
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
     * 璇ユ柟娉曠敤浜庡皢鏂伴椈娣诲姞鍒版暟鎹簱涓��
     */
    protected void newsToDataBase(final String table) {

        //寤虹珛涓�涓嚎绋嬬敤鏉ユ墽琛屽皢鏂伴椈鎻掑叆鍒版暟鎹簱涓��
        Thread thread = new Thread(new Runnable() {

            public void run() {
                boolean sucess = saveToDB(bean,table);
                if (sucess != false) {
                    System.out.println("鎻掑叆鏁版嵁澶辫触");
                }
            }
        });
        thread.start();
    }

    /**
     * 灏嗘柊闂绘彃鍏ュ埌鏁版嵁搴撲腑
     * @param bean
     * @return
     */
    public boolean saveToDB(NewsBean bean,String table) {
        boolean flag = true;
        String sql = "insert into "+table+"(newstitle,newsauthor,newscontent,newsurl,newsdate,newspic) values(?,?,?,?,?,?)";
        manager = new ConnectionManager();
        String titleLength = bean.getNewsTitle();
        if (titleLength.length() > 60) {  //鏍囬澶暱鐨勬柊闂讳笉瑕併��
            return flag;
        }
        try {
            pstmt = manager.getConnection().prepareStatement(sql);
//            pstmt.setString(1, new String(bean.getNewsTitle().getBytes("gb2312"),"utf-8"));
//            pstmt.setString(2, new String(bean.getNewsAuthor().getBytes("gb2312"),"utf-8"));
//            pstmt.setString(3, new String(bean.getNewsContent().getBytes("gb2312"),"utf-8"));
//            pstmt.setString(4, new String(bean.getNewsURL().getBytes("gb2312"),"utf-8"));
            pstmt.setString(1, bean.getNewsTitle());
            pstmt.setString(1, new String("乱码".getBytes(),"UTF-8"));
            pstmt.setString(2, bean.getNewsAuthor());
            //pstmt.setString(3, bean.getNewsContent());
            pstmt.setString(3, bean.getNewsContent());
            pstmt.setString(3, "中文乱码怎解决");
            pstmt.setString(4, bean.getNewsURL());
            pstmt.setString(5, bean.getNewsDate());
            pstmt.setString(6,  bean.getNewsPic());
            flag = pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
                pstmt.close();
                manager.close();
            } catch (SQLException ex) {
                Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return flag;
    }

    /**
     * 鑾峰緱鏂伴椈鐨勬爣棰�
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
     * 鑾峰緱鏂伴椈鐨勮矗浠荤紪杈戯紝涔熷氨鏄綔鑰呫��
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
     * 鑾峰緱鏂伴椈鐨勬棩鏈�
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
     * 鑾峰彇鏂伴椈鐨勫唴瀹�
     * @param newsContentFilter
     * @param parser
     * @return  content 鏂伴椈鍐呭
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
            content = builder.toString();  //杞崲涓篠tring 绫诲瀷銆�
            if (content != null) {
                parser.reset();
                parser = Parser.createParser(content, "gb2312");
                StringBean sb = new StringBean();
                sb.setCollapse(true);
                parser.visitAllNodesWith(sb);
                content = sb.getStrings();
                //澶勭悊鍐椾綑淇℃伅
                if(content.indexOf("鏂伴椈鍔犵偣鏂�")>=0){
                content =content.substring(0,content.indexOf("鏂伴椈鍔犵偣鏂�"));//搴旇鏄鏋滄湁鏂伴椈鍔犵偣鏂�,閭ｅ氨澶勭悊鏂伴椈鍔犵偣鏂�,娌℃湁鍦ㄥ鐞嗚繖涓�
                }
                if(content.indexOf("鐐瑰嚮",200)>=0){
                	content =content.substring(0,content.indexOf("鐐瑰嚮",200));
                }
                if(content.indexOf("http://")>=0){
                	content =content.substring(0,content.indexOf("http://"));
                }
                if(content.indexOf("鐩稿叧闃呰",200)>=0){
                	content =content.substring(0,content.indexOf("鐩稿叧闃呰",200));
                }
                if(content.indexOf("鐩稿叧鏂伴椈",200)>=0){
                	content =content.substring(0,content.indexOf("鐩稿叧鏂伴椈",200));
                }

//                String s = "\";} else{ document.getElementById('TurnAD444').innerHTML = \"\";} } showTurnAD444(intTurnAD444); }catch(e){}";
              //鍦ㄨ繖澶勭悊椤甸潰
//                    content = content.replaceAll("\\\".*[a-z].*\\}", "");
//                    content = content.replace("[鎴戞潵璇翠袱鍙", "");//杩囨护鍐呭鍐呬笉鍙敤鐨勪俊鎭�
                


            } else {
                System.out.println("娌℃湁寰楀埌鏂伴椈鍐呭锛�");
            }

        } catch (ParserException ex) {
            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println(content);
        return content;
    }
    


    
    private String getImage(NodeFilter imageFilter, Parser parser) throws Exception{
    	
        StringBuilder builder = new StringBuilder();
        String newsPics = null;



            
			try {
				NodeList imageList = (NodeList) parser.parse(imageFilter);
	            for (int i = 0; i < imageList.size(); i++) {
	            	TableTag newsContenTag = (TableTag) imageList.elementAt(i);
	            	int start = newsContenTag.getStringText().indexOf("<img src=\"");
	            	int end = newsContenTag.getStringText().indexOf("\" alt=");
	            	String urls = newsContenTag.getStringText().substring(start+"<img src=\"".length(),end);
//	            	int starts = urls.indexOf("Img");
	            	int ends = urls.indexOf(".jpg");
	            	filename = urls.substring(ends-9);//鍛藉悕瑙勫垯
	            	 URL url = new URL(urls);  
	                 // 鎵撳紑杩炴帴  
	                 URLConnection con = url.openConnection();  
	                 //璁剧疆璇锋眰瓒呮椂涓�5s  
	                 con.setConnectTimeout(5*1000);  
	                 // 杈撳叆娴�  
	                 InputStream is = con.getInputStream();  
	               
	                 // 1K鐨勬暟鎹紦鍐�  
	                 byte[] bs = new byte[1024];  
	                 // 璇诲彇鍒扮殑鏁版嵁闀垮害  
	                 int len;  
	                 // 杈撳嚭鐨勬枃浠舵祦  
	                File sf=new File(".\\image\\");  
	                if(!sf.exists()){  
	                    sf.mkdirs();  
	                }  
	                OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
	                newsPics = newsPics + filename + ",";
	                 // 寮�濮嬭鍙�  
	                 while ((len = is.read(bs)) != -1) {  
	                   os.write(bs, 0, len);  
	                 }  
	                 // 瀹屾瘯锛屽叧闂墍鏈夐摼鎺�  
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
     * 鏍规嵁鎻愪緵鐨刄RL锛岃幏鍙栨URL瀵瑰簲缃戦〉鎵�鏈夌殑绾枃鏈俊鎭紝娆℃柟娉曞緱鍒扮殑淇℃伅涓嶆槸寰堢函锛�
     *甯稿父浼氬緱鍒版垜浠笉鎯宠鐨勬暟鎹�備笉杩囧鏋滀綘鍙槸鎯冲緱鍒版煇涓猆RL 閲岀殑鎵�鏈夌函鏂囨湰淇℃伅锛岃鏂规硶杩樻槸寰堝ソ鐢ㄧ殑銆�
     * @param url 鎻愪緵鐨刄RL閾炬帴
     * @return RL瀵瑰簲缃戦〉鐨勭函鏂囨湰淇℃伅
     * @throws ParserException
     * @deprecated 璇ユ柟娉曡 getNewsContent()鏇夸唬銆�
     */
    @Deprecated
    public String getText(String url) throws ParserException {
        StringBean sb = new StringBean();

        //璁剧疆涓嶉渶瑕佸緱鍒伴〉闈㈡墍鍖呭惈鐨勯摼鎺ヤ俊鎭�
        sb.setLinks(false);
        //璁剧疆灏嗕笉闂存柇绌烘牸鐢辨瑙勭┖鏍兼墍鏇夸唬
        sb.setReplaceNonBreakingSpaces(true);
        //璁剧疆灏嗕竴搴忓垪绌烘牸鐢变竴涓崟涓�绌烘牸鎵�浠ｆ浛
        sb.setCollapse(true);
        //浼犲叆瑕佽В鏋愮殑URL
        sb.setURL(url);

        //杩斿洖瑙ｆ瀽鍚庣殑缃戦〉绾枃鏈俊鎭�
        return sb.getStrings();
    }

    /**
     * 瀵规柊闂籙RL杩涜瑙ｆ瀽鎻愬彇鏂伴椈锛屽悓鏃跺皢鏂伴椈鎻掑叆鍒版暟鎹簱涓��
     * @param url 鏂伴椈杩炴帴銆�
     * @throws Exception 
     */
    public void parser(String url,String table) throws Exception {
        try {
            parser = new Parser(url);
            
            NodeFilter titleFilter = new TagNameFilter("h1");
            NodeFilter contentFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "contentText")); //2010 鐗�
            NodeFilter imageFilter1 = new AndFilter(new TagNameFilter("table"), new HasAttributeFilter("class", "tableImg"));//鍥剧墖杩囨护鍣�1
            NodeFilter imageFilter2 = new AndFilter(new TagNameFilter("table"), new HasAttributeFilter("align", "center"));//鍥剧墖杩囨护鍣�2
            NodeFilter imageFilter = new OrFilter(imageFilter1,imageFilter2);

            //NodeFilter videotextFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "sele-now top-pager-current"));
            //NodeFilter subTitleFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "sele-con top-pager-options"));
            NodeFilter newsdateFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "time"));
            NodeFilter newsauthorFilter = new AndFilter(new TagNameFilter("span"), new HasAttributeFilter("class", "editer"));
            
            //寰楀埌鐨勬槸鍥剧墖鐨勫悕瀛�

            
            
            newsTitle = getTitle(titleFilter, parser);
            System.out.println(newsTitle+"\n");
            parser.reset();   //璁板緱姣忔鐢ㄥ畬parser鍚庯紝瑕侀噸缃竴娆arser銆傝涓嶇劧灏卞緱涓嶅埌鎴戜滑鎯宠鐨勫唴瀹逛簡銆�
            
            
            newsDate = getNewsDate(newsdateFilter, parser);
            System.out.println(newsDate+"\n");
            parser.reset();
            
            
            newsContent = getNewsContent(contentFilter, parser);
            System.out.println(newsContent);   //杈撳嚭鏂伴椈鐨勫唴瀹癸紝鏌ョ湅鏄惁绗﹀悎瑕佹眰
            parser.reset(); 
            
            
            //String subTitle = getNewsContent(subTitleFilter, parser);
            //System.out.println(subTitle);   //杈撳嚭鏂伴椈鐨勫唴瀹癸紝鏌ョ湅鏄惁绗﹀悎瑕佹眰
            //parser.reset();
            //newsContent.replace(subTitle, " ");//鎶妔ubTitle鍘婚櫎
            //System.out.println(newsContent);         
            
            newsauthor = getNewsAuthor(newsauthorFilter, parser);
            System.out.println(newsauthor+"\n");
            parser.reset();  
            
            newsPic = getImage(imageFilter, parser);
            parser.reset();  
            
            
            
            
            //鍏堣缃柊闂诲璞★紝璁╂柊闂诲璞￠噷鏈夋柊闂诲唴瀹广��
            setNews(newsTitle, newsauthor, newsContent, newsDate, url, newsPic);
            //灏嗘柊闂绘坊鍔犲埌鏁版嵁涓��
            this.newsToDataBase(table);//(涓轰粈涔堟墽琛屽け璐�)

        } catch (ParserException ex) {
            Logger.getLogger(SohuNews.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    //鍗曚釜鏂囦欢娴嬭瘯缃戦〉
//    public static void main(String[] args) throws Exception {
//        SohuNews news = new SohuNews();
//        news.parser("http://sports.sohu.com/20140522/n399886225.shtml");//鍒涘缓涓�涓彉閲�,瀹炵幇鎷栨嫿
//    }
}
