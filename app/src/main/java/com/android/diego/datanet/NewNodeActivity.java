package com.android.diego.datanet;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.android.diego.datanet.Libraries.MultiSelectionSpinner;

public class NewNodeActivity extends AppCompatActivity {

    private EditText mEditTextInField;
    private Button mButtonAdd;
    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_node);

        String[] strings = { "Node1", "Node2", "Node3" };

        MultiSelectionSpinner mySpin = (MultiSelectionSpinner)findViewById(R.id.spinner);
        mySpin.setItems(strings);

        //List<String> selected = mySpin.getSelectedStrings();

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
            Toast.makeText(getApplicationContext(), "Done Pressed",
                    Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_cancel_new_node) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
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

