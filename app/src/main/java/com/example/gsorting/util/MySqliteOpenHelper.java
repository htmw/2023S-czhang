package com.example.gsorting.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySqliteOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "hotel.db";


    public static final int DB_VERSION = 1;
    private Context context;

    public MySqliteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String rubbishSql = "create table rubbish(id integer primary key autoincrement,name,typeId, type,content)";
        db.execSQL(rubbishSql);

        String[] names = new String[]{"Dumplings","Flowers shells","egg shell","cigarette end","langoustine","Diapers","wet tissue","tissue","glass","Mobile Phone Battery","biscuits","watermelon rind","watermelon seeds","shrimp shell","lithium battery"};
        String[] types = new String[]{"wet garbage","dry refuse","wet garbage","dry refuse","wet garbage","dry refuse","dry refuse","dry refuse","recyclables","harmful","wet garbage","wet garbage","wet garbage","wet garbage","harmful"};
        String[] typeIds = new String[]{"0","1","0","1","0","1","0","0","2","3","0","0","0","0","3"};
        String[] contents = new String[]{"Wet garbage delivery guide\n" +
                "Kitchen garbage should be drained in the water, packaging should be removed after classification.\n" +
                "Large bones and coconut shells, durian shells and other biochemical degradation, as dry garbage for placing.\n" +
                "Pure liquid (such as milk, etc.) can be poured directly into the drain.",
                "Dry garbage garbage delivery guidance\n" +
                        "Drain as much water as possible.\n" +
                        "Household garbage that is difficult to identify should be put into dry garbage containers.\n" +
                        "Napkins, paper towels, diapers, cigarette butts and other garbage.",
                "Wet garbage delivery guide\n" +
                        "Kitchen garbage should be drained in the water, packaging should be removed after classification.\n" +
                        "Large bones and coconut shells, durian shells and other biochemical degradation, as dry garbage for placing.\n" +
                        "Pure liquid (such as milk, etc.) can be poured directly into the drain.",
                "Dry garbage garbage delivery guidance\n" +
                        "Drain as much water as possible.\n" +
                        "Household garbage that is difficult to identify should be put into dry garbage containers.\n" +
                        "Napkins, paper towels, diapers, cigarette butts and other garbage.",
                "Wet garbage delivery guide\n" +
                        "Kitchen garbage should be drained in the water, packaging should be removed after classification.\n" +
                        "Large bones and coconut shells, durian shells and other biochemical degradation, as dry garbage for placing.\n" +
                        "Pure liquid (such as milk, etc.) can be poured directly into the drain.",
                "Garbage garbage delivery guidance\n" +
                        "Drain as much water as possible.\n" +
                        "Household garbage that is difficult to identify should be put into dry garbage containers.\n" +
                        "Napkins, paper towels, diapers, cigarette butts and other garbage.",
                "Dry garbage garbage delivery guidance\n" +
                        "Drain as much water as possible.\n" +
                        "Household garbage that is difficult to identify should be put into dry garbage containers.\n" +
                        "Napkins, paper towels, diapers, cigarette butts and other garbage.",
                "Dry garbage garbage delivery guidance\n" +
                        "Drain as much water as possible.\n" +
                        "Household garbage that is difficult to identify should be put into dry garbage containers.\n" +
                        "Napkins, paper towels, diapers, cigarette butts and other garbage.",
                "Guidelines for placing recyclable garbage\n" +
                        "Clean and dry, avoid contamination, handle with care.\n" +
                        "Waste paper as flat as possible, three-dimensional packaging please empty the contents, clean after crushing put.\n" +
                        "Those with sharp edges and corners should be wrapped and put in.",
                "Guidelines for hazardous waste disposal\n" +
                        "Rechargeable batteries, button batteries, batteries should be placed with care.\n" +
                        "Paint bucket, insecticide if there is residue, please close after putting.\n" +
                        "Fluorescent lamps and energy-saving lamps are easy to be damaged and placed after packaging or wrapping.\n" +
                        "Waste drugs and their packaging are put together.",
                "Wet garbage delivery guide\n" +
                        "Kitchen garbage should be drained in the water, packaging should be removed after classification.\n" +
                        "Large bones and coconut shells, durian shells and other biochemical degradation, as dry garbage for placing.\n" +
                        "Pure liquid (such as milk, etc.) can be poured directly into the drain.",
                "Wet garbage delivery guide\n" +
                        "Kitchen garbage should be drained in the water, packaging should be removed after classification.\n" +
                        "Large bones and coconut shells, durian shells and other biochemical degradation, as dry garbage for placing.\n" +
                        "Pure liquid (such as milk, etc.) can be poured directly into the drain.",
                "Wet garbage delivery guide\n" +
                        "Kitchen garbage should be drained in the water, packaging should be removed after classification.\n" +
                        "Large bones and coconut shells, durian shells and other biochemical degradation, as dry garbage for placing.\n" +
                        "Pure liquid (such as milk, etc.) can be poured directly into the drain.",
                "Wet garbage delivery guide\n" +
                        "Kitchen garbage should be drained in the water, packaging should be removed after classification.\n" +
                        "Large bones and coconut shells, durian shells and other biochemical degradation, as dry garbage for placing.\n" +
                        "Pure liquid (such as milk, etc.) can be poured directly into the drain.",
                "Guidelines for hazardous waste disposal\n" +
                        "Rechargeable batteries, button batteries, batteries should be placed with care.\n" +
                        "Paint bucket, insecticide if there is residue, please close after putting.\n" +
                        "Fluorescent lamps and energy-saving lamps are easy to be damaged and placed after packaging or wrapping.\n" +
                        "Waste drugs and their packaging are put together."};
        for (int i = 0; i < names.length; i++) {
            String sql = "insert into rubbish(name,typeId, type,content) values(?,?,?,?)";
            db.execSQL(sql,new Object[]{names[i],typeIds[i], types[i],contents[i]});
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}

