package com.example.data.network.weather;

import com.example.models.Weather;

public interface WeatherApiProvider {

    String WEATHER_API_KEY = "52890ff4987cf82ef04c79cccc4bd74c";
    String BASE_URL = "https://api.openweathermap.org/";

    Weather getWeather();
}
