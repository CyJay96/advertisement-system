package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.builder.country.CountryRequestTestBuilder;
import com.advertisement.advertisementsystem.builder.country.CountryResponseTestBuilder;
import com.advertisement.advertisementsystem.builder.country.CountryTestBuilder;
import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.CountryMapper;
import com.advertisement.advertisementsystem.model.dto.request.CountryRequest;
import com.advertisement.advertisementsystem.model.dto.response.CountryResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.entity.Country;
import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.repository.CountryRepository;
import com.advertisement.advertisementsystem.service.impl.CountryServiceImpl;
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
class CountryServiceTest {

    private CountryService countryService;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @Captor
    private ArgumentCaptor<Country> countryCaptor;

    private final CountryRequest countryRequest = CountryRequestTestBuilder.aCountryRequest().build();
    private final CountryResponse expectedCountryResponse = CountryResponseTestBuilder.aCountryResponse().build();
    private final Country expectedCountry = CountryTestBuilder.aCountry().build();
    private final Pageable pageable = PageRequest.of(TEST_PAGE, TEST_PAGE_SIZE);

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl(countryRepository, countryMapper);
    }

    @Nested
    public class SaveTest {
        @Test
        @DisplayName("Save Country")
        void checkSaveShouldReturnCountryResponse() {
            doReturn(expectedCountry).when(countryMapper).toCountry(countryRequest);
            doReturn(expectedCountry).when(countryRepository).save(expectedCountry);
            doReturn(expectedCountryResponse).when(countryMapper).toCountryResponse(expectedCountry);

            CountryResponse actualCountry = countryService.save(countryRequest);

            verify(countryMapper).toCountry(any());
            verify(countryRepository).save(any());
            verify(countryMapper).toCountryResponse(any());

            assertThat(actualCountry).isEqualTo(expectedCountryResponse);
        }

        @Test
        @DisplayName("Save Country with Argument Captor")
        void checkSaveWithArgumentCaptorShouldReturnCountryResponse() {
            doReturn(expectedCountry).when(countryMapper).toCountry(countryRequest);
            doReturn(expectedCountry).when(countryRepository).save(expectedCountry);
            doReturn(expectedCountryResponse).when(countryMapper).toCountryResponse(expectedCountry);

            countryService.save(countryRequest);

            verify(countryMapper).toCountry(any());
            verify(countryRepository).save(countryCaptor.capture());
            verify(countryMapper).toCountryResponse(any());

            assertThat(countryCaptor.getValue()).isEqualTo(expectedCountry);
        }
    }

    @Test
    @DisplayName("Find all Countries")
    void checkFindAllShouldReturnCountryResponsePage() {
        doReturn(new PageImpl<>(List.of(expectedCountry))).when(countryRepository).findAllByStatus(any(Status.class), eq(pageable));
        doReturn(expectedCountryResponse).when(countryMapper).toCountryResponse(expectedCountry);

        PageResponse<CountryResponse> actualCountries = countryService.findAll(pageable);

        verify(countryRepository).findAllByStatus(any(Status.class), eq(pageable));
        verify(countryMapper).toCountryResponse(any());

        assertThat(actualCountries.getContent().stream()
                .anyMatch(actualCountryResponse -> actualCountryResponse.equals(expectedCountryResponse))
        ).isTrue();
    }

    @Nested
    public class FindByIdTest {
        @DisplayName("Find Country by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkFindByIdShouldReturnCountryResponse(Long id) {
            doReturn(Optional.of(expectedCountry)).when(countryRepository).findById(id);
            doReturn(expectedCountryResponse).when(countryMapper).toCountryResponse(expectedCountry);

            CountryResponse actualCountry = countryService.findById(id);

            verify(countryRepository).findById(anyLong());
            verify(countryMapper).toCountryResponse(any());

            assertThat(actualCountry).isEqualTo(expectedCountryResponse);
        }

        @Test
        @DisplayName("Find Country by ID; not found")
        void checkFindByIdShouldThrowCountryNotFoundException() {
            doThrow(EntityNotFoundException.class).when(countryRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> countryService.findById(TEST_ID));

            verify(countryRepository).findById(anyLong());
        }
    }

    @Nested
    public class UpdateTest {
        @DisplayName("Update Country by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateShouldReturnCountryResponse(Long id) {
            doReturn(Optional.of(expectedCountry)).when(countryRepository).findById(id);
            doNothing().when(countryMapper).updateCountry(countryRequest, expectedCountry);
            doReturn(expectedCountry).when(countryRepository).save(expectedCountry);
            doReturn(expectedCountryResponse).when(countryMapper).toCountryResponse(expectedCountry);

            CountryResponse actualCountry = countryService.update(id, countryRequest);

            verify(countryRepository).findById(anyLong());
            verify(countryMapper).updateCountry(any(), any());
            verify(countryRepository).save(any());
            verify(countryMapper).toCountryResponse(any());

            assertThat(actualCountry).isEqualTo(expectedCountryResponse);
        }

        @DisplayName("Update Country by ID with Argument Captor")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateWithArgumentCaptorShouldReturnCountryResponse(Long id) {
            doReturn(Optional.of(expectedCountry)).when(countryRepository).findById(id);
            doNothing().when(countryMapper).updateCountry(countryRequest, expectedCountry);
            doReturn(expectedCountry).when(countryRepository).save(expectedCountry);
            doReturn(expectedCountryResponse).when(countryMapper).toCountryResponse(expectedCountry);

            countryService.update(id, countryRequest);

            verify(countryRepository).findById(anyLong());
            verify(countryMapper).updateCountry(any(), any());
            verify(countryRepository).save(countryCaptor.capture());
            verify(countryMapper).toCountryResponse(any());

            assertThat(countryCaptor.getValue()).isEqualTo(expectedCountry);
        }

        @Test
        @DisplayName("Update Country by ID; not found")
        void checkUpdateShouldThrowCountryNotFoundException() {
            doThrow(EntityNotFoundException.class).when(countryRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class,
                    () -> countryService.update(TEST_ID, countryRequest)
            );

            verify(countryRepository).findById(anyLong());
        }
    }

    @Nested
    public class RestoreByIdTest {
        @DisplayName("Restore Country by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkRestoreByIdShouldReturnCountryResponse(Long id) {
            doReturn(Optional.of(expectedCountry)).when(countryRepository).findById(id);
            doReturn(expectedCountry).when(countryRepository).save(expectedCountry);
            doReturn(expectedCountryResponse).when(countryMapper).toCountryResponse(expectedCountry);

            CountryResponse actualCountry = countryService.restoreById(id);

            verify(countryRepository).findById(anyLong());
            verify(countryRepository).save(any());
            verify(countryMapper).toCountryResponse(any());

            assertThat(actualCountry.getStatus()).isEqualTo(Status.ACTIVE);
        }

        @Test
        @DisplayName("Restore Country by ID; not found")
        void checkDeleteByIdShouldThrowCountryNotFoundException() {
            doThrow(EntityNotFoundException.class).when(countryRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> countryService.restoreById(TEST_ID));

            verify(countryRepository).findById(anyLong());
        }
    }

    @Nested
    public class DeleteByIdTest {
        @DisplayName("Delete Country by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkDeleteByIdShouldReturnCountryResponse(Long id) {
            CountryResponse countryResponse = CountryResponseTestBuilder.aCountryResponse().build();
            countryResponse.setStatus(Status.DELETED);

            doReturn(Optional.of(expectedCountry)).when(countryRepository).findById(id);
            doReturn(expectedCountry).when(countryRepository).save(expectedCountry);
            doReturn(countryResponse).when(countryMapper).toCountryResponse(expectedCountry);

            CountryResponse actualCountry = countryService.deleteById(id);

            verify(countryRepository).findById(anyLong());
            verify(countryRepository).save(any());
            verify(countryMapper).toCountryResponse(any());

            assertThat(actualCountry.getStatus()).isEqualTo(Status.DELETED);
        }

        @Test
        @DisplayName("Delete Country by ID; not found")
        void checkDeleteByIdShouldThrowCountryNotFoundException() {
            doThrow(EntityNotFoundException.class).when(countryRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> countryService.deleteById(TEST_ID));

            verify(countryRepository).findById(anyLong());
        }
    }
}
