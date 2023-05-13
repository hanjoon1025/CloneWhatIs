package com.team10.whatis.email.repository;

import com.team10.whatis.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email,String> {
}
