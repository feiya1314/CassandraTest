package main.loadbalance;

import com.datastax.driver.core.Host;

public interface DCSwitchLoadbalance {
    public String selectDC(Host host);
}
