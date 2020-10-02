package com.example.pogoda;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;


public class FiveDaysWeatherModel {
    @SerializedName("cod")
    public float cod;

    @SerializedName("message")
    public String message;

    @SerializedName("cnt")
    public int cnt;

    @SerializedName("list")
    public ArrayList<FiveDaysWeatherModel.List> list = new ArrayList<FiveDaysWeatherModel.List>();

    @SerializedName("city")
    public City city;

    public float getCod() {
        return cod;
    }

    public void setCod(float cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<List> getList() {
        return list;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
//==============================================================================================
    // subclasses:

    class List {
        @SerializedName("dt")
        public String dt;

        @SerializedName("main")
        public FiveDaysWeatherModel.Main main;

        @SerializedName("weather")
        public ArrayList<FiveDaysWeatherModel.Weather> weather = new ArrayList<FiveDaysWeatherModel.Weather>();

        @SerializedName("clouds")
        public FiveDaysWeatherModel.Clouds clouds;

        @SerializedName("wind")
        public FiveDaysWeatherModel.Wind wind;

        @SerializedName("sys")
        public FiveDaysWeatherModel.Sys sys;

        @SerializedName("dt_txt")
        public String dt_txt;

        public String getDt() {
            return dt;
        }

        public void setDt(String dt) {
            this.dt = dt;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public ArrayList<Weather> getWeather() {
            return weather;
        }

        public void setWeather(ArrayList<Weather> weather) {
            this.weather = weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }
    }

    //===============================================
    // subclasses for List class:
    class Main {
        @SerializedName("temp")
        public float temp;
        @SerializedName("humidity")
        public float humidity;
        @SerializedName("pressure")
        public float pressure;
        @SerializedName("temp_min")
        public float temp_min;
        @SerializedName("temp_max")
        public float temp_max;

        public float getTemp() {
            return temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public float getHumidity() {
            return humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public float getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(float temp_min) {
            this.temp_min = temp_min;
        }

        public float getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(float temp_max) {
            this.temp_max = temp_max;
        }
    }

    class Weather {
        @SerializedName("id")
        public int id;
        @SerializedName("main")
        public String main;
        @SerializedName("description")
        public String description;
        @SerializedName("icon")
        public String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    class Clouds {
        @SerializedName("all")
        public float all;

        public float getAll() {
            return all;
        }

        public void setAll(float all) {
            this.all = all;
        }
    }

    class Wind {
        @SerializedName("speed")
        public float speed;
        @SerializedName("deg")
        public float deg;

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public float getDeg() {
            return deg;
        }

        public void setDeg(float deg) {
            this.deg = deg;
        }
    }

    class Sys {
        @SerializedName("pod")
        public String pod;

        public String getPod() {
            return pod;
        }

        public void setPod(String pod) {
            this.pod = pod;
        }
    }

    //================================================================
    // Class city:

    class City {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("coord")
        public FiveDaysWeatherModel.Coord coord;
        @SerializedName("country")
        public String country;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    //=====================================
    // subclass for City class:
    class Coord {
        @SerializedName("lon")
        public float lon;
        @SerializedName("lat")
        public float lat;

        public float getLon() {
            return lon;
        }

        public void setLon(float lon) {
            this.lon = lon;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }
    }
}
