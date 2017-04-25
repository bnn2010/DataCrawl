package com.bun.datacrawl;

import org.junit.Test;

/**
 * Created by bun@csip.org.cn on 2017/4/24.
 */
public class BaiduDataTest {
    @Test
    public void getBaiduData() throws Exception {
    BaiduData bd = new BaiduData();
//    String query = "site:gov.cn 大气污染文件and京津冀";
    String query = "site:gov.cn 大气污染and京津冀and(法律or法规or规章or通知)";
    int size = 80;
    for (int i = 0; i <=size ; i+=10) {
            bd.getBaiduData(query,i);
            Thread.sleep(1000);
        }

    }

    @org.junit.Test
    public void getRealLinkFromBaiduLink() throws Exception {

    }

    @org.junit.Test
    public void jsoupTest() throws Exception {
//        BaiduData.jsoupTest();

    }

}