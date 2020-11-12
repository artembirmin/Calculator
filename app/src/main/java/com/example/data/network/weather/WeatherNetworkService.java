package com.example.data.network.weather;

import com.example.data.repositories.WeatherApiRepository;
import com.google.gson.Gson;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherNetworkService implements  WeatherApiProvider {

    private  WeatherApi weatherApi;

    @Inject
    public WeatherNetworkService(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApiRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        weatherApi = retrofit.create(WeatherApi.class);
    }

    @Override
    public WeatherApi getWeatherApi() {
        return weatherApi;
    }
}
