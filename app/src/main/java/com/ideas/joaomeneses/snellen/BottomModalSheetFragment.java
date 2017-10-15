package com.ideas.joaomeneses.snellen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Classe para apresentar a Bottom Modal Sheet com os resultados maiores.
 * Created by user on 24/01/2017.
 */

public class BottomModalSheetFragment extends BottomSheetDialogFragment {

    public String lastSnellenFraction;
    public String lastDecimalValue;
    public String lastLogmarValue;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_modal_bottom_sheet, null);
        dialog.setContentView(contentView);
        dialog.setCancelable(true);

        // Change Modal Sheet Height (setPeekHeight)
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            ((BottomSheetBehavior) behavior).setPeekHeight(getContext().getResources().getDisplayMetrics().heightPixels);
        }

        // Update TextViews Content
        TextView snellenFractionValueZoom = (TextView) dialog.findViewById(R.id.snellenFractionValueZoom);
        snellenFractionValueZoom.setText(lastSnellenFraction);
        TextView snellenDecimalValueZoom = (TextView) dialog.findViewById(R.id.decimalValueZoom);
        snellenDecimalValueZoom.setText(lastDecimalValue);
        TextView snellenLogMarValueZoom = (TextView) dialog.findViewById(R.id.logMarValueZoom);
        snellenLogMarValueZoom.setText(lastLogmarValue);

        // Low Color Mode - For devices with low color screens, greyscale like e-readers
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isLowColorModeOn = sharedPref.getBoolean("device_low_color_mode",false);
        if (isLowColorModeOn){
            snellenFractionValueZoom.setTextColor(Color.BLACK);
            snellenDecimalValueZoom.setTextColor(Color.BLACK);
            snellenLogMarValueZoom.setTextColor(Color.BLACK);
            ((TextView) dialog.findViewById(R.id.snellenFractionTitleZoom)).setTextColor(Color.BLACK);
            ((TextView) dialog.findViewById(R.id.decimalTitleZoom)).setTextColor(Color.BLACK);
            ((TextView) dialog.findViewById(R.id.logMarTitleZoom)).setTextColor(Color.BLACK);
        }
    }

}



