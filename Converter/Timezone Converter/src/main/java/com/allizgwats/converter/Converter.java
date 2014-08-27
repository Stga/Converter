package com.allizgwats.converter;

import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Helper class for converter fragments
 */
public class Converter {

    /**
     * Set the text of the converterMetric TextViews
     * @param textViewToSet the textView that will be edited
     * @param identifier string representation of metric unit
     * @param convertedWeight double representing the converted weight from metric A to metric B
     */
    protected static void setConvertedSpeedTextView(TextView textViewToSet,
                                                    String identifier,
                                                    Double convertedWeight) {

        //Format the text by stripping all trailing zereos and adding in an identifier based on
        //what unit was converted to.
        String strippedConvertedWeight = new BigDecimal(convertedWeight)
                .stripTrailingZeros().toPlainString();

        //Format the String by adding in ',' in appropriate places
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedConvertedWeight = df.format(Double.parseDouble(strippedConvertedWeight));

        textViewToSet.setText(formattedConvertedWeight + identifier);
    }
}
