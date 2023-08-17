package com.wcd.userservice.service.user;

import com.wcd.userservice.dto.user.request.RequestUsernames;
import com.wcd.userservice.dto.user.response.ResponseUsernames;
import com.wcd.userservice.exception.UserNotFoundException;
import com.wcd.userservice.file.FileStore;
import com.wcd.userservice.client.ClubServiceClient;
import com.wcd.userservice.dto.user.response.ResponseUserById;
import com.wcd.userservice.entity.Users;
import com.wcd.userservice.repository.UserRepository;
import com.wcd.userservice.dto.user.request.RequestUpdateUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ClubServiceClient clubServiceClient;
    private final FileStore fileStore;


    @Override
    public ResponseUserById getUserById(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return ResponseUserById.builder().user(user).build();
    }

    @Transactional
    @Override
    public Long updateUserById(Long userId, RequestUpdateUser requestUpdateUser) {
        Users user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        String profileImageUrl = null;

        try {
            fileStore.deleteFile(user.getProfileImage());
            profileImageUrl = fileStore.storeFile(requestUpdateUser.getProfileImage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.updateUser(requestUpdateUser, profileImageUrl);

        return userId;
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
        clubServiceClient.deleteMember(userId);
    }

    @Override
    public ResponseUsernames getUserNamesByIds(RequestUsernames requestUsernames) {
        List<Users> users = userRepository.findByIdIn(requestUsernames.getUserIds());

        return ResponseUsernames.builder().userList(users).build();
    }

    @Override
    public String getUserNameById(Long userId) {
<<<<<<< HEAD
        Users users = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
=======
        Users users = userRepository.findById(userId).orElseThrow();
>>>>>>> b738a79786a9b7ffc7e20ab9212949cefe1144b5

        return users.getName();
    }
}
