package com.android.diego.datanet;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public void addNode(Node node) {
        mNodes.add(node);
    }

    private NodeStore(Context context) {
        mNodes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Node node = new Node();
            node.setName("Node #" + i);
            node.setEvidence(i % 2 == 0);
            //node.addParent("Parent: " + ++i);
            mNodes.add(node);
        }

    }

    public List<Node> getNodes() {
        return mNodes;
    }

    public Node getNode(UUID id) {
        for (Node node : mNodes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }
}