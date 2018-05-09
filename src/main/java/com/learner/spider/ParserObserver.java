package com.learner.spider;


import org.apache.http.HttpResponse;

/**
 * @author : liufangliang
 * @description :
 * @date : 2017\12\28 0028
 */
public abstract class ParserObserver implements Observer {

  public ParserObserver(){

  }

  public abstract void afterRequest(RespContext response);

}
