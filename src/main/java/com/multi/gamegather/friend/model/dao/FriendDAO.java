package com.multi.gamegather.friend.model.dao;

import com.multi.gamegather.friend.model.dto.FriendDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // 매퍼랑 연결
public interface FriendDAO {

    List<FriendDTO> selectFriendList(String userId, String frFlag);
    void insertFriend(FriendDTO friendDTO);
    void updateFriendFlag(String frCode, String frFlag);
    void deleteFriend(String frCode);

    // 대기 중인 친구 요청 찾기
    FriendDTO findWaitingRequest(String userId, String frId);

    void insertFriendFlagDouble(String frCode, String userId, String frId);
}
