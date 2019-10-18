package com.duan.android.base.loadsir;

import com.duan.android.base.R;
import com.kingja.loadsir.callback.Callback;


/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :
 * version: 2.2.0
 * </pre>
 */

public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}
