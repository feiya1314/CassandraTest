package com.yufeiblog.cassandra.service;

import com.yufeiblog.cassandra.common.Condition;
import com.yufeiblog.cassandra.common.TableOptions;
import com.yufeiblog.cassandra.result.CreateUserResult;
import com.yufeiblog.cassandra.result.FindResult;
import com.yufeiblog.cassandra.result.Result;

import java.util.List;
import java.util.Map;

public interface CassandraManageService {

    boolean insert(Object pk , String keyspace , Map<String,Object> records);
    CreateUserResult createUser(String username, String password);
    Result createTable(String keyspace,String tableName,String[] columns,TableOptions options);
    Result createKeyspace(String keyspace);
    FindResult find(String tableName, String[] columns, List<Condition> conditions);
}
