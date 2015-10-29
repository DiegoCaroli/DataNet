package com.android.diego.datanet;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego on 29/10/2015.
 */
public class NodeStore {

    private static NodeStore sNodeStore;
    private List<Node> mNodes;

    public static NodeStore get(Context context) {
        if (sNodeStore == null) {
            sNodeStore = new Node(context);
        }
        return sNodeStore;
    }

    private NodeStore(Context context) {
        mNodes = new ArrayList<>();
    }
}
