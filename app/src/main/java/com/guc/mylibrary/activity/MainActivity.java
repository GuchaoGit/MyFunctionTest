package com.guc.mylibrary.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guc.mylibrary.R;
import com.guc.mylibrary.utils.IPUtil;
import com.guc.mylibrary.widgets.FoldView;
import com.guc.mylibrary.widgets.LooperTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.tv_expandable)
    TextView tvHello;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_ip)
    TextView mTvIP;
    @BindView(R.id.fd_view)
    FoldView mFdView;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.ltv_notice)
    LooperTextView mLtvNotice;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                mTvIP.setText((String) msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFdView.setControlView(mLlContent);
        initView();
        getNetIP();

    }

    /**
     * 获取公网ip
     */
    private void getNetIP() {
        Log.e(TAG, "getNetIP: Thread id :" + Thread.currentThread().getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = IPUtil.getNetIp();
                Message msg = new Message();
                msg.obj = ip;
                msg.what = 100;
                mHandler.sendMessage(msg);
                Log.e(TAG, "run: Thread id :" + Thread.currentThread().getId());
            }
        }).start();
    }

    private void initView() {
        mLtvNotice.setTipList(new ArrayList<String>(){
            {
                add("华为天际通全球上网，6月流量提前买...");
                add("2019国外移民新政策...");
                add("李小龙生前拍照姿势...");
                add("为跑滴滴，男子“零首付”买奥迪...");
            }
        });
    }

    @OnClick({R.id.tv_expandable, R.id.tv_contacts, R.id.tv_selector, R.id.fd_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_expandable:
                startActivity(new Intent(this, ActivityExpandableListView.class));
                break;
            case R.id.tv_contacts:
                startActivity(new Intent(this, ActivityContacts.class));
                break;
            case R.id.tv_selector:
                ActivityPickerViewTest.jump(this);
                break;
        }
    }

}
