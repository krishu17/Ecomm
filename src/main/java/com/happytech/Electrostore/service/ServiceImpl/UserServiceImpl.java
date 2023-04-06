package com.happytech.Electrostore.service.ServiceImpl;

import com.happytech.Electrostore.Helper.Helper;
import com.happytech.Electrostore.config.AppConstants;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.dto.UserDto;
import com.happytech.Electrostore.entity.User;
import com.happytech.Electrostore.exceptions.ResourceNotFoundException;
import com.happytech.Electrostore.exceptions.ValueNotFoundException;
import com.happytech.Electrostore.repo.UserRepo;
import com.happytech.Electrostore.service.UserServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile(value = {"dev","qa","prod","pilot"})
public class UserServiceImpl implements UserServiceI {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("creating user ...");
        User user = this.modelMapper.map(userDto, User.class);

        user.setIsActive(AppConstants.YES);

        User savedUser = this.userRepo.save(user);

        UserDto userDto1 = this.modelMapper.map(savedUser, UserDto.class);
        log.info("user created successfully !!");
        return userDto1;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        log.info("updating user ...");
        User map = this.modelMapper.map(userDto, User.class);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        user.setPassword(userDto.getPassword());
        user.setEmailId(userDto.getEmailId());

        User save = this.userRepo.save(user);

        UserDto dto = this.modelMapper.map(save, UserDto.class);
        log.info("user updated successfully !!");
        return dto;
    }

    @Override
    public void deleteUserById(Long userId) {
        log.info("checking for user id ...");
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));
        
        //delete userImage profile
        String fullPath = imagePath + user.getImageName();

        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }
        catch(NoSuchFileException ex){
        log.info("User Image Not Found !!");
        ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        user.setIsActive(AppConstants.NO);

        this.userRepo.save(user);
        log.info("user deleted successfully !!");
    }

    @Override
    public UserDto getUserById(Long userId) {
        log.info("user getting ... !!");
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

        UserDto dto = this.modelMapper.map(user, UserDto.class);
        log.info("user got successfully !!");
        return dto;
    }


  @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        log.info("Intitating dao request for getAllUsers");

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        PageRequest pagebale = PageRequest.of(pageNumber, pageSize, sort);

         Page<User> page =userRepo.findAll(pagebale);

//      List<User> collect = page.stream().filter(n -> n.getIsActive().AppConstants.YES).collect(Collectors.toList());


       PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

       log.info("Completed dao request for getAll users");

        return response;

    }


    @Override
    public UserDto findByName(String name) {
        log.info("getting user by name ...");
        User user = this.userRepo.findByName(name).orElseThrow(() -> new ValueNotFoundException(AppConstants.USER, AppConstants.USER_ID, name));

        UserDto dto = this.modelMapper.map(user, UserDto.class);
        log.info("User found successfully...");
        return dto;

    }

    @Override
    public UserDto findByEmailId(String emailId) {
        log.info("getting user by email...");
        User user = this.userRepo.findByEmailId(emailId).orElseThrow(() -> new ValueNotFoundException(AppConstants.USER, AppConstants.USER_EMAIL, emailId));
        UserDto map = this.modelMapper.map(user, UserDto.class);
        log.info("user with this email got successfully !!");
        return map;
    }

    @Override
    public List<UserDto> searchByKeyword(String keyword) {
        log.info("getting user by word...");
        List<User> byNameContaining = this.userRepo.findByNameContaining(keyword);
        List<UserDto> collect = byNameContaining.stream().map((str) -> this.modelMapper.map(str, UserDto.class)).collect(Collectors.toList());
        log.info("user with this word got successfully !!");
        return collect;
    }


}
