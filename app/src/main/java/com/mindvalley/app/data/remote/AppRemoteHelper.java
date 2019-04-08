package com.mindvalley.app.data.remote;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.mindvalley.app.data.model.api.Pinboard;
import com.mindvalley.asyncloader.AsyncLoader;
import com.mindvalley.asyncloader.callback.JsonRequestListener;

import java.util.ArrayList;
import java.util.List;

import static com.mindvalley.app.utils.AppConstants.API_URL;

public class AppRemoteHelper implements RemoteHelper {


    MutableLiveData<List<Pinboard>> pinboardList;

    public AppRemoteHelper() {


    }

    @Override
    public MutableLiveData<List<Pinboard>> getAllPins() {

        if (pinboardList == null) {
            pinboardList = new MutableLiveData<>();
        }
        return pinboardList;
    }

    @Override
    public void fetchPins() {


        AsyncLoader.get()
                .loadJson(API_URL, new JsonRequestListener() {
                    @Override
                    public void onLoadFailed(String errorMessage) {
                        pinboardList.postValue(new ArrayList<Pinboard>());
                    }

                    @Override
                    public void onJsonReady(String json) {

                        Gson gson = new Gson();

                        try {
                            Log.e("TAG", "Json =>"+json);
                            List<Pinboard> pinboard = gson.fromJson(json, new TypeToken<List<Pinboard>>() {
                            }.getType());

                            pinboardList.postValue(pinboard);

                        } catch (JsonParseException ex) {
                            ex.getMessage();
                        } catch (Exception ex) {
                            ex.getMessage();
                        }
                    }
                });
    }
}
