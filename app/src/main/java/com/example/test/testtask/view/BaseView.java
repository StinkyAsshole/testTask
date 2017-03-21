package com.example.test.testtask.view;

import android.content.Context;

public interface BaseView {
    Context getContext();
    void showError(Throwable e);
}
