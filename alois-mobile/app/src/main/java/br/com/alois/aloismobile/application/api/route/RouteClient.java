package br.com.alois.aloismobile.application.api.route;

import java.util.List;
import java.util.Map;

import br.com.alois.domain.entity.route.Route;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

/**
 * Created by victor on 3/24/17.
 */

public interface RouteClient
{
    @RequestLine("GET /route/listRoutesByPatientId/{patientId}")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    List<Route> listRoutesByPatientId(@Param("patientId") Long patientId, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("GET /json?")
    @Headers({"Content-Type: application/json"})
    String generateGoogleRoute(@QueryMap Map<String, String> queryMap);

    @RequestLine("POST /route/addRoute")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Route addRoute(Route route, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("PUT /route/updateRoute")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    Route updateRoute(Route route, @Param("basicAuthToken") String basicAuthToken);

    @RequestLine("POST /route/deleteRoute")
    @Headers({"Content-Type: application/json", "Authorization: Basic {basicAuthToken}"})
    void deleteRoute(Route route, @Param("basicAuthToken") String basicAuthToken);
}
