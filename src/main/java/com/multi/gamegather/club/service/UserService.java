//package com.multi.gamegather.club.service;
//
//
//import com.multi.gamegather.club.model.dao.ClubManagementMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class UserService {
//    private final ClubManagementMapper clubManagementMapper;
//
//    public void kickUser(Long userId) {
//        ClubManagement user = clubManagementMapper.findByUsername(String.valueOf(userId));
//        if (user.getRole().equals("ADMIN")) {
//            throw new RuntimeException("Admin cannot be kicked");
//        }
//        clubManagementMapper.deleteUser(userId);
//    }
//}
