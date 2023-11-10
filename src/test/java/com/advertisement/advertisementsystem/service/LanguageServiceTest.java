package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.builder.language.LanguageRequestTestBuilder;
import com.advertisement.advertisementsystem.builder.language.LanguageResponseTestBuilder;
import com.advertisement.advertisementsystem.builder.language.LanguageTestBuilder;
import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.LanguageMapper;
import com.advertisement.advertisementsystem.model.dto.request.LanguageRequest;
import com.advertisement.advertisementsystem.model.dto.response.LanguageResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.entity.Language;
import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.repository.LanguageRepository;
import com.advertisement.advertisementsystem.service.impl.LanguageServiceImpl;
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

import java.util.List;
import java.util.Optional;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_PAGE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_PAGE_SIZE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {

    private LanguageService languageService;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private LanguageMapper languageMapper;

    @Captor
    private ArgumentCaptor<Language> languageCaptor;

    private final LanguageRequest languageRequest = LanguageRequestTestBuilder.aLanguageRequest().build();
    private final LanguageResponse expectedLanguageResponse = LanguageResponseTestBuilder.aLanguageResponse().build();
    private final Language expectedLanguage = LanguageTestBuilder.aLanguage().build();
    private final Pageable pageable = PageRequest.of(TEST_PAGE, TEST_PAGE_SIZE);

    @BeforeEach
    void setUp() {
        languageService = new LanguageServiceImpl(languageRepository, languageMapper);
    }

    @Nested
    public class SaveTest {
        @Test
        @DisplayName("Save Language")
        void checkSaveShouldReturnLanguageResponse() {
            doReturn(expectedLanguage).when(languageMapper).toLanguage(languageRequest);
            doReturn(expectedLanguage).when(languageRepository).save(expectedLanguage);
            doReturn(expectedLanguageResponse).when(languageMapper).toLanguageResponse(expectedLanguage);

            LanguageResponse actualLanguage = languageService.save(languageRequest);

            verify(languageMapper).toLanguage(any());
            verify(languageRepository).save(any());
            verify(languageMapper).toLanguageResponse(any());

            assertThat(actualLanguage).isEqualTo(expectedLanguageResponse);
        }

        @Test
        @DisplayName("Save Language with Argument Captor")
        void checkSaveWithArgumentCaptorShouldReturnLanguageResponse() {
            doReturn(expectedLanguage).when(languageMapper).toLanguage(languageRequest);
            doReturn(expectedLanguage).when(languageRepository).save(expectedLanguage);
            doReturn(expectedLanguageResponse).when(languageMapper).toLanguageResponse(expectedLanguage);

            languageService.save(languageRequest);

            verify(languageMapper).toLanguage(any());
            verify(languageRepository).save(languageCaptor.capture());
            verify(languageMapper).toLanguageResponse(any());

            assertThat(languageCaptor.getValue()).isEqualTo(expectedLanguage);
        }
    }

    @Test
    @DisplayName("Find all Languages")
    void checkFindAllShouldReturnLanguageResponsePage() {
        doReturn(new PageImpl<>(List.of(expectedLanguage))).when(languageRepository).findAllByStatus(any(Status.class), eq(pageable));
        doReturn(expectedLanguageResponse).when(languageMapper).toLanguageResponse(expectedLanguage);

        PageResponse<LanguageResponse> actualLanguages = languageService.findAll(pageable);

        verify(languageRepository).findAllByStatus(any(Status.class), eq(pageable));
        verify(languageMapper).toLanguageResponse(any());

        assertThat(actualLanguages.getContent().stream()
                .anyMatch(actualLanguageResponse -> actualLanguageResponse.equals(expectedLanguageResponse))
        ).isTrue();
    }

    @Nested
    public class FindByIdTest {
        @DisplayName("Find Language by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkFindByIdShouldReturnLanguageResponse(Long id) {
            doReturn(Optional.of(expectedLanguage)).when(languageRepository).findById(id);
            doReturn(expectedLanguageResponse).when(languageMapper).toLanguageResponse(expectedLanguage);

            LanguageResponse actualLanguage = languageService.findById(id);

            verify(languageRepository).findById(anyLong());
            verify(languageMapper).toLanguageResponse(any());

            assertThat(actualLanguage).isEqualTo(expectedLanguageResponse);
        }

        @Test
        @DisplayName("Find Language by ID; not found")
        void checkFindByIdShouldThrowLanguageNotFoundException() {
            doThrow(EntityNotFoundException.class).when(languageRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> languageService.findById(TEST_ID));

            verify(languageRepository).findById(anyLong());
        }
    }

    @Nested
    public class UpdateTest {
        @DisplayName("Update Language by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateShouldReturnLanguageResponse(Long id) {
            doReturn(Optional.of(expectedLanguage)).when(languageRepository).findById(id);
            doNothing().when(languageMapper).updateLanguage(languageRequest, expectedLanguage);
            doReturn(expectedLanguage).when(languageRepository).save(expectedLanguage);
            doReturn(expectedLanguageResponse).when(languageMapper).toLanguageResponse(expectedLanguage);

            LanguageResponse actualLanguage = languageService.update(id, languageRequest);

            verify(languageRepository).findById(anyLong());
            verify(languageMapper).updateLanguage(any(), any());
            verify(languageRepository).save(any());
            verify(languageMapper).toLanguageResponse(any());

            assertThat(actualLanguage).isEqualTo(expectedLanguageResponse);
        }

        @DisplayName("Update Language by ID with Argument Captor")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateWithArgumentCaptorShouldReturnLanguageResponse(Long id) {
            doReturn(Optional.of(expectedLanguage)).when(languageRepository).findById(id);
            doNothing().when(languageMapper).updateLanguage(languageRequest, expectedLanguage);
            doReturn(expectedLanguage).when(languageRepository).save(expectedLanguage);
            doReturn(expectedLanguageResponse).when(languageMapper).toLanguageResponse(expectedLanguage);

            languageService.update(id, languageRequest);

            verify(languageRepository).findById(anyLong());
            verify(languageMapper).updateLanguage(any(), any());
            verify(languageRepository).save(languageCaptor.capture());
            verify(languageMapper).toLanguageResponse(any());

            assertThat(languageCaptor.getValue()).isEqualTo(expectedLanguage);
        }

        @Test
        @DisplayName("Update Language by ID; not found")
        void checkUpdateShouldThrowLanguageNotFoundException() {
            doThrow(EntityNotFoundException.class).when(languageRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class,
                    () -> languageService.update(TEST_ID, languageRequest)
            );

            verify(languageRepository).findById(anyLong());
        }
    }

    @Nested
    public class RestoreByIdTest {
        @DisplayName("Restore Language by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkRestoreByIdShouldReturnLanguageResponse(Long id) {
            doReturn(Optional.of(expectedLanguage)).when(languageRepository).findById(id);
            doReturn(expectedLanguage).when(languageRepository).save(expectedLanguage);
            doReturn(expectedLanguageResponse).when(languageMapper).toLanguageResponse(expectedLanguage);

            LanguageResponse actualLanguage = languageService.restoreById(id);

            verify(languageRepository).findById(anyLong());
            verify(languageRepository).save(any());
            verify(languageMapper).toLanguageResponse(any());

            assertThat(actualLanguage.getStatus()).isEqualTo(Status.ACTIVE);
        }

        @Test
        @DisplayName("Restore Language by ID; not found")
        void checkDeleteByIdShouldThrowLanguageNotFoundException() {
            doThrow(EntityNotFoundException.class).when(languageRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> languageService.restoreById(TEST_ID));

            verify(languageRepository).findById(anyLong());
        }
    }

    @Nested
    public class DeleteByIdTest {
        @DisplayName("Delete Language by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkDeleteByIdShouldReturnLanguageResponse(Long id) {
            LanguageResponse languageResponse = LanguageResponseTestBuilder.aLanguageResponse().build();
            languageResponse.setStatus(Status.DELETED);

            doReturn(Optional.of(expectedLanguage)).when(languageRepository).findById(id);
            doReturn(expectedLanguage).when(languageRepository).save(expectedLanguage);
            doReturn(languageResponse).when(languageMapper).toLanguageResponse(expectedLanguage);

            LanguageResponse actualLanguage = languageService.deleteById(id);

            verify(languageRepository).findById(anyLong());
            verify(languageRepository).save(any());
            verify(languageMapper).toLanguageResponse(any());

            assertThat(actualLanguage.getStatus()).isEqualTo(Status.DELETED);
        }

        @Test
        @DisplayName("Delete Language by ID; not found")
        void checkDeleteByIdShouldThrowLanguageNotFoundException() {
            doThrow(EntityNotFoundException.class).when(languageRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> languageService.deleteById(TEST_ID));

            verify(languageRepository).findById(anyLong());
        }
    }
}
