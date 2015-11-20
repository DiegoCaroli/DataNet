package com.android.diego.datanet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.android.diego.datanet.Libraries.MultiSelectionSpinner;

public class NewNodeActivity extends AppCompatActivity {

    private Node mNode;
    private NodeStore mNodeStore;
    private List<Node> mNodes;
    private EditText mNameField;
    private EditText mEditTextInField;
    private ImageButton mButtonAdd;
    private MultiSelectionSpinner mySpinner;
    private ListView mListView;
    private ArrayAdapter<String> adapter;

    private static final String EXTRA_NET_NAME = "com.android.diego.datanet.net_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_node);

        mNode = new Node();

        mNodeStore = NodeStore.get(getBaseContext());
        mNodes = mNodeStore.getNodes();

        setNameNode();
        addParentsOnSpinner();
        addValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_node, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done_new_node) {
            setSelectedParents();
            setProbabilies();

            NodeStore.get(getBaseContext()).addNode(mNode);

            Intent intent = new Intent(NewNodeActivity.this, NodeListActivity.class);
            intent.putExtra(EXTRA_NET_NAME, getIntent().getStringExtra(EXTRA_NET_NAME));
            startActivity(intent);
        } else if (id == R.id.action_cancel_new_node) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setNameNode() {
        mNameField = (EditText) findViewById(R.id.node_name);

        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNode.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addParentsOnSpinner() {
        List<String> parentsNode = new ArrayList<>();

        for (Node node : mNodes) {
            parentsNode.add(node.getName());
        }

        mySpinner = (MultiSelectionSpinner)findViewById(R.id.spinner);

        if (parentsNode.isEmpty()) {
            mySpinner.setEnabled(false);
            mySpinner.setClickable(false);
        } else {
            mySpinner.setItems(parentsNode);
        }
    }

    private void setSelectedParents() {
        if (!mNodes.isEmpty()) {
            List<String> nodeSelected = mySpinner.getSelectedStrings();

            List<UUID> nodeIDs = new ArrayList<>();

            for (String nameNode : nodeSelected) {
                UUID nodeID = mNodeStore.getNodeID(nameNode);

                if (nodeID != null) {
                    nodeIDs.add(nodeID);
                }
            }
            mNode.setParents(nodeIDs);
        }
    }

    private void addValues() {
        mListView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, R.layout.row_values, mNode.getValues());
        mListView.setAdapter(adapter);

        mEditTextInField = (EditText)findViewById(R.id.textin);
        mButtonAdd = (ImageButton)findViewById(R.id.add);

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextInField.getText().toString().length() > 0) {
                    mNode.addValue(mEditTextInField.getText().toString());
                    mEditTextInField.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        /*
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeItemFromList(position);
            }
        });
        */
        mListView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItemFromList(position);
                return true;
            }
        });
    }

    private void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                NewNodeActivity.this);

        alert.setTitle(R.string.delete_alert);
        alert.setMessage(R.string.delete_value_message);
        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                mNode.removeValue(deletePosition);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void setProbabilies() {
        int i = 1;
        if (mNode.getParents().isEmpty()) {
            int tot_conf = mNode.getValues().size() * i;
            double prob = 1.0 / mNode.getValues().size();

            calculateProbabilities(tot_conf, prob);
        } else {
            for (UUID nodeID : mNode.getParents()) {
                Node node = mNodeStore.getNode(nodeID);
                i *= node.getValues().size();

                int tot_conf = mNode.getValues().size() * i;
                double prob = 1.0 / mNode.getValues().size();

                calculateProbabilities(tot_conf, prob);
            }
        }
    }

    private void calculateProbabilities(int tot_conf, double prob) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for(int j = 0; j < tot_conf; j++) {
            if (mNode.getValues().size() % 2 == 0 || mNode.getValues().size() == 1) {
                mNode.addProbability(Double.valueOf(decimalFormat.format(prob)));
            } else if (mNode.getValues().size() % 2 == 0 && j % mNode.getValues().size() == mNode.getValues().size()-1) {
                double newProb = prob + 0.01;
                mNode.addProbability(Double.valueOf(decimalFormat.format(newProb)));
            } else if (mNode.getValues().size() % 7 == 0 && j % mNode.getValues().size() == mNode.getValues().size()-1) {
                double newProb = prob + 0.02;
                mNode.addProbability(Double.valueOf(decimalFormat.format(newProb)));
            } else {
                mNode.addProbability(Double.valueOf(decimalFormat.format(prob)));
            }
        }
    }

}
