package com.example.examplework;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ThirdWork extends Worker {
    public ThirdWork(@NonNull  Context context, @NonNull  WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull

    @Override
    public Result doWork() {
        Log.i("APPSDC","This is the Third Work");
        return Result.success();
    }
}
