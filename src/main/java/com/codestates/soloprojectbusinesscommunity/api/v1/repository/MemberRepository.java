package com.codestates.soloprojectbusinesscommunity.api.v1.repository;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
