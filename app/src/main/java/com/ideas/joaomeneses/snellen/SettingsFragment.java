package com.ideas.joaomeneses.snellen;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

/**
 * Settings Fragment
 * Created by joaomeneses on 31/05/16.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    String startingUnitSystem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        // Save Initial Unit System
        SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();
        startingUnitSystem = prefs.getString("unit_system_preference",this.getString(R.string.not_selected_capitalized));

        // Change preferences summary to include the last selected sharedpreference value
        updatePreferencesTexts();

    }

    private void updatePreferencesTexts() {

        // Set New TextView Variable Values
        //---------------------------------
        SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();
        String selectedUnit = prefs.getString("unit_system_preference",this.getString(R.string.not_selected_capitalized));
        String selectedUnit_lang;
        String distanceUnit_lang;
        String displaySizeUnit_lang;
        switch (selectedUnit) {
            case AcuityToolbox.METRIC_UNITS:
                selectedUnit_lang = getResources().getString(R.string.metric_system);
                distanceUnit_lang = getResources().getString(R.string.metric_unit_centimeters);
                displaySizeUnit_lang = getResources().getString(R.string.metric_unit_millimeters);
                break;
            case AcuityToolbox.IMPERIAL_UNITS:
                selectedUnit_lang = getResources().getString(R.string.imperial_system);
                distanceUnit_lang = getResources().getString(R.string.imperial_unit_feet);
                displaySizeUnit_lang =  getResources().getString(R.string.imperial_unit_inches);
                break;
            default:
                selectedUnit_lang = getResources().getString(R.string.undefined_lowercase);
                distanceUnit_lang = getResources().getString(R.string.undefined_lowercase);
                displaySizeUnit_lang = getResources().getString(R.string.undefined_lowercase);
        }

        // Update Texts
        //-------------
        ListPreference listSelectedUnit = (ListPreference) findPreference("unit_system_preference");
        listSelectedUnit.setSummary(selectedUnit_lang);
        ListPreference listSnellenFractionDenominator = (ListPreference) findPreference("snellen_fraction_denominator_preference");
        String snellenFractionDenominatorSummary = prefs.getString("snellen_fraction_denominator_preference",getResources().getString(R.string.not_selected_capitalized));
        listSnellenFractionDenominator.setSummary(snellenFractionDenominatorSummary);
        EditTextPreference editTextUserDistance = (EditTextPreference) findPreference("user_distance_preference");
        String userDistanceSummary = prefs.getString("user_distance_preference",getResources().getString(R.string.zero_value))+" "+distanceUnit_lang;
        editTextUserDistance.setSummary(userDistanceSummary);
        String dialogUserDistanceText = getResources().getString(R.string.settings_user_distance_dialog_message)+" "+distanceUnit_lang+":";
        editTextUserDistance.setDialogMessage(dialogUserDistanceText);
        EditTextPreference editTextDiagonal = (EditTextPreference) findPreference("display_diagonal_size_preference");
        String diagonalSummary = prefs.getString("display_diagonal_size_preference",getResources().getString(R.string.zero_value))+" "+displaySizeUnit_lang;
        editTextDiagonal.setSummary(diagonalSummary);
        String dialogDiagonal = getResources().getString(R.string.settings_display_diagonal_dialog_message)+" "+displaySizeUnit_lang+":";
        editTextDiagonal.setDialogMessage(dialogDiagonal);
        EditTextPreference editTextHorizontalRes = (EditTextPreference) findPreference("display_horizontal_resolution_preference");
        String horizontalSummary = prefs.getString("display_horizontal_resolution_preference",getResources().getString(R.string.zero_value))+" "+getResources().getString(R.string.pixels);
        editTextHorizontalRes.setSummary(horizontalSummary);
        EditTextPreference editTextVerticalRes = (EditTextPreference) findPreference("display_vertical_resolution_preference");
        String verticalSummary = prefs.getString("display_vertical_resolution_preference",getResources().getString(R.string.zero_value))+" "+getResources().getString(R.string.pixels);
        editTextVerticalRes.setSummary(verticalSummary);
        String selectedOptotype = prefs.getString("optotype_preference",getResources().getString(R.string.not_selected_capitalized));
        String optotypeFormatSelected;
        switch (selectedOptotype) {
            case "Snellen_Ref":
                optotypeFormatSelected = getResources().getString(R.string.optotype_format_snellen);
                break;
            case "Tumbling_E_Ref":
                optotypeFormatSelected = getResources().getString(R.string.optotype_format_tumbling_e);
                break;
            case "Landolt_C_Ref":
                optotypeFormatSelected = getResources().getString(R.string.optotype_format_landolt_c);
                break;
            case "LEA_Ref":
                optotypeFormatSelected = getResources().getString(R.string.optotype_format_lea);
                break;
            case "Sloan_Ref":
                optotypeFormatSelected = getResources().getString(R.string.optotype_format_sloan);
                break;
            default:
                optotypeFormatSelected = getResources().getString(R.string.not_selected_capitalized);
        }
        ListPreference listSelectedOptotype = (ListPreference) findPreference("optotype_preference");
        listSelectedOptotype.setSummary(optotypeFormatSelected);
        EditTextPreference editOptotypeAlpha = (EditTextPreference) findPreference("optotype_alpha_preference");
        String selectedOptotypeAlpha = prefs.getString("optotype_alpha_preference",getResources().getString(R.string.not_selected_capitalized));
        editOptotypeAlpha.setSummary(selectedOptotypeAlpha);
        EditTextPreference editOptotypeCustomUpperLimit = (EditTextPreference) findPreference("custom_limit_upper_preference");
        String selectedOptotypeUpperLimit = prefs.getString("custom_limit_upper_preference","");
        editOptotypeCustomUpperLimit.setSummary(selectedOptotypeUpperLimit);
        EditTextPreference editOptotypeCustomLowerLimit = (EditTextPreference) findPreference("custom_limit_lower_preference");
        String selectedOptotypeLowerLimit = prefs.getString("custom_limit_lower_preference","");
        editOptotypeCustomLowerLimit.setSummary(selectedOptotypeLowerLimit);
        EditTextPreference editCustomResultsFontSize = (EditTextPreference) findPreference("custom_results_font_size_preference");
        String selectedCustomResultsFontSize = prefs.getString("custom_results_font_size_preference","");
        editCustomResultsFontSize.setSummary(selectedCustomResultsFontSize);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {


        // If User Changed Unit System Convert Values, And Save Them In New Unit System
        //-----------------------------------------------------------------------------
        SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();
        String selectedUnit = prefs.getString("unit_system_preference",getResources().getString(R.string.not_selected_capitalized));

        if (!startingUnitSystem.equals(selectedUnit)) {

            startingUnitSystem = selectedUnit;
            String oldUserDistance = prefs.getString("user_distance_preference",getResources().getString(R.string.zero_value));
            String oldScreenDiagonal = prefs.getString("display_diagonal_size_preference",getResources().getString(R.string.zero_value));
            String convertedUserDistance;
            String convertedScreenDiagonal;

            switch (selectedUnit) {
                case AcuityToolbox.METRIC_UNITS:
                    convertedUserDistance = String.valueOf((double)Math.round(AcuityToolbox.convertFeet2Centimeters(Double.parseDouble(oldUserDistance))*100d)/100d);
                    convertedScreenDiagonal = String.valueOf((double)Math.round(AcuityToolbox.convertInches2Millimeters(Double.parseDouble(oldScreenDiagonal))*100d)/100d);
                    break;
                case AcuityToolbox.IMPERIAL_UNITS:
                    convertedUserDistance = String.valueOf((double)Math.round(AcuityToolbox.convertCentimeters2Feet(Double.parseDouble(oldUserDistance))*100d)/100d);
                    convertedScreenDiagonal = String.valueOf((double)Math.round(AcuityToolbox.convertMillimeters2Inches(Double.parseDouble(oldScreenDiagonal))*100d)/100d);
                    break;
                default:
                    return;
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_distance_preference",convertedUserDistance);
            editor.putString("display_diagonal_size_preference",convertedScreenDiagonal);
            editor.apply();

            // Update Prefrences Fragments Values
            //-----------------------------------
            EditTextPreference editTextUserDistance = (EditTextPreference) findPreference("user_distance_preference");
            editTextUserDistance.setText(convertedUserDistance);
            EditTextPreference editTextDiagonal = (EditTextPreference) findPreference("display_diagonal_size_preference");
            editTextDiagonal.setText(convertedScreenDiagonal);

        }

        // Update Labels
        //--------------
        updatePreferencesTexts();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
