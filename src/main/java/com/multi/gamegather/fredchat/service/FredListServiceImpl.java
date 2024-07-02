package com.multi.gamegather.fredchat.service;

import com.multi.gamegather.fredchat.model.dao.FredListDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FredListServiceImpl implements FredListService{

    private final FredListDAO fredListDAO;

    @Override
    public List<String> getFrndCodesByUserId(String userId) {
        return fredListDAO.selectFrndCodesByUserId(userId);
    }
}
