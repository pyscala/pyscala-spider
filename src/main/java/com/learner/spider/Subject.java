package com.learner.spider;

import org.apache.http.HttpResponse;

/**
 * @author : liufangliang
 * @description :
 * @date : 2017\12\28 0028
 */
public interface Subject {

    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObserver();
    void setResponse(HttpResponse response);
}
