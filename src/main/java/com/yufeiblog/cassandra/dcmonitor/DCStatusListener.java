package main.java.com.yufeiblog.cassandra.dcmonitor;

public interface DCStatusListener {
    public void notifyClient(DCStatus dcStatus);
}