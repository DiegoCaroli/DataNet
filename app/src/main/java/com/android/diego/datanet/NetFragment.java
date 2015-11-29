package com.android.diego.datanet;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.diego.datanet.Model.Net;

public class NetFragment extends Fragment {

    private Net mNet;
    private EditText mNetField;
    private Button mNextButton;
    private static final String EXTRA_NET_NAME = "com.android.diego.datanet.net_name";

    public NetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net, container, false);

        mNet = new Net();

        mNetField = (EditText) view.findViewById(R.id.edit_net);
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

        mNextButton = (Button) view.findViewById(R.id.next_insert_net);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNameNet();
            }
        });

        return view;
    }

    private void checkNameNet() {
        if (mNet.getName().length() == 0) {
            Toast.makeText(getActivity(), R.string.empty_name_net, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getActivity(), NodeListActivity.class);
            intent.putExtra(EXTRA_NET_NAME, mNet.getName());
            startActivity(intent);
        }
    }
}

