package com.wcd.userservice.service;

import com.wcd.userservice.client.ClubServiceClient;
import com.wcd.userservice.dto.RegenerateTokenDto;
import com.wcd.userservice.dto.TokenDto;
import com.wcd.userservice.dto.UserDto;
import com.wcd.userservice.dto.UserEvaluationDto;
import com.wcd.userservice.entity.UserEntity;
import com.wcd.userservice.entity.UserEvaluationEntity;
import com.wcd.userservice.exception.CustomException;
import com.wcd.userservice.repository.UserEvaluationRepository;
import com.wcd.userservice.repository.UserRepository;
import com.wcd.userservice.security.jwt.JwtTokenProvider;
import com.wcd.userservice.vo.RequestUpdateUser;
import com.wcd.userservice.vo.ResponseClub;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    UserEvaluationRepository userEvaluationRepository;
    BCryptPasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;

    Environment env;

    ClubServiceClient clubServiceClient;

    RedisTemplate<String, String> redisTemplate;

    public UserServiceImpl(UserRepository userRepository, UserEvaluationRepository userEvaluationRepository, BCryptPasswordEncoder passwordEncoder, Environment env, ClubServiceClient clubServiceClient, RedisTemplate<String, String> redisTemplate, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userEvaluationRepository = userEvaluationRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.clubServiceClient = clubServiceClient;
        this.redisTemplate = redisTemplate;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*
        UserDetailsService를 상속받아서 재정의 해줘야함
        loginId를 가지고 사용자를 찾아오는 메서드
     */


    @Override
    public TokenDto regenerateToken(RegenerateTokenDto refreshTokenDto) {
        String refresh_token = refreshTokenDto.getRefresh_token();

        // Refresh Token 검증
        if(!jwtTokenProvider.validateRefreshToken(refresh_token)) {
            throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
        }

        // Access Token에서 userId 가져오기
        Authentication authentication = jwtTokenProvider.getAuthenticationByRefreshToken(refresh_token);

        // Redis에 저장된 Refresh Token 값 가져오기
        String refreshToken = redisTemplate.opsForValue().get(authentication.getName());
        if(!refreshToken.equals(refresh_token)) {
            throw new CustomException("Refresh Token doesn't match.", HttpStatus.BAD_REQUEST);
        }

        // 토큰 재발행
        String new_refresh_token = jwtTokenProvider.generateRefreshToken(authentication);

        TokenDto tokenDto = new TokenDto(
                jwtTokenProvider.generateAccessToken(authentication),
                new_refresh_token
        );

        redisTemplate.opsForValue().set(
                authentication.getName(),
                new_refresh_token,
                Long.parseLong(env.getProperty("refresh_token.expiration_time")),
                TimeUnit.MICROSECONDS
        );

        return tokenDto;
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
    public void logout(TokenDto tokenDto) {
        // 로그아웃 하고 싶은 토큰이 유효한 지 검증
        if(!jwtTokenProvider.validateAccessToken(tokenDto.getAccess_token())) {
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access Token에서 userId를 가져옴
        Authentication authentication = jwtTokenProvider.getAuthenticationByAccessToken(tokenDto.getAccess_token());

        // Redis에서 해당 userId로 저장된 Refresh Token이 있는지 여부 확인
        if ( redisTemplate.opsForValue().get(authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete(authentication.getName());
        }

        // 해당 Access Token 유효시간을 가져와 BlackList에 저장장
        Long expiration = jwtTokenProvider.getAccessTokenExpiration(tokenDto.getAccess_token());
        redisTemplate.opsForValue().set(tokenDto.getAccess_token(), "logout", expiration);
    }

    @Override
    public UserDto getUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<ResponseClub> clubList = clubServiceClient.getClubByUserId(userId);
        userDto.setJoinClubList(clubList);

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

    @Override
    public UserEvaluationDto getUserEvaluationByUserId(Long userId) {
        UserEvaluationEntity userEvaluationEntity = userEvaluationRepository.findByUserId(userId);

        UserEvaluationDto userEvaluationDto = new ModelMapper().map(userEvaluationEntity, UserEvaluationDto.class);

        return userEvaluationDto;
    }
}
