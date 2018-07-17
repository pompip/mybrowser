package cn.pompip.browser.controller;


import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.CommentPraiseBean;
import cn.pompip.browser.model.NewCommentBean;
import cn.pompip.browser.service.CommentPraiseService;
import cn.pompip.browser.service.NewsCommentService;
import cn.pompip.browser.util.date.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequestMapping("/newComment")
@Controller
public class NewsCommentController {


    @Autowired
    private NewsCommentService newsCommentService;

    @Autowired
    private CommentPraiseService commentPraiseService;

    @ResponseBody
    @RequestMapping(value = "/praisenum.html")
    public Result praiseNum(HttpServletRequest request)  {
        Result result = new Result();
        String commentId = request.getParameter("commentId");
        String fromUid = request.getParameter("uid");
        CommentPraiseBean commentPraiseBean = new CommentPraiseBean();
        commentPraiseBean.setFromUid(Long.parseLong(fromUid));
        commentPraiseBean.setCommentId(Long.parseLong(commentId));
        long num = commentPraiseService.count(commentPraiseBean);
        if (num > 0) { //已赞过
            result.setCode(1);
            result.setMsg("1001");
        } else {
            NewCommentBean newComment = new NewCommentBean();
            newComment.setId(Long.parseLong(commentId));
            newsCommentService.praise(newComment);

            commentPraiseBean.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
            commentPraiseService.insert(commentPraiseBean);
            result.setCode(0);
        }
        return result;

    }

    @ResponseBody
    @RequestMapping(value = "/getCommentList.html")
    public Result getCommentList(HttpServletRequest request)  {
        String newId = request.getParameter("newId");
        String uid = request.getParameter("uid");
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        Integer topicType = Integer.parseInt(request.getParameter("topicType"));//区分状态，1：新闻，2：视频

        NewCommentBean newComment = new NewCommentBean();
        newComment.setNewId(Long.parseLong(newId));
        newComment.setTopicType(topicType);//区分状态，1：新闻，2：视频
        List<NewCommentBean> newCommentList = newsCommentService.pageList(newComment, Integer.parseInt(pageNum), Integer.parseInt(pageSize));

        return Result.success(newCommentList);

    }

    @ResponseBody
    @RequestMapping(value = "/comment.html")
    public Result comment(HttpServletRequest request) {
        String fromUid = request.getParameter("uid");
        String content = request.getParameter("content");
        String topicType = request.getParameter("type");
        String newId = request.getParameter("newId");

        NewCommentBean newComment = new NewCommentBean();
        newComment.setFromUid(Long.parseLong(fromUid));
        newComment.setContent(content);
        newComment.setTopicType(Integer.parseInt(topicType));
        newComment.setNewId(Long.parseLong(newId));
        newComment.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
        newsCommentService.insert(newComment);

        return Result.success(newsCommentService.getById(newComment.getId()));

    }

}
