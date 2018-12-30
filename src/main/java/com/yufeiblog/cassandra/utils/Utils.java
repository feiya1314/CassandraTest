package com.yufeiblog.cassandra.utils;

public class Utils {
    public static String getKeyspace(int appId){
        return "app"+appId;
    }
}
