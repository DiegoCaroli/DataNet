package com.android.diego.datanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class NodeListActivity extends AppCompatActivity {

    private String mNameNet;
    private NodeStore mNodeStore;
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
            Toast.makeText(getApplicationContext(), "Done Pressed",
                    Toast.LENGTH_LONG).show();
            if (mNodes.size() > 0) {
                String fileNameCSV = mNameNet + ".csv";
                File fileCSV = new File(getBaseContext().getFilesDir(), fileNameCSV);
                CsvFileWriter.writeCsvFile(fileCSV, mNodeStore);

                String fileNameXML = mNameNet + ".xml";
                File fileXML = new File(getBaseContext().getFilesDir(), fileNameXML);
                XmlFileWriter.writeXmlFile(fileXML, mNodeStore);
            } else {
                Toast.makeText(getApplicationContext(), "You can't do it.",
                        Toast.LENGTH_LONG).show();
            }


        }

        return super.onOptionsItemSelected(item);
    }
}
