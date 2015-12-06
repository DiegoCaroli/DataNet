package com.android.diego.datanet.Model;

/**
 * Created by Diego on 03/12/2015.
 */
public class URLServer {

    private static URLServer sInstance;

    private String mURL;

    private URLServer(){
        mURL = "http://10.0.2.2:8080/BayesService";
    }

    public static URLServer getInstance() {
        if(sInstance == null) {
            sInstance = new URLServer();
        }
        return sInstance;
    }

    public String getURL(){
        return mURL;
    }

}
