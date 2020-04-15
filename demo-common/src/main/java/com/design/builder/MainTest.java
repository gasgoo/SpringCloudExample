package com.design.builder;

/**
 * @TODO
 * @Date 2019/7/1 15:49
 */
public class MainTest {
    public static void main(String[] args) {
        String cmd="html";
        if(cmd.equals("html")){
            HtmlBuilder htmlBuilder=new HtmlBuilder();
            Director director=new Director(htmlBuilder);
            director.construct();
        }else{
            TextBuilder textBuilder=new TextBuilder();
            Director director=new Director(textBuilder);
            director.construct();
        }

    }
}
