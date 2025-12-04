package com.bounce.memo.user.service;

import com.bounce.memo.common.MD5HashingEncoder;
import com.bounce.memo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

//    @Autowired
    // 클래스 내에 생성자가 객체 주입을 위한 생성자가 유일한 경우 @Autowired 생략 가능
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(
            String loginId
            , String password
            , String name
            , String email) {

        String encodedPassword = MD5HashingEncoder.encode(password);

        // 데이터베이스에 원본이 아닌 해싱된 비밀번호가 저장
        int count = userRepository.insertUser(loginId, encodedPassword, name, email);

        if(count == 1) {
            return true;
        } else {
            return false;
        }

    }

}
