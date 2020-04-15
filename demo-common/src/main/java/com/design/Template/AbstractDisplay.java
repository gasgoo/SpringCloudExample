package com.design.Template;

/**
 * 模板模式  模板类 定义程序行为的框架  子类去实现
 * @Date 2019/7/8 9:45
 */
public abstract class AbstractDisplay {

        public abstract void open();
        public abstract void print();
        public abstract void close();

        /*关键的方法 该方法子类不能重写*/
        public final void display( ){
            open();
            for(int i=0;i<10;i++){
                print();
            }
            close();
        }
}
