package com.yufeiblog.cassandra.loadbalance;

import com.datastax.driver.core.*;
import com.google.common.base.Joiner;
import com.google.common.collect.AbstractIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DCSwitchRoundRobinPolicy implements SwitchLoadbalancePolicy {
    private static final Logger logger = LoggerFactory.getLogger(DCSwitchRoundRobinPolicy.class);

    private static final String UNSET = "";
    private final ConcurrentMap<String, CopyOnWriteArrayList<Host>> perDcLiveHosts = new ConcurrentHashMap<String, CopyOnWriteArrayList<Host>>();
    private final AtomicInteger index = new AtomicInteger();
    volatile String localDc;
    private volatile Configuration configuration;
    private final int usedHostsPerRemoteDc=0;

    @Override
    public void setLoaclDC(String dc) {

    }

    @Override
    public void setShard(boolean isShard) {

    }

    @Override
    public void setDegraded(boolean isDegraded) {

    }

    @Override
    public void init(Cluster cluster, Collection<Host> hosts) {
        if (localDc != UNSET)
            logger.info("Using provided data-center name '{}' for DCAwareRoundRobinPolicy", localDc);
        this.configuration = cluster.getConfiguration();

        ArrayList<String> notInLocalDC = new ArrayList<String>();

        for (Host host : hosts) {
            String dc = dc(host);

            // If the localDC was in "auto-discover" mode and it's the first host for which we have a DC, use it.
            if (localDc == UNSET && dc != UNSET) {
                logger.info("Using data-center name '{}' for DCAwareRoundRobinPolicy (if this is incorrect, please provide the correct datacenter name with DCAwareRoundRobinPolicy constructor)", dc);
                localDc = dc;
            } else if (!dc.equals(localDc))
                notInLocalDC.add(String.format("%s (%s)", host.toString(), dc));

            CopyOnWriteArrayList<Host> prev = perDcLiveHosts.get(dc);
            if (prev == null)
                perDcLiveHosts.put(dc, new CopyOnWriteArrayList<Host>(Collections.singletonList(host)));
            else
                prev.addIfAbsent(host);
        }

        if (notInLocalDC.size() > 0) {
            String nonLocalHosts = Joiner.on(",").join(notInLocalDC);
            logger.warn("Some contact points don't match local data center. Local DC = {}. Non-conforming contact points: {}", localDc, nonLocalHosts);
        }

        this.index.set(new Random().nextInt(Math.max(hosts.size(), 1)));
    }

    private String dc(Host host) {
        String dc = host.getDatacenter();
        return dc == null ? localDc : dc;
    }

    @Override
    public HostDistance distance(Host host) {
        String dc = dc(host);
        if (dc == UNSET || dc.equals(localDc))
            return HostDistance.LOCAL;

        CopyOnWriteArrayList<Host> dcHosts = perDcLiveHosts.get(dc);
        if (dcHosts == null || usedHostsPerRemoteDc == 0)
            return HostDistance.IGNORED;

        // We need to clone, otherwise our subList call is not thread safe
        dcHosts = cloneList(dcHosts);
        return dcHosts.subList(0, Math.min(dcHosts.size(), usedHostsPerRemoteDc)).contains(host)
                ? HostDistance.REMOTE
                : HostDistance.IGNORED;
    }

    @SuppressWarnings("unchecked")
    private static CopyOnWriteArrayList<Host> cloneList(CopyOnWriteArrayList<Host> list) {
        return (CopyOnWriteArrayList<Host>) list.clone();
    }

    @Override
    public Iterator<Host> newQueryPlan(String loggedKeyspace, Statement statement) {
        CopyOnWriteArrayList<Host> localLiveHosts = perDcLiveHosts.get(localDc);
        final List<Host> hosts = localLiveHosts == null ? Collections.<Host>emptyList() : cloneList(localLiveHosts);
        final int startIdx = index.getAndIncrement();

        return null;
    }

    private class HostIterator extends AbstractIterator<Host>{

        public HostIterator(String loggedKeyspace,List<Host> hosts){

        }
        @Override
        protected Host computeNext() {
            return null;
        }
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
