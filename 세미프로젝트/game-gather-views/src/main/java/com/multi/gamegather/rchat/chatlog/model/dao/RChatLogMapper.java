package com.multi.gamegather.rchat.chatlog.model.dao;

import com.multi.gamegather.rchat.chatlog.model.dto.RChatLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RChatLogMapper {
    void insertLog(RChatLogDTO rChatLogDTO);
    List<RChatLogDTO> selectLog();
}