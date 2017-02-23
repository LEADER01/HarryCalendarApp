package com.incrazing.harrycalendar.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.incrazing.harrycalendar.R;
import com.incrazing.harrycalendar.app.Config;
import com.incrazing.harrycalendar.helper.CommentsDataSource;
import com.incrazing.harrycalendar.helper.MySQLiteHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    RelativeLayout leftRL;
    DrawerLayout drawerLayout;
    Dialog myDialog;
    boolean isOverlayToday;
    String OverlayDate;
    CommentsDataSource datasource;
    Button clickedDayButton;
    int negativeDays = 0, positiveDays = 0;
    TextView negText, posText;

    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        leftRL = (RelativeLayout)findViewById(R.id.whatYouWantInLeftDrawer);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);


        final FlexboxLayout content = (FlexboxLayout) findViewById(R.id.table_layout_container);

        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));// assuming the parent view is a LinearLayout

        Button dayButton;
        boolean isFutureDate = false;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int one = 1;
        String dateKey;
        Calendar mycal, computedDay, today = Calendar.getInstance();
        int daysInMonth;
        FlexboxLayout.LayoutParams param;

        datasource = new CommentsDataSource(this);
        datasource.open();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/AldotheApache.ttf");
        int value = 0;
        for (int i = 0; i < 12; i++) {
            mycal = new GregorianCalendar(currentYear, i, one);
            daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
            for (int j = 1; j <= daysInMonth; j++) {
                computedDay = new GregorianCalendar(currentYear, i, j-1);
                dateKey = currentYear+"-"+(i+1)+"-"+j;
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
                value = datasource.getValueByDate(dateKey);
                if (computedDay.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                    switch (value) {
                        case 0:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                dayButton.setBackground(getDrawable(R.drawable.today_button_filled_negative));
                            }
                            dayButton.setOnClickListener(dayOnClickListener(dateKey, "FeelsBadMan...\n\nYou can change your decision if you think you were better than this ", true, false));
                            negativeDays++;
                            break;
                        case 1:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                dayButton.setBackground(getDrawable(R.drawable.today_button_filled_positive));
                            }
                            dayButton.setOnClickListener(dayOnClickListener(dateKey, "FeelsGoodMan! What a champ!", true, true));
                            positiveDays++;
                            break;
                        case 3:
                            dayButton.setBackgroundColor(Config.getColor(R.color.BLUE_300, context));
                            dayButton.setOnClickListener(dayOnClickListener(dateKey, "Are you completely satisfied with your daily performance?\n(You can change your decision l8r)", true));
                    }
                    isFutureDate = true;
                }
                if (!isFutureDate) {

                    switch (value) {
                        case 0:
                            dayButton.setBackgroundColor(Config.getColor(R.color.colorNegative, context));
                            dayButton.setOnClickListener(dayOnClickListener(dateKey, "FeelsBadMan...\n\nYou can change your decision if you think you were better than this ", false, false));
                            negativeDays++;
                            break;
                        case 1:
                            dayButton.setBackgroundColor(Config.getColor(R.color.colorPositive, context));
                            dayButton.setOnClickListener(dayOnClickListener(dateKey, "FeelsGoodMan! What a champ!", false, true));
                            positiveDays++;
                            break;
                        case 3:
                            dayButton.setBackgroundColor(Config.getColor(R.color.DEEP_ORANGE_500, context));
                            dayButton.setOnClickListener(dayOnClickListener(dateKey, "Looks like You missed this day.\nUnprofessional...\nVery unprofessional...", false));
                    }
                }

                dayButton.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                content.addView(dayButton);
            }
        }

        negText = (TextView) findViewById(R.id.negative_day_text);
        negText.setTypeface(typeface);
        posText = (TextView) findViewById(R.id.positive_day_text);
        posText.setTypeface(typeface);
        recalculateBigNumbers();

        MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
        Log.d(TAG, "onCreate: "+helper.getTableAsString(MySQLiteHelper.TABLE_DAYS));

    }

    public  void onLeft(View view) {
        drawerLayout.openDrawer(leftRL);
    }

    public void closeOverlay (View v) {
        myDialog.cancel();
    }

    public void valueChoosed (View v) {
        final int id = v.getId();
        int value = 0;
        if (id == R.id.overlay_value_positive)
            value = 1;
        else if (id == R.id.overlay_layout)
            return;
        if (isOverlayToday) {
            datasource.recordValue(OverlayDate, value);
            recalculateBigNumbers();
            if (value == 1) {
                clickedDayButton.setBackground(Config.getDrawable(R.drawable.today_button_filled_positive, context));
            } else {
                clickedDayButton.setBackground(Config.getDrawable(R.drawable.today_button_filled_negative, context));
            }
            myDialog.cancel();
        } else {
            final int finalValue = value, previousValue = datasource.getValueByDate(OverlayDate);
            if (previousValue == 3) {
                datasource.recordValue(OverlayDate, finalValue);
                recalculateBigNumbers();
                clickedDayButton.setBackgroundColor(Config.getColor((finalValue == 1) ? R.color.colorPositive : R.color.colorNegative, context));
                myDialog.cancel();
            }
            else {
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_help_grey600_24dp)
                        .setTitle("U Sure?")
                        .setMessage("Are you sure you want to change your previous decision?")
                        .setPositiveButton("Fuck yea", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                datasource.recordValue(OverlayDate, finalValue);
                                recalculateBigNumbers();
                                clickedDayButton.setBackgroundColor(Config.getColor((finalValue == 1) ? R.color.colorPositive : R.color.colorNegative, context));
                                myDialog.cancel();
                            }
                        })
                        .setNegativeButton("Nah I'm good", null)
                        .show();
            }
        }
    }

    private void recalculateBigNumbers() {
        negText.setText(datasource.getRowCountByValue(0)+" Days");
        posText.setText(datasource.getRowCountByValue(1)+" Days");
    }

    private View.OnClickListener dayOnClickListener (final String title, final String desc, final boolean... isDayPositive) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                OverlayDate = title;
                String finalTitle = "";
                if (isDayPositive != null && isDayPositive.length > 0) {
                    if (isDayPositive[0]) {
                        isOverlayToday = true;
                        finalTitle = "TODAY!";
                    } else {
                        finalTitle = title;
                        isOverlayToday = false;
                    }
                }
                clickedDayButton = (Button) view;
                int value = datasource.getValueByDate(title);
                myDialog = new Dialog(MainActivity.this, R.style.CustomDialogTheme);
                myDialog.setContentView(R.layout.day_detail_overlay);
                TextView titleTxt = (TextView) myDialog.findViewById(R.id.overlay_title);
                TextView descTxt = (TextView) myDialog.findViewById(R.id.overlay_desc);
                descTxt.setText(desc);
                if (value != 3) {
                    ImageView pos = (ImageView) myDialog.findViewById(R.id.overlay_value_positive);
                    ImageView neg = (ImageView) myDialog.findViewById(R.id.overlay_value_negative);
                    LinearLayout layout = (LinearLayout) myDialog.findViewById(R.id.overlay_layout);
                    if (value == 1) {
                        pos.setImageDrawable(Config.getDrawable(R.drawable.ic_emoticon_cool_white_48dp, context));
                        pos.setBackgroundColor(Config.getColor(R.color.GREEN_500, context));
                        neg.setBackgroundColor(Config.getColor(R.color.RED_100, context));
                        neg.setColorFilter(Config.getColor(R.color.RED_200, context));
                        layout.setBackgroundColor(Config.getColor(R.color.LIGHT_GREEN_900, context));
                    } else if (value == 0){
                        neg.setImageDrawable(Config.getDrawable(R.drawable.ic_emoticon_sad_white_48dp, context));
                        pos.setBackgroundColor(Config.getColor(R.color.GREEN_200, context));
                        pos.setColorFilter(Config.getColor(R.color.LIGHT_GREEN_200, context));
                        neg.setBackgroundColor(Config.getColor(R.color.RED_500, context));
                        layout.setBackgroundColor(Config.getColor(R.color.RED_800, context));
                    }
                }
                titleTxt.setText(finalTitle);
                myDialog.show();
            }
        };
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
