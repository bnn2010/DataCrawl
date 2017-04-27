package com.bun.datacrawl;

import com.bun.datacrawl.dao.MongoDBConnectUtil;
import com.bun.datacrawl.util.spiderUtil.PostUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by bun@csip.org.cn on 2017/4/24.
 */
public class BaiduData {

    public void getBaiduData(String query,int pn){
//        String query = "site:gov.cn 大气and文件and(京津冀or北京or天津or河北or山东or山西or内蒙古)";

        String collectionName = "CrawledData";
        MongoCollection collection= MongoDBConnectUtil.instance.getCollection(collectionName);
        try {
            String urlquery = java.net.URLEncoder.encode(query,"utf-8");
//           这里要加上pn参数，翻页用的。
            String urlBuilder = "http://www.baidu.com/s?wd="+urlquery+"&pn="+pn;
            System.out.println("urlEncoderStr:"+urlBuilder);
            String content = PostUtil.excuteGet(urlBuilder);
            Document doc = Jsoup.parse(content,"utf-8");
            Element contentLeft = doc.select("div[id=content_left]").first();
            Elements itemList = contentLeft.select("div[class=result c-container ]");
            for (Element item :
                    itemList) {
//                System.out.println(item);
                Element hrefElement=item.select("a[href]").first();
                //网站链接，注意是百度加密后的链接。
                String href = hrefElement.attr("href");
//                System.out.println("baiduLink:"+href);
                //真实链接
                String realHref = getRealLinkFromBaiduLink(href);
                System.out.println("readLink:"+realHref);

//                System.out.println("--------------------------------");
                String title = item.select("h3[class=t]").first().text();

                Element articleAbstractTimeElement =item.select("div[class=c-abstract]").first();
                if(articleAbstractTimeElement==null)
                {
                    continue;
                }
                String articleAbstractTime=articleAbstractTimeElement.text();

                Element publishTimeElement = item.select("span[class= newTimeFactor_before_abs m]").first();
                String publishTime="";
                String articleAbstract="";
                if (publishTimeElement != null) {
                    publishTime=publishTimeElement.text();
                    articleAbstract= articleAbstractTime.replace(publishTime,"");
                    publishTime = publishTime.replace(" - ","");
                }
                else
                {
                    articleAbstract=articleAbstractTime;

                }

                System.out.println("publishTime:"+publishTime);
                System.out.println("abstract:"+articleAbstract);
                System.out.println("title:"+title);
                System.out.println("---------------------------------------------------");
//                break;

                //准备往数据库中写入
                org.bson.Document docInsert = new org.bson.Document();
                docInsert.append("title",title);
                docInsert.append("publish_time",publishTime);
                docInsert.append("url",realHref);
                docInsert.append("abstract",articleAbstract);
                docInsert.append("query",query);
                collection.insertOne(docInsert);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getLawxyRealLinkFromBaidu(){
       String collectionName = "lawxy";
        MongoCollection collection= MongoDBConnectUtil.instance.getCollection(collectionName);
        try {
            MongoCursor<org.bson.Document> cursor = collection.find().iterator();
            int count=0;
            while (cursor.hasNext()) {
                count++;
                org.bson.Document docMon = cursor.next();
                String title = docMon.getString("文件标题");
                String quertTitle = "site:gov.cn "+title;
                String urlquery = java.net.URLEncoder.encode(quertTitle, "utf-8");

//           这里要加上pn参数，翻页用的。
                String urlBuilder = "http://www.baidu.com/s?wd=" + urlquery;
                System.out.println("urlEncoderStr:" + urlBuilder);
                String content = PostUtil.excuteGet(urlBuilder);
                Document doc = Jsoup.parse(content, "utf-8");
                Element contentLeft = doc.select("div[id=content_left]").first();
                Elements itemList = contentLeft.select("div[class=result c-container ]");
                for (Element item :
                        itemList) {
//                System.out.println(item);
                    Element hrefElement = item.select("a[href]").first();
                    //网站链接，注意是百度加密后的链接。
                    String href = hrefElement.attr("href");
//                System.out.println("baiduLink:"+href);
                    //真实链接
                    String realHref = getRealLinkFromBaiduLink(href);
                    System.out.println("readLink:" + realHref);



                    docMon.put("url", realHref);
                    collection.replaceOne(
                            Filters.eq( "_id", docMon.get( "_id" ) ),
                            docMon );
                    break;


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    //利用这个方法获取真实的链接是可行的。
    //更多方法，见http://www.itseo.net/direction/show-156.html
    public static String  getRealLinkFromBaiduLink(String url)
    {
        Connection.Response res = null;
        String readLink="";
        try {
            res = Jsoup.connect(url).timeout(60000).method(Connection.Method.GET).followRedirects(false).execute();
            readLink= res.header("Location");
//            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readLink;

    }

    public static void main(String[] args) {
        BaiduData bd = new BaiduData();
        bd.getLawxyRealLinkFromBaidu();

    }
}
