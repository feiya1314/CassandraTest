package com.yufeiblog.cassandra.test;

import com.yufeiblog.cassandra.SessionManager;
import com.yufeiblog.cassandra.service.ICassandraManageService;
import com.yufeiblog.cassandra.service.CassandraManageService;

abstract class BaseTest {

    protected int appId = 20190103;
    protected String replication = "{\"class\": \"NetworkTopologyStrategy\",\"DC1\": \"2\",\"DC2\": \"2\"}";
    protected String tableName = "yftest88";
    protected String username = "cassandra";
    protected String password = "cassandra";
    protected String contactPoints = "192.168.3.8,192.168.3.10";
    protected ICassandraManageService service;

    public BaseTest() {
        init();
    }

    abstract void execute();

    protected void init() {
        SessionManager.Builder builder = SessionManager.builder()
                .withCredentials(username, password)
                .withContactPoints(contactPoints)
                .withReplication(replication);
                //.withLoadBalancingPolicy()
        SessionManager sessionManager =builder.build();
        service = new CassandraManageService(sessionManager.getSessionRepository());
    }

}
