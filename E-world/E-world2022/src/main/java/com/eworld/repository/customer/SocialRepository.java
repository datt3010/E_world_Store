package com.eworld.repository.customer;

import com.eworld.entity.SocialConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialRepository extends JpaRepository<SocialConnection, Integer> {
}
