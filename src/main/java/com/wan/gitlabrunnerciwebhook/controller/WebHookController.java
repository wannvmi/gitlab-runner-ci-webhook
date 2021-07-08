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
        var dateString = getCurrDateStr();

        var title = "#" + formData.getFirst("CI_PIPELINE_IID") + " "
                + formData.getFirst("CI_PROJECT_NAME") + " 打包开始！";

        var content = "### <font color=\"#0189FF\">`#`" + formData.getFirst("CI_PIPELINE_IID") + " "
                + formData.getFirst("CI_PROJECT_NAME") + " 任务 " + formData.getFirst("CI_JOB_STAGE") + " 开始！</font>"
                + "  \n  任务：<font color=\"#0189FF\">#" + formData.getFirst("CI_PIPELINE_IID") + " "
                + formData.getFirst("CI_JOB_NAME") + " " + formData.getFirst("CI_JOB_STAGE") + "</font>"
                + "  \n  开始时间：<font color=\"#0189FF\">" + dateString + "</font>"
                + "  \n  用户：" + formData.getFirst("CI_COMMIT_AUTHOR")
                + "  \n  分支：" + formData.getFirst("CI_COMMIT_BRANCH")
                + "  \n  提交内容：" + formData.getFirst("CI_COMMIT_MESSAGE")
                + "  \n  提交时间：<font color=\"#ff294d\">**" + formData.getFirst("CI_COMMIT_TIMESTAMP") + "**</font>"
                + "  \n  打包位置：" + formData.getFirst("CI_BUILDS_DIR")
                + "  \n  完整路径：" + formData.getFirst("CI_PROJECT_DIR");

        var resStr = dingTalkService.send(title, content);

        return resStr;
    }

    @RequestMapping(value = "/WebHook/sendPost",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String sendPost(@RequestBody MultiValueMap<String, String> formData) throws Exception {
        var dateString = getCurrDateStr();

        var title = "#" + formData.getFirst("CI_PIPELINE_IID") + " "
                + formData.getFirst("CI_PROJECT_NAME") + " 打包结束！";

        var statusStr = "";

        if (formData.getFirst("CI_JOB_STATUS").equals("success")) {
            statusStr = "  \n  #### 结果：<font color=\"#5EC82D\">" + formData.getFirst("CI_JOB_STATUS") + "</font>";
        } else {
            statusStr = "  \n  #### 结果：<font color=\"#f56c6c\">" + formData.getFirst("CI_JOB_STATUS") + "  任务失败请关注！</font>";
        }

        var content = "### <font color=\"#0189FF\">`#`" + formData.getFirst("CI_PIPELINE_IID") + " "
                + formData.getFirst("CI_PROJECT_NAME") + " 任务 " + formData.getFirst("CI_JOB_STAGE") + " 结束！</font>"
                + "  \n  任务：<font color=\"#0189FF\">#" + formData.getFirst("CI_PIPELINE_IID") + " "
                + formData.getFirst("CI_JOB_NAME") + " " + formData.getFirst("CI_JOB_STAGE") + "</font>"
                + "  \n  结束时间：<font color=\"#0189FF\">" + dateString + "</font>"
                + "  \n  用户：" + formData.getFirst("CI_COMMIT_AUTHOR")
                + "  \n  分支：" + formData.getFirst("CI_COMMIT_BRANCH")
                + "  \n  提交内容：" + formData.getFirst("CI_COMMIT_MESSAGE")
                + "  \n  提交时间： **" + formData.getFirst("CI_COMMIT_TIMESTAMP") + "** "
                + "  \n  打包位置：" + formData.getFirst("CI_BUILDS_DIR")
                + "  \n  完整路径：" + formData.getFirst("CI_PROJECT_DIR")
                + statusStr;

        var resStr = dingTalkService.send(title, content);

        return resStr;
    }

    String getCurrDateStr() {
        var currentTime = new Date();
        var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(currentTime);
    }
}
