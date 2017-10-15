package com.ideas.joaomeneses.snellen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Duochrome View Creation Class
 * Created by joaomeneses on 16/06/16.
 */
public class DuochromeView extends View {

    // Class Constructor
    //------------------
    public DuochromeView(Context context, AttributeSet attrs) {
        super(context,attrs);

        firstTimeToDraw = true;
    }

    // Class Properties
    //-----------------
    private int externalMaxRadius;
    private int externalMinRadius;
    private int radiusToDraw;
    private boolean firstTimeToDraw = true;

    // Custom Draw
    //------------
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Center Optotype on Screen
        int screenCenterX = canvas.getWidth()/2;
        int screenCenterY = canvas.getHeight()/2;
        int distanceToMoveFromCenter = canvas.getHeight()/4;
        int minimumGap = canvas.getHeight()/20;
        if (canvas.getWidth()<=(canvas.getHeight()/2)) {
            externalMaxRadius = (canvas.getWidth()/2)-minimumGap;
        } else {
            externalMaxRadius = (canvas.getHeight()/4)-minimumGap;
        }
        externalMinRadius = 24; // Minimum Shape, must be divisible by 4,and 8

        if (firstTimeToDraw) {
            radiusToDraw = externalMaxRadius;
            firstTimeToDraw = false;
        }

        // Draw Background Color Rectangles
        Path rectangleOnePath = new Path();
        rectangleOnePath.addRect(0,0,canvas.getWidth(),canvas.getHeight()/2,Path.Direction.CW);
        Path rectangleTwoPath = new Path();
        rectangleTwoPath.addRect(0,canvas.getHeight()/2,canvas.getWidth(),canvas.getHeight(),Path.Direction.CW);

        Paint paintColorOne = new Paint();
        paintColorOne.setColor(Color.RED);
        paintColorOne.setStyle(Paint.Style.FILL);
        Paint paintColorTwo = new Paint();
        paintColorTwo.setColor(Color.GREEN);
        paintColorTwo.setStyle(Paint.Style.FILL);

        canvas.drawPath(rectangleOnePath,paintColorOne);
        canvas.drawPath(rectangleTwoPath,paintColorTwo);

        // Draw Reflection Patterns
        Path optotypeOnePath = this.circularPattern(screenCenterX,screenCenterY-distanceToMoveFromCenter,radiusToDraw);
        Path optotypeTwoPath = this.circularPattern(screenCenterX,screenCenterY+distanceToMoveFromCenter,radiusToDraw);

        Paint paintColor = new Paint();
        paintColor.setColor(Color.BLACK);
        paintColor.setStyle(Paint.Style.FILL);

        canvas.drawPath(optotypeOnePath,paintColor);
        canvas.drawPath(optotypeTwoPath,paintColor);

    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    // Redraw Optotype
    public void reDraw() {
        this.invalidate();
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    // Change Optotype Size
    public boolean increaseSize(int pxToIncrease) {
        if (validateSize(externalMaxRadius,externalMinRadius,radiusToDraw+pxToIncrease)) {
            radiusToDraw += pxToIncrease;
            return true;
        } else {
            return false;
        }
    }

    public boolean decreaseSize(int pxToDecrease) {
        if (validateSize(externalMaxRadius,externalMinRadius,radiusToDraw-pxToDecrease)) {
            radiusToDraw -= pxToDecrease;
            return true;
        } else {
            return false;
        }
    }

    private boolean validateSize(int max, int min, int size) {

        // Validate Grid Step in Pixels
        if (size<min){
            return false;
        } else if (size>max) {
            return false;
        } else {
            return true;
        }
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    // Refraction Patterns
    private Path circularPattern(int centerX, int centerY, int radius) {

        float externalR = radius;
        float internalR = externalR - (radius/4);
        float externalSmallR = internalR - (radius/4);
        float internalSmallR = externalSmallR - (radius/8);

        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float)centerX,(float)centerY,externalR,Path.Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float)centerX,(float)centerY,internalR,Path.Direction.CW);
        Path smallOuterCirclePath = new Path();
        smallOuterCirclePath.addCircle((float)centerX,(float)centerY,externalSmallR,Path.Direction.CW);
        Path smallInnerCirclePath = new Path();
        smallInnerCirclePath.addCircle((float)centerX,(float)centerY,internalSmallR,Path.Direction.CW);

        optotypePath.op(outerCirclePath,innerCirclePath,Path.Op.DIFFERENCE);
        optotypePath.op(smallOuterCirclePath,Path.Op.UNION);
        optotypePath.op(smallInnerCirclePath,Path.Op.DIFFERENCE);

        return optotypePath;
    }

}
