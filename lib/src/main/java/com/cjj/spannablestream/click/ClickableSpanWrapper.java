package com.cjj.spannablestream.click;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.cjj.spannablestream.color.ColorConfig;

/**
 * Created on 2016/8/12
 *
 * @author chenjj2048
 */
public class ClickableSpanWrapper extends ClickableSpan {
    private final OnSpannableClickListener mListener;
    private final int mTextColorNormal;
    private final int mTextColorPressed;
    private final int mBackgroundColorNormal;
    private final int mBackgroundColorPressed;

    private boolean isPressed;
    @NonNull
    private CharSequence mClickText = "";

    public ClickableSpanWrapper(ColorConfig colorConfig, OnSpannableClickListener listener) {
        this.mListener = listener;
        this.mTextColorNormal = colorConfig.getTextColorNormal();
        this.mTextColorPressed = colorConfig.getTextColorPressed();
        this.mBackgroundColorNormal = colorConfig.getBackgroundColorNormal();
        this.mBackgroundColorPressed = colorConfig.getBackgroundColorPressed();
    }

    public ClickableSpanWrapper setClickText(CharSequence clickText) {
        this.mClickText = (clickText == null) ? "" : clickText;
        return this;
    }

    public ClickableSpanWrapper setClickText(CharSequence clickText, int start, int end) {
        CharSequence str = (clickText == null) ? "" : clickText.subSequence(start, end);
        return this.setClickText(str);
    }

    protected void onPressStateChanged(boolean pressed) {
        this.isPressed = pressed;
        if (mListener != null)
            mListener.onPressedStateChanged(pressed);
    }

    @Override
    public void onClick(View view) {
        if (mListener != null)
            mListener.onSpannableItemClick(view, mClickText.toString());
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        if ((mTextColorNormal | mTextColorPressed | mBackgroundColorNormal | mBackgroundColorPressed) == 0) {
            super.updateDrawState(ds);
            return;
        }

        //Set foreground color of text
        if (isPressed && mTextColorPressed != 0) {
            ds.setColor(mTextColorPressed);
        } else if (!isPressed && mTextColorNormal != 0) {
            ds.setColor(mTextColorNormal);
        }

        //Set background color of text
        if (isPressed) {
            ds.bgColor = (mBackgroundColorPressed == 0) ? Color.TRANSPARENT : mBackgroundColorPressed;
        } else {
            ds.bgColor = (mBackgroundColorNormal == 0) ? Color.TRANSPARENT : mBackgroundColorNormal;
        }
    }
}