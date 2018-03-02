package com.learner.spider;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author : liufangliang
 * @description :
 * @date : 2017\12\28 0028
 */

@Getter
@Setter
@Slf4j
public  class Spider {

    private Site site=new Site();

    private  CloseableHttpClient client = getClient();

    public Spider(){
        init();
    }
    private void init(){

        List<Header> headerList = new ArrayList();
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9," +
                "image/webp,image/apng,*/*;q=0.8"));
        headerList.add(new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"));
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate"));
        headerList.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "max-age=0"));
        headerList.add(new BasicHeader(HttpHeaders.CONNECTION, "keep-alive"));
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,ja;q=0.2," +
                "de;q=0.2"));
        client= HttpClients.custom().setDefaultHeaders(headerList).build();
    }
    private HttpContext httpContext= HttpClientContext.create();

    private BlockingQueue<Request> tasks=new ArrayBlockingQueue<Request>(50);

    public void addRequest(Request request){
        tasks.add(request);
    }

    public void start(){
        if (tasks.size()<1){
            log.info("no request for use !");
            return;
        }
        while (true){
            if(tasks.isEmpty()){
                break;
            }
            String url=null;
            try {
                Request req=tasks.poll();
                HttpUriRequest request= req.getRequest();
                url=request.getURI().toString();
                HttpResponse response=client.execute(request,httpContext);
                RespContext respContext=new RespContext();
                respContext.setResponse(response);
                log.info("execute ("+url+") success !");
                req.setResponse(respContext);
                Thread.sleep(3000);
            } catch (Exception e) {
                log.info("execute ("+url+") failed !"+e);
            }
        }
    }

    public void shutdown(){

        if(client!=null){
            try {
                client.close();
            } catch (IOException e) {
                log.error("httpclient close error !"+e);
            }

        }
    }

}
