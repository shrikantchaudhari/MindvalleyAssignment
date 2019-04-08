package com.mindvalley.asyncloader.callback;

import android.graphics.Bitmap;

public interface ImageRequestListener {

    void onLoadFailed(String errorMessage);

    void onResourceReady(Bitmap bitmap);
}
