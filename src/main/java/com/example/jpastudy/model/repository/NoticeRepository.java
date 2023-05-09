package com.example.jpastudy.model.repository;

import com.example.jpastudy.model.entity.Notice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {
    Optional<List<Notice>> findByIdIn(List<Long> idList);
}
