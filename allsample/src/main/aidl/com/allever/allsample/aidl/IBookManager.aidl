// IBookManager.aidl
package com.allever.allsample.aidl;

// Declare any non-default types here with import statements
import com.allever.allsample.aidl.Book;
import com.allever.allsample.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     List<Book> getBookList();
     void addBook(in Book book);
     void registListener(IOnNewBookArrivedListener listener);
     void unregistListener(IOnNewBookArrivedListener listener);
}
