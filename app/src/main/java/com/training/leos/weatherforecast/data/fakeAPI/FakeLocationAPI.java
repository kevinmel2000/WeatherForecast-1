package com.training.leos.weatherforecast.data.fakeAPI;

import java.util.ArrayList;

/**
 * Created by Leo on 12/23/2017.
 */

public class FakeLocationAPI {
    public ArrayList<String> fetchResult(String query) {
        return getData();
    }

    private ArrayList<String> getData(){
        ArrayList<String> data = new ArrayList<>();
        data.add("jakarta");
        data.add("jakabaring");
        data.add("jakuta");
        data.add("jalan desa");

        return data;
    }
}
