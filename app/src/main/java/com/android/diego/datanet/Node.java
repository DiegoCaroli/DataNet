package com.android.diego.datanet;

/**
 * Created by Diego on 29/10/2015.
 */
public class Node {

    private String mName;
    private String mOutcomes;
    private String mParents;
    private String mProbabilities;

    public Node() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
