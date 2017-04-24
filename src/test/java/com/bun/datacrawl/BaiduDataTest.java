package com.bun.datacrawl;

import org.junit.Test;

/**
 * Created by bun@csip.org.cn on 2017/4/24.
 */
public class BaiduDataTest {
    @Test
    public void getBaiduData() throws Exception {
    BaiduData bd = new BaiduData();
    bd.getBaiduData();
    }

    @org.junit.Test
    public void getRealLinkFromBaiduLink() throws Exception {

    }

    @org.junit.Test
    public void jsoupTest() throws Exception {
        BaiduData.jsoupTest();

    }

}