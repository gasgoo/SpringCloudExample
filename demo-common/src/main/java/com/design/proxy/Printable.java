package com.design.proxy;

/**
 * 代理模式中的 顶层接口
 * @Date 2019/8/19 15:19
 */
public interface Printable {

    public abstract  void setPrinterName(String name);
    public abstract  String getPrinterName();
    public abstract  void print(String string);
}
