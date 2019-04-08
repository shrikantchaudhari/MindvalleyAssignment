package com.mindvalley.asyncloader.utilities;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mindvalley.asyncloader.callback.RequestStatusListener;
import com.mindvalley.asyncloader.models.CommonRequestModel;

import cz.msebera.android.httpclient.Header;

public class SyncTaskManager
{
	public AsyncHttpClient get(final CommonRequestModel commonRequestModel, final RequestStatusListener requestStatusListener)
	{
		AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
		client.get(commonRequestModel.getUrl(), new AsyncHttpResponseHandler()
		{
			@Override
			public void onStart()
			{
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response)
			{
				// called when response HTTP status is "200 OK"
				commonRequestModel.setResponseData(response);
				commonRequestModel.getAsyncRequestListener().onSuccess(commonRequestModel);
				// Successful response from server
				requestStatusListener.setDone(commonRequestModel);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e)
			{
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)
				commonRequestModel.getAsyncRequestListener().onFailure(commonRequestModel, statusCode, errorResponse, e);
				// Failure Response from server
				requestStatusListener.onFailure(commonRequestModel);
			}
			
			@Override
			public void onRetry(int retryNo)
			{
			}
			
			@Override
			public void onCancel()
			{
				super.onCancel();
				// Cancel the request
				requestStatusListener.setCancelled(commonRequestModel);
			}
		});
		return client;
	}
}


