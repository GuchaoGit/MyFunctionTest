package com.guc.mylibrary.utils.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.guc.mylibrary.utils.commonadapter.base.ChildViewHolder;
import com.guc.mylibrary.utils.commonadapter.base.ParentViewHolder;

import java.util.List;

/**
 * Author:guc
 * Time:2019/5/20
 * Description:
 */
public abstract class GroupAdapter2<Parent, Child> extends BaseExpandableListAdapter {

    protected List<Parent> mGroups;
    protected List<List<Child>> mChilds;
    protected Context context;
    private LayoutInflater inflater;
    private int mParentLayoutId;
    private int mChildLayoutId;

    public GroupAdapter2(Context context, List<Parent> groups, List<List<Child>> childs, int parentLayoutId, int childLayoutId) {
        super();
        this.mGroups = groups;
        this.mChilds = childs;
        this.context = context;
        this.mParentLayoutId = parentLayoutId;
        this.mChildLayoutId = childLayoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChilds.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return mChilds.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ParentViewHolder holder = ParentViewHolder.get(context, convertView, null, mParentLayoutId, groupPosition);
        bindParentData(holder, (Parent) getGroup(groupPosition), groupPosition, isExpanded);
        return holder.getConvertView();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = ChildViewHolder.get(context, convertView, null, mChildLayoutId, childPosition);
        bindChildData(holder, (Child) getChild(groupPosition, childPosition), groupPosition, childPosition, isLastChild);
        return holder.getConvertView();
    }

    // 子选项是否可以选择
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public abstract void bindParentData(ParentViewHolder holder, Parent parent, int groupPosition, boolean isExpanded);

    public abstract void bindChildData(ChildViewHolder holder, Child child, int groupPosition, int childPosition,
                                       boolean isLastChild);

}
