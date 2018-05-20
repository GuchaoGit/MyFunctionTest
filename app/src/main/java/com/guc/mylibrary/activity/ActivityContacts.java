package com.guc.mylibrary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guc.mylibrary.R;
import com.guc.mylibrary.widgets.contacts.ChineseToEnglish;
import com.guc.mylibrary.widgets.contacts.CompareSort;
import com.guc.mylibrary.widgets.contacts.SideBarView;
import com.guc.mylibrary.widgets.contacts.UserAdapter;
import com.guc.mylibrary.widgets.contacts.UserKH;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:guc
 * Time:2018/5/20
 * Description:仿微信通讯录
 */
public class ActivityContacts extends Activity implements SideBarView.LetterSelectListener{
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.sidebarview)
    SideBarView sidebarview;
    @BindView(R.id.tip)
    TextView tip;

    private List<UserKH> mKhList;
    private UserAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        sidebarview.setOnLetterSelectListen(this);
        String firstSpell;
        String substring ;
        String dataStr = "[{\"ID\":2,\"YHM\":\"\",\"SFBM\":\"\",\"CSBM\":\"\",\"XQBM\":\"3604280000\", \"DWBM\":\"100\",\"DWMC\":\"测试通讯录1\",\"DWDZ\":\"河南\",\"TXFZ\":\"2\",\"LXRXM\":\"陈宇豪\",\"SJHM\":\"13232323232\",\"SJHM2\":\"\",\"QQ\":\"853241851\",\"WX\":\"132\",\"ZJH\":\"0371-38989889\",\"BZ\":\"备足\",\"TJR\":\"admin\",\"TJSJ\":\"20170531154831\",\"XGR\":\"admin\",\"XGSJ\":\"20170602101519\",\"XTPT\":\"NT10.0\",\"SBMC\":\"DESKTOP-IIFH102\",\"SBIP\":\"\",\"BY1\":\"\",\"BY2\":\"\",\"BY3\":\" \",\"BY4\":\" \",\"BY5\":\"\",\"BY6\":\"\",\"DQ\":\"江西省|九江市|都昌县\",\"TXZ\":\"上海\"}" +
                ",{\"ID\":2,\"YHM\":\"\",\"SFBM\":\"\",\"CSBM\":\"\",\"XQBM\":\"3604280000\", \"DWBM\":\"100\",\"DWMC\":\"你好啊\",\"DWDZ\":\"河南\",\"TXFZ\":\"2\",\"LXRXM\":\"陈宇豪\",\"SJHM\":\"13232323232\",\"SJHM2\":\"\",\"QQ\":\"853241851\",\"WX\":\"132\",\"ZJH\":\"0371-38989889\",\"BZ\":\"备足\",\"TJR\":\"admin\",\"TJSJ\":\"20170531154831\",\"XGR\":\"admin\",\"XGSJ\":\"20170602101519\",\"XTPT\":\"NT10.0\",\"SBMC\":\"DESKTOP-IIFH102\",\"SBIP\":\"\",\"BY1\":\"\",\"BY2\":\"\",\"BY3\":\" \",\"BY4\":\" \",\"BY5\":\"\",\"BY6\":\"\",\"DQ\":\"江西省|九江市|都昌县\",\"TXZ\":\"上海\"}," +
                "{\"ID\":2,\"YHM\":\"\",\"SFBM\":\"\",\"CSBM\":\"\",\"XQBM\":\"3604280000\", \"DWBM\":\"100\",\"DWMC\":\"我的地盘\",\"DWDZ\":\"河南\",\"TXFZ\":\"2\",\"LXRXM\":\"陈宇豪\",\"SJHM\":\"13232323232\",\"SJHM2\":\"\",\"QQ\":\"853241851\",\"WX\":\"132\",\"ZJH\":\"0371-38989889\",\"BZ\":\"备足\",\"TJR\":\"admin\",\"TJSJ\":\"20170531154831\",\"XGR\":\"admin\",\"XGSJ\":\"20170602101519\",\"XTPT\":\"NT10.0\",\"SBMC\":\"DESKTOP-IIFH102\",\"SBIP\":\"\",\"BY1\":\"\",\"BY2\":\"\",\"BY3\":\" \",\"BY4\":\" \",\"BY5\":\"\",\"BY6\":\"\",\"DQ\":\"江西省|九江市|都昌县\",\"TXZ\":\"上海\"}," +
                "{\"ID\":2,\"YHM\":\"\",\"SFBM\":\"\",\"CSBM\":\"\",\"XQBM\":\"3604280000\", \"DWBM\":\"100\",\"DWMC\":\"好的\",\"DWDZ\":\"河南\",\"TXFZ\":\"2\",\"LXRXM\":\"陈宇豪\",\"SJHM\":\"13232323232\",\"SJHM2\":\"\",\"QQ\":\"853241851\",\"WX\":\"132\",\"ZJH\":\"0371-38989889\",\"BZ\":\"备足\",\"TJR\":\"admin\",\"TJSJ\":\"20170531154831\",\"XGR\":\"admin\",\"XGSJ\":\"20170602101519\",\"XTPT\":\"NT10.0\",\"SBMC\":\"DESKTOP-IIFH102\",\"SBIP\":\"\",\"BY1\":\"\",\"BY2\":\"\",\"BY3\":\" \",\"BY4\":\" \",\"BY5\":\"\",\"BY6\":\"\",\"DQ\":\"江西省|九江市|都昌县\",\"TXZ\":\"上海\"}]";
            mKhList = new Gson().fromJson(dataStr,new TypeToken<List<UserKH>>(){}.getType());
        for (UserKH user : mKhList){
            firstSpell = ChineseToEnglish.getFirstSpell(user.getDWMC());
            substring = firstSpell.substring(0, 1).toUpperCase();
            if(substring.matches("[A-Z]")){
                user.letter=substring;
            }else {
                user.letter="#";
            }
        }
        mAdapter = new UserAdapter(this);
        Collections.sort(mKhList, new CompareSort());

        mAdapter.setData(mKhList);
        listview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLetterSelected(String letter) {
        setListviewPosition(letter);
        tip.setText(letter);
        tip.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLetterChanged(String letter) {
        setListviewPosition(letter);
        tip.setText(letter);
    }

    @Override
    public void onLetterReleased(String letter) {
        tip.setVisibility(View.GONE);
    }

    private void setListviewPosition(String letter) {
        int firstLetterPosition = mAdapter.getFirstLetterPosition(letter);
        if (firstLetterPosition != -1) {
            listview.setSelection(firstLetterPosition);
        }
    }
}
