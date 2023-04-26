package com.happytech.Electrostore.controller;

import com.happytech.Electrostore.dto.UserDto;
import com.happytech.Electrostore.payloads.ImageResponse;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.service.FileService;
import com.happytech.Electrostore.service.UserServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private  String imageUploadPath;
        Logger logger = LoggerFactory.getLogger(UserController.class);
    //postMapping
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userServiceI.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
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
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image ,@PathVariable Long userId) throws IOException {
        logger.info("Uploading image started...");
        String imageName = fileService.uploadFile(image, imageUploadPath);

        UserDto user = userServiceI.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userServiceI.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("User image").success(true).status(HttpStatus.CREATED).build();
        logger.info("Successfully uploaded ...");
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    //serve Image
    @GetMapping("/image/{userId}")
    public void serveImage(@PathVariable Long userId, HttpServletResponse response) throws IOException {
        UserDto user = userServiceI.getUserById(userId);
        logger.info("User image Name : {} ",user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
