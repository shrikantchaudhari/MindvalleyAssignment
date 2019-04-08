package com.mindvalley.asyncloader.callback;

public interface JsonRequestListener {

    void onLoadFailed(String errorMessage);

    void onJsonReady(String json);
}
