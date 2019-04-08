package com.mindvalley.asyncloader.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mindvalley.asyncloader.callback.AsyncRequestListener;
import com.mindvalley.asyncloader.callback.ImageRequestListener;
import com.mindvalley.asyncloader.utilities.RequestDataType;


public class ImageRequestModel extends CommonRequestModel {

    ImageRequestListener imageRequestListener;

    public ImageRequestModel(String url, ImageRequestListener imageRequestListener, AsyncRequestListener asyncRequestListener) {
        super(url, RequestDataType.IMAGE, asyncRequestListener);
        this.imageRequestListener = imageRequestListener;
    }

    @Override
    public CommonRequestModel clone(AsyncRequestListener asyncRequestListener) {
        return new ImageRequestModel(this.getUrl(), this.getImageRequestListener(), asyncRequestListener);
    }

    public Bitmap getImageBitmap() {
        return BitmapFactory.decodeByteArray(getResponseData(), 0, getResponseData().length);
    }

    public ImageRequestListener getImageRequestListener() {
        return imageRequestListener;
    }

}
