package com.project.planb.common.security.repository;

import com.project.planb.common.security.dto.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    // redis 객체 조회
    Optional<RefreshToken> findByAccessToken(String accessToken);
}
