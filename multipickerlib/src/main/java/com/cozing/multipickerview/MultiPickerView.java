package com.cozing.multipickerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cozing.multipickerview.base.BasePickerView;
import com.cozing.multipickerview.view.WheelView;

import java.util.List;

/**
 * desc:多级滚动选择菜单控件
 * <p>
 * Author: Cozing
 * GitHub: https://github.com/Cozing
 * Date: 2018/8/6
 */

public class MultiPickerView<T> extends BasePickerView {

    private int DEFAULT_PICKER_TEXT_SIZE = 20;
    private int pickerTextSize = DEFAULT_PICKER_TEXT_SIZE;

    private TextView multi_picker_cancel, multi_picker_title, multi_picker_confirm;
    private WheelView multi_picker_wheelview1, multi_picker_wheelview2;

    private List picker1Datas;
    private List<List> picker2Datas;

    private OnMultiPickerSelectListener mOnMultiPickerSelectListener;
    private OnMultiPickerDismissListener mOnMultiPickerDismissListener;
    private boolean loopable = false;

    public MultiPickerView(Context context) {
        super(context);
        initMultiPickerView(context);
    }

    private void initMultiPickerView(Context mContext){
        if(contentContainer.get() != null){
            LayoutInflater.from(mContext).inflate(R.layout.multipicker_view, contentContainer.get(), true);

            multi_picker_cancel = (TextView) findViewById(R.id.multi_picker_cancel);
            multi_picker_title = (TextView) findViewById(R.id.multi_picker_title);
            multi_picker_confirm = (TextView) findViewById(R.id.multi_picker_confirm);
            multi_picker_wheelview1 = (WheelView) findViewById(R.id.multi_picker_wheelview1);
            multi_picker_wheelview2 = (WheelView) findViewById(R.id.multi_picker_wheelview2);

            multi_picker_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if(mOnMultiPickerDismissListener != null){
                        mOnMultiPickerDismissListener.onDismiss();
                    }

                }
            });

            multi_picker_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if(mOnMultiPickerSelectListener != null){
                        mOnMultiPickerSelectListener.onSelect(multi_picker_wheelview1.getCurrentItem(), multi_picker_wheelview2.getCurrentItem());
                    }

                }
            });
        }
    }

    @Override
    protected void setMultipickerDatas() {
        if(picker1Datas != null && picker1Datas.size() != 0){
            multi_picker_wheelview1.setVisibility(View.VISIBLE);
            multi_picker_wheelview1.setAdapter(new ArrayWheelAdapter(picker1Datas));
            multi_picker_wheelview1.setCurrentItem(0);
            multi_picker_wheelview1.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    if(picker2Datas != null && picker2Datas.size() != 0){
                        int mMultiPicker2Item = multi_picker_wheelview2.getCurrentItem();
                        mMultiPicker2Item = mMultiPicker2Item >= picker2Datas.get(index).size() - 1 ? picker2Datas.get(index).size() - 1 : mMultiPicker2Item;
                        multi_picker_wheelview2.setAdapter(new ArrayWheelAdapter(picker2Datas.get(index)));
                        multi_picker_wheelview2.setCurrentItem(mMultiPicker2Item);
                    }
                }
            });
        }

        if(picker2Datas != null && picker2Datas.size() != 0){
            multi_picker_wheelview2.setVisibility(View.VISIBLE);
            multi_picker_wheelview2.setAdapter(new ArrayWheelAdapter(picker2Datas.get(0)));
            multi_picker_wheelview2.setCurrentItem(multi_picker_wheelview2.getCurrentItem());
            multi_picker_wheelview2.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {

                }
            });
        }

        multi_picker_wheelview1.setTextSize(pickerTextSize);
        multi_picker_wheelview2.setTextSize(pickerTextSize);
        multi_picker_wheelview1.setCyclic(loopable);
        multi_picker_wheelview2.setCyclic(loopable);
    }

    @Override
    protected OnDismissListener getOnDismissListener() {
        return new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                if(mOnMultiPickerDismissListener != null){
                    mOnMultiPickerDismissListener.onDismiss();
                }
            }
        };
    }

    /**
     * 设置滚动选择菜单的显示数据（该方法只显示一列滚动选择菜单）
     * @param datas 第一列显示的数据列表
     */
    public void setSinglePicker(List datas) {
        setDoublePicker(datas, null);
    }

    /**
     * 设置滚动选择菜单的显示数据（该方法显示两列滚动选择菜单）
     * @param datas1 第一列显示的数据列表
     * @param datas2 第二列显示的数据列表
     */
    public void setDoublePicker(List datas1, List<List> datas2) {
        this.picker1Datas = datas1;
        this.picker2Datas = datas2;
    }

    /**
     * 设置取消按钮的显示文字
     * @param cancelText
     */
    public void setCancelText(String cancelText){
        multi_picker_cancel.setText(cancelText);
    }

    /**
     * 设置确定按钮的显示文字
     * @param confirmText
     */
    public void setConfirmText(String confirmText){
        multi_picker_confirm.setText(confirmText);
    }

    /**
     * 设置两级联动选择菜单的标题
     * @param titleText
     */
    public void setTitleText(String titleText){
        multi_picker_title.setText(titleText);
    }

    /**
     * 设置正文数据显示的字体大小（单位sp）
     * @param sizeSP
     */
    public void setPickerTextSize(int sizeSP){
        this.pickerTextSize = sizeSP;
    }

    /**
     * 设置滚动数据是否循环
     * @param loopable  true:循环 false:不循环
     */
    public void setLoopable(boolean loopable){
        this.loopable = loopable;
    }

    /**
     * 设置确定按钮监听
     * @param listener 自定义OnMultiPickerSelectListener
     */
    public void setOnMultiPickerSelectListener(OnMultiPickerSelectListener listener){
        this.mOnMultiPickerSelectListener = listener;
    }

    /**
     * 设置取消监听，包括点击取消按钮的返回和点击设备物理返回键的监听
     * @param listener 自定义OnMultiPickerDismissListener
     */
    public void setOnMultiPickerDismissListener(OnMultiPickerDismissListener listener){
        this.mOnMultiPickerDismissListener = listener;
    }

    public class ArrayWheelAdapter<T> implements WheelView.WheelAdapter {

        /** The default items length */
        public static final int DEFAULT_LENGTH = 4;

        // items
        private List<T> items;

        /**
         * Constructor
         * @param items the items
         */
        public ArrayWheelAdapter(List<T> items) {
            this.items = items;
        }

        @Override
        public Object getItem(int index) {
            if (index >= 0 && index < items.size()) {
                return items.get(index);
            }
            return "";
        }

        @Override
        public int getItemsCount() {
            return items.size();
        }

        @Override
        public int indexOf(Object o){
            return items.indexOf(o);
        }
    }

    public interface OnMultiPickerSelectListener {
        void onSelect(int index1, int index2);
    }

    public interface OnMultiPickerDismissListener{
        void onDismiss();
    }
}
