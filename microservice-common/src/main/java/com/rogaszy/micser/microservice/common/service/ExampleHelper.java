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
        connection.createStatement().executeUpdate("INSERT INTO \"Losses\" (\"lossId\", \"lossAmount\") VALUES ('small', 1234)");
        connection.createStatement().executeUpdate("INSERT INTO \"Losses\" (\"lossId\", \"lossAmount\") VALUES ('medium', 1234)");
        connection.createStatement().executeUpdate("INSERT INTO \"Losses\" (\"lossId\", \"lossAmount\") VALUES ('large', 1234)");
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
