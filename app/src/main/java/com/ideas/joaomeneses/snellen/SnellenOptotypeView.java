package com.ideas.joaomeneses.snellen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Optotype View Creation Class
 * Created by joaomeneses on 31/05/16.
 */
public class SnellenOptotypeView extends View {

    // Local Variables
    //----------------
    private int gridStep = 100;                                 // Square Grid Unit in Pixels to Draw gridStep x gridStep
    private String currentSnellenOptotype = "E";                // Current Draw Snellen Optotype
    private int currentChildrenOptotype = 0;                    // Current Draw Children Optotype
    private int currentSloanOptotype = 0;
    private int currentRotationInDegrees = 0;
    private String optotypeFormatToDraw = "Snellen_Ref";
    private int currentAlphaToDraw = 255;
    private Paint paintColor;

    // Class Constructor
    //------------------
    public SnellenOptotypeView(Context context, AttributeSet attrs) {
        super(context,attrs);

        // Init
        paintColor = new Paint();
    }

    // Custom Draw
    //------------
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Log.d("Optotype View","Pixels 1 Step Draw: "+gridStep);

        // Center Optotype on Screen
        // Define Shape Path

        int originOffsetX;
        int originOffsetY;
        Path optotypePath;



        switch (optotypeFormatToDraw) {

            case "Snellen_Ref":

                // Optotype Grid Positioning 5x5
                originOffsetX = canvas.getWidth()/2;
                originOffsetY = canvas.getHeight()/2;

                // Define Shape Path
                if (currentSnellenOptotype.equals("E")){
                    optotypePath = this.snellenOptotypeE(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("T")) {
                    optotypePath = this.snellenOptotypeT(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("F")) {
                    optotypePath = this.snellenOptotypeF(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("Z")) {
                    optotypePath = this.snellenOptotypeZ(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("L")) {
                    optotypePath = this.snellenOptotypeL(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("O")) {
                    optotypePath = this.snellenOptotypeO(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("C")) {
                    optotypePath = this.snellenOptotypeC(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("D")) {
                    optotypePath = this.snellenOptotypeD(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("P")) {
                    optotypePath = this.snellenOptotypeP(originOffsetX,originOffsetY);
                } else {
                    optotypePath = this.snellenOptotypeE(originOffsetX,originOffsetY);
                }
                break;

            case "Landolt_C_Ref":

                // Optotype Grid Positioning 5x5
                originOffsetX = canvas.getWidth()/2;
                originOffsetY = canvas.getHeight()/2;

                // Rotate
                canvas.rotate(this.currentRotationInDegrees,originOffsetX,originOffsetY);

                // Define Shape Path
                optotypePath = this.optotypeLandoltC(originOffsetX,originOffsetY);
                break;

            case "Tumbling_E_Ref":

                // Optotype Grid Positioning in 5x5 Grid
                originOffsetX = canvas.getWidth()/2;
                originOffsetY = canvas.getHeight()/2;

                // Rotate
                canvas.rotate(this.currentRotationInDegrees,canvas.getWidth()/2,canvas.getHeight()/2);

                // Define Shape Path
                optotypePath = this.optotypeTumblingE(originOffsetX,originOffsetY);
                break;

            case "LEA_Ref":

                // Optotype Grid Positioning 7x7
                originOffsetX = canvas.getWidth()/2;
                originOffsetY = canvas.getHeight()/2;

                // Define Shape Path
                switch (this.currentChildrenOptotype) {
                    case 0:
                        optotypePath = this.optotypeLeaSymbolApple(originOffsetX,originOffsetY);
                        break;
                    case 1:
                        optotypePath = this.optotypeLeaSymbolCircle(originOffsetX,originOffsetY);
                        break;
                    case 2:
                        optotypePath = this.optotypeLeaSymbolSquare(originOffsetX,originOffsetY);
                        break;
                    case 3:
                        optotypePath = this.optotypeLeaSymbolHouse(originOffsetX,originOffsetY);
                        break;
                    default:
                        optotypePath = this.optotypeLeaSymbolApple(originOffsetX,originOffsetY);
                }
                break;

            case "Sloan_Ref":

                // Optotype Grid Positioning in 5x5 Grid
                originOffsetX = canvas.getWidth()/2;
                originOffsetY = canvas.getHeight()/2;

                // Define Shape Path
                switch (this.currentSloanOptotype) {
                    case 0:
                        optotypePath = this.optotypeSloanH(originOffsetX,originOffsetY);
                        break;
                    case 1:
                        optotypePath = this.optotypeSloanC(originOffsetX,originOffsetY);
                        break;
                    case 2:
                        optotypePath = this.optotypeSloanO(originOffsetX,originOffsetY);
                        break;
                    case 3:
                        optotypePath = this.optotypeSloanV(originOffsetX,originOffsetY);
                        break;
                    case 4:
                        optotypePath = this.optotypeSloanN(originOffsetX,originOffsetY);
                        break;
                    case 5:
                        optotypePath = this.optotypeSloanZ(originOffsetX,originOffsetY);
                        break;
                    case 6:
                        optotypePath = this.optotypeSloanD(originOffsetX,originOffsetY);
                        break;
                    default:
                        optotypePath = this.optotypeSloanH(originOffsetX,originOffsetY);
                }
                break;

            default:

                // Optotype Grid Positioning 5x5
                originOffsetX = canvas.getWidth()/2;
                originOffsetY = canvas.getHeight()/2;

                // Define Shape Path
                if (currentSnellenOptotype.equals("E")){
                    optotypePath = this.snellenOptotypeE(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("T")) {
                    optotypePath = this.snellenOptotypeT(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("F")) {
                    optotypePath = this.snellenOptotypeF(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("Z")) {
                    optotypePath = this.snellenOptotypeZ(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("L")) {
                    optotypePath = this.snellenOptotypeL(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("O")) {
                    optotypePath = this.snellenOptotypeO(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("C")) {
                    optotypePath = this.snellenOptotypeC(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("D")) {
                    optotypePath = this.snellenOptotypeD(originOffsetX,originOffsetY);
                } else if (currentSnellenOptotype.equals("P")) {
                    optotypePath = this.snellenOptotypeP(originOffsetX,originOffsetY);
                } else {
                    optotypePath = this.snellenOptotypeE(originOffsetX,originOffsetY);
                }
        }

        // Draw Shape Path
        //Paint paintColor = new Paint();
        paintColor.setColor(Color.BLACK);
        paintColor.setAlpha(currentAlphaToDraw);
        paintColor.setStyle(Paint.Style.FILL);
        canvas.drawPath(optotypePath,paintColor);

    }

    // Set Optotype
    //-------------
    public void setOptotypeFormat(String format) {

        this.optotypeFormatToDraw = format;
    }

    public void setOptotypeAlpha(int alpha) {

        if (alpha>255) {
            alpha = 255;
        } else if (alpha<0) {
            alpha = 0;
        }

        this.currentAlphaToDraw = alpha;
    }


    // Redraw Optotype
    //----------------
    public void reDrawOptotype() {

        // force display redraw
        this.invalidate();
    }


    // Set & Get Total Optotype Grid Size
    //-----------------------------------
    public boolean setOptotypeTotalPixelSize(int pxSize) {

        if (validateGridStepSize(this.getWidth(),this.getHeight(),pxSize/5)) {

            //Log.d("Optotype View","VALID gridStep Value After Validation: "+gridStep);
            gridStep = pxSize/5;
            return true;

        } else {

            //Log.d("Optotype View","INVALID gridStep Value After Validation: "+gridStep);
            return false;
        }
    }

    public int getOptotypeTotalPixelSize() {
        return 5*gridStep;
    }

    // Change Optotype Size
    //---------------------
    // TODO - Aqui falta adicionar os tais limites costumizados se os houver
    public boolean increaseSize(int pxToIncrease) {
        if (validateGridStepSize(this.getWidth(),this.getHeight(),gridStep+pxToIncrease)) {
            gridStep += pxToIncrease;
            return true;
        } else {
            return false;
        }
    }

    public boolean decreaseSize(int pxToDecrease) {
        if (validateGridStepSize(this.getWidth(),this.getHeight(),gridStep-pxToDecrease)) {
            gridStep -= pxToDecrease;
            return true;
        } else {
            return false;
        }
    }

    // Canvas Size Minimum and Maximum Validation
    //-------------------------------------------
    private boolean validateGridStepSize(int width, int height, int gridStep) {

        // Grid Format Size
        int gridFormatSize;
        switch (optotypeFormatToDraw) {

            case "Snellen_Ref":
                gridFormatSize = 5;
                break;
            case "Landolt_C_Ref":
                gridFormatSize = 5;
                break;
            case "Tumbling_E_Ref":
                gridFormatSize = 5;
                break;
            case "LEA_Ref":
                gridFormatSize = 7;
                break;
            case "Sloan_Ref":
                gridFormatSize = 5;
                break;
            default:
                gridFormatSize = 5;
        }

        // Validate Grid Step in Pixels
        if (gridStep<=1){
            return false;
        }
        if (width<=height) {
            if ((gridFormatSize*gridStep)>=width){
                return false;
            }
        } else {
            if ((gridFormatSize*gridStep)>=height){
                return false;
            }
        }

        // All tests passed, is valid
        return true;
    }


    // Change Optotype Form
    //---------------------
    public void selectNewRandomOptotype() {

        switch (optotypeFormatToDraw) {

            case "Snellen_Ref":
                this.generateNewRandomSnellenOptotypeAndRotation();
                break;
            case "Landolt_C_Ref":
                this.generateNewRandomSnellenOptotypeAndRotation();
                break;
            case "Tumbling_E_Ref":
                this.generateNewRandomSnellenOptotypeAndRotation();
                break;
            case "LEA_Ref":
                this.generateNewRandomChildrenOptotype();
                break;
            case "Sloan_Ref":
                this.generateNewRandomSloanOptotype();
                break;
            default:
                this.generateNewRandomSnellenOptotypeAndRotation();
        }

    }

    private void generateNewRandomSnellenOptotypeAndRotation() {

        String[] allOptotypesAvailable = {"E","T","F","Z","O","L","C","D","P"};
        int[] allRotationsAvailable = {0,90,180,270};

        Random rand = new Random();

        String randomOptotype;
        do {
            int randomOptotypeIndex = rand.nextInt(allOptotypesAvailable.length);
            randomOptotype = allOptotypesAvailable[randomOptotypeIndex];
        } while (randomOptotype.equals(this.currentSnellenOptotype));
        this.currentSnellenOptotype = randomOptotype;

        int randomDegrees;
        do {
            int randomRotationIndex = rand.nextInt(allRotationsAvailable.length);
            randomDegrees = allRotationsAvailable[randomRotationIndex];
        } while (randomDegrees == this.currentRotationInDegrees);
        this.currentRotationInDegrees = randomDegrees;
    }

    private void generateNewRandomChildrenOptotype() {

        int allOptotypesAvailable = 4;

        Random rand = new Random();

        int randomOptotype;
        do {
            randomOptotype = rand.nextInt(allOptotypesAvailable);
        } while (randomOptotype==this.currentSloanOptotype);
        this.currentSloanOptotype = randomOptotype;
        do {
            randomOptotype = rand.nextInt(allOptotypesAvailable);
        } while (randomOptotype==this.currentChildrenOptotype);
        this.currentChildrenOptotype = randomOptotype;

    }

    private void generateNewRandomSloanOptotype() {



        int allOptotypesAvailable = 7;

        Random rand = new Random();

        int randomOptotype;
        do {
            randomOptotype = rand.nextInt(allOptotypesAvailable);
        } while (randomOptotype==this.currentSloanOptotype);
        this.currentSloanOptotype = randomOptotype;

    }

    //==============================================================================================
    //==============================================================================================

    // Snellen Optotype Draws In 5x5 Grid Style
    //-----------------------------------------
    private Path snellenOptotypeE(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((3*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((3*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,(4*gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo(originX,(gridStep)+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    private Path snellenOptotypeT(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((3*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((3*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(5*gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(5*gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo(originX,(2*gridStep)+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    private Path snellenOptotypeF(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((3*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((3*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((3*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((3*gridStep)+originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,(4*gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo(originX,(gridStep)+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    private Path snellenOptotypeZ(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((float)(1.5*gridStep+originX),(4*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,(4*gridStep)+originY);
        optotypePath.lineTo((float)(3.5*gridStep+originX),(gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo(originX,(2*gridStep)+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    private Path snellenOptotypeL(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo((3*gridStep)+originX,originY);
        optotypePath.lineTo((3*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo((2*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((4*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,(4*gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((gridStep)+originX,(gridStep)+originY);
        optotypePath.lineTo(originX,(gridStep)+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    private Path snellenOptotypeO(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(2.5*gridStep),Path.Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        optotypePath.op(outerCirclePath,innerCirclePath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    private Path snellenOptotypeC(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(2.5*gridStep),Path.Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float)(2.5*gridStep+originX),(float)(2*gridStep+originY),(float)(5*gridStep+originX),(float)(3*gridStep+originY),Path.Direction.CW);
        Path circlePath = new Path();
        circlePath.op(outerCirclePath,innerCirclePath,Path.Op.DIFFERENCE);
        optotypePath.op(circlePath,rectanglePath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    private Path snellenOptotypeD(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        Path structPath = new Path();
        structPath.moveTo(originX,originY);
        structPath.lineTo((float)(2.5*gridStep)+originX,originY);
        structPath.lineTo((float)(2.5*gridStep)+originX,gridStep+originY);
        structPath.lineTo((2*gridStep)+originX,gridStep+originY);
        structPath.lineTo((2*gridStep)+originX,(4*gridStep)+originY);
        structPath.lineTo((float)(2.5*gridStep)+originX,(4*gridStep)+originY);
        structPath.lineTo((float)(2.5*gridStep)+originX,(5*gridStep)+originY);
        structPath.lineTo(originX,(5*gridStep)+originY);
        structPath.lineTo(originX,(4*gridStep)+originY);
        structPath.lineTo(gridStep+originX,(4*gridStep)+originY);
        structPath.lineTo(gridStep+originX,gridStep+originY);
        structPath.lineTo(originX,gridStep+originY);
        structPath.lineTo(originX,originY);
        Path outerCircleHalfPath = new Path();
        outerCircleHalfPath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(2.5*gridStep),Path.Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float)(originX),(float)(originY),(float)(2.5*gridStep+originX),(float)(5*gridStep+originY),Path.Direction.CW);
        outerCircleHalfPath.op(rectanglePath,Path.Op.DIFFERENCE);
        Path innerCircleHalfPath = new Path();
        innerCircleHalfPath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        innerCircleHalfPath.op(rectanglePath,Path.Op.DIFFERENCE);
        optotypePath.op(structPath,outerCircleHalfPath,Path.Op.UNION);
        optotypePath.op(innerCircleHalfPath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    private Path snellenOptotypeP(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        Path structPath = new Path();
        structPath.moveTo(originX,originY);
        structPath.lineTo((float)(3.5*gridStep)+originX,originY);
        structPath.lineTo((float)(3.5*gridStep)+originX,gridStep+originY);
        structPath.lineTo((2*gridStep)+originX,gridStep+originY);
        structPath.lineTo((2*gridStep)+originX,(2*gridStep)+originY);
        structPath.lineTo((float)(3.5*gridStep)+originX,(2*gridStep)+originY);
        structPath.lineTo((float)(3.5*gridStep)+originX,(3*gridStep)+originY);
        structPath.lineTo((2*gridStep)+originX,(3*gridStep)+originY);
        structPath.lineTo((2*gridStep)+originX,(4*gridStep)+originY);
        structPath.lineTo((3*gridStep)+originX,(4*gridStep)+originY);
        structPath.lineTo((3*gridStep)+originX,(5*gridStep)+originY);
        structPath.lineTo(originX,(5*gridStep)+originY);
        structPath.lineTo(originX,(4*gridStep)+originY);
        structPath.lineTo(gridStep+originX,(4*gridStep)+originY);
        structPath.lineTo(gridStep+originX,gridStep+originY);
        structPath.lineTo(originX,gridStep+originY);
        structPath.lineTo(originX,originY);
        Path outerCircleHalfPath = new Path();
        outerCircleHalfPath.addCircle((float)(3.5*gridStep+originX),(float)(1.5*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float)(originX),(float)(originY),(float)(3.5*gridStep+originX),(float)(3*gridStep+originY),Path.Direction.CW);
        outerCircleHalfPath.op(rectanglePath,Path.Op.DIFFERENCE);
        Path innerCircleHalfPath = new Path();
        innerCircleHalfPath.addCircle((float)(3.5*gridStep+originX),(float)(1.5*gridStep+originY),(float)(0.5*gridStep),Path.Direction.CW);
        innerCircleHalfPath.op(rectanglePath,Path.Op.DIFFERENCE);
        optotypePath.op(structPath,outerCircleHalfPath,Path.Op.UNION);
        optotypePath.op(innerCircleHalfPath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    // Tumbling E Optotype Draw in 5x5 Grid Style
    //-------------------------------------------
    private Path optotypeTumblingE(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,originY);
        optotypePath.lineTo((5*gridStep)+originX,gridStep+originY);
        optotypePath.lineTo(gridStep+originX,gridStep+originY);
        optotypePath.lineTo(gridStep+originX,(2*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(2*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(3*gridStep)+originY);
        optotypePath.lineTo(gridStep+originX,(3*gridStep)+originY);
        optotypePath.lineTo(gridStep+originX,(4*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(4*gridStep)+originY);
        optotypePath.lineTo((5*gridStep)+originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,(5*gridStep)+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    // Landolt C Optotype Draw in 5x5 Grid Style
    //------------------------------------------
    private Path optotypeLandoltC(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(2.5*gridStep),Path.Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float)(2.5*gridStep+originX),(float)(2*gridStep+originY),(float)(5*gridStep+originX),(float)(3*gridStep+originY),Path.Direction.CW);
        Path circlePath = new Path();
        circlePath.op(outerCirclePath,innerCirclePath,Path.Op.DIFFERENCE);
        optotypePath.op(circlePath,rectanglePath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    // LEA Symbols Optotypes - House - 7x7 Grid
    //-----------------------------------------
    private Path optotypeLeaSymbolHouse(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(3.5*gridStep);
        int originY = centerY - (int)(3.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();

        Path outerHousePath = new Path();
        outerHousePath.moveTo((float)3.5*gridStep+originX,originY);
        outerHousePath.lineTo((float)8.2*gridStep+originX,2*gridStep+originY);
        outerHousePath.lineTo(7*gridStep+originX,2*gridStep+originY);
        outerHousePath.lineTo(7*gridStep+originX,7*gridStep+originY);
        outerHousePath.lineTo(originX,7*gridStep+originY);
        outerHousePath.lineTo(originX,2*gridStep+originY);
        outerHousePath.lineTo(originX-(float)1.2*gridStep,2*gridStep+originY);
        outerHousePath.lineTo((float)3.5*gridStep+originX,originY);

        Path innerHousePath = new Path();
        innerHousePath.moveTo((float)3.5*gridStep+originX,gridStep+originY);
        innerHousePath.lineTo(6*gridStep+originX,(float)2*gridStep+originY);
        innerHousePath.lineTo(6*gridStep+originX,6*gridStep+originY);
        innerHousePath.lineTo(gridStep+originX,6*gridStep+originY);
        innerHousePath.lineTo(gridStep+originX,(float)2*gridStep+originY);
        innerHousePath.lineTo((float)3.5*gridStep+originX,gridStep+originY);

        optotypePath.op(outerHousePath,innerHousePath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    // LEA Symbols Optotypes - Square - 7x7 Grid
    //------------------------------------------
    private Path optotypeLeaSymbolSquare(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(3.5*gridStep);
        int originY = centerY - (int)(3.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();

        Path outerSquarePath = new Path();
        outerSquarePath.moveTo(originX,originY);
        outerSquarePath.lineTo((7*gridStep)+originX,originY);
        outerSquarePath.lineTo((7*gridStep)+originX,(7*gridStep)+originY);
        outerSquarePath.lineTo(originX,(7*gridStep)+originY);
        outerSquarePath.lineTo(originX,originY);

        Path innerSquarePath = new Path();
        innerSquarePath.moveTo(gridStep+originX,gridStep+originY);
        innerSquarePath.lineTo((6*gridStep)+originX,gridStep+originY);
        innerSquarePath.lineTo((6*gridStep)+originX,(6*gridStep)+originY);
        innerSquarePath.lineTo(gridStep+originX,(6*gridStep)+originY);
        innerSquarePath.lineTo(gridStep+originX,gridStep+originY);

        optotypePath.op(outerSquarePath,innerSquarePath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    // LEA Symbols Optotypes - Circle - 7x7 Grid
    //------------------------------------------
    private Path optotypeLeaSymbolCircle(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(3.5*gridStep);
        int originY = centerY - (int)(3.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();

        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float)(3.5*gridStep+originX),(float)(3.5*gridStep+originY),(float)(3.5*gridStep),Path.Direction.CW);

        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float)(3.5*gridStep+originX),(float)(3.5*gridStep+originY),(float)(2.5*gridStep),Path.Direction.CW);

        optotypePath.op(outerCirclePath,innerCirclePath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    // LEA Symbols Optotypes - Apple - 7x7 Grid
    //-----------------------------------------
    private Path optotypeLeaSymbolApple(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(3.5*gridStep);
        int originY = centerY - (int)(3.5*gridStep);

        // The Origin is the Left Upper Corner
        Path outerApplePath = new Path();
        outerApplePath.moveTo((float)(3.5*gridStep+originX),(float)(gridStep+originY));
        outerApplePath.cubicTo(
                (float)(4*gridStep+originX),(float)(originY-0.5*gridStep),
                (float)(7*gridStep+originX),(float)(originY-0.5*gridStep),
                (float)(7*gridStep+originX),(float)(2*gridStep+originY));
        outerApplePath.cubicTo(
                (float)(7*gridStep+originX),(float)(4*gridStep+originY),
                (float)(5*gridStep+originX),(float)(6.5*gridStep+originY),
                (float)(5*gridStep+originX),(float)(6.5*gridStep+originY));
        outerApplePath.cubicTo(
                (float)(4.7*gridStep+originX),(float)(7*gridStep+originY),
                (float)(4.3*gridStep+originX),(float)(7*gridStep+originY),
                (float)(4*gridStep+originX),(float)(6.5*gridStep+originY));
        outerApplePath.cubicTo(
                (float)(3.7*gridStep+originX),(float)(6.1*gridStep+originY),
                (float)(3.3*gridStep+originX),(float)(6.1*gridStep+originY),
                (float)(3*gridStep+originX),(float)(6.5*gridStep+originY));
        outerApplePath.cubicTo(
                (float)(2.7*gridStep+originX),(float)(7*gridStep+originY),
                (float)(2.3*gridStep+originX),(float)(7*gridStep+originY),
                (float)(2*gridStep+originX),(float)(6.5*gridStep+originY));
        outerApplePath.cubicTo(
                (float)(2*gridStep+originX),(float)(6.5*gridStep+originY),
                (float)(originX),(float)(4*gridStep+originY),
                (float)(originX),(float)(2*gridStep+originY));
        outerApplePath.cubicTo(
                (float)(0.2*gridStep+originX),(float)(originY),
                (float)(2.7*gridStep+originX),(float)(originY),
                (float)(3.5*gridStep+originX),(float)(gridStep+originY));

        Path innerApplePath = new Path();
        innerApplePath.moveTo((float)(3.5*gridStep+originX),(float)(2.5*gridStep+originY));
        innerApplePath.cubicTo(
                (float)(4.3*gridStep+originX),(float)(0.2*gridStep+originY),
                (float)(6*gridStep+originX),(float)(0.7*gridStep+originY),
                (float)(6*gridStep+originX),(float)(2*gridStep+originY));
        innerApplePath.cubicTo(
                (float)(6*gridStep+originX),(float)(3*gridStep+originY),
                (float)(5.5*gridStep+originX),(float)(4.5*gridStep+originY),
                (float)(4.7*gridStep+originX),(float)(5.5*gridStep+originY));
        innerApplePath.cubicTo(
                (float)(4.5*gridStep+originX),(float)(5.8*gridStep+originY),
                (float)(4.1*gridStep+originX),(float)(5.8*gridStep+originY),
                (float)(3.9*gridStep+originX),(float)(5.5*gridStep+originY));
        innerApplePath.cubicTo(
                (float)(3.7*gridStep+originX),(float)(5.2*gridStep+originY),
                (float)(3.3*gridStep+originX),(float)(5.2*gridStep+originY),
                (float)(3.1*gridStep+originX),(float)(5.5*gridStep+originY));
        innerApplePath.cubicTo(
                (float)(2.9*gridStep+originX),(float)(5.8*gridStep+originY),
                (float)(2.5*gridStep+originX),(float)(5.8*gridStep+originY),
                (float)(2.3*gridStep+originX),(float)(5.5*gridStep+originY));
        innerApplePath.cubicTo(
                (float)(2.3*gridStep+originX),(float)(5.5*gridStep+originY),
                (float)(gridStep+originX),(float)(4*gridStep+originY),
                (float)(gridStep+originX),(float)(2.5*gridStep+originY));
        innerApplePath.cubicTo(
                (float)(gridStep+originX),(float)(0.8*gridStep+originY),
                (float)(3*gridStep+originX),(float)(0.9*gridStep+originY),
                (float)(3.5*gridStep+originX),(float)(2.5*gridStep+originY));

        Path completeOptotypePath = new Path();
        completeOptotypePath.reset();
        completeOptotypePath.op(outerApplePath,innerApplePath,Path.Op.DIFFERENCE);
        return completeOptotypePath;
    }

    // Sloan Letters Optotypes - C - 5x5 Grid
    //---------------------------------------
    private Path optotypeSloanC(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(2.5*gridStep),Path.Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float)(2.5*gridStep+originX),(float)(2*gridStep+originY),(float)(5*gridStep+originX),(float)(3*gridStep+originY),Path.Direction.CW);
        Path circlePath = new Path();
        circlePath.op(outerCirclePath,innerCirclePath,Path.Op.DIFFERENCE);
        optotypePath.op(circlePath,rectanglePath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    // Sloan Letters Optotypes - O - 5x5 Grid
    //---------------------------------------
    private Path optotypeSloanO(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(2.5*gridStep),Path.Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        optotypePath.op(outerCirclePath,innerCirclePath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    // Sloan Letters Optotypes - H - 5x5 Grid
    //---------------------------------------
    private Path optotypeSloanH(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo(gridStep+originX,originY);
        optotypePath.lineTo(gridStep+originX,2*gridStep+originY);
        optotypePath.lineTo(4*gridStep+originX,2*gridStep+originY);
        optotypePath.lineTo(4*gridStep+originX,originY);
        optotypePath.lineTo(5*gridStep+originX,originY);
        optotypePath.lineTo(5*gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(4*gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(4*gridStep+originX,3*gridStep+originY);
        optotypePath.lineTo(gridStep+originX,3*gridStep+originY);
        optotypePath.lineTo(gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(originX,5*gridStep+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    // Sloan Letters Optotypes - V - 5x5 Grid
    //---------------------------------------
    private Path optotypeSloanV(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo(gridStep+originX,originY);
        optotypePath.lineTo((float)(2.5*gridStep+originX),4*gridStep+originY);
        optotypePath.lineTo(4*gridStep+originX,originY);
        optotypePath.lineTo(5*gridStep+originX,originY);
        optotypePath.lineTo(3*gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(2*gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    // Sloan Letters Optotypes - N - 5x5 Grid
    //---------------------------------------
    private Path optotypeSloanN(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo(gridStep+originX,originY);
        optotypePath.lineTo(4*gridStep+originX,(float)(3.5*gridStep+originY));
        optotypePath.lineTo(4*gridStep+originX,originY);
        optotypePath.lineTo(5*gridStep+originX,originY);
        optotypePath.lineTo(5*gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(4*gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(gridStep+originX,(float)(1.5*gridStep+originY));
        optotypePath.lineTo(gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(originX,5*gridStep+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    // Sloan Letters Optotypes - Z - 5x5 Grid
    //---------------------------------------
    private Path optotypeSloanZ(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(originX,originY);
        optotypePath.lineTo(5*gridStep+originX,originY);
        optotypePath.lineTo(5*gridStep+originX,gridStep+originY);
        optotypePath.lineTo((float)1.5*gridStep+originX,4*gridStep+originY);
        optotypePath.lineTo(5*gridStep+originX,4*gridStep+originY);
        optotypePath.lineTo(5*gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(originX,5*gridStep+originY);
        optotypePath.lineTo(originX,4*gridStep+originY);
        optotypePath.lineTo((float)3.5*gridStep+originX,gridStep+originY);
        optotypePath.lineTo(originX,gridStep+originY);
        optotypePath.lineTo(originX,originY);
        return optotypePath;
    }

    // Sloan Letters Optotypes - D - 5x5 Grid
    //---------------------------------------
    private Path optotypeSloanD(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        Path structPath = new Path();
        structPath.moveTo(originX,originY);
        structPath.lineTo((float)(2.5*gridStep)+originX,originY);
        structPath.lineTo((float)(2.5*gridStep)+originX,gridStep+originY);
        structPath.lineTo(gridStep+originX,gridStep+originY);
        structPath.lineTo(gridStep+originX,(4*gridStep)+originY);
        structPath.lineTo((float)(2.5*gridStep)+originX,(4*gridStep)+originY);
        structPath.lineTo((float)(2.5*gridStep)+originX,(5*gridStep)+originY);
        structPath.lineTo(originX,(5*gridStep)+originY);
        structPath.lineTo(originX,originY);
        Path outerCircleHalfPath = new Path();
        outerCircleHalfPath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(2.5*gridStep),Path.Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float)(originX),(float)(originY),(float)(2.5*gridStep+originX),(float)(5*gridStep+originY),Path.Direction.CW);
        outerCircleHalfPath.op(rectanglePath,Path.Op.DIFFERENCE);
        Path innerCircleHalfPath = new Path();
        innerCircleHalfPath.addCircle((float)(2.5*gridStep+originX),(float)(2.5*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        innerCircleHalfPath.op(rectanglePath,Path.Op.DIFFERENCE);
        optotypePath.op(structPath,outerCircleHalfPath,Path.Op.UNION);
        optotypePath.op(innerCircleHalfPath,Path.Op.DIFFERENCE);
        return optotypePath;

    }

    /* UNUSED OPTOTYPES
    // Children Geometry Optotypes Triangle
    //-------------------------------------
    private Path optotypeChildrenGeometryTriangle(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerTrianglePath = new Path();
        outerTrianglePath.moveTo((float)(2.5*gridStep+originX),originY);
        outerTrianglePath.lineTo((5*gridStep)+originX,(5*gridStep)+originY);
        outerTrianglePath.lineTo(originX,(5*gridStep)+originY);
        outerTrianglePath.lineTo((float)(2.5*gridStep+originX),originY);
        Path innerTrianglePath = new Path();
        innerTrianglePath.moveTo((float)(2.5*gridStep+originX),(float)(2*gridStep+originY));
        innerTrianglePath.lineTo((float)(3.5*gridStep+originX),(4*gridStep)+originY);
        innerTrianglePath.lineTo((float)(1.5*gridStep+originX),4*gridStep+originY);
        innerTrianglePath.lineTo((float)(2.5*gridStep+originX),(float)(2*gridStep+originY));
        optotypePath.op(outerTrianglePath,innerTrianglePath,Path.Op.DIFFERENCE);
        return optotypePath;
    }

    // Children Geometry Optotypes Cross
    //----------------------------------
    private Path optotypeChildrenGeometryCross(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo(2*gridStep+originX,originY);
        optotypePath.lineTo(3*gridStep+originX,originY);
        optotypePath.lineTo(3*gridStep+originX,2*gridStep+originY);
        optotypePath.lineTo(5*gridStep+originX,2*gridStep+originY);
        optotypePath.lineTo(5*gridStep+originX,3*gridStep+originY);
        optotypePath.lineTo(3*gridStep+originX,3*gridStep+originY);
        optotypePath.lineTo(3*gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(2*gridStep+originX,5*gridStep+originY);
        optotypePath.lineTo(2*gridStep+originX,3*gridStep+originY);
        optotypePath.lineTo(originX,3*gridStep+originY);
        optotypePath.lineTo(originX,2*gridStep+originY);
        optotypePath.lineTo(2*gridStep+originX,2*gridStep+originY);
        optotypePath.lineTo(2*gridStep+originX,originY);
        return optotypePath;
    }

    // Children Draw Optotypes Car
    //----------------------------
    private Path optotypeChildrenDrawCar(int centerX, int centerY) {

        // Build Arround the Optotype Center
        int originX = centerX - (int)(2.5*gridStep);
        int originY = centerY - (int)(2.5*gridStep);

        // The Origin is the Left Upper Corner
        Path optotypePath = new Path();
        optotypePath.reset();

        Path outerCarBodyPath = new Path();
        outerCarBodyPath.moveTo(2*gridStep+originX,originY);
        outerCarBodyPath.lineTo(5*gridStep+originX,originY);
        outerCarBodyPath.lineTo(5*gridStep+originX,4*gridStep+originY);
        outerCarBodyPath.lineTo(originX,4*gridStep+originY);
        outerCarBodyPath.lineTo(originX,gridStep+originY);
        outerCarBodyPath.lineTo(2*gridStep+originX,gridStep+originY);
        outerCarBodyPath.lineTo(2*gridStep+originX,originY);

        Path innerCarBodyPath = new Path();
        innerCarBodyPath.moveTo(3*gridStep+originX,gridStep+originY);
        innerCarBodyPath.lineTo(4*gridStep+originX,gridStep+originY);
        innerCarBodyPath.lineTo(4*gridStep+originX,3*gridStep+originY);
        innerCarBodyPath.lineTo(gridStep+originX,3*gridStep+originY);
        innerCarBodyPath.lineTo(gridStep+originX,2*gridStep+originY);
        innerCarBodyPath.lineTo(3*gridStep+originX,2*gridStep+originY);
        innerCarBodyPath.lineTo(3*gridStep+originX,gridStep+originY);

        Path carFrontWheelPath = new Path();
        Path outerCarFrontWheelPath = new Path();
        outerCarFrontWheelPath.addCircle((float)(1*gridStep+originX),(float)(4*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        Path innerCarFrontWheelPath = new Path();
        innerCarFrontWheelPath.addCircle((float)(1*gridStep+originX),(float)(4*gridStep+originY),(float)(0.5*gridStep),Path.Direction.CW);
        carFrontWheelPath.op(outerCarFrontWheelPath,innerCarFrontWheelPath,Path.Op.DIFFERENCE);

        Path carBackWheelPath = new Path();
        Path outerCarBackWheelPath = new Path();
        outerCarBackWheelPath.addCircle((float)(4*gridStep+originX),(float)(4*gridStep+originY),(float)(1.5*gridStep),Path.Direction.CW);
        Path innerCarBackWheelPath = new Path();
        innerCarBackWheelPath.addCircle((float)(4*gridStep+originX),(float)(4*gridStep+originY),(float)(0.5*gridStep),Path.Direction.CW);
        carBackWheelPath.op(outerCarBackWheelPath,innerCarBackWheelPath,Path.Op.DIFFERENCE);

        optotypePath.op(outerCarBodyPath,innerCarBodyPath,Path.Op.DIFFERENCE);
        optotypePath.op(outerCarFrontWheelPath,Path.Op.DIFFERENCE);
        optotypePath.op(outerCarBackWheelPath,Path.Op.DIFFERENCE);
        optotypePath.op(carFrontWheelPath,Path.Op.UNION);
        optotypePath.op(carBackWheelPath,Path.Op.UNION);
        return optotypePath;
    }
   */

}