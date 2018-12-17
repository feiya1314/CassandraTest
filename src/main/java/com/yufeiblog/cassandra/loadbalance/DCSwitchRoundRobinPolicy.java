package main.java.com.yufeiblog.cassandra.loadbalance;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.Statement;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DCSwitchRoundRobinPolicy implements BaseSwitchLoadbalancePolicy {
    private static final String UNSET = "";
    private final ConcurrentMap<String, CopyOnWriteArrayList<Host>> perDcLiveHosts = new ConcurrentHashMap<String, CopyOnWriteArrayList<Host>>();
    private final AtomicInteger index = new AtomicInteger();


    @Override
    public void setLoaclDC(String dc) {

    }

    @Override
    public void setShard(boolean isShard) {

    }

    @Override
    public void init(Cluster cluster, Collection<Host> hosts) {

    }

    @Override
    public HostDistance distance(Host host) {
        return null;
    }

    @Override
    public Iterator<Host> newQueryPlan(String loggedKeyspace, Statement statement) {
        return null;
    }

    @Override
    public void onAdd(Host host) {

    }

    @Override
    public void onUp(Host host) {

    }

    @Override
    public void onDown(Host host) {

    }

    @Override
    public void onRemove(Host host) {

    }

    @Override
    public void close() {

    }
}
