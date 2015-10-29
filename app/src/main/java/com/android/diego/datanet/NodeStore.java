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
        if (NodeStore == null) {
            sNodeStore = new NodeStore(context);
        }
        return sNodeStore;
    }

    private CrimeLab(Context context) {
        mNodes = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            Node node = new Node();
            node.setName("Node: " + i);
            mNodes.add(node);
        }
    }

    public List<Node> getCrimes() {
        return mNodes;
    }

    public Node getCrime(String name) {
        for (Node node : mNodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }
}
