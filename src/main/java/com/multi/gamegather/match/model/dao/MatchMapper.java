package com.multi.gamegather.match.model.dao;

import com.multi.gamegather.match.model.dto.MatchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MatchMapper {

    int countByGameName(@Param("userId") String userId, @Param("gameName") String gameName);

    int setMatch(MatchDTO setting);
}
