package com.design.bridge;

/**
 * 类的功能层次结构 添加功能扩展
 * @Date 2019/7/8 17:51
 */
public class CountDisplay extends Display {

    public CountDisplay(DisplayImpl impl) {
        super(impl);
    }
    public void multiDisplay(int times){
        open();
        for(int i=0;i<times;i++){
            print();
        }
        close();
    }
}
