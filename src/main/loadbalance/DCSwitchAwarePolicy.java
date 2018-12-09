package main.loadbalance;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.Statement;

import java.util.Collection;
import java.util.Iterator;

public class DCSwitchAwarePolicy implements BaseSwitchLoadbalancePolicy {
    @Override
    public void setLoaclDC(String dc) {

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
