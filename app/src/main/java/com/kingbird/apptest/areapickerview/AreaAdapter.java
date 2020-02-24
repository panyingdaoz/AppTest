package com.kingbird.apptest.areapickerview;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kingbird.apptest.R;

import java.util.List;

/**
 * @ClassName: Constant
 * @Description: java类作用描述
 * @Author: Pan
 * @CreateDate: 2020/1/17 17:05
 */
public class AreaAdapter extends BaseQuickAdapter<AddressBean.CityBean.AreaBean, BaseViewHolder> {
    public AreaAdapter(int layoutResId, @Nullable List<AddressBean.CityBean.AreaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean.CityBean.AreaBean item) {
        helper.setText(R.id.textview, item.getLabel());
        helper.setTextColor(R.id.textview, item.isStatus() ? Color.parseColor("#65C15C") : Color.parseColor("#444444"));
    }
}
