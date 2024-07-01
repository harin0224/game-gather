package com.multi.gamegather.club.model.dao;

import com.multi.gamegather.club.model.dto.ChatLogDTO;
import com.multi.gamegather.club.model.dto.MessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClubChatMapper {
    void saveChat(int clubId, int userId, String message);

    List<ChatLogDTO> getChatLog(int clubId);
}
