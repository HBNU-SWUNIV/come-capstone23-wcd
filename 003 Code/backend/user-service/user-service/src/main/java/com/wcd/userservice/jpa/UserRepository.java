package com.wcd.userservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// CrudRepository 인터페이스를 상속함으로써, UserRepository는 UserEntity에 대한 CRUD 연산을 수행할 수 있음
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByLoginId(String loginId);

    UserEntity findByUserId(String userId);
}
