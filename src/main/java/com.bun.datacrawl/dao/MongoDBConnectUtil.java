package com.bun.datacrawl.dao;

import com.bun.datacrawl.util.ParamsConfigurationUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;


import java.util.Arrays;

/**
 * Created by bun@csip.org.cn on 2016/9/12.
 */
public enum MongoDBConnectUtil {
    instance;
    private MongoClient mongoClient;
    private static String ip= ParamsConfigurationUtil.instance.getParamString("mongodb.host");
    private static int port=ParamsConfigurationUtil.instance.getParamInteger("mongodb.port");
    private static String userName=ParamsConfigurationUtil.instance.getParamString("mongodb.userName");
    private static  String password=ParamsConfigurationUtil.instance.getParamString("mongodb.passwd");
    private static  String database=ParamsConfigurationUtil.instance.getParamString("mongodb.database");
    static {
//        MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
//        instance.mongoClient = new MongoClient(new ServerAddress(ip, port), Arrays.asList(credential));
        instance.mongoClient = new MongoClient(new ServerAddress(ip, port));
    }

    public MongoCollection getCollection(String collectionName)
    {
        return mongoClient.getDatabase(database).getCollection(collectionName);
    }
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}
