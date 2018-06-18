package com.erickrim.contacts.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AndroidInteractorSchedulers implements InteractorSchedulers {

    @Override
    public Scheduler getExecutionScheduler() {
        return Schedulers.io();
    }

    @Override
    public Scheduler getPostExecutionScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
