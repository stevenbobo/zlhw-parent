package com.zb.pcare.service;

import com.zb.pcare.common.ReplyType;
import com.zb.pcare.dao.QuestionDao;
import com.zb.pcare.dao.ReplyDao;
import com.zb.pcare.model.Question;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-10-4
 * Time: 下午9:55
 * To change this template use File | Settings | File Templates.
 */
public class QuestionService {
    @Resource
    private QuestionDao questionDao;
    @Resource
    private ReplyDao replyDao;

    public void createQuestion(Question question){
        question.setCreateDate(new Date());
        questionDao.create(question);
    }

    public void replyForQuestion(Integer questionId,String content){
         Question question = questionDao.getById(questionId);
         if(question.getReplyType().equals(ReplyType.DATE)){

         }

    }
}
