package com.learner.crawler;

import com.learner.spider.ParserObserver;
import com.learner.spider.Request;
import com.learner.spider.Site;
import com.learner.spider.Spider;
import lombok.Getter;
import lombok.Setter;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


/**
 * Created by liufangliang on 2018/2/14.
 */

@Getter
@Setter
public abstract class AbstractCrawler {

    public static final Logger log = LoggerFactory.getLogger(AbstractCrawler.class);

    private Spider spider;

    public AbstractCrawler() {

    }

    public AbstractCrawler(Spider spider) {
        this.spider = spider;
    }


    protected void getUrl(String url, String[][] headers, ParserObserver observer) {
        getUrl(url, headers, observer, new Site());
    }

    protected void getUrl(String url, String[][] headers, ParserObserver observer, Site site) {
        if (url == null) {
            log.error("request.GET no url");
            return;
        }
        Request request = new Request();
//        log.info("GET: request init ...");

        setRequest(request, RequestBuilder.get(), url, null, null, headers, site);
//        log.info("GET: set request success...");

        request.addObserver(observer);
//        log.info("GET: add observer to this request...");

        spider.addRequest(request);
//        log.info("GET: add request to spider...");

    }

    protected void postUrl(String url, String[][] params, Object[] objects, String[][] headers, ParserObserver observer) {
        postUrl(url, params, objects, headers, observer, new Site());
    }
    protected void postUrl(String url, Object[] objects, String[][] headers, ParserObserver observer) {
        postUrl(url, null, objects, headers, observer, new Site());
    }

    protected void postUrl(String url, String[][] params, Object[] objects, String[][] headers, ParserObserver observerss, Site site) {
        if (url == null) {
            log.error("request.POST no url");
            return;
        }
        Request request = new Request();
//        log.info("POST: request init ...");
        setRequest(request, RequestBuilder.post(), url, params, objects, headers, site);
//        log.info("POST: set request success...");

        request.addObserver(observerss);
//        log.info("POST: add observer to this request...");

        spider.addRequest(request);
//        log.info("POST: add request to spider...");
    }


    private void setRequest(Request req, RequestBuilder builder, String url, String[][] params, Object[] objects, String[][] headers, Site site) {
        builder.setUri(url);
        if (headers != null && headers.length > 0) {
            for (String[] header : headers) {
                builder.addHeader(header[0], header[1]);
            }
        }
        Site si = site != null ? site : spider.getSite();
        if (params != null && params.length > 0) {
            for (String[] ent : params) {
                builder.addParameter(ent[0], ent[1]);
            }
        }
        if (objects != null && objects.length > 0) {
            for (int i = 0; i < objects.length; i++) {
                if (i == 0) {
                    // 设置返回页面的编码
                    req.setResponseChatset(objects[0].toString());
                } else if (i == 1) {
                    // TODO: 2018/5/14
                    try {
                        //json 请求的内容
                        StringEntity str=new StringEntity(objects[1].toString());
                        builder.setEntity(str);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

//        UsernamePasswordCredentials credentials=new UsernamePasswordCredentials("dpadsl","dpadsl2017");
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(si.getTimeOut())
                .setSocketTimeout(si.getTimeOut())
                .setConnectTimeout(si.getTimeOut())
                .setRedirectsEnabled(si.isFollowRedirect());
//        HttpHost host=new HttpHost("59.55.148.144",8888);
//        requestConfigBuilder.setProxy(host);
//        requestConfigBuilder.setAuthenticationEnabled(true);
        builder.setConfig(requestConfigBuilder.build());


// TODO: 2018/5/14 设置version，charset
        builder.setCharset(Charset.defaultCharset());
        builder.setVersion(null);
        req.setRequest(builder.build());
    }


}
