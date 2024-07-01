package com.multi.gamegather.rchat.chatlog.model.dao;

import com.multi.gamegather.rchat.chatlog.model.dto.RChatLogDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RChatLogMapper {
    void insertLog(RChatLogDTO rChatLogDTO);
    void selectLog(RChatLogDTO rChatLogDTO);
}