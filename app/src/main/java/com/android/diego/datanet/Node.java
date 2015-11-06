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
    private boolean mEvidence;
    private List<String> mValues;
    private List<String> mParents;
    private List<Double> mProbabilities;

    public Node() {
        mId = UUID.randomUUID();
        mName = "";
        mEvidence = true;
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

    public boolean isEvidence() {
        return mEvidence;
    }

    public void setEvidence(boolean evidence) {
        mEvidence = evidence;
    }
}
