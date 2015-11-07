package com.android.diego.datanet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import com.android.diego.datanet.Libraries.MultiSelectionSpinner;

public class NewNodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_node);

        String[] strings = { "Red", "Blue", "Green" };

        MultiSelectionSpinner mySpin = (MultiSelectionSpinner)findViewById(R.id.spinner);
        mySpin.setItems(strings);

// ...

        List<String> selected = mySpin.getSelectedStrings();
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
            Toast.makeText(getApplicationContext(), "Done Pressed",
                    Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_cancel_new_node) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}

