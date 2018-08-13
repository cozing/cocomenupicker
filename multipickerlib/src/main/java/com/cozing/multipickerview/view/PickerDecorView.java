package com.cozing.multipickerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

/**
 * desc:
 * <p>
 * Author: Cozing
 * GitHub: https://github.com/Cozing
 * Date: 2018/8/9
 */

public class PickerDecorView extends FrameLayout{

    private OnDispatchBackKeyEventListener listener;

    public PickerDecorView(Context context) {
        super(context);
    }

    public PickerDecorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PickerDecorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if(event.getAction() == KeyEvent.ACTION_UP){
                if(listener != null){
                    listener.onBack();
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void setDispatchBackKeyEventListener(OnDispatchBackKeyEventListener listener){
        this.listener = listener;
    }

    public interface OnDispatchBackKeyEventListener{
        void onBack();
    }
}
