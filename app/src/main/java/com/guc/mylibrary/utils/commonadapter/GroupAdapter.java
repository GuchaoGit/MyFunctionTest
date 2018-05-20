package com.guc.mylibrary.utils.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guc.mylibrary.R;

import java.util.List;

/**
 * Author:guc
 * Time:2018/5/20
 * Description:
 */
public class GroupAdapter extends BaseExpandableListAdapter {

    private List<String> groups;
    private List<List<String>> childs;
    private Context context;
    private LayoutInflater inflater;

    public GroupAdapter(List<String> groups, List<List<String>> childs, Context context) {
        super();
        this.groups = groups;
        this.childs = childs;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childs.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return childs.get(groupPosition).get(childPosition);
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
        convertView = inflater.inflate(R.layout.item_group, null);
        TextView groupNameTextView = (TextView) convertView
                .findViewById(R.id.tv_group);
        ImageView ivSelector = (ImageView) convertView
                .findViewById(R.id.iv_selector);
        groupNameTextView.setText(getGroup(groupPosition).toString());
        ivSelector.setImageResource(R.drawable.icon_close);

        // 更换展开分组图片
        if (!isExpanded) {
            ivSelector.setImageResource(R.drawable.icon_open);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_child, null);
        TextView nickTextView = (TextView) convertView
                .findViewById(R.id.tv_child);

        nickTextView.setText(getChild(groupPosition, childPosition).toString());

        return convertView;
    }

    // 子选项是否可以选择
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
