package com.ideas.joaomeneses.snellen;

import android.util.Log;

/*
 * TOOLBOX FUNÇÔES MATEMATICAS PARA CALCULO DA ACUIDADE (v0.5)
 *
 * Visual Acuity = Distance at which test is made / distance at which the smallest optotype identified subtends an angle of 5 arcminutes (MAR)
 *
 * Created by joaomeneses on 17/05/16.
 */
public class AcuityToolbox {

    public static final String METRIC_UNITS = "Metric";
    public static final String IMPERIAL_UNITS = "Imperial";
    public static final String DENOMINATOR_20_SNELLEN_FRACTION = "20/20";
    public static final String DENOMINATOR_6_SNELLEN_FRACTION = "6/6";

    private static final Double INCH_2_MILLIMETER = 25.400;     // 1 inch = 25,4 mm
    private static final Double FOOT_2_CENTIMETER = 30.480;     // 1 foot = 30,48 cm

    private static Double playerDistanceToScreen_mm;            // Variável da Posição do Sujeito face ao Monitor (mm)
    private static Double conversionRatioPixelsPerMillimeter;   // Declarar Factor de Conversão Pixeis/Milímetro (px/mm)


    //====================
    // (Class Constructor)
    //  plyrDistanceToScreen (centimeters or feet)
    //  screenPxPerInchX (pixels per inch)
    //  screenPxPerInchY (pixels per inch)
    //====================
    public AcuityToolbox(String measurementUnits,
                         Double plyrDistanceToScreen,
                         Double screenPxPerInchX,
                         Double screenPxPerInchY,
                         Double screenPxPerInchCustom) {

        // Debug
        //Log.d("Acuity Toolbox","INPUT >> unit: "+measurementUnits+" distance: "+plyrDistanceToScreen+" xdpi: "+screenPxPerInchX+" ydpi: "+screenPxPerInchY+" ppi: "+screenPxPerInchCustom);

        // Player Distance To Screen
        if (measurementUnits.equals(METRIC_UNITS)) {
            //Log.d("Acuity Toolbox","Metric System Selected");
            // from cm to mm
            playerDistanceToScreen_mm = plyrDistanceToScreen*10;
        } else if (measurementUnits.equals(IMPERIAL_UNITS)) {
            //Log.d("Acuity Toolbox","Imperial System Selected");
            // from foot to cm to mm
            playerDistanceToScreen_mm = plyrDistanceToScreen*FOOT_2_CENTIMETER*10;
        } else {
            //Log.d("Acuity Toolbox","Invalid or Null Unit System");
        }

        // Pixel Aspect Ratio
        if (screenPxPerInchY.equals(screenPxPerInchX)) {
            //Log.d("Acuity Toolbox","Pixel Aspect Ratio Square");
        } else {
            //Log.d("Acuity Toolbox","Pixel Aspect Ratio Rectangular");
        }

        // Calculate Conversion Ratio from px/in to px/mm
        conversionRatioPixelsPerMillimeter = screenPxPerInchCustom*(1/INCH_2_MILLIMETER);

        // Debug
        Log.d("Acuity Toolbox","OUTPUT >> unit: "+measurementUnits+" distance: "+playerDistanceToScreen_mm+" ratioPxMm: "+conversionRatioPixelsPerMillimeter);

    }


    //================
    // (Class Methods)
    //================


    // --------------------------------------------------------------------------------------
    // Cálculo Dimensão Imagem em Pixeis com Base na Acuidade (Snellen Fraction)
    //
    // Descrição: Função para cálculo da dimensão da imagem de acordo com a acuidade.
    // 			  Calcula-se tamanho do caracter em pixeis, assumindo que a visão 20/20
    // 			  é a correspondente á percepção correcta a 20 pés de distância de um
    // 			  objecto com 5 minutos de arco de dimensão.
    // --------------------------------------------------------------------------------------
    public Double calculateOptotypeSizeInPixelsForSnellenFraction(Double snellenFraction) {

        Double optotypeSize_mm;             // Declarar Variável do Tamanho de Caracter (mm)
        Double optotypeSize_px;             // Declarar Variável do Tamanho de Caracter (px)
        Double imaginaryDistance_mm;         // Declarar Variável da distância imaginária para ver o caracter com 5 MAR. (mm)

        imaginaryDistance_mm = playerDistanceToScreen_mm/snellenFraction;
        optotypeSize_mm = Math.tan((5.0/60.0)*(Math.PI/180.0))*imaginaryDistance_mm;
        optotypeSize_px = (double) Math.round(optotypeSize_mm*conversionRatioPixelsPerMillimeter) ;

        return optotypeSize_px;
    }


    //---------------------------------------------------------------------------------------
    // Cálculo da Snellen Fraction com Base no Tamanho do Optótipo
    //
    // Descrição: Função para cálculo do valor do denominador da Snellen Fraction que está a
    //			  ser efectivamente mostrado no computador.
    //            Retorna o denominador da fracção.
    //---------------------------------------------------------------------------------------
    public Double calculateSnellenFractionForOptotypeSizeInPixels(Double optotypeSize_px) {

        Double snellenFractionDenom;    // Snellen Fraction Denominator

        snellenFractionDenom = 20.0/(playerDistanceToScreen_mm/((optotypeSize_px/conversionRatioPixelsPerMillimeter)/(Math.tan((5.0/60.0)*(Math.PI/180.0)))));
        return snellenFractionDenom;
    }

    public Double calculateDecimalForOptotypeSizeInPixels(Double optotypeSize_px) {

        Double snellenFractionDenom = 20.0/(playerDistanceToScreen_mm/((optotypeSize_px/conversionRatioPixelsPerMillimeter)/(Math.tan((5.0/60.0)*(Math.PI/180.0)))));

        Double snellenFractionDecimal = 20.0/snellenFractionDenom;

        return snellenFractionDecimal;
    }


    //---------------------------------------------------------------------------------------
    // Valor Angulos Visuais (MAR - Minimal Angle Resolution)
    //
    // Descrição: Função para cálculo do valor em angulos visuais do estímulo que está a ser
    //			  efectivamente mostrado no computador.
    //---------------------------------------------------------------------------------------
    public Double calculateVisualAngleArcForOptotypeSizeInPixels(Double optotypeSize_px) {

        Double visualAngleArc;   // Declara o Arco de Ângulo

        visualAngleArc = Math.atan((optotypeSize_px/conversionRatioPixelsPerMillimeter)/(playerDistanceToScreen_mm))*(180.0/Math.PI);
        return visualAngleArc;
    }


    //---------------------------------------------------------------------------------------
    // Valor LogMAR
    //
    // Descrição: Função para cálculo do valor em LogMAR do estímulo que está a ser
    //			  efectivamente mostrado no computador.
    //---------------------------------------------------------------------------------------
    public Double calculateLogMARforOptotypeSizeInPixels(Double optotypeSize_px) {

        // 1. Calculate Visual Angle in Arc Minute

        // 2. Get Letter Resoltion in Arc Minute (One letter with total size of 5 Arc Minute have a resolution of 1 Arc Minute)

        // 3. Logarithm base 10 of acuity

        Double visualAngleArcDegrees = Math.atan((optotypeSize_px/conversionRatioPixelsPerMillimeter)/(playerDistanceToScreen_mm))*(180.0/Math.PI);

        // Sai em Degrees, e calcula Minimal Angle Resolution

        Double visualAngleArcMinute = (visualAngleArcDegrees*60)/5;

        return Math.log10(visualAngleArcMinute);
    }

    public Double calculateOptotypeSizeInPixelsForLogMar(Double logMAR) {

        Double visualAngleArcDegrees = (Math.pow(10,logMAR)*5)/60;

        Double optotypeSize_px = Math.tan(visualAngleArcDegrees/(180.0/Math.PI))*playerDistanceToScreen_mm*conversionRatioPixelsPerMillimeter;

        return optotypeSize_px;
    }

    //Converter formato de 20/20 para 6/6
    public static double convertSnellenFractionFormatTo6(double current20denominator) {

        double current6denominator = (6*current20denominator)/20;
        return current6denominator;
    }




    public static Double convertInches2Millimeters(Double inches) {
        return inches*INCH_2_MILLIMETER;
    }
    public static Double convertMillimeters2Inches(Double millimeters) {
        return millimeters/INCH_2_MILLIMETER;
    }
    public static Double convertFeet2Centimeters(Double feet) {
        return feet*FOOT_2_CENTIMETER;
    }
    public static Double convertCentimeters2Feet(Double centimeters) {
        return centimeters/FOOT_2_CENTIMETER;
    }

}