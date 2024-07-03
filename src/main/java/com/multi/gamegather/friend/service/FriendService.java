package com.multi.gamegather.friend.service;

import com.multi.gamegather.friend.model.dao.FriendDAO;
import com.multi.gamegather.friend.model.dto.FriendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendDAO friendDAO;

    public List<FriendDTO> getFriendList(String userId){
        // 친구 목록 반환
        return friendDAO.selectFriendList(userId, "FR");
    }
    //return friendDAO.selectFriendList(userId, frFlag);

    public List<FriendDTO> getBlockedFriendList(String userId){
        // 차단 목록 조회
        return friendDAO.selectFriendList(userId, "BLK");
    }

    public void addFriend(FriendDTO friendDTO){
        // 친구 추가
        FriendDTO existingRequest = friendDAO.findWaitingRequest(friendDTO.getFrId(), friendDTO.getUserId());
        if (existingRequest != null) {
            String sharedFrCode = existingRequest.getFrCode();
            friendDTO.setFrCode(sharedFrCode);
            friendDTO.setFrFlag("FR");
            friendDAO.updateFriendFlag(sharedFrCode, "FR");
            friendDAO.insertFriendFlagDouble(sharedFrCode, friendDTO.getUserId(), friendDTO.getFrId());
        } else {
            friendDTO.setFrFlag("WAIT");
            friendDAO.insertFriend(friendDTO);
        }
    }

    public void updateFriendFlag(String frCode, String frFlag){
        // 친구 차단
        friendDAO.updateFriendFlag(frCode, frFlag);
    }

    public void deleteFriend(String frCode){
        // 친구 삭제
        friendDAO.deleteFriend(frCode);
    }

    public void unblockFriend(String frCode){
        // 차단 해제
        friendDAO.deleteFriend(frCode);
    }
}
