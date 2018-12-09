package main.service;

import main.common.TableOptions;
import main.result.CreateUserResult;
import main.result.Result;

import java.util.Map;

public interface CassandraManageService {

    boolean insert(Object pk , String keyspace , Map<String,Object> records);
    CreateUserResult createUser(String username, String password);
    Result createTable(String keyspace,String tableName,String[] columns,TableOptions options);
    Result createKeyspace(String keyspace);
}
