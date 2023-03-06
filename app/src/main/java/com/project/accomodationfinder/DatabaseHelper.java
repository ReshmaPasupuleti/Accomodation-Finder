package com.project.accomodationfinder;


import static java.sql.Types.NULL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.core.util.Pair;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    protected static final String TAG = "DatabaseHelper";
    public static final String DBNAME = "Login.db";
    public DatabaseHelper(Context context) {
        super(context, "AccommodationFinder.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        try {
        MyDB.execSQL("create Table users(" +
                "userSrNo integer primary key autoincrement," +
                "userFullName string," +
                "userEmail string," +
                "userDOB date, " +
                " userGender string," +
                "userNationality string, " +
                "userMobile string," +
                "userOccupation string," +
                "userName string," +
                "userPassword sring," +
                "accCreationDate date)");




            MyDB.execSQL("create Table accommodation_master(" +
                    "accSrNo integer primary key autoincrement," +
                    "appType  string," +
                    "accGenderPref  string," +
                    "accRent int," +
                    "accNoOfPersons int," +
                    "accNoOfDays int," +
                    "accNearTo string," +
                    "conEmail string," +
                    "conWhatsApp string," +
                    "accFacilites string," +
                    "accCountry  string," +
                    "accState  string," +
                    "accCity  string," +
                    "accPincode   string," +
                    "accNearByPlaces   string," +
                    "accLocationURL    string," +
                    "accPostDate    date," +
                    "isOccupied     string," +
                    "accMovingDate     date," +
                    "postedBy string references users(userSrNo)," +
                    "occupiedBy string references users(userSrNo))");


            MyDB.execSQL("create Table airport_pickup_master(" +
                    "pickupSrNo  integer primary key autoincrement," +
                    "pickupAirportName  string," +
                    "pickupDate date, " +
                    "pickupTime string," +
                    "pickupDropLocation  string, " +
                    "isAccepted  string, " +
                    "postedBy string ," +
                    "pickedBy  string )");



        }
        catch (Exception e)
        {
            Log.e(TAG, "SQLiteExceptions:" + e.toString());

        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }
    public Boolean registerUser(String fullName, String email, String dob, String gender, String nationality, String mobile,
                                String occupation, String username, String password, String creationDate){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();


        //contentValues.put("userSrNo", NULL);
        contentValues.put("userFullName", fullName);
        contentValues.put("userEmail", email);
        contentValues.put("userDOB", dob);
        contentValues.put("userGender", gender);
        contentValues.put("userNationality", nationality);
        contentValues.put("userMobile", mobile);
        contentValues.put("userOccupation", occupation);
        contentValues.put("userName", username);
        contentValues.put("userPassword", password);
        contentValues.put("accCreationDate", creationDate);




        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    public Boolean addAccommodation(String appartmentType, String genderPref,String rent, String noOfPersons,String noOfDays,
                                    String nearTo,String  email,String whatsapp,String facilities,String country,String state,
                                    String city,String pincode,String nearByPlaces,String locationURL,String postDate, String Occupied,
                                    String movingDate, String postedBy,String occupiedBy){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        //contentValues.put("accSrNo", NULL);
        contentValues.put("appType", appartmentType);
        contentValues.put("accGenderPref", genderPref);
        contentValues.put("accRent", rent);
        contentValues.put("accNoOfPersons", noOfPersons);
        contentValues.put("accNoOfDays", noOfDays);
        contentValues.put("accNearTo", nearTo);
        contentValues.put("conEmail", email);
        contentValues.put("conWhatsApp", whatsapp);
        contentValues.put("accFacilites", facilities);
        contentValues.put("accCountry", country);
        contentValues.put("accState", state);
        contentValues.put("accCity", city);
        contentValues.put("accPincode", pincode);
        contentValues.put("accNearByPlaces", nearByPlaces);
        contentValues.put("accLocationURL", locationURL);
        contentValues.put("accPostDate", postDate);
        contentValues.put("isOccupied", Occupied);
        contentValues.put("accMovingDate", movingDate);
        contentValues.put("postedBy", postedBy);
        contentValues.put("occupiedBy", occupiedBy);

        long result = MyDB.insert("accommodation_master", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    public Boolean addAirportPickup(String name, String date, String time, String location, String isAccepted, String postedBy, String pickedBy){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("pickupAirportName", name);
        contentValues.put("pickupDate", date);
        contentValues.put("pickupTime", time);
        contentValues.put("pickupDropLocation", location);
        contentValues.put("isAccepted", isAccepted);
        contentValues.put("postedBy", postedBy);
        contentValues.put("pickedBy", pickedBy);


            long result = MyDB.insert("airport_pickup_master", null, contentValues);
            if (result == -1) return false;
            else
                return true;

    }
    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where userEmail = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean bookAccommodation(int userID, int accID) {


            SQLiteDatabase MyDB = this.getWritableDatabase();
            ContentValues contentValues= new ContentValues();

            contentValues.put("occupiedBy", userID);

            long result = MyDB.update("accommodation_master", contentValues, "accSrNo = ?", new String[]{String.valueOf(accID)});
        if(result==-1) return false;
        else
            return true;
    }
    public Boolean bookPickup(int userID, int accID) {


        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("pickedBy", userID);
        contentValues.put("isAccepted", "YES");


        long result = MyDB.update("airport_pickup_master", contentValues, "pickupSrNo = ?", new String[]{String.valueOf(accID)});
        if(result==-1) return false;
        else
            return true;
    }
    public int getUserID(String mail) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where userEmail = ?", new String[]{mail});
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        else
            return -99;
    }
    Cursor getUserProfile(String ID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where userSrNo = ?", new String[]{ID});

            cursor.moveToFirst();
            return cursor;
    }
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where userEmail = ? and userPassword = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    Cursor fetchAccommodations(String search, int userID){
        SQLiteDatabase MyDB = this.getReadableDatabase();


       // Cursor cursor = MyDB.rawQuery("Select * from accommodation_master where accNearTo LIKE  '%'+search+'%'", null);
        Cursor cursor = MyDB.rawQuery( "SELECT * FROM " + "accommodation_master" + " WHERE " +
                "accNearTo" + " LIKE '%" + search + "%'" + " AND " + "postedBy" +  " != +  '" + userID + "'"    , null);

        return cursor;
    }
    Cursor fetchPickup(String airport, String date, int userID){
        SQLiteDatabase MyDB = this.getReadableDatabase();

        String query = "SELECT * FROM " + "airport_pickup_master" + " WHERE " +
                    "isAccepted" +  "='" + "NO" + "'" +
                " AND " + "postedBy" +  " <>'" + userID + "'" +
                " AND " + "pickupAirportName" +  " LIKE '%" + airport + "%'" +
                " AND " + "pickupDate" +  " LIKE '%" + date + "%'" ;

        Cursor cursor = MyDB.rawQuery(query, null);


        Log.d("cursor", query);

        return cursor;
    }
    Cursor fetchUserOrders(int userID){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from accommodation_master where postedBy = ?", new String[]{String.valueOf(userID)});
        return cursor;
    }
    Cursor fetchUserPickup(int userID){
        SQLiteDatabase MyDB = this.getReadableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from airport_pickup_master where postedBy = ?", new String[]{String.valueOf(userID)});
        return cursor;
    }
    Cursor fetchAreaAccommodations(int userID, String country, String state, String city, String pincode, String nearby,
                                   String budget, String movin, String noofPerson, String stayPreference){
        SQLiteDatabase MyDB = this.getReadableDatabase();


        String accQuery = "SELECT * FROM accommodation_master WHERE " +
                "postedBy" + " !=  '" + userID + "'" +
                " AND " + "occupiedBy" +  "='" + "NONE" + "'" +
                " AND " + "accCountry" +  " LIKE '%" + country + "%'" +
                " AND " + "accState" +  " LIKE '%" + state + "%'" +
                " AND " + "accCity" +  " LIKE '%" + city + "%'" +
                " AND " + "accPincode" +  " LIKE '%" + pincode + "%'" +
                " AND " + "accNearTo" +  " LIKE '%" + nearby + "%'" +
                " AND " + "accRent" +  " LIKE '%" + budget + "%'" +
                " AND " + "accMovingDate" +  " LIKE '%" + movin + "%'" +
                " AND " + "accNoOfPersons" +  " LIKE '%" + noofPerson + "%'";
        // Cursor cursor = MyDB.rawQuery("Select * from accommodation_master where accNearTo LIKE  '%'+search+'%'", null);
        Cursor cursor = MyDB.rawQuery( accQuery  , null);

        Log.d("cursorAcc", accQuery);




        return cursor;
    }
}
