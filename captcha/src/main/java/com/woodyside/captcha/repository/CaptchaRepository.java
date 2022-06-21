package com.woodyside.captcha.repository;

import com.woodyside.captcha.model.CaptchaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaEntity,Integer> {

    Optional<CaptchaEntity> findByCaptchaId(String captchaId);
}
