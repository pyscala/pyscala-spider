package com.learner.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by liufangliang on 2018/2/17.
 */
public class JSUtil {


    private static ScriptEngineManager scriptEngineManager=new ScriptEngineManager();

    private static ScriptEngine engine=scriptEngineManager.getEngineByName("JavaScript");

    public static String run(String jsCode,String propertityName){
        String result= null;
        try {
            engine.eval(jsCode);
            result = engine.get(propertityName).toString();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String runJSFile(String path,String returnName) {
//        String filePath=Thread.currentThread();
        return null;
    }
    public static void main(String[] args) {
        netscape.javascript.JSUtil jsUtil=new netscape.javascript.JSUtil();

        String js="var i =10;i++";
        System.out.println(JSUtil.run(js,"i"));
    }
}
