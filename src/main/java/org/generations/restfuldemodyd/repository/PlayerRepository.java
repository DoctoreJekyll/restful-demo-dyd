package org.generations.restfuldemodyd.repository;

import org.generations.restfuldemodyd.model.Job;
import org.generations.restfuldemodyd.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
    Page<Player> findByJobIgnoreCaseContaining(String Job, Pageable pageable);
    Page<Player> findByJobId(Integer jobId, Pageable pageable);
}
