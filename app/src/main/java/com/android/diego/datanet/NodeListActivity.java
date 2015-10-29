package com.android.diego.datanet;

import android.support.v4.app.Fragment;

public class NodeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new NodeListFragment();
    }
}
