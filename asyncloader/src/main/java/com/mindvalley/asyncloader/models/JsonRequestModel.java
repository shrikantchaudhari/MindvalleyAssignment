package com.mindvalley.asyncloader.models;

import com.google.gson.Gson;
import com.mindvalley.asyncloader.callback.AsyncRequestListener;
import com.mindvalley.asyncloader.utilities.RequestDataType;

import java.lang.reflect.Type;

public class JsonRequestModel extends CommonRequestModel
{
	public JsonRequestModel(String url, AsyncRequestListener asyncRequestListener)
	{
		super(url, RequestDataType.JSON, asyncRequestListener);
	}
	
	@Override
	public CommonRequestModel clone(AsyncRequestListener asyncRequestListener)
	{
		return new JsonRequestModel(this.getUrl(), asyncRequestListener);
	}
	
	public String getJsonAsString()
	{
		try
		{
			return new String(getResponseData(), "UTF-8");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public Object getJson(Type type)
	{
		Gson gson = new Gson();
		return gson.fromJson(getJsonAsString(), type);
	}
}
