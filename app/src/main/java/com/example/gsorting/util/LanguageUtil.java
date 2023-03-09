package com.example.gsorting.util;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;


public class LanguageUtil {

    public static void changeAppLanguage(Context context, Locale locale, boolean persistence) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, metrics);
        if (persistence) {
            saveLanguageSetting(context, locale);
        }
    }


    public static boolean isSetting(Context context) {
        String lang= (String)SPUtils.get(context,SPUtils.SP_LANGUAGE,"");
        if ("".equals(lang)){
            return true;
        }
        return false;
    }




    public static void saveLanguageSetting(Context context,Locale locale){

        SPUtils.put(context,SPUtils.SP_LANGUAGE,locale.getLanguage());

    }

    public static String getAppLanguage(Context context){
        return (String)SPUtils.get(context,SPUtils.SP_LANGUAGE,Locale.getDefault().getLanguage());
    }



    public static Locale getAppLocale(Context context){
        String lang = (String)SPUtils.get(context,SPUtils.SP_LANGUAGE,Locale.getDefault().getLanguage());
        if(!lang.equals(Locale.SIMPLIFIED_CHINESE.getLanguage())&&!lang.equals(Locale.ENGLISH.getLanguage())){
            lang = Locale.SIMPLIFIED_CHINESE.getLanguage();
        }
        Locale locale = new Locale(lang);
        return locale;
    }


    public static void setLocale(Locale myLocale,Activity activity){
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        saveLanguageSetting(activity,myLocale);
    }
}



