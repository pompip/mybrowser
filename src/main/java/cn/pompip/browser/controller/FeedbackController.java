package cn.pompip.browser.controller;


import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.FeedbackBean;
import cn.pompip.browser.service.FeedbackService;
import cn.pompip.browser.util.date.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/feedback")
@Controller
public class FeedbackController {


    @Autowired
    private FeedbackService feedbackService;

    @ResponseBody
    @RequestMapping(value = "/submit.html")
    public Result submit(HttpServletRequest request) {
        String content = request.getParameter("content");
        String uid = request.getParameter("uid");

        FeedbackBean feedback = new FeedbackBean();
        feedback.setUid(Long.parseLong(uid));
        feedback.setContent(content);
        feedback.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
        feedbackService.insert(feedback);
        return Result.success();

    }

}
