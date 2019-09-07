package com.allever.mysimple.decoratorPattern;

import android.content.PeriodicSync;

/**
 * Created by Allever on 2016/11/19.
 */

public abstract class PersonCloth extends Person {
    protected Person mPerson;//保持Person对象引用
    public PersonCloth(Person mPerson){
        this.mPerson = mPerson;
    }

    @Override
    public void dressed() {
        mPerson.dressed();
    }
}
