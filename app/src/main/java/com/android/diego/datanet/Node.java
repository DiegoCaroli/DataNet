package com.android.diego.datanet;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego on 29/10/2015.
 */
public class Node {

    private String mName;
    private List<String> mValues;
    private List<String> mParents;
    private List<Double> mProbabilities;

    public Node() {
        mName = "";
        mValues = new ArrayList<>();
        mParents = new ArrayList<>();
        mProbabilities = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<String> getValues() {
        return mValues;
    }

    public List<String> getParents() {
        return mParents;
    }

    public void addParent(String parent) {
        mParents.add(parent);
    }

    public List<Double> getProbabilities() {
        return mProbabilities;
    }
}
