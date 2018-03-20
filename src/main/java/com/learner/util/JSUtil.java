package com.learner.util;

import lombok.extern.slf4j.Slf4j;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by liufangliang on 2018/2/17.
 */
@Slf4j
public class JSUtil {


    private static ScriptEngineManager scriptEngineManager=new ScriptEngineManager();

    public static String runResourceJs(String filePath,String funcName,Object ...args){
        return  run(filePath,true,funcName,args);
    }
    public static String runJs(String filePath,String funcName,Object ...args){
        return  run(filePath,false,funcName,args);
    }

    public static String run(String filePath,boolean resourcesOrNot,String funcName,Object ...args){

        String result= null;
        try {
            InputStream inputStream;
            if(resourcesOrNot){
                inputStream=JSUtil.class.getResourceAsStream(filePath);
            }else{
                inputStream=new FileInputStream(filePath);
            }
            ScriptEngine engine=scriptEngineManager.getEngineByName("JavaScript");
            InputStreamReader reader=new InputStreamReader(inputStream);
            engine.eval(reader);
            Invocable invocable=(Invocable)engine;
            result=invocable.invokeFunction(funcName,args).toString();
        } catch (Exception e) {
            log.error("run js err :"+e);
        }
        return result;
    }


    public static String runJSFile(String path,String returnName) {
//        String filePath=Thread.currentThread();
        return null;
    }
    public static void main(String[] args) {

        String js="function a(a,b,c){return a+b+c}";
        System.out.println(JSUtil.runResourceJs("/Users/liufangliang/IdeaProjects/common/src/a.js","a",1,2,3));
    }
}
