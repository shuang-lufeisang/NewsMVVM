package com.duan.newsmvvm.otherfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.duan.newsmvvm.R;
import com.duan.newsmvvm.databinding.FragmentOthersBinding;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

/**
 * <pre>
 * author : Duan
 * time : 2019/10/14
 * desc :  AccountFragment
 * version: 2.2.0
 * </pre>
 */

public class AccountFragment extends Fragment {
    FragmentOthersBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_others, container, false);
        mBinding.homeTxtTitle.setText(getString(R.string.menu_account));
        return mBinding.getRoot();
    }
}
