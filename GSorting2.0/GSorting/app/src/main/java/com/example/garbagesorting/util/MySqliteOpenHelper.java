package com.example.garbagesorting.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * description :数据库管理类,负责管理数据库的创建、升级工作
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
    //数据库名字
    public static final String DB_NAME = "hotel.db";

    //数据库版本
    public static final int DB_VERSION = 1;
    private Context context;

    public MySqliteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    /**
     * 在数据库首次创建的时候调用，创建表以及可以进行一些表数据的初始化
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        //_id为主键并且自增长一般命名为_id
        String userSql = "create table user(id integer primary key autoincrement,account, password,name,sex, phone,address,photo)";
        db.execSQL(userSql);
        String rubbishSql = "create table rubbish(id integer primary key autoincrement,name,typeId, type,content)";
        db.execSQL(rubbishSql);
        //初始化数据
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

    /**
     * 数据库升级的时候回调该方法，在数据库版本号DB_VERSION升级的时候才会调用
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //给表添加一个字段
        //db.execSQL("alter table person add age integer");
    }

    /**
     * 数据库打开的时候回调该方法
     *
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}

