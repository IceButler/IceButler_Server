package com.example.icebutler_server.global.util;

public final class Constant {
  public static final String ENCODE_TYPE = "multipart/form-data";
  public static final String FRIDGE = "fridge";
  public static final String MULTI_FRIDGE = "multi";
  public static final String COMMA = ",";
  public static final String API_URL="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
  public static final String ACCESS_KEYID="726nwzjyd7";
  public static final String SECRET_KEY="UlPo60Xf0LuYOmzZTcT7N2w6YV7Hx9WS9cHGVDU5";
  public static final String APPLE_SANDBOX_URL = "https://sandbox.itunes.apple.com/verifyReceipt";
  public static final String APPLE_PRODUCTION_URL = "https://buy.itunes.apple.com/verifyReceipt";

  public static class Food {
    public static final String IMG_FOLDER = "food/";
    public static final String ICON_EXTENSION = ".png";
  }
  public static class FridgeFood {
    public static final String IMG_FOLDER = "fridge-food/";
  }

  public static class PushNotification{
    public static final String FRIDGE = "냉장고";
    public static final String IMMINENT_EXPIRATION = "유통기한";
  }
}
