package org.example;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;

public class FlinkMySqlConnector {

  public MySqlSource getMySqlStreamSource(String hostName, Integer port,
      Boolean scanNewlyAddedTableEnable, String databaseList, String tableList, String userName,
      String password) {
    MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
        .hostname(hostName)
        .port(port)
        .scanNewlyAddedTableEnabled(scanNewlyAddedTableEnable)
        .databaseList(databaseList)
        .tableList(tableList)
        .username(userName)
        .password(password)
        .deserializer(new JsonDebeziumDeserializationSchema())
        .startupOptions(StartupOptions.latest())
        .build();

    return mySqlSource;
  }
}
