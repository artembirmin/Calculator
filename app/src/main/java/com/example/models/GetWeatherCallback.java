package com.example.models;

import com.example.models.Weather;

public interface GetWeatherCallback {

    void onSuccess(Weather weather);

    void onError(Throwable throwable);
}
