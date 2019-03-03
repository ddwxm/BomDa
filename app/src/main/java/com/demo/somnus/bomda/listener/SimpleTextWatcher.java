package com.demo.somnus.bomda.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Somnus on 2018/4/12.
 *
 */

public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public abstract void onTextChanged(CharSequence s, int start, int before, int count);

    @Override
    public void afterTextChanged(Editable s) {

    }
}
