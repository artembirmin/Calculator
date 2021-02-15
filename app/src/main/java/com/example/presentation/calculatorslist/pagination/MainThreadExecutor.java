package com.example.presentation.calculatorslist.pagination;

import android.os.Handler;
import android.os.Looper;

public class MainThreadExecutor implements java.util.concurrent.Executor {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }
}

