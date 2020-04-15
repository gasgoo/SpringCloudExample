package com.design.Iterator;


/**
 * 遍历书架的类   具体的迭代器类和抽象的迭代器Iterator
 * @Date 2019/7/2 16:13
 */
public class BookShelfIterator implements Iterator{


    private BookShelf bookShelf;
    private int index;
    public BookShelfIterator(BookShelf bookShelf){
        this.bookShelf=bookShelf;
    }
    @Override
    public boolean hasNext(){
        if(index<bookShelf.getLength()){
            return true;
        }else{
            return  false;
        }
    }
    @Override
    public Object next(){
        Book book=bookShelf.getBookAt(index);
        index++;
        return book;
    }



}
