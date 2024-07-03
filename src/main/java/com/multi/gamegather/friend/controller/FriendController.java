package com.multi.gamegather.friend.controller;

import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.friend.model.dto.FriendDTO;
import com.multi.gamegather.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping("/list")
    public String getFriendList(Model model, @AuthenticationPrincipal CustomUser customUser) {
        // String userId로 파라미터 값을 받아올 때는 userId의 값이 null(입력한 값이 없음)이어서 최초 페이지 실행 시, 친구 목록에(userId로 호출이 안됨) 친구가 표시가 안됨
        // 그래서 String userId 대신 @AuthenticationPrincipal CustomUser customUser 을 사용하여 시큐리티 로그인에 저장된 userId를 가져와서 입력해줌.
        // -> 최초 실행 해도 친구 목록(호출에 사용할 userId가 존재)이 보임
        // 친구 목록 반환
        List<FriendDTO> friends = friendService.getFriendList(customUser.getId());
        model.addAttribute("friends", friends);
        model.addAttribute("userId", customUser.getId());
        return "friend/list";
    }
    //List<FriendDTO> blockedFriends = friendService.getBlockedFriendList(currentUser.getId(), "FR");

    @RequestMapping("/blocklist")
    public String getBlockedFriendList(Model model, @AuthenticationPrincipal CustomUser currentUser) {
        // 차단 목록 조회
        List<FriendDTO> blockedFriends = friendService.getBlockedFriendList(currentUser.getId());
        model.addAttribute("blockedFriends", blockedFriends);
        model.addAttribute("userId", currentUser.getId());
        return "friend/blocklist";
    }
    //List<FriendDTO> blockedFriends = friendService.getBlockedFriendList(currentUser.getId(), "BLK");

    @PostMapping("/add")
    public String addFriend(FriendDTO friendDTO, @AuthenticationPrincipal CustomUser currentUser){
        // 친구 추가
        String randomId = UUID.randomUUID().toString();

        friendDTO.setUserId(currentUser.getId());
        friendDTO.setFrCode(randomId);

        friendService.addFriend(friendDTO);
        return "redirect:/friend/list?userId=" + friendDTO.getUserId();
    }

    @PostMapping("/update")
    public String updateFriendFlag(String frCode, String frFlag, String userId){
        // 친구 차단
        friendService.updateFriendFlag(frCode, frFlag);
        return "redirect:/friend/list?userId=" + userId;
    }

    @PostMapping("/delete")
    public String deleteFriend(String frCode, String userId){
        // 친구 삭제
        friendService.deleteFriend(frCode);
        return "redirect:/friend/list?userId=" + userId;
    }

    @PostMapping("/unblock")
    public String unblockFriend(String frCode, String userId){
        // 차단 해제
        friendService.unblockFriend(frCode);
        return "redirect:/friend/blocklist?userId=" + userId;
    }
}

