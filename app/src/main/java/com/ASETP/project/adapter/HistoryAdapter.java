package com.ASETP.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ASETP.project.R;
import com.ASETP.project.model.PlacePaidData;

import java.util.List;

/**
 * @author MirageLee
 * @date 2020/11/29
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final Context context;
    private List<PlacePaidData> data;

    public HistoryAdapter(Context context, List<PlacePaidData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        PlacePaidData item = data.get(position);
        holder.country.setText(item.getCountry());
        holder.recordStatus.setText(item.getRecordStatusS());
        holder.categoryType.setText(item.getCategoryType());
        holder.district.setText(item.getDistrict());
        holder.locality.setText(item.getLocality());
        holder.street.setText(item.getStrees());
        holder.sano.setText(item.getSaon());
        holder.pano.setText(item.getPaon());
        holder.duration.setText(item.getDuration());
        holder.newOrOld.setText(item.getNewOrOld());
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.propertyType.setText(item.getPropertyType());
        holder.time.setText(item.getTransferDate());
        holder.id.setText(item.getUniqueIdentifier());
        holder.town.setText(item.getTown());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, propertyType, id, price, newOrOld, duration, pano, sano, street, locality, town, district, categoryType, recordStatus, country;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            propertyType = itemView.findViewById(R.id.propertyType);
            id = itemView.findViewById(R.id.id);
            price = itemView.findViewById(R.id.price);
            newOrOld = itemView.findViewById(R.id.newOrOld);
            duration = itemView.findViewById(R.id.duration);
            pano = itemView.findViewById(R.id.pano);
            sano = itemView.findViewById(R.id.sano);
            street = itemView.findViewById(R.id.street);
            locality = itemView.findViewById(R.id.locality);
            town = itemView.findViewById(R.id.town);
            district = itemView.findViewById(R.id.district);
            categoryType = itemView.findViewById(R.id.categoryType);
            recordStatus = itemView.findViewById(R.id.recordStatus);
            country = itemView.findViewById(R.id.country);

        }
    }
}
