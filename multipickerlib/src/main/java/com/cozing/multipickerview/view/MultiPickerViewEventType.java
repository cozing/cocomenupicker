package com.cozing.multipickerview.view;

/**
 * desc:如果用于显示的对象不是String类型，而是一个javabean，则这个javabena需要实现该接口，
 * 并在重写方法中返回需要显示的内容String类型的子属性。此时可显示传进去的javabean类型的数据
 *
 * <p>
 * Author: Cozing
 * GitHub: https://github.com/Cozing
 * Date: 2018/8/14
 */

public interface MultiPickerViewEventType {
    String getPickerViewTextFieldForShow();
}
