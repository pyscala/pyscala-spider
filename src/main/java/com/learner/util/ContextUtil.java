package com.learner.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.learner.spider.RespContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liufangliang on 2018/2/14.
 */
@Slf4j
public class ContextUtil {


    public static String getString(RespContext context) {
        HttpResponse response = context.getResponse();
        String charset = context.getCharset();
        try {
            HttpEntity entity = response.getEntity();
            String cont;
            if (charset == null) {
                cont = EntityUtils.toString(entity, Charsets.UTF_8);
            } else {
                cont = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consumeQuietly(entity);
            return cont;
        } catch (Exception e) {
            log.error("getContext err :" + e);
        }
        return null;
    }


    public static Document getDocument(RespContext context) {
        String str = getString(context);
        if (str != null) {
            Document doc = Jsoup.parse(str);
            return doc;
        }
        return null;
    }


    public static JSONObject getJSONObject(RespContext context) {
        String str = getString(context);
        try {
            return JSON.parseObject(str);
        } catch (Exception e) {
            log.error("String to  JSONObject error :" + e);
        }
        return null;
    }


    public static JSONArray getJSONArray(RespContext context) {
        String str = getString(context);
        try {
            return JSON.parseArray(str);
        } catch (Exception e) {
            log.error("String to JSONArray error :" + e);
        }
        return null;
    }

    public static InputStream getInputStream(RespContext context){
        try {
            return context.getResponse().getEntity().getContent();
        } catch (IOException e) {
            log.error("get inputStream error :"+e);
        }
        return null;
    }






}
