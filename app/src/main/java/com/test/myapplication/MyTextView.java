package com.test.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;

public class MyTextView extends androidx.appcompat.widget.AppCompatTextView{

    public MyTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    @Override
    @ExportedProperty(category = "focus")
    public boolean isFocused() {
        // TODO Auto-generated method stub
        return true;
    }
}