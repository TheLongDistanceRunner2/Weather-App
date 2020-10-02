package com.example.pogoda;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.pogoda.CurrentWeatherModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CurrentWeatherFragment extends Fragment {

    private static String baseURL = "https://api.openweathermap.org/";
    private static String AppId= "afc7c746c4417731a59ccc589c4b7ffc";
    private String city = "Lodz,pl";
    private String language = "pl";
    private String units = "metric";

    ImageView icon;
    TextView city_name;
    TextView temperature;
    TextView description;
    TextView pressure;
    TextView humidity;
    TextView sunrise;
    TextView sunset;

    public void setCity(String text) {
        city = text + ",pl";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String tmp = getArguments().getString("params");
            city = tmp += ",pl";
        }
        else {
            city = "Lodz,pl";
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        this.city = event.getMessage();

        getCurrentData();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current_weather_layout, container, false);

        findViews(rootView);
        getCurrentData();

        // weather refresh:
        final SwipeRefreshLayout swipeRefreshLayout =  rootView.findViewById(R.id.swipeLayout_current);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh, R.color.refresh1, R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        getCurrentData();

//                        Toast.makeText(getActivity(),"Odświeżono aktualną prognozę!",
//                                Toast.LENGTH_SHORT).show();
                    }

                }, 2000);
            }
        });

        return rootView;
    }

    private void findViews(View rootView) {
        icon = rootView.findViewById(R.id.im_current_icon);
        city_name = rootView.findViewById(R.id.tv_current_city_name);
        temperature = rootView.findViewById(R.id.tv_current_temperature);
        description = rootView.findViewById(R.id.tv_current_description);
        pressure =  rootView.findViewById(R.id.tv_current_pressure);
        humidity = rootView.findViewById(R.id.tv_current_humidity);
        sunrise = rootView.findViewById(R.id.tv_current_sunrise);
        sunset = rootView.findViewById(R.id.tv_current_sunset);
    }

    void getCurrentData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Call<CurrentWeatherModel> call = service.getCurrentWeatherData(city, language, AppId, units);

        call.enqueue(new Callback<CurrentWeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<CurrentWeatherModel> call, @NonNull Response<CurrentWeatherModel> response) {
                // if request is ok:
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "Odświeżono aktualną prognozę!",
                            Toast.LENGTH_SHORT).show();
                    
                    CurrentWeatherModel currentWeatherModel = response.body();
                    assert currentWeatherModel != null;

                    setComponents(currentWeatherModel);
                }
                else {
                    Toast.makeText(getActivity(), "Wprowadzono złą nazwę miasta!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeatherModel> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),"Nie udało się pobrać danych!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setComponents(CurrentWeatherModel currentWeatherModel) {
        this.city_name.setText(currentWeatherModel.name);

        setIcon(currentWeatherModel);

        Float tmp = currentWeatherModel.main.temp;
        this.temperature.setText(tmp.toString() + " °C");

        String description = currentWeatherModel.weather.get(0).description;
        this.description.setText(description);

        Float pressure = currentWeatherModel.main.pressure;
        this.pressure.setText(pressure.toString() + " hPa");

        Float humidity = currentWeatherModel.main.humidity;
        this.humidity.setText(humidity.toString() + " %");

        String countryCode = currentWeatherModel.sys.country;

        if (countryCode.equals("PL")) {
            long sunrise = currentWeatherModel.sys.sunrise;
            // convert seconds to milliseconds
            Date date2 = new java.util.Date(sunrise*1000L);
            // the format of your date
            SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("h:mm a");
            // give a timezone reference for formatting (see comment at the bottom)
            sdf2.setTimeZone(java.util.TimeZone.getTimeZone("GMT+2"));
            String formattedSunrise = sdf2.format(date2);

            this.sunrise.setText(formattedSunrise);

            long sunset = currentWeatherModel.sys.sunset;
            // convert seconds to milliseconds
            Date date = new java.util.Date(sunset*1000L);
            // the format of your date
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("h:mm a");
            // give a timezone reference for formatting (see comment at the bottom)
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+2"));
            String formattedSunset = sdf.format(date);

            this.sunset.setText(formattedSunset);
        }
        else {
            this.sunrise.setText("brak danych");
            this.sunset.setText("brak danych");
        }

    }

    private void setIcon(CurrentWeatherModel currentWeatherModel) {
        String iconName = currentWeatherModel.weather.get(0).icon;

        // day:
        if (iconName.equals("01d")) {
            this.icon.setImageResource(R.mipmap.d01d);
        }
        else if (iconName.equals("02d")) {
            this.icon.setImageResource(R.drawable.d02d);
        }
        else if (iconName.equals("03d")) {
            this.icon.setImageResource(R.drawable.d03d);
        }
        else if (iconName.equals("04d")) {
            this.icon.setImageResource(R.drawable.d04d);
        }
        else if (iconName.equals("09d")) {
            this.icon.setImageResource(R.drawable.d09d);
        }
        else if (iconName.equals("10d")) {
            this.icon.setImageResource(R.drawable.d10d);
        }
        else if (iconName.equals("11d")) {
            this.icon.setImageResource(R.drawable.d11d);
        }
        else if (iconName.equals("13d")) {
            this.icon.setImageResource(R.drawable.d13d);
        }
        else if (iconName.equals("50d")) {
            this.icon.setImageResource(R.drawable.d50d);
        }
        // night:
        else if (iconName.equals("01n")) {
            this.icon.setImageResource(R.drawable.n01n);
        }
        else if (iconName.equals("02n")) {
            this.icon.setImageResource(R.drawable.n02n);
        }
        else if (iconName.equals("03n")) {
            this.icon.setImageResource(R.drawable.n03n);
        }
        else if (iconName.equals("04n")) {
            this.icon.setImageResource(R.drawable.n04n);
        }
        else if (iconName.equals("09n")) {
            this.icon.setImageResource(R.drawable.n09n);
        }
        else if (iconName.equals("10n")) {
            this.icon.setImageResource(R.drawable.n10n);
        }
        else if (iconName.equals("11n")) {
            this.icon.setImageResource(R.drawable.n11n);
        }
        else if (iconName.equals("13n")) {
            this.icon.setImageResource(R.drawable.n13n);
        }
        else if (iconName.equals("50n")) {
            this.icon.setImageResource(R.drawable.n50n);
        }
        else {
            this.icon.setImageResource(R.drawable.ic_launcher_background);
        }
    }


}

