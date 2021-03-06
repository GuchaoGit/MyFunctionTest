package com.guc.mylibrary.utils.commonadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author: hardcattle
 * Time: 2018/3/13 下午3:37
 * Description:
 */

public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    protected Context mContext;
    protected List<T> mDataList;
    protected int mLayoutId;
    private LayoutInflater mLayoutInflater;

    public CommonRecycleAdapter(Context context, List<T> dataList, int layoutId) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDataList = dataList;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    @NonNull
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(mLayoutId, parent, false);
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        bindData(holder, mDataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public void update(List<T> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    public void addList(List<T> dataList) {
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public abstract void bindData(CommonViewHolder holder, T data, int position);
}
