package com.taskflow.domain.member.repository;

import com.taskflow.domain.member.dto.MemberProfileResponseDto;
import com.taskflow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일 중복 체크
    boolean existsByEmail(String email);

    // 닉네임 중복 체크
    boolean existsByUsername(String username);

    // Dto Projection을 사용해 불필요한 쿼리 제거 - 성능 개선
    @Query("SELECT new com.taskflow.domain.member.dto.MemberProfileResponseDto(m.name, m.email, m.userRole) FROM Member m WHERE m.id = :memberId")
    Optional<MemberProfileResponseDto> findProfileDtoById(@Param("memberId") Long memberId);
}
