package com.android.diego.datanet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class NewNodeActivity extends AppCompatActivity {

    public static final String TAG = "FitHistory";

    private Node mNode;
    private EditText mNameNodeField;
    private Button mDoneButton;
    //private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_node);

        mNode = new Node();

        mNameNodeField = (EditText) findViewById(R.id.node_name);
        String strUserName = mNameNodeField.getText().toString();

        if(TextUtils.isEmpty(strUserName)) {
            mNameNodeField.setError("Your message");
            return;
        }

        mDoneButton = (Button) findViewById(R.id.done_new_node);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NodeStore.get(getBaseContext()).addNode(mNode);

                Intent intent = new Intent(NewNodeActivity.this, NodeListActivity.class);
                startActivity(intent);
            }
        });


        //addItemsOnSpinner();

        //spinner = (Spinner) findViewById(R.id.spinner_parent);
    }
    /*
    public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner_parent);
        List<String> list = new ArrayList<String>();
        list.add("None");

        NodeStore nodeStore = NodeStore.get(this).getNode();



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
    */
}
