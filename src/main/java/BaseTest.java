package main.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class BaseTest {
    public static void main(String[] args) throws IOException{
        String rep = "{\"class\": \"NetworkTopologyStrategy\",\"DC1\": \"2\",\"DC2\": \"2\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> replication = null;
        replication = objectMapper.readValue(rep,Map.class);
        System.out.println(replication.toString());
    }
}
