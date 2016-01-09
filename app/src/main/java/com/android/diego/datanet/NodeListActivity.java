package com.android.diego.datanet;

import android.app.ProgressDialog;
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
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class NodeListActivity extends AppCompatActivity {

    private String mNameNet;
    private List<Node> mNodes;

    private static final String EXTRA_NET_NAME = "com.android.diego.datanet.net_name";

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

        mNodes = NodeStore.get(getBaseContext()).getNodes();

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
                FileWriter fileWriter = new FileWriter(NodeStore.get(getBaseContext()));

                String fileNameCSV = mNameNet + ".csv";
                File fileCSV = new File(getBaseContext().getFilesDir(), fileNameCSV);
                fileWriter.writeCsvFile(fileCSV);

                String fileNameXML = mNameNet + ".xml";
                File fileXML = new File(getBaseContext().getFilesDir(), fileNameXML);
                fileWriter.writeXmlFile(fileXML);

                uploadFile(fileCSV, fileXML);

                createNet(fileNameCSV);
            } else {
                Toast.makeText(getApplicationContext(), R.string.net_empty,
                        Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }

    private void uploadFile(File fileCSVPath, File fileXMLPath) {
        final ProgressDialog dialog = ProgressDialog.show(this, "Uploading file", "Please wait...");

        String pathServer = URLServer.getInstance().getURL() + "/upload";

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(3, 1000);
        RequestParams params = new RequestParams();
        try {
            params.put("filecsv", fileCSVPath);
            params.put("filexml", fileXMLPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        client.post(pathServer, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.server_ok,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.server_fail,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void createNet(String nameFileCsv) {
        String pathServer = URLServer.getInstance().getURL() + "/createnet";

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("filename", nameFileCsv);

        client.setMaxRetriesAndTimeout(3, 1000);
        client.get(pathServer, param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), R.string.server_net,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), R.string.server_fail,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

}


