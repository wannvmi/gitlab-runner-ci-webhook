package com.wan.gitlabrunnerciwebhook.controller;

import com.wan.gitlabrunnerciwebhook.service.DingTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@RestController
public class WebHookController {
    @Autowired
    private DingTalkService dingTalkService;

    @RequestMapping(value = "/WebHook/sendPre",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String sendPre(@RequestBody MultiValueMap<String, String> formData) throws Exception {
        var currentTime = new Date();
        var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        var dateString = formatter.format(currentTime);

        var content = "#" + formData.getFirst("CI_PIPELINE_IID") + " "
                + formData.getFirst("CI_PROJECT_NAME") + " 打包开始！"
                + "\n开始时间：" + dateString
                + "\n用户：" + formData.getFirst("CI_COMMIT_AUTHOR")
                + "\n分支：" + formData.getFirst("CI_COMMIT_BRANCH")
                + "\n提交内容：" + formData.getFirst("CI_COMMIT_MESSAGE")
                + "\n提交时间：" + formData.getFirst("CI_COMMIT_TIMESTAMP")
                + "\n打包位置：" + formData.getFirst("CI_BUILDS_DIR")
                + "\n完整路径：" + formData.getFirst("CI_PROJECT_DIR");

        var resStr = dingTalkService.send(content);

        return resStr;
    }

    @RequestMapping(value = "/WebHook/sendPost",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String sendPost(@RequestBody MultiValueMap<String, String> formData) throws Exception {

        var currentTime = new Date();
        var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        var dateString = formatter.format(currentTime);

        var content = "#" + formData.getFirst("CI_PIPELINE_IID") + " "
                + formData.getFirst("CI_PROJECT_NAME") + " 打包结束！"
                + "\n结束时间：" + dateString
                + "\n用户：" + formData.getFirst("CI_COMMIT_AUTHOR")
                + "\n分支：" + formData.getFirst("CI_COMMIT_BRANCH")
                + "\n提交内容：" + formData.getFirst("CI_COMMIT_MESSAGE")
                + "\n提交时间：" + formData.getFirst("CI_COMMIT_TIMESTAMP")
                + "\n打包位置：" + formData.getFirst("CI_BUILDS_DIR")
                + "\n完整路径：" + formData.getFirst("CI_PROJECT_DIR")
                + "\n结果：" + formData.getFirst("CI_JOB_STATUS");

        var resStr = dingTalkService.send(content);

        return resStr;
    }
}
