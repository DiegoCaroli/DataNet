package com.android.diego.datanet.Model;

import java.math.BigDecimal;
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
    private List<UUID> mParents;
    private List<BigDecimal> mProbabilities;

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

    public List<UUID> getParents() {
        return mParents;
    }

    public void setParents(List<UUID> parents) {
        mParents = parents;
    }

    public List<BigDecimal> getProbabilities() {
        return mProbabilities;
    }

    public void addValue (String value) {
        mValues.add(value);
    }

    public void removeValue (int position) {
        mValues.remove(position);
    }

    public void addProbability (BigDecimal prob) {
        mProbabilities.add(prob);
    }
}
