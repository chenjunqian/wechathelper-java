package com.wechathelper.service;

import com.wechathelper.model.TextMessageTask;
import com.wechathelper.repository.TextMessageTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextMessageTaskService {

    @Autowired
    private TextMessageTaskRepository textMessageTaskRepository;

    public int createTextMessageTask(TextMessageTask textMessageTask){

        if (textMessageTaskRepository.equals(textMessageTask)){
            return 0;
        }

        textMessageTaskRepository.save(textMessageTask);
        return 1;
    }

    public List<TextMessageTask> getTextMessageTasksListByUserId(String wechatId){
        List<TextMessageTask> list = textMessageTaskRepository.findAllByWechatId(wechatId);

        return list;
    }
}
