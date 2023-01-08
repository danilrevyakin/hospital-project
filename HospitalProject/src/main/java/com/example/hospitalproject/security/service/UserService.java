package com.example.hospitalproject.security.service;


import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.InvalidPasswordException;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.exception.UsersByRoleNotFoundException;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import com.example.hospitalproject.security.repository.RepresentativeRepository;
import com.example.hospitalproject.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final static String NOT_FOUND = "Invalid credentials. User wasn't found";
    private final static String INVALID_PASSWORD = "Invalid password";
    private final static String ALREADY_EXISTS = "Email or phone number already exists";
    private final UserRepository userRepository;
    private final RepresentativeRepository representativeRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, RepresentativeRepository representativeRepository) {
        this.userRepository = userRepository;
        this.representativeRepository = representativeRepository;
    }

    public User getUserByCredentials(AuthenticationRequestDto dto){
        Optional<User> user = userRepository.getUserByEmail(dto.getCredentials());
        if(user.isEmpty()){
           user = userRepository.getUserByPhoneNumber(dto.getCredentials());
           if (user.isEmpty()){
               LOG.error(NOT_FOUND);
               throw new NotFoundException(NOT_FOUND);
           }
        }
        return validatePassword(user.get(), dto.getPassword());
    }

    public User validatePassword(User user, String password){
        if(passwordEncoder.matches(password, user.getPassword())){
            LOG.info("Authenticated successfully!");
            return user;
        }else{
            LOG.error(INVALID_PASSWORD);
            throw new InvalidPasswordException();
        }
    }

    public User registerUser(RegistrationDto dto){
        if(checkIfUserNotExist(dto)){
            User user = mapDtoToEntity(dto);
            LOG.info("Created user with email: {}", user.getEmail());
            LOG.debug("Created user : {}", user);
            user = userRepository.save(user);
            userRepository.connect(user);
            return user;
        }else{
            LOG.error(ALREADY_EXISTS);
            throw new UserExistsException();
        }
    }

    private boolean checkIfUserNotExist(RegistrationDto dto){
        User userToCheckEmail = getUserByEmail(dto.getEmail());
        User userToCheckPhoneNumber = getUserByPhoneNumber(dto.getPhoneNumber());
        return Objects.isNull(userToCheckEmail) && Objects.isNull(userToCheckPhoneNumber);
    }

    private User mapDtoToEntity(RegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole(registrationDto.isDoctor() ? Role.DOCTOR : Role.PATIENT);
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        return user;
    }

    public List<User> getAllByRole(Role role){
        return userRepository.getUsersByRole(role).orElseThrow(UsersByRoleNotFoundException::new);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public void deleteUserById(Long id){
        userRepository.deleteUserById(id);
    }

    public User getUserById(Long id){
        Optional<User> user = userRepository.getUserById(id);
        return user.orElse(null);
    }

    public User getUserByEmail(String email){
        Optional<User> user = userRepository.getUserByEmail(email);
        return user.orElse(null);
    }

    public User getUserByPhoneNumber(String phoneNumber){
        Optional<User> user = userRepository.getUserByPhoneNumber(phoneNumber);
        return user.orElse(null);
    }
}