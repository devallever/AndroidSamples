package com.allever.allsample.aidl.manumal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.allever.allsample.aidl.Book;

import java.util.List;

/**
 * Created by allever on 17-11-4.
 */

public interface IBookManager  extends IInterface{
    static final String DESCRIPTOR = "com.allever.allsample.aidl.manumal.IBookManager";

    static final int TRANSCATION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;
    static final int TRANSCATION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    public List<Book> getBookList() throws RemoteException;
    public void addBook(Book book) throws RemoteException;
}
