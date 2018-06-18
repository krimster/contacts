package com.erickrim.contacts.rx;

import io.reactivex.Scheduler;

public interface InteractorSchedulers {
    Scheduler getExecutionScheduler();
    Scheduler getPostExecutionScheduler();
}
