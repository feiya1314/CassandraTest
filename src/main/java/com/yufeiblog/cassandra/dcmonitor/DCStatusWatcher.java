package com.yufeiblog.cassandra.dcmonitor;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.ClientBuilder;
import com.coreos.jetcd.Watch;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.watch.WatchResponse;
import com.yufeiblog.cassandra.common.EtcdConfiguraion;
import com.yufeiblog.cassandra.SessionRepository;

public class DCStatusWatcher {
    private SessionRepository sessionRepository;
    private static final String baseEtcdDir = "/default/status";
    private Client etcdClient;
    private ClientBuilder etcdClientBuilder;
    private EtcdConfiguraion etcdConfiguraion;
    private Watch watch;

    public void init() {
        etcdClientBuilder = Client.builder();
        etcdClientBuilder.endpoints(etcdConfiguraion.getEctdHosts());
        etcdClient = etcdClientBuilder.build();

        watch = etcdClient.getWatchClient();
    }

    public void watch() {
        ByteSequence byteSequence = new ByteSequence(baseEtcdDir);
        Watch.Watcher watcher = watch.watch(byteSequence);
        WatchResponse watchResponse = null;
        try {
            watchResponse = watcher.listen();
        } catch (InterruptedException e) {

        }
        if (watchResponse != null) {
            watchResponse.getEvents();
        }
        sessionRepository.notifyClient(null);
    }
}
