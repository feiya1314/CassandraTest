package com.yufeiblog.cassandra.service;

import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TableMetadata;
import com.yufeiblog.cassandra.SessionManager;
import com.yufeiblog.cassandra.SessionRepository;
import com.yufeiblog.cassandra.utils.Utils;

import java.util.Map;

public class BaseService {
    protected SessionManager sessionManager;
    protected String createUser = "CREATE USER %s WITH PASSWORD '%s' NOSUPERUSER";
    protected String grantPermissionSelect = "GRANT SELECT ON ALL KEYSPACES TO '%s'";
    protected String grantPermissionModify = "GRANT MODIFY ON ALL KEYSPACES TO '%s'";
    protected Map<String, Statement> statementCache;
    protected Integer appId;
    protected SessionRepository sessionRepository;

    public BaseService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public BaseService(int appId, SessionRepository sessionRepository) {
        this(sessionRepository);
        this.appId = appId;
    }

    protected Statement prepareStatement(int appId, String sql) {
        Statement statement = statementCache.get(sql);
        return statement;
    }

    protected boolean isKeyspaceExist(int appId){
        KeyspaceMetadata keyspaceMetadata = sessionRepository.getSession().getCluster().getMetadata().getKeyspace(Utils.getKeyspace(appId));
        return keyspaceMetadata != null;
    }
    protected boolean isTableExist(int appId, String tableName) {
        TableMetadata tableMetadata = sessionRepository.getSession().getCluster().getMetadata().getKeyspace(Utils.getKeyspace(appId)).getTable(tableName);
        return tableMetadata != null;
    }
}
