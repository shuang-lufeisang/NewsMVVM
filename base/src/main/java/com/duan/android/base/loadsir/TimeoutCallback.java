package com.duan.android.base.loadsir;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.kingja.loadsir.callback.Callback;
import com.duan.android.base.R;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :
 * version: 2.2.0
 * </pre>
 */
public class TimeoutCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_timeout;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        Toast.makeText(context.getApplicationContext(),"Connecting to the network again!",Toast.LENGTH_SHORT).show();
        return false;
    }

}
