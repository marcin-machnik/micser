package com.rogaszy.micser.microservice.common.service;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Example helper class. Executes SQL statement.
 */
public class ExampleHelper {
  public static void initData(Vertx vertx, JsonObject config) {
    vertx.executeBlocking(future -> {
      try {
        Connection connection = DriverManager.getConnection(config.getString("url"),
          config.getString("user"), config.getString("password"));
        connection.createStatement().executeUpdate("DELETE FROM \"Losses\"");
        connection.createStatement().executeUpdate("INSERT INTO \"Losses\" (\"lossId\", \"lossAmount\", \"currency\", \"businessLine\", \"linkGroupId\") " +
                                                        "VALUES ('small', 1234, 'USD', 'regular', 2)");
        connection.createStatement().executeUpdate("INSERT INTO \"Losses\" VALUES ('medium', 2222, 'PLN', 'group', 3)");
        connection.createStatement().executeUpdate("INSERT INTO \"Losses\" VALUES ('large', 9999, 'EUR', 'regular', 3)");
        future.complete();
      } catch (SQLException ex) {
        ex.printStackTrace();
        future.fail(ex);
      }
    }, ar -> {
      if (ar.failed()) {
        ar.cause().printStackTrace();
      }
    });
  }
}
