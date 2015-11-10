package com.android.diego.datanet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.diego.datanet.Libraries.MultiSelectionSpinner;

public class NewNodeActivity extends AppCompatActivity {

    private Node mNode;
    private EditText mNameField;
    private EditText mEditTextInField;
    private Button mButtonAdd;
    private LinearLayout mContainer;
    private MultiSelectionSpinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_node);

        mNode = new Node();

        setNameNode();
        addParentsOnSpinner();
        //addValues();
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
            NodeStore.get(getBaseContext()).addNode(mNode);

            Intent intent = new Intent(NewNodeActivity.this, NodeListActivity.class);
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
        NodeStore nodeStore = NodeStore.get(getBaseContext());
        List<Node> nodes = nodeStore.getNodes();

        List<String> parentsNode = new ArrayList<String>();

        for (Node node : nodes) {
            parentsNode.add(node.getName());
        }

        mySpinner = (MultiSelectionSpinner)findViewById(R.id.spinner);

        if (parentsNode.isEmpty()) {
            mySpinner.setEnabled(false);
            mySpinner.setClickable(false);
        } else {
            mySpinner.setItems(parentsNode);
        }


        //List<String> selected = mySpin.getSelectedStrings();
    }

    private void addValues() {
        mEditTextInField = (EditText)findViewById(R.id.textin);
        mButtonAdd = (Button)findViewById(R.id.add);
        mContainer = (LinearLayout)findViewById(R.id.container);

        mButtonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row_values, null);
                TextView textOut = (TextView) addView.findViewById(R.id.textout);
                textOut.setTextSize(18);
                textOut.setText("Value: " + mEditTextInField.getText().toString());
                mEditTextInField.setText("");

                mContainer.addView(addView);
            }
        });

    }
}

