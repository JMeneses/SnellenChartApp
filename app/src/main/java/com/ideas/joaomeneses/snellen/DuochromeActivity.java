package com.ideas.joaomeneses.snellen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class DuochromeActivity extends AppCompatActivity implements GestureDetector.OnDoubleTapListener,GestureDetector.OnGestureListener {

    // Class Properties
    //-----------------
    private GestureDetector mGestureDetector;
    private DuochromeView mDuochromeView;

    // Voice Recognition
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duochrome);

        // Add Gesture Listeners
        //----------------------
        mGestureDetector = new GestureDetector(this,this);
        mGestureDetector.setOnDoubleTapListener(this);

        // Grab Duochrome View Instance On Canvas
        //---------------------------------------
        mDuochromeView = (DuochromeView) findViewById(R.id.duochrome_view);
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.voice_recognition_message_duochrome));
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

                if (mDuochromeView.decreaseSize(15)) {
                    mDuochromeView.reDraw();
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

                if (mDuochromeView.increaseSize(15)) {
                    mDuochromeView.reDraw();
                } else {
                    // Maximum size limit reached
                    Context context = getApplicationContext();
                    CharSequence text = getResources().getString(R.string.toast_maximum_size_reached);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }

            // To Repeat Voice Recognition after 5 seconds
            final CounterClass timer = new CounterClass(5000,1000);
            timer.start();

        }
    }


    //==============================================================================================
    //----------------------------------------------------------------------------------------------
    // Action Bar Methods

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Create Custom Action Bar
        //-------------------------
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle Presses on the Action Bar Items
        //---------------------------------------
        switch (item.getItemId()) {
            case R.id.action_bar_item_help:

                // 0. SHOW HELP TEXT ALERT

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_help_content_duochrome_activity).setTitle(R.string.dialog_help_title_duochrome_activity);

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
    // Gesture Methods

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

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
                /*
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        Log.d("Swipe", "Left to Right swipe performed");
                    } else {
                        Log.d("Swipe", "Right to Left swipe performed");
                    }
                }
                */
                result = true;
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    //Log.d("Swipe", "Up to Down swipe performed");
                    if (mDuochromeView.decreaseSize(5)) {
                        mDuochromeView.reDraw();
                    } else {
                        // Minimum index limit reached
                        Context context = getApplicationContext();
                        CharSequence text = getResources().getString(R.string.toast_minimum_size_reached);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                } else {
                    //Log.d("Swipe", "Down to Up swipe performed");
                    if (mDuochromeView.increaseSize(5)) {
                        mDuochromeView.reDraw();
                    } else {
                        // Maximum size limit reached
                        Context context = getApplicationContext();
                        CharSequence text = getResources().getString(R.string.toast_maximum_size_reached);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    // External Hardware Keyboard And Mouse Support
    // TODO - Testar esta cena do teclado melhorar o código se funcionar
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mDuochromeView.increaseSize(5)) {
                    mDuochromeView.reDraw();
                } else {
                    // Maximum size limit reached
                    Context context = getApplicationContext();
                    CharSequence text = getResources().getString(R.string.toast_maximum_size_reached);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mDuochromeView.decreaseSize(5)) {
                    mDuochromeView.reDraw();
                } else {
                    // Minimum index limit reached
                    Context context = getApplicationContext();
                    CharSequence text = getResources().getString(R.string.toast_minimum_size_reached);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

}
