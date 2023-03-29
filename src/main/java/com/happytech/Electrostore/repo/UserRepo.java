package com.happytech.Electrostore.repo;

import com.happytech.Electrostore.dto.UserDto;
import com.happytech.Electrostore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByName(String name);

    List<User> findByNameContaining(String keyword);

    Optional<User> findByEmailId(String emailId);

}
