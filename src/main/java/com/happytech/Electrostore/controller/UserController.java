package com.happytech.Electrostore.controller;

import com.happytech.Electrostore.dto.UserDto;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

    //postMapping
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userServiceI.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //getMapping

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto userById = this.userServiceI.getUserById(userId);

        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    //getMapping
    @GetMapping("/getAllUsers")
    public ResponseEntity<PageableResponse<UserDto>>getAllUsers(@RequestParam (value = "pageNumber",defaultValue = "0",required =false)int pageNumber,
                                                                @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                                                @RequestParam(value = "sortBy",defaultValue = "name",required = false)String sortBy,
                                                                @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){
        return new ResponseEntity<>(userServiceI.getAllUsers(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);

    }

    //getMapping

    @GetMapping("/getByName/{name}")
    public ResponseEntity<UserDto> findByName(@PathVariable String name) {

        UserDto byName = this.userServiceI.findByName(name);

        return new ResponseEntity<>(byName, HttpStatus.OK);
    }

    // getMapping
    @GetMapping("/email/{emailId}")
    public ResponseEntity<UserDto> findByEmailId(@PathVariable String emailId) {
        UserDto byEmailId = this.userServiceI.findByEmailId(emailId);
        return new ResponseEntity<>(byEmailId, HttpStatus.OK);
    }

    //getMapping

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> getByWord(@PathVariable String keyword) {
        List<UserDto> userDtos = this.userServiceI.searchByKeyword(keyword);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }


    //putmapping

    @PutMapping("/updateUserById/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId) {

        UserDto userDto1 = this.userServiceI.updateUser(userDto, userId);

        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //deleteMapping

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {

        this.userServiceI.deleteUserById(userId);

        return new ResponseEntity<>("User deleted successfully !", HttpStatus.OK);

    }


}
