package com.mindvalley.app.data;

import androidx.lifecycle.LiveData;

import com.mindvalley.app.data.model.api.Pinboard;
import com.mindvalley.app.data.remote.AppRemoteHelper;

import java.util.List;

public class AppDataManager implements DataManager {

    private AppRemoteHelper appRemoteHelper;

    public AppDataManager() {
        appRemoteHelper = new AppRemoteHelper();
    }

    @Override
    public LiveData<List<Pinboard>> getAllPins() {
        return appRemoteHelper.getAllPins();
    }

    @Override
    public void fetchPins() {
        appRemoteHelper.fetchPins();
    }
}
