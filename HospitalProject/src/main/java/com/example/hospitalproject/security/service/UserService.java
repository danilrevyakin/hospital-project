package com.example.hospitalproject.security.service;


import com.example.hospitalproject.security.config.Matcher;
import com.example.hospitalproject.security.dto.AuthenticationRequestDto;
import com.example.hospitalproject.security.dto.AuthenticationResponseDto;
import com.example.hospitalproject.security.dto.RegistrationDto;
import com.example.hospitalproject.security.exception.InvalidPasswordException;
import com.example.hospitalproject.security.exception.UsersByRoleNotFoundException;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.mapper.Mapper;
import com.example.hospitalproject.security.node.Representative;
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
    private final Mapper<User, RegistrationDto, AuthenticationResponseDto> userMapper;
    private final UserRepository userRepository;
    private final RepresentativeRepository representativeRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(Mapper<User, RegistrationDto, AuthenticationResponseDto> userMapper, UserRepository userRepository, RepresentativeRepository representativeRepository) {
        this.userMapper = userMapper;
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
        User userToCheckEmail = getUserByEmail(dto.getEmail());
        User userToCheckPhoneNumber = getUserByPhoneNumber(dto.getPhoneNumber());

        if(Objects.isNull(userToCheckEmail) && Objects.isNull(userToCheckPhoneNumber)){
            if(true){
                User user = userMapper.mapDtoToEntity(dto);
                Role role = dto.isDoctor() ? Role.DOCTOR : Role.PATIENT;
                Representative representative = representativeRepository.getByRole(role);
                user.setRepresentative(representative);
                LOG.info("Created user with email: {}", user.getEmail());
                LOG.debug("Created user : {}", user);
                return userRepository.save(user);
            }
        }else{
            LOG.error(ALREADY_EXISTS);
            throw new UserExistsException(ALREADY_EXISTS);
        }
        return null;
    }

//    public boolean validateDto(RegistrationDto dto){
//
//    }

    public List<User> getAllByRole(Role role){
        return userRepository.getUsersByRole(role).orElseThrow(UsersByRoleNotFoundException::new);
    }

    public void saveAll(List<User> users){
        userRepository.saveAll(users);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
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