package com.bun.datacrawl;

import com.bun.datacrawl.util.spiderUtil.PostUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by bun@csip.org.cn on 2017/4/24.
 */
public class BaiduData {

    public void getBaiduData(){
        String query = "site:gov.cn 大气and文件and(京津冀or北京or天津or河北or山东or山西or内蒙古)";

        try {
            String urlquery = java.net.URLEncoder.encode(query,"utf-8");
//            String pn=
            String urlBuilder = "http://www.baidu.com/s?wd="+urlquery;
            System.out.println("urlEncoderStr:"+urlBuilder);
            String content = PostUtil.excuteGet(urlBuilder);
            Document doc = Jsoup.parse(content,"utf-8");
            Elements links=doc.select("a[href]");
            Element first=doc.select("id.1").first();
            System.out.println(first);
//            for (Element link:links
//                 ) {
//                System.out.println(link);
//            }
//            Element contentLeft = doc.select("div.content_left").first();
//            System.out.println(contentLeft);
//            for (Element item:contentLefts
//                 ) {
//                System.out.println(item.html());
//                System.out.println("--------------------");
//            }
//            System.out.println(doc.body());


//            System.out.println(content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }




    }





    //利用这个方法获取真实的链接是可行的。
    //更多方法，见http://www.itseo.net/direction/show-156.html
    public static void jsoupTest()
    {
        Connection.Response res = null;
        try {
            res = Jsoup.connect("https://www.baidu.com/link?url=NsHkjUPGl_mZhzM-TUlu6ZfyO3os9ubGKhDv9XiMklKxONjEtFTVLyrKWiW50gJzRFL2hChm_hkarUGQkmwPS_").timeout(60000).method(Connection.Method.GET).followRedirects(false).execute();
            String str= res.header("Location");
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

    }
}
