package flink.utils.flink.query1;

import redis.clients.jedis.Jedis;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

public class FinalRank {

    private String key;
    private BufferedWriter writer;
    private int position;
    public FinalRank(String key, BufferedWriter writer,int position) {
        this.key=key;
        this.writer=writer;
        this.position=position;
    }


    public void getRank()  {

            //Get position element from Redis
            Jedis jedis=new Jedis("localhost");
            Set<String> rank = jedis.zrange(key, 0, position-1);
            String[] finalRank= rank.toArray(new String[0]);

            //Write result on file
            try {
                //writer.write(""+key.split("_")[1]+",");

                for(int i=0;i<finalRank.length;i++){
                    writer.write("("+finalRank[i].split("_")[0]+","+finalRank[i].split("_")[1]+"),");
                    if(i==position-1)
                        break;
                }
                writer.write("\n");
                jedis.del(this.key);
                writer.close();
                jedis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

