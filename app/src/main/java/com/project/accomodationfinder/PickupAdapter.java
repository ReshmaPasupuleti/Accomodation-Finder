package com.project.accomodationfinder;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PickupAdapter extends RecyclerView.Adapter<PickupAdapter.ViewHolder> {


    private Context ct;

    private ArrayList<String> fpSrno, fpAirport, fpLocation, fpDate, fpTime, fpPostedBy;
    private String argType;

    PickupAdapter(Context ct, ArrayList<String> fpSrno, ArrayList<String> fpAirport, ArrayList<String> fpLocation, ArrayList<String> fpDate, ArrayList<String> fpTime, ArrayList<String> fpPostedBy, String argType) {

        this.ct = ct;
        this.fpSrno = fpSrno;
        this.fpAirport = fpAirport;
        this.fpLocation = fpLocation;
        this.fpDate = fpDate;
        this.fpTime = fpTime;
        this.fpPostedBy = fpPostedBy;
        this.argType = argType;


    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public PickupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);

        View view = inflater.inflate(R.layout.pickup_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PickupAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.fpSrno.setText(fpSrno.get(position));
        holder.fpNAME.setText(fpAirport.get(position));
        holder.fpLOCATION.setText(fpLocation.get(position));
        holder.fpDATE.setText(fpDate.get(position));
        holder.fpTIME.setText(fpTime.get(position));
        //holder.fpPOSTEDBY.setText(fpPostedBy.get(position));


    }

    @Override
    public int getItemCount() {
        return fpSrno.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fpSrno, fpNAME, fpLOCATION, fpDATE, fpTIME, fpPOSTEDBY;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            fpSrno = itemView.findViewById(R.id.rcSrno);
            fpNAME = itemView.findViewById(R.id.rcAirport);
            fpLOCATION = itemView.findViewById(R.id.rcDropLocation);
            fpDATE = itemView.findViewById(R.id.rcDate);
            fpTIME = itemView.findViewById(R.id.rcTime);
            fpPOSTEDBY = itemView.findViewById(R.id.rcPostedBy);


        }
    }

}
