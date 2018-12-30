package com.yufeiblog.cassandra.service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.yufeiblog.cassandra.common.Condition;
import com.yufeiblog.cassandra.common.SessionManager;
import com.yufeiblog.cassandra.common.TableOptions;
import com.yufeiblog.cassandra.result.CreateKeyspaceResult;
import com.yufeiblog.cassandra.result.CreateUserResult;
import com.yufeiblog.cassandra.result.FindResult;
import com.yufeiblog.cassandra.result.Result;

import java.util.List;
import java.util.Map;

public class CassandraManageServiceImpl implements CassandraManageService {
    private SessionManager session;
    private String createUser = "CREATE USER %s WITH PASSWORD '%s' NOSUPERUSER";
    private String grantPermissionSelect = "GRANT SELECT ON ALL KEYSPACES TO '%s'";
    private String grantPermissionModify = "GRANT MODIFY ON ALL KEYSPACES TO '%s'";

    public CassandraManageServiceImpl(SessionManager session) {
        this.session = session;
    }

    @Override
    public boolean insert(Object pk, String keyspace, Map<String, Object> records) {
        return false;
    }

    @Override
    public CreateUserResult createUser(String username, String password) {
        try {
            session.execute(String.format(createUser, username, password));
            session.execute(String.format(grantPermissionSelect, username));
            session.execute(String.format(grantPermissionModify, username));
        } catch (Exception e) {
            e.printStackTrace();
            //todo throw new Excetion
        }
        return null;
    }

    @Override
    public Result createTable(String keyspace, String tableName, String[] columns, TableOptions options) {
        /*CreateKeyspace createKeyspace = SchemaBuilder.createKeyspace(keyspace);
        createKeyspace.with().replication(session.getReplication());
        session.getSession().execute(createKeyspace);*/
        return null;
    }

    @Override
    public Result createKeyspace(String keyspace) {
        ResultSet resultSet=null;
        try {
            resultSet = session.getSession().execute(SchemaBuilder.createKeyspace(keyspace)
                    .with()
                    .replication(session.getReplication()
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
       // resultSet.wasApplied();
        Result result = new CreateKeyspaceResult();
        return result;
    }

    @Override
    public FindResult find(String tableName, String[] columns, List<Condition> conditions) {
        return null;
    }
}



