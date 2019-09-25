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
        Integer totleCount = questionMapper.count();
        pageDTO.setPagenation(totleCount,page,size);

        if (page < 1){
            page = 1;
        }

        if (page > pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }

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
}
