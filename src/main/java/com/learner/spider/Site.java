package com.learner.spider;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Site {

    private boolean followRedirect = true;
    private int timeOut = 60000;


}
