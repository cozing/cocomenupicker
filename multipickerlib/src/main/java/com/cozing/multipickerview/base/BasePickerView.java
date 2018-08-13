package com.cozing.multipickerview.base;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.cozing.multipickerview.R;
import com.cozing.multipickerview.utils.PickerViewAnimateUtil;
import com.cozing.multipickerview.view.PickerDecorView;

import java.lang.ref.WeakReference;

public abstract class BasePickerView{
    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );

    private final WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            0, 0,
            PixelFormat.RGBA_8888
    );

    private WeakReference<Context> context;

    private WeakReference<WindowManager> mWindowManager;
    private WeakReference<PickerDecorView>  pickerDecorView;
    protected WeakReference<ViewGroup> contentContainer;

    private OnDismissListener onDismissListener;
    private boolean isDismissing;

    private Animation outAnim;
    private Animation inAnim;
    private int gravity = Gravity.BOTTOM;

    public BasePickerView(Context context){
        this.context = new WeakReference<Context>(context);

        initViews();
        init();
        initEvents();
    }

    protected void initViews(){
        WindowManager manager = (WindowManager) context.get().getSystemService(Context.WINDOW_SERVICE);

        PickerDecorView decorView = (PickerDecorView) LayoutInflater.from(context.get()).inflate(R.layout.multipicker_basepickerview, null);
        ViewGroup container = decorView.findViewById(R.id.content_container);
        decorView.setDispatchBackKeyEventListener(new PickerDecorView.OnDispatchBackKeyEventListener() {
            @Override
            public void onBack() {
                dismiss();
                if(onDismissListener != null){
                    onDismissListener.onDismiss("");
                }
            }
        });

        decorView.setLayoutParams(new FrameLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setLayoutParams(params);

        mWindowManager = new WeakReference<WindowManager>(manager);
        pickerDecorView = new WeakReference<PickerDecorView>(decorView);
        contentContainer = new WeakReference<ViewGroup>(container);

        onDismissListener = getOnDismissListener();
    }

    protected void init() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }
    protected void initEvents() {
    }

    /**
     * show的时候调用
     *
     * @param decorView 这个View
     */
    private void onAttached(View decorView) {
        if(mWindowManager.get() != null && contentContainer.get() != null){
            windowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
            windowParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            windowParams.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
            mWindowManager.get().addView(decorView, windowParams);
            contentContainer.get().startAnimation(inAnim);
        }

    }
    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        setMultipickerDatas();
        if(context.get() != null && pickerDecorView.get() != null){
            onAttached(pickerDecorView.get());
        }
    }

    protected void setMultipickerDatas(){}

    public void dismiss() {
        if (isDismissing) {
            return;
        }

        //消失动画
        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                new Runnable() {
                    @Override
                    public void run() {
                        //从activity根视图移除
                        if(mWindowManager.get() != null && pickerDecorView.get() != null){
                            mWindowManager.get().removeViewImmediate(pickerDecorView.get());
                            isDismissing = false;
                        }

                    }
                }.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        if(contentContainer.get() != null){
            contentContainer.get().startAnimation(outAnim);
            isDismissing = true;
        }

    }
    public Animation getInAnimation() {
        if(context.get() != null){
            int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, true);
            return AnimationUtils.loadAnimation(context.get(), res);
        }
       return null;
    }

    public Animation getOutAnimation() {
        if(context.get() != null){
            int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, false);
            return AnimationUtils.loadAnimation(context.get(), res);
        }
        return null;
    }

    protected abstract OnDismissListener getOnDismissListener();

    public View findViewById(int id){
        if(contentContainer != null){
            return contentContainer.get().findViewById(id);
        }
        return null;

    }

    protected interface OnDismissListener {
        void onDismiss(Object o);
    }
}
