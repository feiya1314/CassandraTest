package com.yufeiblog.cassandra.common;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.yufeiblog.cassandra.dcmonitor.DCStatus;
import com.yufeiblog.cassandra.dcmonitor.DCStatusListener;
import com.yufeiblog.cassandra.loadbalance.SwitchLoadbalancePolicy;
import com.yufeiblog.cassandra.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class SessionRepository implements DCStatusListener {
    private SwitchLoadbalancePolicy loadbalancePolicy;
    private Cluster cluster;
    private CassandraConfiguration configuration;
    private Session defaultSession;
    private Map<String, Object> replication;
    private Map<Integer,Session> sessionCache = new HashMap<>();

    public SessionRepository(Cluster cluster,CassandraConfiguration configuration, Map<String, Object> replication) {
        this.cluster = cluster;
        this.configuration=configuration;
        this.replication=replication;
    }


    @Override
    public void notifyClient(DCStatus dcStatus) {
        //loadbalancePolicy
        LoadBalancingPolicy loadBalancingPolicy = cluster.getConfiguration().getPolicies().getLoadBalancingPolicy();
        if (loadBalancingPolicy instanceof SwitchLoadbalancePolicy) {
            //todo switch
        }
        cluster.getConfiguration().getPoolingOptions().refreshConnectedHosts();
    }

    public Session getSession() {
        synchronized (defaultSession) {
            if (defaultSession == null) {
                defaultSession = cluster.connect();
            }
        }
        return defaultSession;
    }

    public Session getSession(int appId) {
        Session session;
        synchronized (sessionCache){
           session=sessionCache.get(appId);
           if (session==null){
               session=cluster.connect(Utils.getKeyspace(appId));
           }
        }
        return session;
    }

}
