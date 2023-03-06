package com.project.accomodationfinder;

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

import java.text.MessageFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private Context ct;

   private ArrayList<String> accSrno, accType, accGenderPref, accRent, accNoPersons, accNoDays, accNearTo, accEmail, accWhatsapp, accFacilities, accCountry, accState, accCity, accPincode, accNearByPlaces, accLocationURL, accPostDate, accOccupied, accMovinDate, accPostedBy, accOccupiedBy ;
   private String argType;

    RecyclerViewAdapter(Context ct, ArrayList<String> accSrno, ArrayList<String> accType, ArrayList<String> accGenderPref, ArrayList<String> accRent, ArrayList<String> accNoPersons, ArrayList<String> accNoDays, ArrayList<String> accNearTo, ArrayList<String> accEmail, ArrayList<String> accWhatsapp, ArrayList<String> accFacilities, ArrayList<String> accCountry, ArrayList<String> accState, ArrayList<String> accCity, ArrayList<String> accPincode, ArrayList<String> accNearByPlaces, ArrayList<String> accLocationURL, ArrayList<String> accPostDate, ArrayList<String> accOccupied, ArrayList<String> accMovinDate, ArrayList<String> accPostedBy, ArrayList<String> accOccupiedBy, String argType) {

        this.ct = ct;
        this.accSrno = accSrno;
        this.accType = accType;
        this.accGenderPref = accGenderPref;
        this.accRent = accRent;
        this.accNoPersons = accNoPersons;
        this.accNoDays = accNoDays;
        this.accNearTo = accNearTo;
        this.accEmail = accEmail;
        this.accWhatsapp = accWhatsapp;
        this.accFacilities = accFacilities;
        this.accCountry = accCountry;
        this.accState = accState;
        this.accCity = accCity;
        this.accPincode = accPincode;
        this.accNearByPlaces = accNearByPlaces;
        this.accLocationURL = accLocationURL;
        this.accPostDate = accPostDate;
        this.accOccupied = accOccupied;
        this.accMovinDate = accMovinDate;
        this.accPostedBy = accPostedBy;
        this.accOccupiedBy = accOccupiedBy;
        this.argType = argType;


    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);

        View view = inflater.inflate(R.layout.accomodation_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.accSrno.setText(accSrno.get(position));
        holder.accType.setText(accType.get(position));
        holder.accGenderPref.setText(accGenderPref.get(position));
        holder.accRent.setText(String.format("$%s", accRent.get(position)));
        holder.accNoPersons.setText(MessageFormat.format("{0} Persons", accNoPersons.get(position)));
        holder.accNoDays.setText(accNoDays.get(position));
        holder.accNearTo.setText(accNearTo.get(position));
        holder.accEmail.setText(accEmail.get(position));
        holder.accWhatsapp.setText(accWhatsapp.get(position));
        holder.accFacilities.setText(accFacilities.get(position));
        holder.accCountry.setText(accCountry.get(position));
        holder.accState.setText(accState.get(position));
        holder.accCity.setText(accCity.get(position));
        holder.accPincode.setText(accPincode.get(position));
        holder.accNearByPlaces.setText(accNearByPlaces.get(position));
        holder.accLocationURL.setText(accLocationURL.get(position));
        holder.accPostDate.setText(accPostDate.get(position));
        holder.accOccupied.setText(accOccupied.get(position));
        holder.accMovinDate.setText(accMovinDate.get(position));
        holder.accPostedBy.setText(accPostedBy.get(position));
        holder.accOccupiedBy.setText(accOccupiedBy.get(position));

        holder.accMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(accLocationURL.get(position)));
                    ct.startActivity(intent);

                }
                catch (Exception e)
                {
                    Toast.makeText(ct, "Error ! Can't Open, URL is Incorrect", Toast.LENGTH_SHORT).show();
                }


            }
        });

        holder.accCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    Uri uri = Uri.parse("smsto:" + accWhatsapp.get(position));
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.setPackage("com.whatsapp");
                    ct.startActivity(Intent.createChooser(i, ""));

                }
                catch (Exception e)
                {
                    Toast.makeText(ct, "Error ! Can't Open URL", Toast.LENGTH_SHORT).show();
                }


            }
            });

        holder.accMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                final Intent intent = new Intent(Intent.ACTION_VIEW)
//                        .setType("plain/text")
//                        .setData(Uri.parse(accEmail.get(position)))
//                        .setPackage("com.google.android.gm")
//                        //.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
//                        .putExtra(Intent.EXTRA_EMAIL, new String[]{accEmail.get(position)})
//                        .putExtra(Intent.EXTRA_SUBJECT, "Regarding Accomodation Booking")
//                        .putExtra(Intent.EXTRA_TEXT, "Hello There,\n\nI am interested in your accomodation.\n\n\n\n\n\nRegards,\n\n");
//                ct.startActivity(intent);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{accEmail.get(position)});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Regarding Accomodation");
                intent.putExtra(Intent.EXTRA_TEXT, "Hello There,\n\nI am interested in your accomodation.\n\n\n\n\n\nRegards,\n\n");
                ct.startActivity(Intent.createChooser(intent, "Send Email"));

            }
        });


        if(argType.equals("myOrders"))
        {
            holder.bookButton.setVisibility(View.GONE);
        }

        else
        {

            holder.bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sh = ct.getSharedPreferences("AccommodationsPref", ct.MODE_PRIVATE);
                    int userID = sh.getInt("userID", 0);

                    Toast.makeText(ct, "Booking ID :  "+ accSrno.get(position) , Toast.LENGTH_SHORT).show();

                    DatabaseHelper db;
                    db = new DatabaseHelper(ct);
                    int  accID =  Integer.parseInt(accSrno.get(position));

                    boolean check =   db.bookAccommodation(userID, accID);

                    if(check)
                    {
                        Toast.makeText(ct, "Booked Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ct, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }











    }

    @Override
    public int getItemCount() {
        return accRent.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView accSrno, accType, accGenderPref, accRent, accNoPersons, accNoDays, accNearTo, accEmail, accWhatsapp, accFacilities, accCountry, accState, accCity, accPincode, accNearByPlaces, accLocationURL, accPostDate, accOccupied, accMovinDate, accPostedBy, accOccupiedBy ;
        ImageButton accMap, accCall, accMail;
        Button bookButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            accSrno = itemView.findViewById(R.id.accSrno);
            accType = itemView.findViewById(R.id.accType);
            accGenderPref = itemView.findViewById(R.id.accGenderPref);
            accRent = itemView.findViewById(R.id.accRent);
            accNoPersons = itemView.findViewById(R.id.accNoPersons);
            accNoDays = itemView.findViewById(R.id.accNoDays);
            accNearTo = itemView.findViewById(R.id.accNearTo);
            accEmail = itemView.findViewById(R.id.accEmail);
            accWhatsapp = itemView.findViewById(R.id.accWhatsapp);
            accFacilities = itemView.findViewById(R.id.accFacilities);
            accCountry = itemView.findViewById(R.id.accCountry);
            accState = itemView.findViewById(R.id.accState);
            accCity = itemView.findViewById(R.id.accCity);
            accPincode = itemView.findViewById(R.id.accPincode);
            accNearByPlaces = itemView.findViewById(R.id.accNearByPlaces);
            accLocationURL = itemView.findViewById(R.id.accLocationURL);
            accPostDate = itemView.findViewById(R.id.accPostDate);
            accOccupied = itemView.findViewById(R.id.accOccupied);
            accMovinDate = itemView.findViewById(R.id.accMovinDate);
            accPostedBy = itemView.findViewById(R.id.accPostedBy);
            accOccupiedBy = itemView.findViewById(R.id.accOccupiedBy);

            accMap = itemView.findViewById(R.id.mapButton);
            accCall = itemView.findViewById(R.id.whatsAppButton);
            accMail = itemView.findViewById(R.id.mailButton);

            bookButton = itemView.findViewById(R.id.bookButton);



        }
    }

}
