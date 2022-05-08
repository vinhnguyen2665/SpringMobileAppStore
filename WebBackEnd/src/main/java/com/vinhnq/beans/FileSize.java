package com.vinhnq.beans;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Data
@RequiredArgsConstructor
public class FileSize {
    public static final String BYTE = "B";
    public static final String KILOBYTE = "KB";
    public static final String MEGABYTE = "MB";
    public static final String GIGABYTE = "GB";
    public static final String TERABYTE = "TB";
    private DecimalFormat df;
    private DecimalFormatSymbols formatSymbols;

    @NonNull
    private Double value;

    private Double valueFormat;
    private String format;

    @NonNull
    private String unit;

    public Double getValueFormat() {
        if(null == this.df){
            this.df = new DecimalFormat(getFormat(), getFormatSymbols());
        }
        return Double.valueOf(this.df.format(value));
    }

    public void setValueFormat(Double valueFormat) {
        this.valueFormat = valueFormat;
    }

    public String getFormat() {
        if(null == format || format.isEmpty()){
            format = "#.##";
        }
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public DecimalFormatSymbols getFormatSymbols() {
        if(null == this.formatSymbols){
            this.formatSymbols= new DecimalFormatSymbols(Locale.ENGLISH);
        }
        return formatSymbols;
    }

    public void setFormatSymbols(DecimalFormatSymbols formatSymbols) {
        this.formatSymbols = formatSymbols;
    }
}
