package com.android.diego.datanet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego on 29/11/2015.
 */
public class UploadNetFragment extends Fragment {

    private Spinner mSpinner;
    private Button mButton;

    public UploadNetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_net, container, false);

        addItemsOnSpinner(view);

        return view;
    }

    private void addItemsOnSpinner(View view) {
        mSpinner = (Spinner) view.findViewById(R.id.spinner_file);

        List<String> file = new ArrayList<>();
        file.add("file 1");
        file.add("file 2");
        file.add("file 3");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, file);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        mSpinner.setAdapter(dataAdapter);
    }
}

