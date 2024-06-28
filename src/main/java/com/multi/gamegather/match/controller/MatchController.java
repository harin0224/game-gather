package com.multi.gamegather.match.controller;

import com.multi.gamegather.match.model.dto.MatchDTO;
import com.multi.gamegather.match.service.MatchService;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public void matchpage(){

    }

    @PostMapping("match")
    public ModelAndView setMatch(ModelAndView mv, MatchDTO setting, HttpSession session, Locale locale) throws Exception {
        // 세션에 setting 데이터를 저장합니다.
        session.setAttribute("setting", setting);



        String settinggamename = setting.getGamename();
        session.setAttribute("gamename", settinggamename);

        String setting2 = setting.getGamename() + setting.getHeadcnt() + setting.getTag1() + setting.getTag2() + setting.getTag3() + setting.getAge();
        session.setAttribute("setting2", setting2);







        MatchDTO savedSetting = (MatchDTO)session.getAttribute("setting");


        matchService.setMatch(setting);


        // 등록 성공 시 메뉴 목록 페이지로 리다이렉트합니다.
        mv.setViewName("redirect:/match/matchchat");

        return mv;
    }


//
//    @PostMapping("match")
//    public ModelAndView setMatch(ModelAndView mv, MatchDTO setting, RedirectAttributes rttr, Locale locale) throws Exception {
//        // 메뉴를 등록합니다.
//        matchService.setMatch(setting);
//
//        // 등록 성공 시 메뉴 목록 페이지로 리다이렉트합니다.
//        mv.setViewName("redirect:/match/matchchat");
//        // 등록 성공 메시지를 Flash 속성에 담아서 전달합니다.
//        rttr.addFlashAttribute("successMessage", messageSource.getMessage("setMatch", null, locale));
//
//        return mv;
//    }


}
