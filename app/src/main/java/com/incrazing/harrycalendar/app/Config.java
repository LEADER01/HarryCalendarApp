package com.incrazing.harrycalendar.app;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.incrazing.harrycalendar.R;

/**
 * Created by Asus on 2/17/2017.
 */

public class Config {

    public static int getColor(int id, Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                return ContextCompat.getColor(context, id);
            } catch (Exception e) {
                return ContextCompat.getColor(context, R.color.RED_200);
            }
        } else {
            try {
                return context.getResources().getColor(id);
            } catch (Exception e) {
                return context.getResources().getColor(R.color.RED_200);
            }
        }
    }
}
