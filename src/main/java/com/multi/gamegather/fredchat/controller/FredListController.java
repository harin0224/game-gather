package com.multi.gamegather.fredchat.controller;

import com.multi.gamegather.authentication.model.dto.CustomUser;
import com.multi.gamegather.fredchat.service.FredListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fredlist")
public class FredListController {

    private final FredListService fredListService;

    @GetMapping("/frndcodes")
    public List<String> getFrndCodes(@AuthenticationPrincipal CustomUser customUser) {
        return fredListService.getFrndCodesByUserId(customUser.getId());
    }
}
