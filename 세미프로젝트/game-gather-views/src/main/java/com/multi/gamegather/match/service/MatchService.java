package com.multi.gamegather.match.service;

import com.multi.gamegather.match.model.dao.MatchMapper;
import com.multi.gamegather.match.model.dto.MatchDTO;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final MatchMapper matchMapper;

    public MatchService(MatchMapper matchMapper) {
        this.matchMapper = matchMapper;
    }

    public boolean setMatch(MatchDTO setting) throws Exception{
        // 메뉴 등록 결과를 받습니다.
        int result  = matchMapper.setMatch(setting);

        // 결과가 0보다 작으면 등록에 실패한 것입니다.
        if(result < 0){
            throw new Exception("매치 등록 실패");
        }
        // 결과가 0보다 크면 등록에 성공한 것입니다.
        return result > 0 ? true :false;
    }
}
