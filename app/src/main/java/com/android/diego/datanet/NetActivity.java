package com.android.diego.datanet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NetActivity extends AppCompatActivity {

    private Net mNet;
    private EditText mNetField;
    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);

        mNet = new Net();

        mNetField = (EditText) findViewById(R.id.edit_net);
        mNetField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNet.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNextButton = (Button) findViewById(R.id.next_insert_net);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNameNet();
            }
        });
    }

    private void checkNameNet() {
        if (mNet.getName().length() == 0) {
            Toast.makeText(this, R.string.empty_name_net, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(NetActivity.this, NodeListActivity.class);
            startActivity(intent);
        }
    }
}

