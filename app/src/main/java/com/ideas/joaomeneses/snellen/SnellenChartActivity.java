package com.ideas.joaomeneses.snellen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SnellenChartActivity extends AppCompatActivity implements GestureDetector.OnDoubleTapListener,GestureDetector.OnGestureListener,KeyEvent.Callback {

    // Local Variables
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private BottomModalSheetFragment zoomDialogFragment;
    private String desiredSnellenFraction;
    private String desiredSnellenDecimal;
    private String desiredSnellenLogMar;
    private String realSnellenDecimal;
    private String realSnellenFraction;
    private String realSnellenLogMar;

    private SnellenOptotypeView snellenOptotype;
    private GestureDetector mGestureDetector;
    private AcuityToolbox mAcuityToolbox;

    private int currentAcuityIndex = 0;
    private Double[] snellenChartAcuitySequenceToDisplay = {400.0,300.0,200.0,160.0,125.0,100.0,80.0,63.0,50.0,40.0,32.0,25.0,20.0,16.0,12.5,10.0};
    private Double[] logmarChartAcuitySequenceToDisplay = {1.3,1.15,1.0,0.9,0.8,0.7,0.6,0.5,0.4,0.3,0.2,0.1,0.0,-0.1,-0.2,-0.3};

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234; //speak




    // On Create Activity
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Load This Activity Content
        //---------------------------
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snellen_chart);

        // Add Gesture Listeners
        //----------------------
        mGestureDetector = new GestureDetector(this,this);
        mGestureDetector.setOnDoubleTapListener(this);

        // Read User Custom Data
        //-----------------------
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String lastSelectedUnit = sharedPref.getString("unit_system_preference","");
        String lastSelectedUserDistance = sharedPref.getString("user_distance_preference","");
        String lastSelectedDiagonalSize = sharedPref.getString("display_diagonal_size_preference","");
        String lastSelectedHorizontalRes = sharedPref.getString("display_horizontal_resolution_preference","");
        String lastSelectedVerticalRes = sharedPref.getString("display_vertical_resolution_preference","");
        String lastSelectedOptotypeFormat = sharedPref.getString("optotype_preference","");
        String lastSelectedOptotypeAlpha = sharedPref.getString("optotype_alpha_preference","255");

        // Calculate This Chart Custom Properties
        //---------------------------------------
        Double diagonalSizeInches = Double.parseDouble(lastSelectedDiagonalSize);
        switch (lastSelectedUnit) {
            case AcuityToolbox.METRIC_UNITS:
                diagonalSizeInches = AcuityToolbox.convertMillimeters2Inches(Double.parseDouble(lastSelectedDiagonalSize));
                break;
            case AcuityToolbox.IMPERIAL_UNITS:
                diagonalSizeInches = Double.parseDouble(lastSelectedDiagonalSize);
                break;
            default:
                //Log.d("Chart Activity","ERROR: Invalid Selected Unit.");
        }
        Double diagonalSizePixels = Math.sqrt(Math.pow(Double.parseDouble(lastSelectedHorizontalRes),2.0)+Math.pow(Double.parseDouble(lastSelectedVerticalRes),2.0));
        Double pixelsPerInchCustomRatio = diagonalSizePixels/diagonalSizeInches;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Start Acuity Toolbox for Calculations
        //--------------------------------------
        mAcuityToolbox = new AcuityToolbox(lastSelectedUnit,
                Double.parseDouble(lastSelectedUserDistance),
                (double) metrics.xdpi,
                (double) metrics.ydpi,
                pixelsPerInchCustomRatio);

        // Get Snellen Optotype View Instance On Canvas and Set it Up
        //-----------------------------------------------------------
        snellenOptotype = (SnellenOptotypeView)findViewById(R.id.snellenOptotypeView);
        snellenOptotype.setOptotypeFormat(lastSelectedOptotypeFormat);
        snellenOptotype.setOptotypeAlpha(Integer.parseInt(lastSelectedOptotypeAlpha));
    }

    // On Start Activity
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    protected void onStart() {
        super.onStart();

        // Draw Current Optotype When Calling onDraw For the First Time
        //-------------------------------------------------------------
        ViewTreeObserver customSnellenViewObserver = snellenOptotype.getViewTreeObserver();
        customSnellenViewObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int startingIndex = 0;
                for (double value:snellenChartAcuitySequenceToDisplay) {
                    // Draw The Biggest Optotype Possible For This Screen
                    Double DesiredOptotypeTotalPixelSize = mAcuityToolbox.calculateOptotypeSizeInPixelsForSnellenFraction((20.0/value));
                    if (snellenOptotype.setOptotypeTotalPixelSize(DesiredOptotypeTotalPixelSize.intValue())) {
                        if (validateAcuityCustomLimits(startingIndex,false)){
                            currentAcuityIndex = startingIndex;
                            updateLabelValues((double)snellenOptotype.getOptotypeTotalPixelSize());
                            break;
                        } else {
                            startingIndex += 1;
                        }
                    } else {
                        startingIndex += 1;
                    }
                }

                snellenOptotype.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    // On Resume Activity
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        // External Hardware Keyboard And Mouse Support
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                this.moveToUpperAcuity();
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                this.moveToLowerAcuity();
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                snellenOptotype.selectNewRandomOptotype();
                snellenOptotype.reDrawOptotype();
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                snellenOptotype.selectNewRandomOptotype();
                snellenOptotype.reDrawOptotype();
                return true;

            case KeyEvent.KEYCODE_SPACE:

                // SPACE KEY (Remote Keyboard Support)
                return this.openResultsZoomWindow();

            case KeyEvent.KEYCODE_ENTER:

                // ENTER KEY (Remote Keyboard Support)
                return this.openResultsZoomWindow();

            case KeyEvent.KEYCODE_NUMPAD_ENTER:

                // ENTER KEY (Remote Numpad Support)
                return this.openResultsZoomWindow();

            case KeyEvent.KEYCODE_NUMPAD_0:

                // 0 KEY (Remote Numpad Support)
                return this.openResultsZoomWindow();

            case KeyEvent.KEYCODE_INSERT:

                // INSERT KEY (Remote Numpad Support)
                return this.openResultsZoomWindow();

            default:
                return super.onKeyUp(keyCode, event);
        }
    }


    //==============================================================================================
    //----------------------------------------------------------------------------------------------
    // Show Modal Sheet Fragment - Zoom The Current Optotype Values (REMOTE TESTING)
    private boolean openResultsZoomWindow(){

        // Create Bottom Modal Sheet
        zoomDialogFragment = new BottomModalSheetFragment();
        zoomDialogFragment.lastSnellenFraction = desiredSnellenFraction;
        zoomDialogFragment.lastDecimalValue = desiredSnellenDecimal;
        zoomDialogFragment.lastLogmarValue = desiredSnellenLogMar;
        zoomDialogFragment.show(getSupportFragmentManager(), zoomDialogFragment.getTag());

        // Timer to Dismiss Bottom Modal Sheet
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {
                zoomDialogFragment.dismiss();
            }
        }.start();

        // Return Boolean
        return true;
    }

    //==============================================================================================
    //----------------------------------------------------------------------------------------------
    // Gesture Detectors

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        // Single Tap
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        // Double Tap
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        // Double Tap
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        // Press Down
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        // On Press
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        // Tap Up
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        // Scroll
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        // Long Press
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Game Mechanics With Swipe Movement
        //-----------------------------------
        int SWIPE_THRESHOLD = 100;
        int SWIPE_VELOCITY_THRESHOLD = 100;

        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        //Log.d("Swipe", "Left to Right swipe performed");
                        snellenOptotype.selectNewRandomOptotype();
                        snellenOptotype.reDrawOptotype();
                    } else {
                        //Log.d("Swipe", "Right to Left swipe performed");
                        snellenOptotype.selectNewRandomOptotype();
                        snellenOptotype.reDrawOptotype();
                    }
                }
                result = true;
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    //Log.d("Swipe", "Up to Down swipe performed");
                    this.moveToLowerAcuity();
                } else {
                    //Log.d("Swipe", "Down to Up swipe performed");
                    this.moveToUpperAcuity();
                }
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
    //==============================================================================================

    private void updateLabelValues(Double totalOptotypePixelsSize) {

        // Update Snellen Labels
        // *Real values - The one's that are really displayed accordingly with the display limitations;
        // *Desired values - The one's that we desire to display but can't duw to display limitation;
        // TODO - The difference between the Real and the Desired is the display error.

        TextView snellenFractionValue = (TextView) this.findViewById(R.id.snellenFractionValue);
        TextView snellenDecimalValue = (TextView) this.findViewById(R.id.decimalValue);
        TextView snellenLogMarValue = (TextView) this.findViewById(R.id.logMarValue);

        desiredSnellenFraction = "20/"+String.valueOf(snellenChartAcuitySequenceToDisplay[currentAcuityIndex].intValue());
        realSnellenFraction = "20/"+String.valueOf(mAcuityToolbox.calculateSnellenFractionForOptotypeSizeInPixels(totalOptotypePixelsSize).intValue());

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        desiredSnellenDecimal = String.valueOf(df.format(20.0/snellenChartAcuitySequenceToDisplay[currentAcuityIndex].floatValue()));
        realSnellenDecimal = String.valueOf(df.format(mAcuityToolbox.calculateDecimalForOptotypeSizeInPixels(totalOptotypePixelsSize)));

        desiredSnellenLogMar = String.valueOf(logmarChartAcuitySequenceToDisplay[currentAcuityIndex].floatValue());
        realSnellenLogMar = String.valueOf(df.format(mAcuityToolbox.calculateLogMARforOptotypeSizeInPixels(totalOptotypePixelsSize)));

        snellenFractionValue.setText(desiredSnellenFraction);
        snellenDecimalValue.setText(desiredSnellenDecimal);
        snellenLogMarValue.setText(desiredSnellenLogMar);


        // Low Color Mode - For devices with low color screens, greyscale like e-readers

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLowColorModeOn = sharedPref.getBoolean("device_low_color_mode",false);
        if (isLowColorModeOn){
            snellenFractionValue.setTextColor(Color.BLACK);
            snellenDecimalValue.setTextColor(Color.BLACK);
            snellenLogMarValue.setTextColor(Color.BLACK);
            ((TextView) this.findViewById(R.id.snellenFractionTitle)).setTextColor(Color.BLACK);
            ((TextView) this.findViewById(R.id.decimalTitle)).setTextColor(Color.BLACK);
            ((TextView) this.findViewById(R.id.logMarTitle)).setTextColor(Color.BLACK);
        }

        // Preference - Distinction beetween the two snellen fraction formats 20/20 or 6/6
        if (sharedPref.getString("snellen_fraction_denominator_preference","0/0").equals(AcuityToolbox.DENOMINATOR_6_SNELLEN_FRACTION)) {
            String convertedDesiredSnellenFraction = "6/"+String.valueOf(AcuityToolbox.convertSnellenFractionFormatTo6(snellenChartAcuitySequenceToDisplay[currentAcuityIndex].doubleValue()));
            desiredSnellenFraction = convertedDesiredSnellenFraction;
            snellenFractionValue.setText(desiredSnellenFraction);
        }

        // Preference - Results Text Size (Singapure Lion's)
        String resultsTextSize = sharedPref.getString("custom_results_font_size_preference","22");
        Float resultsTextSizef = Float.parseFloat(resultsTextSize);
        snellenFractionValue.setTextSize(resultsTextSizef);
        snellenDecimalValue.setTextSize(resultsTextSizef);
        snellenLogMarValue.setTextSize(resultsTextSizef);







    }


    // TODO - Keep improving this, clean code

    // MACRO
    private void moveToUpperAcuity(){

        // Method to move optotype to upper acuity optotypes (smaller one's)
        //------------------------------------------------------------------
        int indexPreview = currentAcuityIndex + 1;
        if (this.validateAcuityTableIndexLimit(indexPreview)){
            if (this.validateAcuityCustomLimits(indexPreview,true)){
                this.buildNewOptotypeWithRealDrawSize(indexPreview, true);
            }
        }
    }

    // MACRO
    private void moveToLowerAcuity(){

        // Method to move optotype to lower acuity optotypes (bigger one's)
        //-----------------------------------------------------------------
        int indexPreview = currentAcuityIndex - 1;
        if (this.validateAcuityTableIndexLimit(indexPreview)){
            if (this.validateAcuityCustomLimits(indexPreview,true)){
                this.buildNewOptotypeWithRealDrawSize(indexPreview, false);
            }
        }
    }


    private boolean validateAcuityTableIndexLimit(int testIndex){

        // Validate Acuity Table Index Limit (1st filter)
        //-----------------------------------------------
        if (testIndex>0){
            if (testIndex<snellenChartAcuitySequenceToDisplay.length){
                return true;
            } else {
                // Maximum index limit reached
                Context context = getApplicationContext();
                CharSequence text = getResources().getString(R.string.chart_smallest_optotype_warning_2);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return false;
            }
        } else {
            // Minimum index limit reached
            Context context = getApplicationContext();
            CharSequence text = getResources().getString(R.string.chart_biggest_optotype_warning_2);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
    }

    private boolean validateAcuityCustomLimits(int testIndex, boolean canLaunchWarnings){

        // Validate Acuity Custom Limits (user preferences - 2nd filter)
        //--------------------------------------------------------------
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isCustomLimitsOn = sharedPref.getBoolean("custom_limits_preference",false);
        String lastSelectedUpperLimit = sharedPref.getString("custom_limit_upper_preference","");
        String lastSelectedLowerLimit = sharedPref.getString("custom_limit_lower_preference","");

        if (isCustomLimitsOn){
            Double desiredOptotypeTotalPixelSize = mAcuityToolbox.calculateOptotypeSizeInPixelsForSnellenFraction((20.0/snellenChartAcuitySequenceToDisplay[testIndex]));
            Double desiredOptotypeSizeInDecimals = mAcuityToolbox.calculateDecimalForOptotypeSizeInPixels(desiredOptotypeTotalPixelSize);
            if (desiredOptotypeSizeInDecimals <= Double.parseDouble(lastSelectedUpperLimit) || desiredOptotypeSizeInDecimals >= Double.parseDouble(lastSelectedLowerLimit)){
                if (canLaunchWarnings){
                    // TOAST - The Desired Optotype is Outside Custom Acuity Limits
                    Context context = getApplicationContext();
                    CharSequence text = getResources().getString(R.string.chart_optotype_outside_limits_warning);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    private void buildNewOptotypeWithRealDrawSize(int newAcuityIndex, boolean isAcuityMovingUp){

        // Validate Acuity Optotype Draw Size

        Double DesiredOptotypeTotalPixelSize = mAcuityToolbox.calculateOptotypeSizeInPixelsForSnellenFraction((20.0/snellenChartAcuitySequenceToDisplay[newAcuityIndex]));

        if (snellenOptotype.setOptotypeTotalPixelSize(DesiredOptotypeTotalPixelSize.intValue())) {
            // Can Draw The Desired Optotype
            currentAcuityIndex = newAcuityIndex;
            snellenOptotype.selectNewRandomOptotype();
            snellenOptotype.reDrawOptotype();
            updateLabelValues((double) snellenOptotype.getOptotypeTotalPixelSize());
        } else {
            // Can't Draw The Desired Optotype
            if (isAcuityMovingUp){
                // Can't Draw The Desired Optotype (UNDO LAST ACTIONS)
                Context context = getApplicationContext();
                CharSequence text = getResources().getString(R.string.chart_smallest_optotype_warning_1);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                // Can't Draw The Desired Optotype (UNDO LAST ACTIONS)
                Context context = getApplicationContext();
                CharSequence text = getResources().getString(R.string.chart_biggest_optotype_warning_1);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
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
                builder.setMessage(R.string.dialog_help_content_chart_activity).setTitle(R.string.dialog_help_title_chart_activity);

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

            case R.id.action_bar_item_micro:

                // 0. RUN VOICE RECOGNITION

                // 1. CHECH SPEECH RECOGNITION AVAILABILITY
                // - Check to see if a recognition activity is present.
                // - If running on AVD virtual device it will give this message.
                // - The mic required only works on an actual android device
                PackageManager pm = getPackageManager();
                List activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
                if (activities.size() != 0) {
                    item.setEnabled(true);
                } else {
                    item.setEnabled(false);
                }

                // 2. SPEECH RECOGNITION START
                startVoiceRecognitionActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //==============================================================================================
    //----------------------------------------------------------------------------------------------
    // Voice Recognition


    // Timer for Sequencial Voice Commands Input
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            startVoiceRecognitionActivity();
        }
    }


    // Start Voice Recognition
    //------------------------
    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.voice_recognition_message_acuity));
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }


    // Process Voice Recognition
    //------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it
            // could have heard
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            //mList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, matches));
            // matches is the result of voice input. It is a list of what the
            // user possibly said.
            // Using an if statement for the keyword you want to use allows the
            // use of any activity if keywords match
            // it is possible to set up multiple keywords to use the same
            // activity so more than one word will allow the user
            // to use the activity (makes it so the user doesn't have to
            // memorize words from a list)
            // to use an activity from the voice input information simply use
            // the following format;
            // if (matches.contains("keyword here") { startActivity(new
            // Intent("name.of.manifest.ACTIVITY")


            if (matches.contains(getResources().getString(R.string.voice_recognition_down))) {

                if (snellenOptotype.decreaseSize(15)) {
                    snellenOptotype.selectNewRandomOptotype();
                    snellenOptotype.reDrawOptotype();
                } else {
                    // Minimum index limit reached
                    Context context = getApplicationContext();
                    CharSequence text = getResources().getString(R.string.toast_minimum_size_reached);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }

            if (matches.contains(getResources().getString(R.string.voice_recognition_up))) {

                if (snellenOptotype.increaseSize(15)) {
                    snellenOptotype.selectNewRandomOptotype();
                    snellenOptotype.reDrawOptotype();
                } else {
                    // Maximum size limit reached
                    Context context = getApplicationContext();
                    CharSequence text = getResources().getString(R.string.toast_maximum_size_reached);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }

            if (matches.contains(getResources().getString(R.string.voice_recognition_left))) {

                snellenOptotype.selectNewRandomOptotype();
                snellenOptotype.reDrawOptotype();
            }

            if (matches.contains(getResources().getString(R.string.voice_recognition_right))) {

                snellenOptotype.selectNewRandomOptotype();
                snellenOptotype.reDrawOptotype();
            }

            // To Repeat Voice Recognition after 5 seconds
            final CounterClass timer = new CounterClass(5000,1000);
            timer.start();

        }
    }
    //==============================================================================================




}
