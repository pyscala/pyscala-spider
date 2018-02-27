package com.learner.crawler;

import com.learner.spider.ParserObserver;
import com.learner.spider.Request;
import com.learner.spider.Site;
import com.learner.spider.Spider;
import lombok.Getter;
import lombok.Setter;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;


/**
 * Created by liufangliang on 2018/2/14.
 */

@Getter
@Setter
public abstract class AbstractCrawler {

    public static final Logger log= LoggerFactory.getLogger(AbstractCrawler.class);

    private Spider spider;


    protected void getUrl(String url, String[][] headers, ParserObserver observer) {
        getUrl(url, headers, observer, new Site());
    }

    protected void getUrl(String url, String[][] headers, ParserObserver observer, Site site) {
        if (url == null) {
            log.error("request.GET no url");
            return;
        }
        Request request = new Request();
        log.info("GET: request init ...");

        HttpUriRequest req = setRequest(RequestBuilder.get(), url, headers, null, site);
        log.info("GET: set request success...");
        request.setRequest(req);

        request.addObserver(observer);
        log.info("GET: add observer to this request...");

        spider.addRequest(request);
        log.info("GET: add request to spider...");

//        HttpResponse response = null;
//        try {
//            response = client.execute(req);
//        } catch (IOException e) {
//            log.error("execute GET URL(" + url + ") :" + e);
//        }
////        this.addObserver(observer);
//        request.setResponse(response);

    }

    protected void postUtl(String url, String[][] headers, String[][] params, ParserObserver observer) {
        postUrl(url, headers, params, observer, new Site());
    }

    protected void postUrl(String url, String[][] headers, String[][] params, ParserObserver observerss, Site site) {
        if (url == null) {
            log.error("request.POST no url");
            return;
        }
        Request request = new Request();
        log.info("POST: request init ...");
        HttpUriRequest req = setRequest(RequestBuilder.post(), url, headers, params, site);
        log.info("POST: set request success...");
        request.setRequest(req);

        request.addObserver(observerss);
        log.info("POST: add observer to this request...");

        spider.addRequest(request);
        log.info("POST: add request to spider...");

//        HttpResponse response = null;
//        try {
//            response = client.execute(req);
//        } catch (IOException e) {
//            log.error("execute POST URL(" + url + ") :" + e);
//        }
////        this.setResponse(response);
//        request.setResponse(response);
    }


    private HttpUriRequest setRequest(RequestBuilder builder, String url, String[][] headers, String[][] entity, Site site) {
        builder.setUri(url);

        if (headers != null && headers.length > 0) {
            for (String[] header : headers) {
                builder.addHeader(header[0], header[1]);
            }
        }
        Site si=site!=null?site:spider.getSite();
        if (entity != null && entity.length > 0) {
            for (String[] ent : entity) {
                builder.addParameter(ent[0], ent[1]);
            }
        }
        if(si!=null) {
            RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                    .setConnectionRequestTimeout(si.getTimeOut())
                    .setSocketTimeout(si.getTimeOut())
                    .setConnectTimeout(si.getTimeOut())
                    .setRedirectsEnabled(si.isFollowRedirect());
            builder.setConfig(requestConfigBuilder.build());
        }
        builder.setCharset(Charset.defaultCharset());
        builder.setVersion(null);
        return builder.build();
    }


}
