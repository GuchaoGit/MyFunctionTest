package com.guc.mylibrary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.guc.mylibrary.R;
import com.guc.mylibrary.widgets.MyDateTimePickDialog;
import com.guc.mylibrary.widgets.MyTimePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv_hello)
    TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_hello)
    public void onViewClicked() {
        startActivity(new Intent(this,ActivityExpandableListView.class));
    }
}
