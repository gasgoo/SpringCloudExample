package com.thread;

import lombok.Getter;
import lombok.Setter;

/**
 * wait notify 使用
 * @Date 2020/4/20 17:44
 * @name Express
 */

@Getter
@Setter
public class Express {

    public static  final String city="shanghai";
    private int km;
    private String site;

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void checkKm(){
        this.km=105;
        notifyAll();
    }
    //公里小于100则等待 大于则通知
    public synchronized void waitKm(){
        while(this.km<100){
            try{
                wait();
                System.out.println("公里数检查线程:"+Thread.currentThread().getName());
            }catch (Exception e){
                System.out.println("Exception");
            }
        }

        System.out.println("the km is:"+this.km+" notify All");
    }

    public synchronized void checkSite(){
        this.site="beijing";
        notifyAll();
    }
    //快递到达北京则通知
    public synchronized  void waitSite(){
        while(this.site.equals(city)){
            try{
                wait();
                System.out.println("站点检查线程:"+Thread.currentThread().getName());
            }catch (Exception e){
                System.out.println("Exception");
            }
        }
        System.out.println("the site is:"+this.site+" notify All");
    }



}
