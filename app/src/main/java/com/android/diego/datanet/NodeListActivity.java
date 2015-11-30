package com.android.diego.datanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.diego.datanet.Model.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class NodeListActivity extends AppCompatActivity {

    private String mNameNet;
    private NodeStore mNodeStore;
    private List<Node> mNodes;

    private static final String EXTRA_NET_NAME = "com.android.diego.datanet.net_name";
    private static final String URL_SERVER = "http://localhost:8080/BayesService/upload/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        mNameNet = getIntent().getStringExtra(EXTRA_NET_NAME);

        actionBar.setTitle(mNameNet);

        mNodeStore = NodeStore.get(getBaseContext());
        mNodes = mNodeStore.getNodes();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_node_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_node) {
            Intent intent = new Intent(NodeListActivity.this, NewNodeActivity.class);
            intent.putExtra(EXTRA_NET_NAME, mNameNet);
            startActivity(intent);
        } else if (id == R.id.action_done) {
            if (mNodes.size() > 0) {
                FileWriter fileWriter = new FileWriter(mNodeStore);

                String fileNameCSV = mNameNet + ".csv";
                File fileCSV = new File(getBaseContext().getFilesDir(), fileNameCSV);
                fileWriter.writeCsvFile(fileCSV);

                String fileNameXML = mNameNet + ".xml";
                File fileXML = new File(getBaseContext().getFilesDir(), fileNameXML);
                fileWriter.writeXmlFile(fileXML);

                Toast.makeText(getApplicationContext(), R.string.file_create, Toast.LENGTH_LONG).show();

                uploadFile(fileCSV);

            } else {
                Toast.makeText(getApplicationContext(), R.string.net_empty,
                        Toast.LENGTH_LONG).show();
            }


        }

        return super.onOptionsItemSelected(item);
    }

    private void uploadFile(File filePath) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        try {
            params.put("file", filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        client.post("http://10.0.2.2:8080/BayesService/upload", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), "200",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "400",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

}


