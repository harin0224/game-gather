package com.multi.gamegather.match.controller;

import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.match.model.dto.MatchDTO;
import com.multi.gamegather.match.service.MatchService;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Locale;

@Controller
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;
    private final MessageSource messageSource;

    public MatchController(MatchService matchService, MessageSource messageSource) {
        this.matchService = matchService;
        this.messageSource = messageSource;
    }


    @GetMapping("/matchchat")
    public void matchchatpage(){

    }
    @GetMapping("/match")
    public String matchpage(@AuthenticationPrincipal CustomUser customUser){

        if (customUser.getBanCount() > 10) {
            return "/error/match";
        }
        else {
            return "/match/match";
        }
    }

    @PostMapping("match")
    public ModelAndView setMatch(ModelAndView mv, MatchDTO setting, HttpSession session, Locale locale, Principal principal) throws Exception {
        // 세션에 setting 데이터를 저장합니다.
        session.setAttribute("setting", setting);
        String Headcnt = setting.getHeadcnt();
        session.setAttribute("Headcnt", Headcnt);

        String setting2 = setting.getGamename() + setting.getHeadcnt() + setting.getTag1() + setting.getTag2() + setting.getTag3() + setting.getAge();
        session.setAttribute("setting2", setting2);

        String username = principal.getName();
        setting.setUserid(username);
        matchService.setMatch(setting);


        // 등록 성공 시 메뉴 목록 페이지로 리다이렉트합니다.
        mv.setViewName("redirect:/rchat/rchat");

        return mv;
    }
//    league_of_legends
//    overwatch
//    battleground
//    vallolant
//    lostark
//    maplestory
//    goosegooseduck
    @GetMapping("/countLeagueOfLegends")
    @ResponseBody
    public ResponseEntity<Integer> countLeagueOfLegends(@AuthenticationPrincipal CustomUser customUser) {
        int count = matchService.countByGameName(customUser.getId(), "league_of_legends");
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countOverwatch")
    @ResponseBody
    public ResponseEntity<Integer> countOverwatch(@AuthenticationPrincipal CustomUser customUser) {
        int count = matchService.countByGameName(customUser.getId(), "overwatch");
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countBattleground")
    @ResponseBody
    public ResponseEntity<Integer> countBattleground(@AuthenticationPrincipal CustomUser customUser) {
        int count = matchService.countByGameName(customUser.getId(), "battleground");
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countVallolant")
    @ResponseBody
    public ResponseEntity<Integer> countVallolant(@AuthenticationPrincipal CustomUser customUser) {
        int count = matchService.countByGameName(customUser.getId(), "vallolant");
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countLostark")
    @ResponseBody
    public ResponseEntity<Integer> countLostark(@AuthenticationPrincipal CustomUser customUser) {
        int count = matchService.countByGameName(customUser.getId(), "lostark");
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countMaplestory")
    @ResponseBody
    public ResponseEntity<Integer> countMaplestory(@AuthenticationPrincipal CustomUser customUser) {
        int count = matchService.countByGameName(customUser.getId(), "maplestory");
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countGoosegooseduck")
    @ResponseBody
    public ResponseEntity<Integer> countGoosegooseduck(@AuthenticationPrincipal CustomUser customUser) {
        int count = matchService.countByGameName(customUser.getId(), "goosegooseduck");
        return ResponseEntity.ok(count);
    }
}
