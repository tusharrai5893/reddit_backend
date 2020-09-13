package com.reddit.backend.repository;

import com.reddit.backend.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RTokenRepo extends JpaRepository<RefreshToken,Long> {
}
