package com.multi.gamegather.match.controller;

import com.multi.gamegather.match.model.dto.MatchDTO;
import com.multi.gamegather.match.service.MatchService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView setMatch(ModelAndView mv, MatchDTO setting, RedirectAttributes rttr, Locale locale) throws Exception {
        // 메뉴를 등록합니다.
        matchService.setMatch(setting);
        // 등록 성공 시 메뉴 목록 페이지로 리다이렉트합니다.
        mv.setViewName("redirect:/match/matchchat");
        // 등록 성공 메시지를 Flash 속성에 담아서 전달합니다.
//        rttr.addFlashAttribute("successMessage", messageSource.getMessage("setMatch", null, locale));

        return mv;
    }


}
