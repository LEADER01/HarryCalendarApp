package com.incrazing.harrycalendar.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.incrazing.harrycalendar.R;
import com.incrazing.harrycalendar.app.Config;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);


        FlexboxLayout content = (FlexboxLayout) findViewById(R.id.table_layout_container);

        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));// assuming the parent view is a LinearLayout

        Button dayButton;
        boolean isFutureDate = false;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int one = 1, negativeDays = 0, positiveDays = 0;
        Calendar mycal, computedDay, today = Calendar.getInstance();
        int daysInMonth;
        FlexboxLayout.LayoutParams param;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/AldotheApache.ttf");

        for (int i = 0; i < 12; i++) {
            mycal = new GregorianCalendar(currentYear, i, one);
            daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
            for (int j = 1; j <= daysInMonth; j++) {
                computedDay = new GregorianCalendar(currentYear, i, j-1);
                dayButton = new Button(context);
                param = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                param.height = 55;
                param.width = 57;
                param.leftMargin = 5;
                param.topMargin = 5;
                dayButton.setPadding(0,4,0,0);
                dayButton.setLayoutParams(param);
                dayButton.setTextSize(15f);
                dayButton.setTypeface(typeface);
                dayButton.setId(View.generateViewId());
                dayButton.setTextColor(Config.getColor(R.color.GRAY_50, context));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dayButton.setBackground(getDrawable(R.drawable.day_button_shape));
                }
                if (!isFutureDate) {
                if (Math.random() < 0.3) {
                    dayButton.setBackgroundColor(Config.getColor(R.color.colorNegative, context));
                    negativeDays++;
                }
                else {
                    dayButton.setBackgroundColor(Config.getColor(R.color.colorPositive, context));
                    positiveDays++;
                }
                }
                if (computedDay.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR))
                    isFutureDate = true;
                dayButton.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                content.addView(dayButton);
            }
        }

        TextView negText = (TextView) findViewById(R.id.negative_day_text);
        negText.setTypeface(typeface);
        negText.setText(negativeDays+" Days");
        TextView posText = (TextView) findViewById(R.id.positive_day_text);
        posText.setTypeface(typeface);
        posText.setText(positiveDays+" Days");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
