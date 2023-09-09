package com.wcd.userservice.repository;

import com.wcd.userservice.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// CrudRepository 인터페이스를 상속함으로써, UserRepository는 UserEntity에 대한 CRUD 연산을 수행할 수 있음
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

    List<Users> findByIdIn(List<Long> userIds);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Users> findByName(String name);
}
