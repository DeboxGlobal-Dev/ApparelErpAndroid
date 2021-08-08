package com.erp.apparel.Data;

import android.app.Application;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;

public class ApperalApplication extends Application {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.INSTANCE.initPrefs(getApplicationContext());


    }
}
