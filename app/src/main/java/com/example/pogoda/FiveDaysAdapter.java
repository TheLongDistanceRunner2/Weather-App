package com.example.pogoda;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class FiveDaysAdapter extends RecyclerView.Adapter<FiveDaysAdapter.FiveDaysViewHolder> {
    private ArrayList<FiveDaysWeatherModel.List> dataList;

    public FiveDaysAdapter(ArrayList<FiveDaysWeatherModel.List> dataList)
    {
        this.dataList = dataList;
    }

    @Override
    public FiveDaysAdapter.FiveDaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);

        return new FiveDaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FiveDaysAdapter.FiveDaysViewHolder holder, int position) {
        holder.dt_txt.setText(dataList.get(position).dt_txt);

        setIcon(holder, position);

        Float temp = dataList.get(position).main.temp;
        holder.temp.setText(temp.toString() + " °C");

        holder.description.setText(dataList.get(position).weather.get(0).description);

        Float pressure = dataList.get(position).main.pressure;
        holder.pressure.setText(pressure.toString() + " hPa");

        Float humidity = dataList.get(position).main.humidity;
        holder.humidity.setText(humidity.toString() + " %");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void setIcon(FiveDaysAdapter.FiveDaysViewHolder holder, int position) {
        String iconName = dataList.get(position).weather.get(0).icon;

        // day:
        if (iconName.equals("01d")) {
            holder.icon.setImageResource(R.mipmap.d01d);
        }
        else if (iconName.equals("02d")) {
            holder.icon.setImageResource(R.drawable.d02d);
        }
        else if (iconName.equals("03d")) {
            holder.icon.setImageResource(R.drawable.d03d);
        }
        else if (iconName.equals("04d")) {
            holder.icon.setImageResource(R.drawable.d04d);
        }
        else if (iconName.equals("09d")) {
            holder.icon.setImageResource(R.drawable.d09d);
        }
        else if (iconName.equals("10d")) {
            holder.icon.setImageResource(R.drawable.d10d);
        }
        else if (iconName.equals("11d")) {
            holder.icon.setImageResource(R.drawable.d11d);
        }
        else if (iconName.equals("13d")) {
            holder.icon.setImageResource(R.drawable.d13d);
        }
        else if (iconName.equals("50d")) {
            holder.icon.setImageResource(R.drawable.d50d);
        }
        // night:
        else if (iconName.equals("01n")) {
            holder.icon.setImageResource(R.drawable.n01n);
        }
        else if (iconName.equals("02n")) {
            holder.icon.setImageResource(R.drawable.n02n);
        }
        else if (iconName.equals("03n")) {
            holder.icon.setImageResource(R.drawable.n03n);
        }
        else if (iconName.equals("04n")) {
            holder.icon.setImageResource(R.drawable.n04n);
        }
        else if (iconName.equals("09n")) {
            holder.icon.setImageResource(R.drawable.n09n);
        }
        else if (iconName.equals("10n")) {
            holder.icon.setImageResource(R.drawable.n10n);
        }
        else if (iconName.equals("11n")) {
            holder.icon.setImageResource(R.drawable.n11n);
        }
        else if (iconName.equals("13n")) {
            holder.icon.setImageResource(R.drawable.n13n);
        }
        else if (iconName.equals("50n")) {
            holder.icon.setImageResource(R.drawable.n50n);
        }
        else {
            holder.icon.setImageResource(R.drawable.ic_launcher_background);
        }
    }


    public class FiveDaysViewHolder extends RecyclerView.ViewHolder{
        public TextView dt_txt;
        public ImageView icon;
        public TextView temp;
        public TextView description;
        public TextView pressure;
        public TextView humidity;

        FiveDaysViewHolder(View view)
        {
            super(view);

            dt_txt = view.findViewById(R.id.card_view_5days_tv_dt_txt);
            icon = view.findViewById(R.id.card_view_5days_iv_icon);
            temp = view.findViewById(R.id.card_view_5days_tv_temp);
            description = view.findViewById(R.id.card_view_5days_tv_description);
            pressure = view.findViewById(R.id.card_view_5days_tv_pressure);
            humidity = view.findViewById(R.id.card_view_5days_tv_humidiy);
        }

    }
}
