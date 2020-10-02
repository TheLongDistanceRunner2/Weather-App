package com.example.pogoda;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FiveDaysWeatherFragment extends Fragment {

    private static String baseURL = "https://api.openweathermap.org/";
    private static String AppId= "afc7c746c4417731a59ccc589c4b7ffc";
    private String city = "Lodz,pl";
    private String language = "pl";
    private String units = "metric";

    private RecyclerView recyclerView;
    private ArrayList<FiveDaysWeatherModel.List> weatherList;
    private FiveDaysAdapter adapter;

    private TextView tv_city;

    public static FiveDaysWeatherFragment newInstance(){
        return new FiveDaysWeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_5_days_weather_layout, container, false);

        initViews(rootView);
        getData();

        final SwipeRefreshLayout swipeRefreshLayout =  rootView.findViewById(R.id.swipeLayout_5_days);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh, R.color.refresh1, R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        getData();

                        Toast.makeText(getActivity(),"Odświeżono 5-dniową prognozę!",
                                Toast.LENGTH_SHORT).show();

                    }

                }, 2000);
            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        this.city = event.getMessage();

        getData();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        EventBus.getDefault().unregister(this);
    }


    private void initViews(View rootView) {
        recyclerView = rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        tv_city = rootView.findViewById(R.id.tv_5days_city_name);
    }

    void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService request = retrofit.create(WeatherService.class);
        Call<FiveDaysWeatherModel> call = request.getFiveDaysWeatherData(city, language, AppId, units);


        call.enqueue(new Callback<FiveDaysWeatherModel>() {
            @Override
            public void onResponse(Call<FiveDaysWeatherModel> call, Response<FiveDaysWeatherModel> response) {
                // if request is ok:
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "Pobrano prognozę 5-dniową!",
                            Toast.LENGTH_SHORT).show();

                    FiveDaysWeatherModel jsonResponse = response.body();
                    weatherList = jsonResponse.list;
                    adapter = new FiveDaysAdapter(weatherList);
                    recyclerView.setAdapter(adapter);

                    tv_city.setText(jsonResponse.city.name);
                }
                else {
                    Toast.makeText(getActivity(), "Wprowadzono złą nazwę miasta!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FiveDaysWeatherModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Nie pobrano pogody 5-dniowej!",
                        Toast.LENGTH_SHORT).show();

                Log.d("Error",t.getMessage());

            }

        });
    }

}
