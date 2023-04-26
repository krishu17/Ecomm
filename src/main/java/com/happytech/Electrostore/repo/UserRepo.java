package com.happytech.Electrostore.repo;

import com.happytech.Electrostore.dto.UserDto;
import com.happytech.Electrostore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    // in custom method find => means introducer, ByName is an criteria from which we have to find.
    Optional<User> findByName(String name);

    List<User> findByNameContaining(String keyword);

    Optional<User> findByEmailId(String emailId);

    @Query("select u from User u")
    List<User> getAllUserWithNumber();

    @Query("select u from User_Table u where u.name =:n") //note there should not be space between : and n
    List<User> getUserByName(@Param("n") String name);

}
