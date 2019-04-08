package com.mindvalley.app.ui.pinlistscreen;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.mindvalley.app.data.AppDataManager;
import com.mindvalley.app.data.model.api.Pinboard;

import java.util.List;

public class PinboardViewModel extends AndroidViewModel {

    private AppDataManager appDataManager;

    private LiveData<List<Pinboard>> pinboardList;


    public PinboardViewModel(@NonNull Application application) {
        super(application);

        appDataManager = new AppDataManager();
    }

    LiveData<List<Pinboard>> getAllPins() {
        return appDataManager.getAllPins();
    }

    void fetchPins() {
        appDataManager.fetchPins();
    }
}
