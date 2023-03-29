package com.happytech.Electrostore.service;

import com.happytech.Electrostore.Helper.Helper;
import com.happytech.Electrostore.config.AppConstants;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.dto.UserDto;
import com.happytech.Electrostore.entity.User;
import com.happytech.Electrostore.exceptions.ResourceNotFoundException;
import com.happytech.Electrostore.exceptions.ValueNotFoundException;
import com.happytech.Electrostore.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        user.setIsActive(AppConstants.YES);

        User savedUser = this.userRepo.save(user);

        UserDto userDto1 = this.modelMapper.map(savedUser, UserDto.class);

        return userDto1;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        User map = this.modelMapper.map(userDto, User.class);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        user.setPassword(userDto.getPassword());
        user.setEmailId(userDto.getEmailId());

        User save = this.userRepo.save(user);

        return this.modelMapper.map(save, UserDto.class);
    }

    @Override
    public void deleteUserById(Long userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

        user.setIsActive(AppConstants.NO);

        this.userRepo.save(user);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

        return this.modelMapper.map(user, UserDto.class);
    }


  @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        logger.info("Intitating dao request for getAllUsers");

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        PageRequest pagebale = PageRequest.of(pageNumber, pageSize, sort);

         Page<User> page =userRepo.findAll(pagebale);

//      List<User> collect = page.stream().filter(n -> n.getIsActive() == AppConstants.YES).collect(Collectors.toList());


       PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
      logger.info("Completed dao request for getAll users");

        return response;

    }


    @Override
    public UserDto findByName(String name) {

        User user = this.userRepo.findByName(name).orElseThrow(() -> new ValueNotFoundException(AppConstants.USER, AppConstants.USER_ID, name));

        return this.modelMapper.map(user, UserDto.class);

    }

    @Override
    public UserDto findByEmailId(String emailId) {

        User user = this.userRepo.findByEmailId(emailId).orElseThrow(() -> new ValueNotFoundException(AppConstants.USER, AppConstants.USER_EMAIL, emailId));
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchByKeyword(String keyword) {

        List<User> byNameContaining = this.userRepo.findByNameContaining(keyword);
        List<UserDto> collect = byNameContaining.stream().map((str) -> this.modelMapper.map(str, UserDto.class)).collect(Collectors.toList());
        return collect;
    }


}
