package com.android.diego.datanet;

import android.support.v4.app.Fragment;


public class NodeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new NodeFragment();
    }

}
