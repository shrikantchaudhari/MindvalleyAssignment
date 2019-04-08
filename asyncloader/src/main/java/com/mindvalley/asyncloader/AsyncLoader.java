package com.mindvalley.asyncloader;

import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.mindvalley.asyncloader.callback.AsyncRequestListener;
import com.mindvalley.asyncloader.callback.ImageRequestListener;
import com.mindvalley.asyncloader.callback.JsonRequestListener;
import com.mindvalley.asyncloader.callback.RequestStatusListener;
import com.mindvalley.asyncloader.models.CommonRequestModel;
import com.mindvalley.asyncloader.models.ImageRequestModel;
import com.mindvalley.asyncloader.models.JsonRequestModel;
import com.mindvalley.asyncloader.utilities.CacheManager;
import com.mindvalley.asyncloader.utilities.SyncTaskManager;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;

import static com.mindvalley.asyncloader.common.Constants.CACHE_DATA_SOURCE;

public class AsyncLoader {

    private static AsyncLoader instance;
    private HashMap<String, LinkedList<CommonRequestModel>> allRequestsByKey = new HashMap<>();
    private HashMap<String, AsyncHttpClient> allRequestsClient = new HashMap<>();
    private CacheManager cacheManager;


    private AsyncLoader() {
        cacheManager = CacheManager.getInstance();
    }

    public static AsyncLoader get() {
        if (instance == null) {
            instance = new AsyncLoader();
        }
        return instance;
    }

    /**
     * This function will load the image and return in
     *
     * @param url                  : URL of image resource
     * @param imageRequestListener : This this the callback for resource download status
     */
    public void loadImage(String url, @NotNull final ImageRequestListener imageRequestListener) {

        CommonRequestModel commonRequestModel = new ImageRequestModel(url, null, new AsyncRequestListener() {
            @Override
            public void onSuccess(CommonRequestModel serviceContentTypeDownload) {


                if (imageRequestListener != null) {
                    imageRequestListener.onResourceReady(((ImageRequestModel) serviceContentTypeDownload).getImageBitmap());
                }
            }

            @Override
            public void onFailure(CommonRequestModel serviceContentTypeDownload, int statusCode, byte[] errorResponse, Throwable e) {

                if (imageRequestListener != null) {
                    imageRequestListener.onLoadFailed(e.getMessage());
                }
            }
        });

        getRequest(commonRequestModel);
    }

    /**
     * This function will download the image from url and set it to provided image view.
     *
     * @param url                  : URL of image resource
     * @param imageView            : ImageView in which image to show
     * @param imageRequestListener : This this the callback for resource download status
     */
    public void displayImage(String url, final ImageView imageView, final ImageRequestListener imageRequestListener) {

        CommonRequestModel commonRequestModel = new ImageRequestModel(url, null, new AsyncRequestListener() {
            @Override
            public void onSuccess(CommonRequestModel serviceContentTypeDownload) {

                imageView.setImageBitmap(((ImageRequestModel) serviceContentTypeDownload).getImageBitmap());

                if (imageRequestListener != null) {
                    imageRequestListener.onResourceReady(((ImageRequestModel) serviceContentTypeDownload).getImageBitmap());
                }
            }

            @Override
            public void onFailure(CommonRequestModel serviceContentTypeDownload, int statusCode, byte[] errorResponse, Throwable e) {

                imageView.setImageResource(R.drawable.ic_no_image);

                if (imageRequestListener != null) {
                    imageRequestListener.onLoadFailed(e.getMessage());
                }
            }
        });

        getRequest(commonRequestModel);
    }

    /**
     * This function will be useful for loading json from given url
     *
     * @param url                 : String url
     * @param jsonRequestListener : Json request callback
     */
    public void loadJson(String url, final JsonRequestListener jsonRequestListener) {

        CommonRequestModel jsonDownloadRequest = new JsonRequestModel(url, new AsyncRequestListener() {
            @Override
            public void onSuccess(CommonRequestModel serviceContentTypeDownload) {

                if (jsonRequestListener != null) {
                    jsonRequestListener.onJsonReady(((JsonRequestModel) serviceContentTypeDownload).getJsonAsString());
                }
            }

            @Override
            public void onFailure(CommonRequestModel serviceContentTypeDownload, int statusCode, byte[] errorResponse, Throwable e) {

                if (jsonRequestListener != null) {
                    jsonRequestListener.onLoadFailed(e.getMessage());
                }
            }
        });

        getRequest(jsonDownloadRequest);
    }

    /**
     * This is the generic function will get required data from given url
     *
     * @param commonRequestModel
     */
    public void getRequest(final CommonRequestModel commonRequestModel) {
        final String mKey = commonRequestModel.getUrl();
        // Check if exist in the cache
        CommonRequestModel commonRequestModelFromCache = cacheManager.getMDownloadDataType(mKey);
        if (commonRequestModelFromCache != null) {
            commonRequestModelFromCache.dataSource = CACHE_DATA_SOURCE;
            // call interface
            AsyncRequestListener asyncRequestListener = commonRequestModel.getAsyncRequestListener();
            asyncRequestListener.onSuccess(commonRequestModelFromCache);
            return;
        }
        // This will run if two request come for same url
        if (allRequestsByKey.containsKey(mKey)) {
            commonRequestModel.dataSource = CACHE_DATA_SOURCE;
            allRequestsByKey.get(mKey).add(commonRequestModel);
            return;
        }
        // Put it in the allRequestsByKey to manage it after done or cancel
        if (allRequestsByKey.containsKey(mKey)) {
            allRequestsByKey.get(mKey).add(commonRequestModel);
        } else {
            LinkedList<CommonRequestModel> lstMDDataType = new LinkedList<>();
            lstMDDataType.add(commonRequestModel);
            allRequestsByKey.put(mKey, lstMDDataType);
        }
        // Create new WebCallDataTypeDownload by clone it from the parameter
        CommonRequestModel newWebCallDataTypeDownload = commonRequestModel.clone(new AsyncRequestListener() {

            @Override
            public void onSuccess(CommonRequestModel mDownloadDataType) {
                for (CommonRequestModel m : allRequestsByKey.get(mDownloadDataType.getUrl())) {
                    m.setResponseData(mDownloadDataType.getResponseData());
                    m.getAsyncRequestListener().onSuccess(m);
                }
                allRequestsByKey.remove(mDownloadDataType.getUrl());
            }

            @Override
            public void onFailure(CommonRequestModel mDownloadDataType, int statusCode, byte[] errorResponse, Throwable e) {
                for (CommonRequestModel m : allRequestsByKey.get(mDownloadDataType.getUrl())) {
                    m.setResponseData(mDownloadDataType.getResponseData());
                    m.getAsyncRequestListener().onFailure(m, statusCode, errorResponse, e);
                }
                allRequestsByKey.remove(mDownloadDataType.getUrl());
            }

        });
        // Get from download manager
        final SyncTaskManager syncTaskManager = new SyncTaskManager();
        AsyncHttpClient client = syncTaskManager.get(newWebCallDataTypeDownload, new RequestStatusListener() {
            @Override
            public void setDone(CommonRequestModel mDownloadDataType) {
                // put in the cache when mark as done
                cacheManager.putMDownloadDataType(mDownloadDataType.getUrl(), mDownloadDataType);
                allRequestsClient.remove(mDownloadDataType.getUrl());
            }

            @Override
            public void onFailure(CommonRequestModel mDownloadDataType) {
                allRequestsClient.remove(mDownloadDataType.getUrl());
            }

            @Override
            public void setCancelled(CommonRequestModel mDownloadDataType) {
                allRequestsClient.remove(mDownloadDataType.getUrl());
            }
        });
        allRequestsClient.put(mKey, client);
    }

    /**
     * This function will
     *
     * @param commonRequestModel
     */
    public void cancelRequest(CommonRequestModel commonRequestModel) {
        if (allRequestsByKey.containsKey(commonRequestModel.getUrl())) {
            allRequestsByKey.get(commonRequestModel.getUrl()).remove(commonRequestModel);
            commonRequestModel.getAsyncRequestListener().onFailure(commonRequestModel, 0, null, null);
        }
    }

    public boolean isQueueEmpty() {
        return allRequestsByKey.size() == 0;
    }

    /**
     * This function will help us to clear cache
     */
    public void clearCache() {
        cacheManager.clearCache();
    }


}
