package com.learner.test;

import com.learner.crawler.AbstractCrawler;
import com.learner.spider.ParserObserver;
import com.learner.spider.RespContext;
import com.learner.spider.Spider;
import com.learner.spider.SpiderManager;
import com.learner.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;

/**
 * Created by liufangliang on 2018/2/14.
 */
@Slf4j
public class BaiduCrawler extends AbstractCrawler {
    public BaiduCrawler(Spider spider) {
        this.setSpider(spider);


    }


    public void getBaidu(){
        getUrl("https://baike.baidu.com/item/%E7%99%BE%E5%BA%A6%E7%99%BE%E7%A7%91/85895?fr=aladdin", null, new ParserObserver() {
            @Override
            public void afterRequest(RespContext response) {
                String cont= ContextUtil.getContext(response.getResponse());
                log.info(cont);
            }
        });
    }

    public static void main(String[] args) {
        Spider spider= SpiderManager.instance().create();
        BaiduCrawler crawler=new BaiduCrawler(spider);
        crawler.getBaidu();
        spider.start();
    }

}
