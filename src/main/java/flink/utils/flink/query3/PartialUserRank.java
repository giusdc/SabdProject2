package flink.utils.flink.query3;

import org.apache.flink.api.java.tuple.Tuple2;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ZAddParams;

import java.util.*;

public class PartialUserRank {
    private String key;
    private Tuple2<Long,Float> tupleWindows;
    public  PartialUserRank(String key, Tuple2<Long, Float> tupleWindows) {
        this.key=key;
        this.tupleWindows=tupleWindows;
    }



    public synchronized void rank() {

        Jedis jedis=new Jedis("localhost");
        jedis.zadd(this.key,-1*tupleWindows.f1,tupleWindows.f0+"_"+tupleWindows.f1);

        if(jedis.zcard(this.key)>=11){
           // Set<String> rankElements = jedis.zrange(key, 0, 9);
            //HashMap<Float, String> hashMapRank = new HashMap<>();
            //rankElements.add(tupleWindows.f0+"_"+tupleWindows.f1);
            /*
            for(String rank :rankElements){
                hashMapRank.put(Float.parseFloat(rank.split("_")[1]),rank.split("_")[0]);
            }
            TreeMap treeMap = new TreeMap<>(Collections.reverseOrder());
            treeMap.putAll(hashMapRank);
            List<Float> values=new ArrayList<>(treeMap.keySet());
            List<String> keys=new ArrayList<>(treeMap.values());*/
            jedis.zremrangeByRank(this.key,10,-1);
            //jedis.zadd(this.key,tupleWindows.f1,tupleWindows.f0+"_"+tupleWindows.f1);
            /*jedis.del(this.key);
            for(int x=0;x<keys.size();x++){
                jedis.zadd(this.key,x+1,keys.get(x)+"_"+values.get(x));
                if(x==9)
                    break;
            }*/
        }
        jedis.close();
    }
}
