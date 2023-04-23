package com.wcd.userservice.controller;

import com.wcd.userservice.security.jwt.dto.RegenerateTokenDto;
import com.wcd.userservice.security.jwt.dto.TokenDto;
import com.wcd.userservice.dto.UserDto;
import com.wcd.userservice.dto.UserEvaluationDto;
import com.wcd.userservice.service.UserService;
import com.wcd.userservice.vo.request.RequestUpdateUser;
import com.wcd.userservice.vo.request.RequestUser;
import com.wcd.userservice.vo.request.RequestUserEvaluation;
import com.wcd.userservice.vo.response.ResponseUser;
import com.wcd.userservice.vo.response.ResponseUserEvaluation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    private Environment env;
    private UserService userService;

    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("local.server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@Valid RegenerateTokenDto refreshTokenDto) {
        TokenDto tokenDto = userService.regenerateToken(refreshTokenDto);

        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseUser> createUser(@RequestBody @Valid RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        // source와 destination 필드의 이름과 타입이 완전히 일치하는 경우에만 매핑을 수행
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userDto = userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @PostMapping("/users/logout")
    public ResponseEntity<Void> logout(@RequestBody TokenDto tokenDto) {
        userService.logout(tokenDto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{user-id}")
    public ResponseEntity<ResponseUser> getUserById(@PathVariable("user-id") Long userId) {
        UserDto userDto = userService.getUserById(userId);

        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PutMapping("/users/{user-id}")
    public ResponseEntity<ResponseUser> updateUser(@PathVariable("user-id") Long userId, @RequestBody @Valid RequestUpdateUser requestUpdateUser) {
        UserDto userDto = userService.updateUserById(userId, requestUpdateUser);

        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @DeleteMapping("/users/{user-id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{user-id}/evaluation")
    public ResponseEntity<ResponseUserEvaluation> createUserEvaluation(@PathVariable("user-id") Long userId, @RequestBody @Valid RequestUserEvaluation requestUserEvaluation) {
        UserEvaluationDto userEvaluationDto = userService.createUserEvaluationByUserId(userId, requestUserEvaluation);

        ResponseUserEvaluation returnValue = new ModelMapper().map(userEvaluationDto, ResponseUserEvaluation.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @GetMapping("/users/{user-id}/evaluation")
    public ResponseEntity<ResponseUserEvaluation> getUserEvaluation(@PathVariable("user-id") Long userId) {
        UserEvaluationDto userEvaluationDto = userService.getUserEvaluationByUserId(userId);

        ResponseUserEvaluation returnValue = new ModelMapper().map(userEvaluationDto, ResponseUserEvaluation.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
