package com.rogaszy.micser.microservice.loss;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link com.rogaszy.micser.microservice.loss.Loss}.
 * NOTE: This class has been automatically generated from the {@link com.rogaszy.micser.microservice.loss.Loss} original class using Vert.x codegen.
 */
public class LossConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Loss obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "lossAmount":
          break;
        case "lossId":
          break;
      }
    }
  }

  public static void toJson(Loss obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Loss obj, java.util.Map<String, Object> json) {
    json.put("lossAmount", obj.getLossAmount());
    if (obj.getLossId() != null) {
      json.put("lossId", obj.getLossId());
    }
  }
}
