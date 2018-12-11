package main.loadbalance;

import com.datastax.driver.core.Host;

import java.util.List;
import java.util.Map;

public interface DCSwitchLoadbalance {
    public String selectDC(String tableName , List<String> dcNames , Map<String,Object> pk);
}