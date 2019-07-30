package com.guc.mylibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.guc.mylibrary.R;
import com.guc.mylibrary.utils.commonadapter.ViewHolder;

import java.util.List;

/**
 * Created by guc on 2019/7/30.
 * 描述：
 */
public class AdapterSingleSelect<T> extends BaseAdapter {
    private List<T> mDatas;
    private Context mContext;
    public AdapterSingleSelect(Context context, List<T> mDatas){
        this.mDatas = mDatas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas == null?0:mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas ==null?null :mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext,convertView,parent, R.layout.item_list,position);
        holder.setText(R.id.tv_content,getItem(position).toString());
        return holder.getConvertView();
    }

}
