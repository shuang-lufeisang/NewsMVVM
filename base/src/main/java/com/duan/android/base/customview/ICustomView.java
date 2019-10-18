package com.duan.android.base.customview;

public interface ICustomView<VM extends BaseCustomViewModel> {

    void setData(VM data);

    void setStyle(int resId);

    void setActionListener(ICustomViewActionListener listener);

}
