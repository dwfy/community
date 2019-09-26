package com.dwfy.community.service;

import com.dwfy.community.dto.PageDTO;
import com.dwfy.community.dto.QuestionDTO;
import com.dwfy.community.mapper.QuestionMapper;
import com.dwfy.community.mapper.UserMapper;
import com.dwfy.community.model.Question;
import com.dwfy.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size) {

        PageDTO pageDTO = new PageDTO();
        Integer totalPage;
        Integer totleCount = questionMapper.count();

        if (totleCount % size == 0) {
            totalPage = totleCount / size;
        } else {
            totalPage = totleCount / size + 1;
        }

        if (page < 1){
            page = 1;
        }

        if (page > totalPage){
            page = totalPage;
        }
        pageDTO.setPagenation(totalPage,page);
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);


        return pageDTO;
    }

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();

        Integer totalPage;
        Integer totleCount = questionMapper.countByUserId(userId);

        if (totleCount % size == 0) {
            totalPage = totleCount / size;
        } else {
            totalPage = totleCount / size + 1;
        }

        if (page < 1){
            page = 1;
        }

        if (page > totalPage){
            page = totalPage;
        }
        pageDTO.setPagenation(totalPage,page);
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);


        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            questionMapper.create(question);
        }else {
            question.setGmt_modified(question.getGmt_create());
            questionMapper.update(question);
        }
    }
}
