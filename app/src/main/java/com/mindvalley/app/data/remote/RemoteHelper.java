package com.mindvalley.app.data.remote;

import androidx.lifecycle.LiveData;

import com.mindvalley.app.data.model.api.Pinboard;

import java.util.List;

public interface RemoteHelper {

    LiveData<List<Pinboard>> getAllPins();

    void fetchPins();
}
