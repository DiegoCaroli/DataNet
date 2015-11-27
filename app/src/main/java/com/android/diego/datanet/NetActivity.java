package com.android.diego.datanet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NetActivity extends AppCompatActivity {

    private Net mNet;
    private EditText mNetField;
    private Button mNextButton;
    private static final String EXTRA_NET_NAME = "com.android.diego.datanet.net_name";

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_net, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create_net_from_file) {
            Toast.makeText(this, "Upload", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkNameNet() {
        if (mNet.getName().length() == 0) {
            Toast.makeText(this, R.string.empty_name_net, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(NetActivity.this, NodeListActivity.class);
            intent.putExtra(EXTRA_NET_NAME, mNet.getName());
            startActivity(intent);
        }
    }
}

