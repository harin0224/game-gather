package com.multi.gamegather.fredchat.model.dao;

import com.multi.gamegather.fredchat.model.dto.FredChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FredChatDAO {
    void insertChatMessage(@Param("chatMessage") FredChatMessage chatMessage);
}
