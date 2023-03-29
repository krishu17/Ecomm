package com.happytech.Electrostore.service;

import com.happytech.Electrostore.dto.UserDto;
import com.happytech.Electrostore.payloads.PageableResponse;

import java.util.List;

public interface UserServiceI {

    //createUser
    UserDto createUser(UserDto userDto);

    //updateUser
    UserDto updateUser(UserDto userDto, Long userId);

    //deleteUser
    void deleteUserById(Long userId);

    //getSingleUser
    UserDto getUserById(Long userId);

    //getAllUser
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    //getUserByName
    UserDto findByName( String name);

    //getUserByEmailId
    UserDto findByEmailId(String emailId);

    //search
    List<UserDto> searchByKeyword(String keyword);

}
