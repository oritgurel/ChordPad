package com.oritmalki.mymusicapp2.model;

import java.io.Serializable;

/**
 * Created by Orit on 24.2.2018.
 */

public class Chord implements Serializable {

    private String root;
    private String rootSign;
    //major, minor etc.
    private String type;
    private String ten1;
    private String ten1Sign;
    private String ten2;
    private String ten2Sign;

    public Chord (String root) {
        this.root = root;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getRootSign() {
        return rootSign;
    }

    public void setRootSign(String rootSign) {
        this.rootSign = rootSign;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTen1() {
        return ten1;
    }

    public void setTen1(String ten1) {
        this.ten1 = ten1;
    }

    public String getTen1Sign() {
        return ten1Sign;
    }

    public void setTen1Sign(String ten1Sign) {
        this.ten1Sign = ten1Sign;
    }

    public String getTen2() {
        return ten2;
    }

    public void setTen2(String ten2) {
        this.ten2 = ten2;
    }

    public String getTen2Sign() {
        return ten2Sign;
    }

    public void setTen2Sign(String ten2Sign) {
        this.ten2Sign = ten2Sign;
    }

    //TODO create a chord concat method with string builder

    public String chordBuilder(String string) {
        StringBuilder builder = new StringBuilder();
        String chord = builder.append(string).toString();
        return chord;
    }
}
