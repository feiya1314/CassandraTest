package com.yufeiblog.cassandra.test;

import com.yufeiblog.cassandra.common.UpdateOption;

import java.sql.SQLOutput;
import java.sql.Time;
import java.util.*;

public class SaveCase extends BaseTest {

    @Override
    protected void test() {
        Map<String, Object>[] records = getRecords();
        service.save(appId, tableName, records, null);
    }

    protected Map<String, Object>[] getRecords() {
        Map<String, Object>[] records = new Map[recordSize];
        int currentUid = uid.getAndIncrement();
        for (int i = 0; i < recordSize; i++) {
            records[i] = new HashMap<>();
            records[i].put("uid", String.valueOf(currentUid));
            records[i].put("prim1", "prim1_" + currentUid + i);
            records[i].put("prim2", "prim2_" + (currentUid + i));
            records[i].put("title", "title" + (currentUid + i));
            records[i].put("author", "feiya");
            records[i].put("time", "time" + new Date().toString());
            records[i].put("phone", "12345679" + (currentUid + i));
            records[i].put("email", "feifei@126.com");
        }
        return records;
    }

    public static void main(String[] args) {
        new SaveCase().execute();
    }
}
