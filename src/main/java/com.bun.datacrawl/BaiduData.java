package com.bun.datacrawl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by bun@csip.org.cn on 2017/4/24.
 */
public class BaiduData {





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
