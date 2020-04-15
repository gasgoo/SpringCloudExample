package com.design.Iterator;


/**
 * 书架类  具体的集合
 * @Date 2019/7/2 16:08
 */
public class BookShelf implements Aggregate {

    private Book[] books;
    private int last=0;
    public BookShelf(int maxsize){
        this.books=new Book[maxsize];
    }
    public Book getBookAt(int index){
        return books[index];
    }
    public void addBook(Book book){
        this.books[last]=book;
        last++;
    }


    public int getLength(){
        return last;
    }


    @Override
    public Iterator iterator() {
        return new BookShelfIterator(this);
    }
}
