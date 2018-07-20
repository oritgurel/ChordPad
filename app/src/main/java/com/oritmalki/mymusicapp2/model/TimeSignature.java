package com.oritmalki.mymusicapp2.model;

import java.io.Serializable;

/**
 * Created by Orit on 20.12.2017.
 */

public class TimeSignature implements Serializable {


    int numerator;
    int denominator;

    public TimeSignature() {
    }


    public TimeSignature(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }


    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }


    public Double toDouble() {
        return (numerator*1.0 / denominator*1.0);
    }

    public boolean compare(TimeSignature timeSignature) {
        return this.numerator*timeSignature.getDenominator() == this.denominator*timeSignature.numerator;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }
}
