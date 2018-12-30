package com.yufeiblog.cassandra.loadbalance;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.List;
import java.util.Map;

public abstract class BaseDCSwitchLoadBalance implements DCSwitchLoadbalance {
    static final HashFunction HASH_FUNCTION= Hashing.murmur3_32();
    @Override
    public String selectDC(String tableName, List<String> dcNames, Map<String, Object> pk) {
        return null;
    }

    private String directDispatch(Map<String, Object> pk){
        return null;
    }
}
