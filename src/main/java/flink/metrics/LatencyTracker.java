package flink.metrics;

import flink.utils.other.FileUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LatencyTracker {

    public static void computeLatency(long startTime,long endTime,int index) throws IOException {
        double latency=(double) (endTime-startTime)/100000;
        BufferedWriter writer;
        switch (index){
            case 1:
                writer = new BufferedWriter(new FileWriter("metrics/query1latency.txt",true));
                writer.write(latency+"\n");
                writer.close();
                break;

            case 2:
                writer = new BufferedWriter(new FileWriter("metrics/query2latency.txt",true));
                writer.write(latency+"\n");
                writer.close();
                break;
            case 3:
                writer = new BufferedWriter(new FileWriter("metrics/query3latencydirect.txt",true));
                writer.write(latency+"\n");
                writer.close();
                break;
            case 4:
                writer = new BufferedWriter(new FileWriter("metrics/query3latencyindirect.txt",true));
                writer.write(latency+"\n");
                writer.close();
                break;
                default:
                    break;
        }
    }
}