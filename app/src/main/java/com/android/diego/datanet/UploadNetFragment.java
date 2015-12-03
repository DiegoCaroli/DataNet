package com.android.diego.datanet;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Diego on 29/11/2015.
 */
public class UploadNetFragment extends Fragment {

    private Spinner mSpinner;
    private Button mButton;
    private List<String> fileNames;

    public UploadNetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_net, container, false);

        fileNames = new ArrayList<>();
        downloadListFile();

        addListenerOnButton(view);

        return view;
    }

    private void addItemsOnSpinner() {
        mSpinner = (Spinner) getView().findViewById(R.id.spinner_file);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, fileNames);

        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        mSpinner.setAdapter(dataAdapter);
    }

    private void addListenerOnButton(View view) {
        mSpinner = (Spinner) view.findViewById(R.id.spinner_file);
        mButton = (Button) view.findViewById(R.id.button_create);

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),
                        "Spinner value : " + String.valueOf(mSpinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void downloadListFile() {

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Download list file", "Please wait...");

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://10.0.2.2:8080/BayesService/listfile", null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONObject rootJson = response;
                    JSONArray filesArray = rootJson.optJSONArray("files");

                    for (int i = 0; i < filesArray.length(); i++) {
                        JSONObject objectName = filesArray.getJSONObject(i);

                        String nameFile = objectName.optString("name").toString();

                        fileNames.add(nameFile);
                    }

                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }

                dialog.dismiss();
                addItemsOnSpinner();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dismiss();
            }
        });
    }

}

