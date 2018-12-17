package com.yufeiblog.cassandra.loadbalance;

import com.datastax.driver.core.policies.LoadBalancingPolicy;

public interface BaseSwitchLoadbalancePolicy extends LoadBalancingPolicy {
    public void setLoaclDC(String dc);

    public void setShard(boolean isShard);
}
