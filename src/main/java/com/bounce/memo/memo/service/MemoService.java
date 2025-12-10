package com.bounce.memo.memo.service;

import com.bounce.memo.memo.domain.Memo;
import com.bounce.memo.memo.repository.MemoRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

//    성공 실패 여부 확인을 위해 boolean으로 return
    public boolean createMemo(long userId, String title, String contents) {

        Memo memo = Memo.builder()
                .userId(userId)
                .title(title)
                .contents(contents)
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



}
