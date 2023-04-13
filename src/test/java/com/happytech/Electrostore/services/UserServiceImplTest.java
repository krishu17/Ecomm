package com.happytech.Electrostore.services;

import com.happytech.Electrostore.config.AppConstants;
import com.happytech.Electrostore.dto.UserDto;
import com.happytech.Electrostore.entity.BaseEntity;
import com.happytech.Electrostore.entity.User;
import com.happytech.Electrostore.payloads.PageableResponse;
import com.happytech.Electrostore.repo.UserRepo;
import com.happytech.Electrostore.service.ServiceImpl.UserServiceImpl;
import com.happytech.Electrostore.service.UserServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest extends BaseEntity {

    // we made it mockBean bcz we dont want
    @MockBean
    private UserRepo userRepo;

    @InjectMocks
    @Autowired
    private UserServiceImpl userServiceI;

    User user;

    User user1;

    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void init(){
        UserDto userDto = UserDto.builder()
                .name("Krishna")
                .about("I am the developer")
                .emailId("Krishu17@gmail.com")
                .gender("Male")
                .password("abcz")
                .imageName("abc.png")
                .build();

         user =  User.builder()
                .name("Krishna")
                .about("I am the developer")
                .emailId("Krishu17@gmail.com")
                .gender("Male")
                .password("abcz")
                .imageName("abc.png")
                .build();

         user1 =  User.builder()
                .name("Arjun")
                .about("I am the Pharmacist")
                .emailId("Arjun@gmail.com")
                .gender("Male")
                .password("hfd")
                .imageName("arj.png")
                .build();


    }
    @Test
   public void createUserTest() {
//      Arrange
        when(userRepo.save(Mockito.any())).thenReturn(user);
//      Act
        UserDto userDto = userServiceI.createUser(mapper.map(user, UserDto.class));
//      Assert
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("Krishna", userDto.getName());
    }

    @Test
    void updateUserTest() {
    Long userId=10L;

        UserDto userDto1 = UserDto.builder()
                .name("Krishna More")
                .password("hgsds")
                .about("Full STack Developer")
                .imageName("hsdf.png")
                .build();

     // Arrange
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(userRepo.save(Mockito.any())).thenReturn(user);
     //Act
        UserDto userDto = userServiceI.updateUser(userDto1, userId);
     //Assert
        Assertions.assertNotNull(userDto1);
        Assertions.assertEquals(userDto1.getName(), userDto.getName(),"Name is not matched");
    }

    @Test
    void deleteUserByIdTest() {
        Long userId=10L;
        User user = User.builder()
//                .imageName("abc.jpg")
                .build();

        user.setIsActive(AppConstants.YES);

        //Arrange
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        //Act
        userServiceI.deleteUserById(userId);
        //Assert
        verify(userRepo, times(1)).findById(userId);
        verify(userRepo, times(1)).save(user);

        Assertions.assertEquals(user.getIsActive(), AppConstants.NO);
    }

    @Test
    void getUserByIdTest() {
        Long userId=10L;

        //Arrange
        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        //Act
        UserDto userById = userServiceI.getUserById(userId);
        //Assert
        Assertions.assertNotNull(userById);

        Assertions.assertEquals(user.getUserId(),userById.getUserId(),"User id not match..!!");
    }

    @Test
    void getAllUsersTest() {

        User user =  User.builder()
                .name("Krishna")
                .about("I am the Pharmacist")
                .emailId("Krishna@gmail.com")
                .gender("Male")
                .password("hfd")
                .imageName("arj.png")
                .build();
        User user1 =  User.builder()
                .name("Arjun")
                .about("I am the Pharmacist")
                .emailId("Arjun@gmail.com")
                .gender("Male")
                .password("hfd")
                .imageName("arj.png")
                .build();

        List<User> users = List.of(user,user1);
        Page<User> page = new PageImpl<>(users);
        //Arrange
        Mockito.when(userRepo.findAll((Pageable)Mockito.any())).thenReturn(page);

        //Act
        PageableResponse<UserDto> allUsers = userServiceI.getAllUsers(1,2,"name","asc");

        //Assert
        Assertions.assertEquals(2, allUsers.getContent().size());
    }

    @Test
    void findByNameTest() {
        String name = "Krishna";

        //Arrange
        Mockito.when(userRepo.findByName(name)).thenReturn(Optional.of(user));
        //Act
        UserDto byName = userServiceI.findByName(name);
        //Assert
        Assertions.assertEquals(user.getName(), byName.getName(),"Name not matched...");
    }


    @Test
    void findByEmailIdTest() {
        String emailId="krishu@gmail.com";

        //Arrange
        Mockito.when(userRepo.findByEmailId(emailId)).thenReturn(Optional.of(user));

        //Act
        UserDto byEmailId = userServiceI.findByEmailId(emailId);
        //Assert
        Assertions.assertNotNull(byEmailId);
        Assertions.assertEquals(user.getEmailId(), byEmailId.getEmailId());
    }

    @Test
    void searchByKeywordTest() {
        String keyword = "Krishna";
        List<User> users = List.of(user,user1);
        //Arrange
        Mockito.when(userRepo.findByNameContaining(keyword)).thenReturn(users);
        //Act
        List<UserDto> userDtos = userServiceI.searchByKeyword(keyword);
        //Assert
        Assertions.assertEquals(2, userDtos.size());
    }
}