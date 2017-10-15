package com.ideas.joaomeneses.snellen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Get Display Properties
        //-----------------------
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Estimate Display Screen Diagonal Size (in Metric System)
        //---------------------------------------------------------
        double wi=(double)metrics.widthPixels/(double)metrics.ydpi;
        double hi=(double)metrics.heightPixels/(double)metrics.xdpi;
        double screenDiagonalSizeInches = Math.sqrt(Math.pow(wi,2)+Math.pow(hi,2));
        double screenDiagonalSizeMillimeters = (double) Math.round(AcuityToolbox.convertInches2Millimeters(screenDiagonalSizeInches)*100d)/100d;

        // Set Default Settings Preferences (in Metric System)
        //----------------------------------------------------
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getString("user_distance_preference","null").equals("null")) {

            //Log.d("MainMenu","Set Default SharedPreferences in Metric System");
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("unit_system_preference",AcuityToolbox.METRIC_UNITS);
            editor.putString("snellen_fraction_denominator_preference",AcuityToolbox.DENOMINATOR_20_SNELLEN_FRACTION);
            editor.putString("user_distance_preference",String.valueOf(600.0));
            editor.putString("display_diagonal_size_preference",String.valueOf(screenDiagonalSizeMillimeters));
            editor.putString("display_horizontal_resolution_preference",String.valueOf((double)metrics.widthPixels));
            editor.putString("display_vertical_resolution_preference",String.valueOf((double)metrics.heightPixels));
            editor.putString("optotype_preference",getResources().getString(R.string.static_variable_snellen_reference));
            editor.putString("optotype_alpha_preference","255");
            editor.putBoolean("device_low_color_mode",false);
            editor.apply();
        }
    }

    // BUTTON ACTIONS
    //+++++++++++++++
    public void openSnellenChartActivity(View clickedView) {

        Intent intent = new Intent(this,SnellenChartActivity.class);
        startActivity(intent);
    }

    public void openDuochromeActivity(View clickedView) {

        Intent intent = new Intent(this, DuochromeActivity.class);
        startActivity(intent);
    }

    public void openSettingsActivity(View clickedView) {

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //==============================================================================================
    //----------------------------------------------------------------------------------------------
    // Action Bar Methods

    // Create Custom Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handle Presses on the Action Bar Items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_bar_item_help:

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_help_content_main_menu_activity).setTitle(R.string.dialog_help_title_main_menu_activity);

                // 3. Add the buttons
                builder.setPositiveButton(R.string.universal_expression_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button, so dismiss
                        dialog.dismiss();
                    }
                });

                // 4. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();

                // 5. Show Dialog
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
