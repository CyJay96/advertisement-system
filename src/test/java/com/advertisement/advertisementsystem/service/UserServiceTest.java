package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.builder.user.UserRequestTestBuilder;
import com.advertisement.advertisementsystem.builder.user.UserResponseTestBuilder;
import com.advertisement.advertisementsystem.builder.user.UserTestBuilder;
import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.UserMapper;
import com.advertisement.advertisementsystem.model.dto.request.UserRequest;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.dto.response.UserResponse;
import com.advertisement.advertisementsystem.model.entity.User;
import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.repository.UserRepository;
import com.advertisement.advertisementsystem.service.impl.UserServiceImpl;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.auth.BasicUserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_EMAIL;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_PAGE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_PAGE_SIZE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private final UserRequest userRequest = UserRequestTestBuilder.aUserRequest().build();
    private final UserResponse expectedUserResponse = UserResponseTestBuilder.aUserResponse().build();
    private final User expectedUser = UserTestBuilder.aUser().build();
    private final Pageable pageable = PageRequest.of(TEST_PAGE, TEST_PAGE_SIZE);
    private final Principal correctPrincipal = new BasicUserPrincipal(TEST_STRING);
    private final Principal incorrectPrincipal = new BasicUserPrincipal(TEST_EMAIL);

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    @DisplayName("Find all Users")
    void checkFindAllShouldReturnUserResponsePage() {
        doReturn(new PageImpl<>(List.of(expectedUser))).when(userRepository).findAll(pageable);
        doReturn(expectedUserResponse).when(userMapper).toUserResponse(expectedUser);

        PageResponse<UserResponse> actualUsers = userService.findAll(pageable);

        verify(userRepository).findAll(eq(pageable));
        verify(userMapper).toUserResponse(any());

        assertThat(actualUsers.getContent().stream()
                .anyMatch(actualUserResponse -> actualUserResponse.equals(expectedUserResponse))
        ).isTrue();
    }

    @Nested
    public class FindByIdTest {
        @DisplayName("Find User by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkFindByIdShouldReturnUserResponse(Long id) {
            doReturn(Optional.of(expectedUser)).when(userRepository).findById(id);
            doReturn(Optional.of(expectedUser)).when(userRepository).findByUsername(correctPrincipal.getName());
            doReturn(expectedUser).when(userRepository).save(expectedUser);
            doReturn(expectedUserResponse).when(userMapper).toUserResponse(expectedUser);

            UserResponse actualUser = userService.findById(id, correctPrincipal);

            verify(userRepository).findById(anyLong());
            verify(userRepository).findByUsername(anyString());
            verify(userRepository).save(any());
            verify(userMapper).toUserResponse(any());

            assertThat(actualUser).isEqualTo(expectedUserResponse);
        }

        @Test
        @DisplayName("Find User by ID; not found")
        void checkFindByIdShouldThrowUserNotFoundException() {
            doThrow(EntityNotFoundException.class).when(userRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> userService.findById(TEST_ID, correctPrincipal));

            verify(userRepository).findById(anyLong());
        }

        @Test
        @DisplayName("Find User by ID; access denied")
        void checkFindByIdShouldThrowAccessDeniedException() {
            doReturn(Optional.of(expectedUser)).when(userRepository).findById(TEST_ID);
            doReturn(Optional.of(expectedUser)).when(userRepository).findByUsername(incorrectPrincipal.getName());

            assertThrows(AccessDeniedException.class, () -> userService.findById(TEST_ID, incorrectPrincipal));

            verify(userRepository).findById(anyLong());
            verify(userRepository).findByUsername(anyString());
        }
    }

    @Nested
    public class FindByUsernameTest {
        @Test
        @DisplayName("Find User by Username")
        void checkFindByUsernameShouldReturnUserResponse() {
            doReturn(Optional.of(expectedUser)).when(userRepository).findByUsername(correctPrincipal.getName());
            doReturn(expectedUserResponse).when(userMapper).toUserResponse(expectedUser);

            UserResponse actualUser = userService.findByUsername(TEST_STRING, correctPrincipal);

            verify(userRepository, times(2)).findByUsername(anyString());
            verify(userMapper).toUserResponse(any());

            assertThat(actualUser).isEqualTo(expectedUserResponse);
        }

        @Test
        @DisplayName("Find User by Username; not found")
        void checkFindByUsernameShouldThrowUserNotFoundException() {
            doThrow(EntityNotFoundException.class).when(userRepository).findByUsername(correctPrincipal.getName());

            assertThrows(EntityNotFoundException.class, () -> userService.findByUsername(TEST_STRING, correctPrincipal));

            verify(userRepository).findByUsername(anyString());
        }
    }

    @Nested
    public class UpdateTest {
        @DisplayName("Update User by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateShouldReturnUserResponse(Long id) {
            doReturn(Optional.of(expectedUser)).when(userRepository).findById(id);
            doReturn(Optional.of(expectedUser)).when(userRepository).findByUsername(correctPrincipal.getName());
            doNothing().when(userMapper).updateUser(userRequest, expectedUser);
            doReturn(expectedUser).when(userRepository).save(expectedUser);
            doReturn(expectedUserResponse).when(userMapper).toUserResponse(expectedUser);

            UserResponse actualUser = userService.update(id, userRequest, correctPrincipal);

            verify(userRepository).findById(anyLong());
            verify(userRepository).findByUsername(anyString());
            verify(userMapper).updateUser(any(), any());
            verify(userRepository).save(any());
            verify(userMapper).toUserResponse(any());

            assertThat(actualUser).isEqualTo(expectedUserResponse);
        }

        @DisplayName("Update User by ID with Argument Captor")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateWithArgumentCaptorShouldReturnUserResponse(Long id) {
            doReturn(Optional.of(expectedUser)).when(userRepository).findById(id);
            doReturn(Optional.of(expectedUser)).when(userRepository).findByUsername(correctPrincipal.getName());
            doNothing().when(userMapper).updateUser(userRequest, expectedUser);
            doReturn(expectedUser).when(userRepository).save(expectedUser);
            doReturn(expectedUserResponse).when(userMapper).toUserResponse(expectedUser);

            userService.update(id, userRequest, correctPrincipal);

            verify(userRepository).findById(anyLong());
            verify(userRepository).findByUsername(anyString());
            verify(userMapper).updateUser(any(), any());
            verify(userRepository).save(userCaptor.capture());
            verify(userMapper).toUserResponse(any());

            assertThat(userCaptor.getValue()).isEqualTo(expectedUser);
        }

        @Test
        @DisplayName("Update User by ID; not found")
        void checkUpdateShouldThrowUserNotFoundException() {
            doThrow(EntityNotFoundException.class).when(userRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class,
                    () -> userService.update(TEST_ID, userRequest, correctPrincipal)
            );

            verify(userRepository).findById(anyLong());
        }

        @Test
        @DisplayName("Update User by ID; access denied")
        void checkUpdateByIdShouldThrowAccessDeniedException() {
            doReturn(Optional.of(expectedUser)).when(userRepository).findById(TEST_ID);
            doReturn(Optional.of(expectedUser)).when(userRepository).findByUsername(incorrectPrincipal.getName());

            assertThrows(AccessDeniedException.class, () -> userService.update(TEST_ID, userRequest, incorrectPrincipal));

            verify(userRepository).findById(anyLong());
            verify(userRepository).findByUsername(anyString());
        }
    }

    @Nested
    public class RestoreByIdTest {
        @DisplayName("Restore User by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkRestoreByIdShouldReturnUserResponse(Long id) {
            doReturn(Optional.of(expectedUser)).when(userRepository).findById(id);
            doReturn(expectedUser).when(userRepository).save(expectedUser);
            doReturn(expectedUserResponse).when(userMapper).toUserResponse(expectedUser);

            UserResponse actualUser = userService.restoreById(id);

            verify(userRepository).findById(anyLong());
            verify(userRepository).save(any());
            verify(userMapper).toUserResponse(any());

            assertThat(actualUser.getStatus()).isEqualTo(Status.ACTIVE);
        }

        @Test
        @DisplayName("Restore User by ID; not found")
        void checkDeleteByIdShouldThrowUserNotFoundException() {
            doThrow(EntityNotFoundException.class).when(userRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> userService.restoreById(TEST_ID));

            verify(userRepository).findById(anyLong());
        }
    }

    @Nested
    public class DeleteByIdTest {
        @DisplayName("Delete User by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkDeleteByIdShouldReturnUserResponse(Long id) {
            UserResponse userResponse = UserResponseTestBuilder.aUserResponse().build();
            userResponse.setStatus(Status.DELETED);

            doReturn(Optional.of(expectedUser)).when(userRepository).findById(id);
            doReturn(expectedUser).when(userRepository).save(expectedUser);
            doReturn(userResponse).when(userMapper).toUserResponse(expectedUser);

            UserResponse actualUser = userService.deleteById(id);

            verify(userRepository).findById(anyLong());
            verify(userRepository).save(any());
            verify(userMapper).toUserResponse(any());

            assertThat(actualUser.getStatus()).isEqualTo(Status.DELETED);
        }

        @Test
        @DisplayName("Delete User by ID; not found")
        void checkDeleteByIdShouldThrowUserNotFoundException() {
            doThrow(EntityNotFoundException.class).when(userRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> userService.deleteById(TEST_ID));

            verify(userRepository).findById(anyLong());
        }
    }
}
