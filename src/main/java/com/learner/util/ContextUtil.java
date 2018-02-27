package com.learner.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

/**
 * Created by liufangliang on 2018/2/14.
 */
@Slf4j
public class ContextUtil {


    public static String getContext(HttpResponse response){

        try {
            HttpEntity entity=response.getEntity();
            String html =  EntityUtils.toString(entity, Charsets.UTF_8);
            EntityUtils.consumeQuietly(entity);
            return html;
//
//            InputStream in=response.getEntity().getContent();
//            byte [] bytes=new byte[2048];
//            ByteArrayOutputStream out=new ByteArrayOutputStream();
//            int len = -1;
//            while((len=in.read(bytes))!=-1){
//                out.write(bytes,0,len);
//            }
//            return  out.toString();
        } catch (Exception e) {
            log.error("getContext err :"+e);
        }
        return null;
    }
}
