package com.multi.gamegather.rchat.chatlog.service;

import com.multi.gamegather.rchat.chatlog.model.dao.RChatLogMapper;
import com.multi.gamegather.rchat.chatlog.model.dto.RChatLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RChatLogServiceImpl implements RChatLogService {

    private final RChatLogMapper rChatLogMapper;

    @Autowired
    public RChatLogServiceImpl(RChatLogMapper rChatLogMapper) {
        this.rChatLogMapper = rChatLogMapper;
    }

    @Override
    public void saveLog(RChatLogDTO rChatLogDTO) {
        rChatLogMapper.insertLog(rChatLogDTO);
    }

    @Override
    public List<RChatLogDTO> selectLog() {
        return rChatLogMapper.selectLog();
    }
}