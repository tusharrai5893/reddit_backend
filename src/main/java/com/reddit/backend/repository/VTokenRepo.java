package com.reddit.backend.repository;

import com.reddit.backend.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VTokenRepo extends JpaRepository<VerificationToken,Long> {
}
