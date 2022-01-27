package com.test.myapplication

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

/**
 * Created by Xinghai.Zhao on 2020/8/4.
 * 自定义选择弹框
 */
class CustomDialog(context: Context?) : AlertDialog(context!!) {

    var mCallBack: clickCallBack? = null;
    var mTextViewTitle: TextView? = null;
    var mTextViewContent: TextView? = null;
    var mYesButton: View? = null;
    var mNoButton: View? = null;
    var mEditText: EditText? = null;

    constructor(context: Context?, title: String?, content: String?, callBack: clickCallBack,text: String)
            : this(context, title, content, callBack, false,text) {
    }

    constructor(
        context: Context?,
        title: String?,
        content: String?,
        callBack: clickCallBack,
        isEdit: Boolean,
        text:String
    ) : this(context) {
        mCallBack = callBack
        if (title != null) mTextViewTitle?.text = title;
        if (content != null) mTextViewContent?.text = content;
        if (isEdit) {
            mTextViewContent?.visibility = View.GONE
            mEditText?.visibility = View.VISIBLE
            //最大输入长度
            mEditText?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(40))
            mEditText?.isFocusable = true
            mEditText?.isFocusableInTouchMode = true
            mEditText?.requestFocus()
            mEditText?.setText(text)
        }

    }

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
        setView(inflate)
        //设置点击别的区域不关闭页面
        setCancelable(false)

        mEditText = inflate.findViewById<EditText>(R.id.dialog_custom_edit)
        mTextViewTitle = inflate.findViewById<TextView>(R.id.dialog_custom_title)
        mTextViewContent = inflate.findViewById<TextView>(R.id.dialog_custom_content)
        mYesButton = inflate.findViewById<View>(R.id.dialog_custom_yes)
        mNoButton = inflate.findViewById<View>(R.id.dialog_custom_no)
        mYesButton?.setOnClickListener { mCallBack?.onYesClick(this) }
        mNoButton?.setOnClickListener { dismiss() }
    }

    interface clickCallBack {
        fun onYesClick(dialog: CustomDialog)
    }
}

