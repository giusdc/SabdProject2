package flink.utils.flink.query1;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.dropwizard.metrics.DropwizardMeterWrapper;
import org.apache.flink.metrics.Meter;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import flink.utils.other.FileUtils;

public class Query1Rank extends ProcessWindowFunction<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple, TimeWindow> {

    private transient Meter meter;
    String file;
    public  Query1Rank(String file) {
        this.file=file;
    }

    @Override
    public synchronized void process(Tuple tuple, Context context, Iterable<Tuple2<String, Integer>> iterable, Collector<Tuple2<String, Integer>> collector) {

        //Only for process
        com.codahale.metrics.Meter dropwizard = new com.codahale.metrics.Meter();
        this.meter = getRuntimeContext().getMetricGroup().addGroup("Query1").meter("throughput_rank "+FileUtils.getId(this.file), new DropwizardMeterWrapper(dropwizard));
        this.meter.markEvent();
        //Create the key from searching in the db
        String id= FileUtils.getId(file)+"1"+"_"+context.window().getStart();
        Tuple2<String, Integer> tupleWindows = iterable.iterator().next();
        collector.collect(iterable.iterator().next());
        new PartialArticleRank(id,tupleWindows).rank();

    }



}
