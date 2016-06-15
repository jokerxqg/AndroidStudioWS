package com.zzptc.joker.baiduguard.utils;

/**
 * Created by joker on 2016/6/1/001.
 */

public class MemoryUtils {

    public static String convertStorage(long memory){
        long kb = 1024;
        long mb = 1024 * kb;
        long gb = 1024 * mb;

        if(memory > 0){
            if(memory > gb){
                float size = (float)memory / gb;
                return String.format("%.1f GB",size);
            }else if(memory > mb){
                float size = (float)memory / mb;
                return String.format("%.1f MB",size);
            }else if(memory > kb){
                float size = (float)memory / kb;
                return String.format("%.1f KB",size);
            }else{
                return String.format("%.1f B",memory);
            }
        }else{
            return "0 B";
        }
    }
}
