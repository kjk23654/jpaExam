package com.example.repositories;

import com.example.entities.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long>, QuerydslPredicateExecutor {

}
