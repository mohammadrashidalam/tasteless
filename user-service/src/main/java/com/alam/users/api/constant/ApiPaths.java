package com.alam.users.api.constant;

public class ApiPaths {
    public static final String FEIGN_CLIENT_CALL="feignCallbackClient";
    public static final String REST_TEMPLATE_CALL="restTemplateCall";
    private ApiPaths() {
    }

    //SERVICE ENDPOINTS
    private static final String RATING_SERVICE_URI="http://RATING-SERVICE/";
    private static final String HOTEL_SERVICE_URI="http://HOTEL-SERVICE/";

    //COMMON URI ALL Controller endpoints.
    public static final String API_VERSION="/api/v1";
    //FEIGN CLIENT ENDPOINTS
    public static final String HOTEL_GET_BY_ID=API_VERSION+"/hotels/getHotelById";
    public static final String SAVE_HOTEL=API_VERSION+"/hotels/saveHotelDetails";
    public static final String SAVE_RATING=API_VERSION+"/ratings/saveRating";
    public static final String GET_RATING_BY_USER_ID=API_VERSION+"/ratings/users";

    //REST TEMPLATE ENDPOINTS
    public static final String RATING_ENDPOINT=RATING_SERVICE_URI+GET_RATING_BY_USER_ID;
    public static final String HOTEL_ENDPOINT=HOTEL_SERVICE_URI+HOTEL_GET_BY_ID;
}
