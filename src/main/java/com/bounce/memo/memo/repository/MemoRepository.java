package com.bounce.memo.memo.repository;

import com.bounce.memo.memo.domain.Memo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

    // WHERE `user_id` = #{}
    public List<Memo> findByUserId(long userId, Sort sort);

}
