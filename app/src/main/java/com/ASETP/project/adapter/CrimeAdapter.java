package com.ASETP.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ASETP.project.R;
import com.amazonaws.amplify.generated.graphql.ListCrimeDatasQuery;

import java.util.List;

/**
 * @author MirageLee
 * @date 2020/12/12
 */
public class CrimeAdapter extends RecyclerView.Adapter<CrimeAdapter.ViewHolder> {
    private Context context;

    private List<ListCrimeDatasQuery.Item> data;

    public CrimeAdapter(Context context, List<ListCrimeDatasQuery.Item> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CrimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crime_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeAdapter.ViewHolder holder, int position) {
        ListCrimeDatasQuery.Item item = data.get(position);
        holder.time.setText(item.month());
        holder.location.setText(item.location());
        holder.crimeType.setText(item.CrimeType());
        holder.reportedBy.setText(item.reportedBy());
        holder.lSAOName.setText(item.LSOAName());
        holder.lSAOCode.setText(item.LSOACode());
        holder.latitude.setText(String.valueOf(item.latitude()));
        holder.longitude.setText(String.valueOf(item.longitude()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, location, crimeType, reportedBy, lSAOName, lSAOCode, latitude, longitude;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);
            crimeType = itemView.findViewById(R.id.crimetype);
            reportedBy = itemView.findViewById(R.id.reportedby);
            lSAOCode = itemView.findViewById(R.id.lsaoCode);
            lSAOName = itemView.findViewById(R.id.lsaoName);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
        }
    }
}
