package com.bounce.memo.memo.service;

import com.bounce.memo.common.FileManager;
import com.bounce.memo.memo.domain.Memo;
import com.bounce.memo.memo.repository.MemoRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

//    성공 실패 여부 확인을 위해 boolean으로 return
    public boolean createMemo(long userId
            , String title
            , String contents
            , MultipartFile imageFile) {

        String imagePath = FileManager.saveFile(userId, imageFile);

        Memo memo = Memo.builder()
                .userId(userId)
                .title(title)
                .contents(contents)
                .image_path(imagePath)
                .build();

        try {
            memoRepository.save(memo);
        } catch(DataAccessException e) {
            return false;
        }

        return true;

    }

//    Memo리스트 전체를 조회하여 가져오기
//    최신 작성글부터 역순으로 조회
    public List<Memo> getMemoList(long userId) {
        return memoRepository.findByUserId(userId, Sort.by("id").descending());
    }

    public Memo getMemo(long id) {
        Optional<Memo> optionalMemo = memoRepository.findById(id);

        return optionalMemo.get();
    }

    public boolean updateMemo(long id, String title, String contents) {
        // 수정 대상 행 조회
        // 수정 내용 적용된 객체 만들기
        // 수정된 객체 저장

        Optional<Memo> optionalMemo = memoRepository.findById(id);

        if(optionalMemo.isPresent()) {

            Memo memo = optionalMemo.get();
            memo = memo.toBuilder()
                    .title(title)
                    .contents(contents)
                    .build();

            try {
                memoRepository.save(memo);
            } catch(DataAccessException e) {
                return false;
            }


        } else {
            return false;
        }

        return true;
    }

    public boolean deleteMemo(long id) {

        Optional<Memo> optionalMemo = memoRepository.findById(id);

        if(optionalMemo.isPresent()) {

            Memo memo = optionalMemo.get();

            FileManager.removeFile(memo.getImage_path());

            try {
                memoRepository.delete(memo);
            } catch (DataAccessException e) {
                return false;
            }

        } else {
            return false;
        }

        return true;

    }

}
