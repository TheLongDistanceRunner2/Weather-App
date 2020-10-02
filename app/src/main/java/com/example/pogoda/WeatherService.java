package com.example.pogoda;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherService {

    @GET("data/2.5/weather?")
    Call<CurrentWeatherModel> getCurrentWeatherData(@Query("q") String city,
                                                    @Query("lang") String language,
                                                    @Query("APPID") String app_id,
                                                    @Query("units") String units);

    @GET("data/2.5/forecast?")
    Call<FiveDaysWeatherModel> getFiveDaysWeatherData(@Query("q") String city,
                                                    @Query("lang") String language,
                                                    @Query("APPID") String app_id,
                                                    @Query("units") String units);
}
