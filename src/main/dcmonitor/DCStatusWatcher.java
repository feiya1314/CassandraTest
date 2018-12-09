package main.dcmonitor;

import main.common.SessionRepository;

public class DCStatusWatcher implements EtcdWatcher{
    private SessionRepository sessionRepository;
    private static final String baseEtcdDir="/default/status";
    public void wartch()
    {
        sessionRepository.notifyClient(null);
    }
}
