package com.learner.spider;

import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * Created by liufangliang on 2018/3/2.
 */
@Data
public class RespContext {

    private String charset;

    private HttpResponse response;

    private HttpClientContext context;


}
