package com.yufeiblog.cassandra.common;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.yufeiblog.cassandra.dcmonitor.DCStatus;
import com.yufeiblog.cassandra.dcmonitor.DCStatusListener;
import com.yufeiblog.cassandra.loadbalance.BaseSwitchLoadbalancePolicy;

import java.util.Map;

public class SessionRepository implements DCStatusListener {
    private BaseSwitchLoadbalancePolicy loadbalancePolicy;
    private Cluster cluster;
    private CassandraConfiguration configuration;
    private Session session;
    private Map<String, Object> replication;

    public SessionRepository(Cluster cluster,CassandraConfiguration configuration, Map<String, Object> replication) {
        this.cluster = cluster;
        this.configuration=configuration;
        this.replication=replication;
    }


    @Override
    public void notifyClient(DCStatus dcStatus) {
        //loadbalancePolicy
        LoadBalancingPolicy loadBalancingPolicy = cluster.getConfiguration().getPolicies().getLoadBalancingPolicy();
        if (loadBalancingPolicy instanceof BaseSwitchLoadbalancePolicy) {
            //todo switch
        }
        cluster.getConfiguration().getPoolingOptions().refreshConnectedHosts();
    }

    public Session getSession() {

        return null;
    }

    public Session getSession(int appId) {

        return cluster.connect(getKeyspace(appId));
    }

    public String getKeyspace(int appId) {
        return null;
    }
}
