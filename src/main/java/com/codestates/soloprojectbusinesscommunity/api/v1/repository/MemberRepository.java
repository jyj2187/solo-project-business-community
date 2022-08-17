package com.codestates.soloprojectbusinesscommunity.api.v1.repository;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByCompanyTypeAndCompanyLocation(String companyType, String companyLocation, Pageable pageable);
    List<Member> findByCompanyType(String companyType, Pageable pageable);
    List<Member> findByCompanyLocation(String companyLocation, Pageable pageable);
}
