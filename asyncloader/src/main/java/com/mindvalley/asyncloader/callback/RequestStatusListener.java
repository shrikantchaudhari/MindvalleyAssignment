package com.mindvalley.asyncloader.callback;


import com.mindvalley.asyncloader.models.CommonRequestModel;

public interface RequestStatusListener {

    void setDone(CommonRequestModel commonRequestModel);

    void setCancelled(CommonRequestModel commonRequestModel);

    void onFailure(CommonRequestModel commonRequestModel);
}
