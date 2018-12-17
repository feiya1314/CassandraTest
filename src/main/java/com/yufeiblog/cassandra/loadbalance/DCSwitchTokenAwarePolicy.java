package main.java.com.yufeiblog.cassandra.loadbalance;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.Statement;
import com.google.common.collect.AbstractIterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class DCSwitchTokenAwarePolicy implements BaseSwitchLoadbalancePolicy {

    private BaseSwitchLoadbalancePolicy childPolicy;
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
        childPolicy.onAdd(host);
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

    private class HostIterator extends AbstractIterator<Host>{
        public HostIterator(Statement statement, Iterator<Host> iterator, String loggedKeyspace, Set<Host> replicas){

        }
        @Override
        protected Host computeNext() {
            return null;
        }
    }
}
