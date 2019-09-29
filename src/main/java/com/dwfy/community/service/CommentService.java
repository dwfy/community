package com.dwfy.community.service;

import com.dwfy.community.exception.CustomizeErrorCode;
import com.dwfy.community.exception.CustomizeException;
import com.dwfy.community.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
    }
}
