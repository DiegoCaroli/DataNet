package com.android.diego.datanet;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class NodeListActivityFragment extends Fragment {

    private RecyclerView mNodeRecyclerView;
    private NodeAdapter mAdapter;

    public NodeListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_node_list, container, false);

        mNodeRecyclerView = (RecyclerView) view
                .findViewById(R.id.node_recycler_view);
        mNodeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        NodeStore nodeStore = NodeStore.get(getActivity());
        List<Node> nodes = nodeStore.getNodes();

        mAdapter = new NodeAdapter(nodes);
        mNodeRecyclerView.setAdapter(mAdapter);
    }

    private class NodeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mSubtitleTextView;

        private Node mNode;

        public NodeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_node_title_text_view);
            mSubtitleTextView = (TextView) itemView.findViewById(R.id.list_item_node_parents_text_view);
        }

        public void bindNode(Node node) {
            mNode = node;
            mTitleTextView.setText(mNode.getName());
            mSubtitleTextView.setText(mNode.getParents().toString());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mNode.getName() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class NodeAdapter extends RecyclerView.Adapter<NodeHolder> {

        private List<Node> mNodes;

        public NodeAdapter(List<Node> nodes) {
            mNodes = nodes;
        }

        @Override
        public NodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_node, parent, false);
            return new NodeHolder(view);
        }

        @Override
        public void onBindViewHolder(NodeHolder holder, int position) {
            Node node = mNodes.get(position);
            holder.bindNode(node);
        }

        @Override
        public int getItemCount() {
            return mNodes.size();
        }
    }
}
