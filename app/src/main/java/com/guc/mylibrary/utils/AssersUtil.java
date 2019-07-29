package com.guc.mylibrary.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guc.mylibrary.bean.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by guc on 2019/7/25.
 * 描述：资源文件读取
 */
public class AssersUtil {
    static Gson gson = new Gson();
    private static String FILE_NAME_CITY = "city.json";

    public static List<City> getCities(Context context) {
        String jsonData = getStringFromAssets(context, FILE_NAME_CITY);
        return gson.fromJson(jsonData, new TypeToken<List<City>>() {
        }.getType());
    }

    private static String getStringFromAssets(Context context, String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(filename)));
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
