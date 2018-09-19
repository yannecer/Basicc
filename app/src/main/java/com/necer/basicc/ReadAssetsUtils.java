package com.necer.basicc;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by zhuodao on 2016/9/28.
 */

public class ReadAssetsUtils {


    /**
     * 读取txt文件
     *
     * @param inputStream
     * @return
     */
    private static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 计算器说明
     *
     * @param context
     * @return
     */
    public static String getImageJson(Context context) {
        AssetManager asset = context.getAssets();
        InputStream in = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer sb = new StringBuffer();
        ;
        try {
            in = asset.open("img.json");
            inputStreamReader = new InputStreamReader(in, "utf-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            in.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static String getAreaJson(Context context) {
        AssetManager asset = context.getAssets();
        InputStream in = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer sb = new StringBuffer();
        ;
        try {
            in = asset.open("SaleRegion.json");
            inputStreamReader = new InputStreamReader(in, "utf-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            in.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
