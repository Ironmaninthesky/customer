package com.example.daxiansheng.customer.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by DaXianSheng on 2016/1/7.
 */
public class CacheUtil {
    public static void cleanInternalCache(Context context){
        deleteDir(context.getCacheDir());
    }

    public static void cleanDatabases(Context context){
        deleteDir(new File("data/data/"+context.getPackageName()+"/databases"));
    }
    //clear the sharePreference
    public static void cleanSharePreference(Context context){
        deleteDir(new File("data/data/"+context.getPackageName()+"/shared_prefs"));
    }
    public static void cleanFiles(Context context){
        deleteDir(context.getFilesDir());
    }
    public static void cleanExternalCache(Context context){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }
    public static void cleanCustomCache(String filePath){
        deleteDir(new File(filePath));
    }
    public static void cleanAppData(Context context){
        cleanInternalCache(context);
        cleanDatabases(context);
        cleanSharePreference(context);
        cleanFiles(context);
        cleanExternalCache(context);
//        if (filePath==null) {
//            return;
//        }
//        for (String file :
//                filePath) {
//           cleanCustomCache(file);
//        }
    }
    private static boolean deleteDir(File dir) {
        if (dir!=null&&dir.isDirectory()) {
            String[] list = dir.list();
            for (int i = 0; i < list.length; i++) {
                boolean success = deleteDir(new File(dir, list[i]));
                if (!success) {
                    return false;
                }
            }

        }
        return dir.delete();
    }

    //calculate the size
    public static long getFolderSize(File file){
        long size=0;
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size=size+getFolderSize(files[i]);
            }else {
                size=size+files[i].length();
            }
        }
        return size;
    }

    //format the 单位←_←
    public static String getFormatSize(double size){
        double kiloByte = size / 1024;
        if (kiloByte<1){
            return size+"Byte";
        }
        double megaByte = kiloByte / 1024;
        if( megaByte<1){
            BigDecimal bigDecimal = new BigDecimal(Double.toString(kiloByte));
            return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(megaByte));
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()+"MB";
        }
        double teraByte = gigaByte / 1024;
        if (teraByte < 1) {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(gigaByte));
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal bigDecimal = new BigDecimal(teraByte);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
    public static String getCacheSize(File file){
        return getFormatSize(getFolderSize(file));
    }
}
