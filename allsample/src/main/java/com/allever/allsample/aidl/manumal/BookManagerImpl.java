package com.allever.allsample.aidl.manumal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.allever.allsample.aidl.Book;

import java.io.FileDescriptor;
import java.util.List;

/**
 * Created by allever on 17-11-4.
 */

public class BookManagerImpl extends Binder implements IBookManager {

    public BookManagerImpl(){
        this.attachInterface(this,DESCRIPTOR);
    }

    public static IBookManager asInstance(IBinder obj){
        if (obj == null){
            return null;
        }

        IInterface iInterface = obj.queryLocalInterface(DESCRIPTOR);
        if ((iInterface != null) && (iInterface instanceof IBookManager)){
            return (IBookManager)iInterface;
        }
        return new BookManagerImpl.Proxy(obj);
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION:{
                reply.writeString(DESCRIPTOR);
            }

                return true;
            case TRANSCATION_addBook:{
                data.enforceInterface(DESCRIPTOR);
                Book book;
                if ( 0 != data.readInt()){
                    book = Book.CREATOR.createFromParcel(data);
                }else {
                    book = null;
                }
                this.addBook(book);
                reply.writeNoException();
                return true;
            }
            case TRANSCATION_getBookList:{
                data.enforceInterface(DESCRIPTOR);
                List<Book> result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
            }
            default:
                break;
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    public void addBook(Book book) throws RemoteException {

    }


    @Override
    public List<Book> getBookList() throws RemoteException {
        return null;
    }

    private static class Proxy implements IBookManager{

        private IBinder mRemote;

        Proxy(IBinder remote){
            mRemote = remote;
        }

        public String getInterfaceDescriptor(){
            return DESCRIPTOR;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<Book> bookList;

            try {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSCATION_getBookList,data,reply,0);
                reply.readException();
                bookList = reply.createTypedArrayList(Book.CREATOR);
            }finally {
                reply.recycle();
                data.recycle();
            }

            return bookList;

        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if (book != null){
                    data.writeInt(1);
                    book.writeToParcel(data,0);
                }else {
                    data.writeInt(0);
                }
                mRemote.transact(TRANSCATION_addBook,data,reply,0);
                reply.readException();
            }finally {
                reply.recycle();
                data.recycle();
            }
        }
    }
}
