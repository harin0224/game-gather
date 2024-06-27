package com.multi.gamegather.match.model.dao;

import com.multi.gamegather.match.model.dto.MatchDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchMapper {
    int setMatch(MatchDTO setting);
}
