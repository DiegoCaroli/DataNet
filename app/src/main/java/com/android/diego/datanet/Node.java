package com.android.diego.datanet;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Diego on 29/10/2015.
 */
public class Node {

    private UUID mId;
    private String mName;
    private List<String> mValues;
    private List<Node> mParents;
    private List<Double> mProbabilities;

    public Node() {
        mId = UUID.randomUUID();
        mName = "";
        mValues = new ArrayList<>();
        mParents = new ArrayList<>();
        mProbabilities = new ArrayList<>();
    }

    public UUID getId() {
        return mId;
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

    public void setValues(List<String> values) {
        mValues = values;
    }

    public List<Node> getParents() {
        return mParents;
    }

    public void setParents(List<Node> parents) {
        mParents = parents;
    }

    public List<Double> getProbabilities() {
        return mProbabilities;
    }

    public void setProbabilities(List<Double> probabilities) {
        mProbabilities = probabilities;
    }
}
