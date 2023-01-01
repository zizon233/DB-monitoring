package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import java.util.HashMap;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {

  public static void main(String[] args) throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

    FlinkMySqlConnector flinkMySqlConnector = new FlinkMySqlConnector();
    MySqlSource<String> mySQlSource = flinkMySqlConnector.getMySqlStreamSource("0.0.0.0", 3306,
        true, "payment", "payment.payment_history", "payments", "payments");
    DataStream<String> mySqlStream = env.fromSource(mySQlSource, WatermarkStrategy.noWatermarks(),
        "MySql Source CDC");

    ObjectMapper objectMApper = new ObjectMapper();

    DataStream<String> newPaymentStream = mySqlStream.filter(s -> {
      HashMap<String, String> cdcMap = objectMApper.readValue(s, HashMap.class);
      if (cdcMap.get("op").equals("c")) {
        return true;
      } else {
        return false;
      }
    });

    DataStream<String> cancelPaymentStream = mySqlStream.filter(s -> {
      HashMap<String, String> cdcMap = objectMApper.readValue(s, HashMap.class);
      if (cdcMap.get("op").equals("u")) {
        return true;
      } else {
        return false;
      }
    });

    env.enableCheckpointing(3000);
    env.execute();
  }
}