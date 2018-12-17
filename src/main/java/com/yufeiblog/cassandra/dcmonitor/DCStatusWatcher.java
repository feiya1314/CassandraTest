package main.java.com.yufeiblog.cassandra.dcmonitor;

import main.java.com.yufeiblog.cassandra.common.SessionRepository;

public class DCStatusWatcher implements EtcdWatcher{
    private SessionRepository sessionRepository;
    private static final String baseEtcdDir="/default/status";
    public void wartch()
    {
        sessionRepository.notifyClient(null);
    }
}
