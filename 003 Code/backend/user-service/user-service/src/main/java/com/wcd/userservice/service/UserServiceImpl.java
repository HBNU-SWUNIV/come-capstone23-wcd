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
import com.wcd.userservice.vo.RequestUpdateUser;
import com.wcd.userservice.vo.ResponseClub;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    UserEvaluationRepository userEvaluationRepository;
    BCryptPasswordEncoder passwordEncoder;

    Environment env;

    ClubServiceClient clubServiceClient;

    RedisTemplate<String, String> redisTemplate;

    public UserServiceImpl(UserRepository userRepository, UserEvaluationRepository userEvaluationRepository, BCryptPasswordEncoder passwordEncoder, Environment env, ClubServiceClient clubServiceClient, RedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.userEvaluationRepository = userEvaluationRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.clubServiceClient = clubServiceClient;
        this.redisTemplate = redisTemplate;
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
        userDto.setUserId(UUID.randomUUID().toString());

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

    @Override
    public TokenDto regenerateToken(RegenerateTokenDto refreshTokenDto) {
        String refresh_token = refreshTokenDto.getRefresh_token();
        String userPrincipal = null;
        try {
            // Refresh Token 검증
            Key secretKey = Keys.hmacShaKeyFor(env.getProperty("refresh_token.secret").getBytes(StandardCharsets.UTF_8));

            // JWT 토큰을 파싱하기 위한 빌더 객체 생성 및 토큰에 사용될 서명 키 설정
            JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(secretKey);
            userPrincipal = jwtParserBuilder
                    .build()
                    // 파싱 대상 JWT 토큰을 Jws(JWT Signature를 포함하는 객체) 객체로 파싱
                    .parseClaimsJws(refresh_token)
                    // 파싱된 JWT 내용을 가져옴
                    .getBody()
                    // JWT의 subject 값을 가져옴
                    .getSubject();
        } catch (Exception ex) {
            log.error("jwtParser = {}", ex.getMessage());
        }

        UserDetails userDetails = loadUserByUsername(userPrincipal);

        String refreshToken = redisTemplate.opsForValue().get(userDetails.getUsername());
        if(!refreshToken.equals(refresh_token)) {
            throw new CustomException("Refresh Token doesn't match.", HttpStatus.BAD_REQUEST);
        }

        Key refresh_secretKey = Keys.hmacShaKeyFor(env.getProperty("refresh_token.secret").getBytes(StandardCharsets.UTF_8));

        String newRefreshToken = Jwts.builder()
                // JWT 토큰의 subject를 설정
                .setSubject(userDetails.getUsername())
                // JWT 토큰의 만료 시간 설정(현재 시간 + token.expiration_time 값)
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("refresh_token.expiration_time"))))
                // JWT 토큰에 서명 추가
                .signWith(refresh_secretKey, SignatureAlgorithm.HS512)
                // JWT 토큰을 문자열로 변환
                .compact();

        Key access_secretKey = Keys.hmacShaKeyFor(env.getProperty("access_token.secret").getBytes(StandardCharsets.UTF_8));

        String accessToken = Jwts.builder()
                // JWT 토큰의 subject를 설정
                .setSubject(userDetails.getUsername())
                // JWT 토큰의 만료 시간 설정(현재 시간 + token.expiration_time 값)
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("refresh_token.expiration_time"))))
                // JWT 토큰에 서명 추가
                .signWith(access_secretKey, SignatureAlgorithm.HS512)
                // JWT 토큰을 문자열로 변환
                .compact();

        TokenDto tokenDto = new TokenDto(
                accessToken,
                newRefreshToken
        );

        redisTemplate.opsForValue().set(
                userDetails.getUsername(),
                newRefreshToken,
                Long.parseLong(env.getProperty("refresh_token_expiration_time")),
                TimeUnit.MICROSECONDS
        );

        return tokenDto;
    }

    public boolean validateRefreshToken(String refresh_token) {
        boolean returnValue = true;
        String subject = null;



        return returnValue;
    }

}
