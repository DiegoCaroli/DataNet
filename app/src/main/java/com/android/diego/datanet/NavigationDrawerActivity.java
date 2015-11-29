package com.android.diego.datanet;

import android.os.Bundle;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by Diego on 29/11/2015.
 */
public class NavigationDrawerActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle savedInstanceState) {

        // set the header image
        //this.setDrawerHeaderImage(R.drawable.mat2);

        // create sections
        this.addSection(newSection("DataNet", new CreateNetFragment()));

        this.disableLearningPattern();

    }
}
