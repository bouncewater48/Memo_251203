package com.bounce.memo.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    // final이 붙은 변수 이름은 일반적으로 다른 변수와 구분짓기 위하여 대문자로 작성함
//    경로 앞에 /를 하나 붙혀놔야 다른 환경(리눅스, 애플 ...)에서도 호환이 가능함
    public final static String FILE_UPLOAD_PATH = "D:\\250820bouncewater\\springProject\\upload\\memo";

    // 파일을 전달 받아, 정해진 경로에 저장하고,
    // 해당 파일을 클라이언트가 접근할 수 있는 url 경로 리턴
    public static String saveFile(long userId, MultipartFile file) {

//        정상적으로 수행되지 않아 null 이 리턴되면 다시 null로 리턴하여 수행하지 마라
        if(file == null) {
            return null;
        }

        // 원본파일 이름 그대로 저장
        // 디렉터리로 구분해서 파일 저장
        // 디렉터리 이름 : 사용자 정보 + 시간 정보 (ex) 3(3 : 사용자의 PK)_32198792314
        // UNIX TIME : 1970년 1월 1일 0시 0분 0초 이후로 흐른 시간 (millisecond) (1/1000 초)

//        System.currentTimeMillis() : UNIX TIME 명령어
        String directoryName = "/" + userId + "_" + System.currentTimeMillis();
        
        // 디렉터리 만들기
        // 전체 디렉터리 경로
        String directoryPath = FILE_UPLOAD_PATH + directoryName;

        // File import : java.io
        File directory = new File(directoryPath);

//        mkdir : make directory 약자
        if(!directory.mkdir()) {
            // 디렉터리 생성 실패
            // 정상적으로 파일이 생성되지 않고 문제가 발생했음을 의미
            return null;
        }
        
        // 파일 저장
//        file.getOriginalFilename() : 기존 파일 경로
        String filePath = directoryPath + "/" + file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();

            Path path = Paths.get(filePath);
            Files.write(path, bytes);

        } catch (IOException e) {
            return null;
        }

//        서버 파일 경로 :  D:\\250820bouncewater\\springProject\\upload\\memo/3_189298/test.png
//        url path : /images/3_189298/test.png

//        directoryName 앞에 이미 /가 있으므로 여기선 /를 추가하지 않음
        return "/images" + directoryName + "/" + file.getOriginalFilename();


    }
}
