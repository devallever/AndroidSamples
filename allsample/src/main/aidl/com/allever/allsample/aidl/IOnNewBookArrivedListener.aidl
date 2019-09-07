// IOnNewBookArrivedListener.aidl
package com.allever.allsample.aidl;

// Declare any non-default types here with import statements

import com.allever.allsample.aidl.Book;

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void onNewBookArrived(in Book book);
}
