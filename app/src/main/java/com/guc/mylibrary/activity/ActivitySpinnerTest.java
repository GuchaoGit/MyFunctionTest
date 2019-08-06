package com.guc.mylibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.guc.mylibrary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitySpinnerTest extends AppCompatActivity {

    @BindView(R.id.spinner)
    AppCompatSpinner mSpinner;
    @BindView(R.id.spinner2)
    AppCompatSpinner mSpinner2;

    public static void jump(Context context) {
        Intent intent = new Intent(context, ActivitySpinnerTest.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_test);
        ButterKnife.bind(this);
        mSpinner.setSelection(2);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String content = parent.getItemAtPosition(position).toString();
                Toast.makeText(ActivitySpinnerTest.this, "选择的专业是：" + content, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] arr = {"0~18", "19~25", "26~40", "41~60", "61~"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        mSpinner2.setAdapter(adapter);

        mSpinner2.getViewTreeObserver().addOnGlobalLayoutListener(() ->
                mSpinner2.setDropDownVerticalOffset(mSpinner2.getHeight()));
    }
}
