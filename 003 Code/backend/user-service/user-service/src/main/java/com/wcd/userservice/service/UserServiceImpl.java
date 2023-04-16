package com.wcd.userservice.service;

import com.wcd.userservice.dto.UserDto;
import com.wcd.userservice.jpa.UserEntity;
import com.wcd.userservice.jpa.UserRepository;
import com.wcd.userservice.vo.RequestUpdateUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    Environment env;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, Environment env) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
    }

    /*
        UserDetailsService를 상속받아서 재정의 해줘야함
        loginId를 가지고 사용자를 찾아오는 메서드
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLoginId(username);

        // 해당하는 사용자가 없다면
        if(userEntity == null)
            // spring security-core에 사용자 정보를 담을 수 있는 UsernamePasswordAuthenticationToken이 있음
            // 마찬가지로 사용자 검색이 안되었을 때, 발생할 수 있는 예외 클래스도 제공
            throw new UsernameNotFoundException(username);

        // User: Spring Security에서 제공해주는 User 모델
        return new User(userEntity.getLoginId(), userEntity.getEncryptedPwd(),
                true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByLoginId(String loginId) {
        UserEntity userEntity = userRepository.findByLoginId(loginId);

        if (userEntity == null)
            throw new UsernameNotFoundException(loginId);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public UserDto updateUserById(Long userId, RequestUpdateUser requestUpdateUser) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userEntity.setName(requestUpdateUser.getName());
        userEntity.setPhoneNumber(requestUpdateUser.getPhoneNumber());
        userEntity.setBirthday(requestUpdateUser.getBirthday());
        userEntity.setGender(requestUpdateUser.getGender());
        userEntity.setProfileImage(requestUpdateUser.getProfile_image());

        UserEntity newUser = userRepository.save(userEntity);

        UserDto userDto = new ModelMapper().map(newUser, UserDto.class);

        return userDto;
    }

    @Override
    public void deleteUser(Long userId) {
        UserDto userDto = getUserById(userId);

        UserEntity userEntity = new ModelMapper().map(userDto, UserEntity.class);

        userRepository.delete(userEntity);
    }

}
