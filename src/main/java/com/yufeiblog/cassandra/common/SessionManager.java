package com.yufeiblog.cassandra.common;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;

public class SessionManager {
    private static Session session = null;
    private static Cluster cluster = null;
    private String contactPoint = "192.168.3.8";
    private SessionRepository sessionRepository;
    private Map<String, Object> replication = null;
    //private String port
    private CassandraConfiguration configuration = null;
    private String cassandraUserName;
    private String cassandraPassword;
    private boolean keepAlive = true;
    private AuthProvider authProvider;
    private LoadBalancingPolicy loadBalancingPolicy;
    private int port = 9042;
    private int connectTimeoutMillis = 5000;

    public Session getSession() {
        if (session == null) {
            //session = getCluster().connect();
            return session;
        }
        return session;
    }

    public void init() {
        Cluster.Builder builder = Cluster.builder()
                //.addContactPoint(contactPoint)
                .addContactPointsWithPorts(prepareContactPoints(contactPoint))
                .withAuthProvider(new PlainTextAuthProvider(cassandraUserName, cassandraPassword))
                .withPort(port)
                .withPoolingOptions(new PoolingOptions()
                        .setCoreConnectionsPerHost(HostDistance.LOCAL, 4)
                        .setMaxConnectionsPerHost(HostDistance.LOCAL, 10)
                        .setCoreConnectionsPerHost(HostDistance.REMOTE, 2)
                        .setMaxConnectionsPerHost(HostDistance.REMOTE, 4))
                //.withCredentials(cassandraUserName, cassandraPassword)
                .withQueryOptions(prepareQueryOptions())
                .withCompression(ProtocolOptions.Compression.SNAPPY)
                .withSocketOptions(prepareSocketOptions())
                .withTimestampGenerator(ServerSideTimestampGenerator.INSTANCE)
                .withRetryPolicy(prepareRetryPolicy())
                .withLoadBalancingPolicy(loadBalancingPolicy == null ? defaultLoadBalance() : loadBalancingPolicy);

        cluster = builder.build();
        sessionRepository = new SessionRepository(cluster,configuration,replication);
    }

   private InetSocketAddress[] prepareContactPoints(String contactPoints){
       String[] contactPointArr = StringUtils.split(contactPoints,",");
       //校验长度
       Validate.notThan();
       InetSocketAddress[] inetSocketAddress =new InetSocketAddress[contactPointArr.length];
        int i=0;
       for(String contactPoint : contactPointArr){
           String[] temp = StringUtils.split(contactPoint,":");
           try {
               if (temp.length>1){
                   InetAddress inetAddresses = InetAddress.getByName(temp[0]);
                   inetSocketAddress[i++] = new InetSocketAddress(inetAddresses,Integer.valueOf(temp[1]));
               } else {
                   InetAddress inetAddresses = InetAddress.getByName(temp[0]);
                   inetSocketAddress[i++] = new InetSocketAddress(inetAddresses,Integer.valueOf(temp[1]));
               }
           }catch (UnknownHostException e) {

           }
       }
       return inetSocketAddress;
   }
    private QueryOptions prepareQueryOptions() {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setConsistencyLevel(ConsistencyLevel.LOCAL_QUORUM);
        queryOptions.setSerialConsistencyLevel(ConsistencyLevel.SERIAL);
        return queryOptions;
    }

    private RetryPolicy prepareRetryPolicy() {
        RetryPolicy retryPolicy = DefaultRetryPolicy.INSTANCE;
        retryPolicy = new LoggingRetryPolicy(retryPolicy);
        //more Policy
        return retryPolicy;
    }

    private SocketOptions prepareSocketOptions() {
        SocketOptions socketOptions = new SocketOptions();
        socketOptions.setKeepAlive(keepAlive);
        socketOptions.setConnectTimeoutMillis(connectTimeoutMillis);
        return socketOptions;
    }

    private void prepareReplication() {
        if (replication != null) {
            return;
        }
        Set<Host> hosts = cluster.getMetadata().getAllHosts();
    }

    public String getContactPoint() {
        return contactPoint;
    }

    public void setContactPoint(String contactPoint) {
        this.contactPoint = contactPoint;
    }

    public String getCassandraUserName() {
        return cassandraUserName;
    }

    public void setCassandraUserName(String cassandraUserName) {
        this.cassandraUserName = cassandraUserName;
    }

    public CassandraConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CassandraConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setReplication(String replication) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.replication = (Map) objectMapper.readValue(replication, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            //throw new exception
        }
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public LoadBalancingPolicy getLoadBalancingPolicy() {
        return loadBalancingPolicy;
    }

    public void setLoadBalancingPolicy(LoadBalancingPolicy loadBalancingPolicy) {
        this.loadBalancingPolicy = loadBalancingPolicy;
    }

    public String getCassandraPassword() {
        return cassandraPassword;
    }

    public void setCassandraPassword(String cassandraPassword) {
        this.cassandraPassword = cassandraPassword;
    }

    public void close() {
        if (cluster == null) {
            return;
        }
        cluster.close();
        cluster = null;
    }

    private LoadBalancingPolicy defaultLoadBalance() {
        return new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().build());
    }

    public Map<String, Object> getReplication() {
        return replication;
    }

    public void execute(String queryString) {
        ResultSet resultSet = session.execute(queryString);
    }
}
