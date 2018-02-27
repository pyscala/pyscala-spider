package com.learner.spider;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : liufangliang
 * @description :
 * @date : 2017\12\28 0028
 */

public class Request implements Subject {

    private List<Observer> observers = new ArrayList<Observer>();
    @Setter
    @Getter
    private HttpUriRequest request;

    private HttpResponse httpResponse;




    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.afterRequest(httpResponse);
        }
    }

    @Override
    public void setResponse(HttpResponse response) {
        this.httpResponse = response;
        this.notifyObserver();
    }

}
