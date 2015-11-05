package com.android.diego.datanet;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego on 29/10/2015.
 */
public class Node {

    private String mName;
    private String mType;
    private List<String> mValues;
    private List<String> mParents;
    private List<Double> mProbabilities;

    public Node() {
        mName = "";
        mType = "evidence";
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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
