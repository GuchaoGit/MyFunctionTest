package com.guc.mylibrary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.guc.mylibrary.R;
import com.guc.mylibrary.utils.commonadapter.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:guc
 * Time:2018/5/20
 * Description: 分组展示的Listview
 */
public class ActivityExpandableListView extends Activity {
    @BindView(R.id.expandable_lv)
    ExpandableListView mExpandableLv;

    private List<String> groups = new ArrayList<String>(){
        {
            add("我的好友");
            add("常用");
        }
    };
    private List<List<String>> childs = new ArrayList<List<String>>(){
        {
            add(new ArrayList<String>(){
                {
                    add("张三");
                    add("李四");
                    add("王五");
                    add("赵六");
                }
            });
            add(new ArrayList<String>(){
                {
                    add("天气");
                    add("你好");
                    add("夏天");
                }
            });
        }
    };

    private GroupAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_lsitview);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mAdapter= new GroupAdapter(groups,childs,this);
        mExpandableLv.setAdapter(mAdapter);
        mExpandableLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(ActivityExpandableListView.this,"您点击了",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
