package com.multi.gamegather.club.controller;

import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.club.model.dao.ClubChatMapper;
import com.multi.gamegather.club.model.dto.*;
import com.multi.gamegather.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController //@Controller
@RequestMapping("/club")
public class ClubController {

    // 클라이언트가 받는 부분은 앞에 sub
    // 서버가 받는 부분은 앞에 pub

    private final ClubService clubService;

    @PostMapping
    public int createRoom(
        @RequestBody CreateClubRequestDTO data,
        @AuthenticationPrincipal CustomUser currentUser
    ) {
        System.out.println("1111 : " + currentUser.toString());
        data.setUserId(currentUser.getNo());
        System.out.println("1");

        return clubService.createClub(data);
    }

    @MessageMapping("/club/hello")
    @SendTo("/sub/club/test")
    public String test(HelloMessageDTO message) throws Exception {
        System.out.println("123123123");
        return ("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


    // 메세지 보내기
    @MessageMapping("/club/send-message/{clubId}")
    @SendTo("/sub/club/receive-message/{clubId}")
    public MessageDTO messageManager(
            @DestinationVariable int clubId,
            MessageDTO message,
            Principal principal
    ) throws Exception {
        String userId = principal.getName();
        System.out.println("userId " + userId);

        int userNo = clubService.getUserNoByUsername(userId);   //userid로 userNo 가져오는게 아니고?
        message.setSenderId(userNo);
        message.setClubId(clubId);
        System.out.println("Get From " + clubId + " send to " + message.getMessage());

        clubService.saveChat(clubId, message.getSenderId(), message.getMessage());
        return message;
    }
    
    // 방 삭제하기
    @DeleteMapping
    public void deleteRoom(
        @RequestBody ClubDTO clubDTO,
        @AuthenticationPrincipal CustomUser currentUser){

        clubService.deleteClub(clubDTO.getId(), currentUser.getNo());
    }


    // 방 목록 출력
    @GetMapping("/list")
    public List<ClubDTO> getClubList(
        @AuthenticationPrincipal CustomUser currentUser) {
        return clubService.getClubList(currentUser.getNo());
    }

    @PostMapping("/join")
    public int joinClub(
            @RequestBody ClubDTO data,
            @AuthenticationPrincipal CustomUser currentUser
    ) {
        return clubService.joinClub(data.getCode(), currentUser.getNo());
    }

    @DeleteMapping("/exit")
    public int exitClub(
            @RequestBody ClubDTO data,
            @AuthenticationPrincipal CustomUser currentUser
    ){
        System.out.println("Id: " + data.getId());
        clubService.deleteUser(currentUser.getNo(), data.getId());
        return data.getId();
    }

    @GetMapping("/chat")
    public List<ChatLogDTO> loadClubChat(
            @RequestParam int id
    ) {
        return clubService.getChat(id);
    }


    @PatchMapping("/member")
    public void kickUser(
            @RequestBody ClubManagementDTO data
    ) {
         clubService.kickUser(data);
    }

    @GetMapping("/categories")
    public List<ClubCategoryDTO> getAllCategories() {

        List<ClubCategoryDTO> tempList = clubService.getAllCategories();
        System.out.println("category" + clubService.getAllCategories());
        System.out.println("list: " + ToStringBuilder.reflectionToString(tempList.get(0), ToStringStyle.JSON_STYLE));

        return clubService.getAllCategories();

    }
}
