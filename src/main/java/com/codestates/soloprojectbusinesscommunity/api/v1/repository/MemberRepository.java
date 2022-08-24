package com.codestates.soloprojectbusinesscommunity.api.v1.repository;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findByCompanyTypeAndCompanyLocation(String companyType, String companyLocation, Pageable pageable);
    Page<Member> findByCompanyType(String companyType, Pageable pageable);
    Page<Member> findByCompanyLocation(String companyLocation, Pageable pageable);
}
