package com.wan.gitlabrunnerciwebhook.service;

import com.wan.gitlabrunnerciwebhook.model.DingTalkRequest;
import com.wan.gitlabrunnerciwebhook.model.DingTalkResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DingTalkClient {

    @POST("robot/send")
    Call<DingTalkResult> Send(@Query("access_token") String accessToken, @Query("timestamp") Long timestamp, @Query("sign") String sign,
                              @Body DingTalkRequest request);
}
