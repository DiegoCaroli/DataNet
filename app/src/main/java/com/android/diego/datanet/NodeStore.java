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
            sNodeStore = new NodeStore(context);
        }
        return sNodeStore;
    }

    private NodeStore(Context context) {
        mNodes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Node node = new Node();
            node.setName("Node #" + i);
            node.addParent("Parent: " + i);
            node.addParent("Parent: " + ++i);
            mNodes.add(node);
        }
    }

    public List<Node> getNodes() {
        return mNodes;
    }

    public Node getNode(String name) {
        for (Node node : mNodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }
}