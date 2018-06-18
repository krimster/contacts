package com.erickrim.contacts.rx;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TestInteractorSchedulers implements InteractorSchedulers {
    @Override
    public Scheduler getExecutionScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getPostExecutionScheduler() {
        return Schedulers.trampoline();
    }
}
