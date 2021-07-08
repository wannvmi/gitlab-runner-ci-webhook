package com.wan.gitlabrunnerciwebhook.service;


import com.wan.gitlabrunnerciwebhook.config.DingTalkSetting;
import com.wan.gitlabrunnerciwebhook.model.DingTalkRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

@Service
public class DingTalkService {
    @Autowired
    DingTalkSetting dingTalkSetting;

    public String send(String title, String text) throws Exception {
        var retrofit = new Retrofit.Builder()
                .baseUrl("https://oapi.dingtalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        var dingTalkClient = retrofit.create(DingTalkClient.class);

        var timestamp = System.currentTimeMillis();
        var sign = getSign(timestamp, dingTalkSetting.getSecret());

        var request = new DingTalkRequest(title, text);
        var call = dingTalkClient.Send(dingTalkSetting.getAccessToken(), timestamp, sign, request);

        var response = call.execute();

        return response.body().toString();
    }

    private static String getSign(long timestamp, String secret) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");

        return sign;
    }
}
