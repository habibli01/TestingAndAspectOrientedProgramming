package org.example.sellingexchangeplatform.repository;

import org.example.sellingexchangeplatform.entity.Replay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ReplayRepository extends JpaRepository<Replay, Long> {

    List<Replay> findByReviewId(Long reviewId);
}
