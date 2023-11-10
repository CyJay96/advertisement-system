package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.builder.advertiser.AdvertiserTestBuilder;
import com.advertisement.advertisementsystem.builder.campaign.CampaignRequestTestBuilder;
import com.advertisement.advertisementsystem.builder.campaign.CampaignResponseTestBuilder;
import com.advertisement.advertisementsystem.builder.campaign.CampaignTestBuilder;
import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.CampaignMapper;
import com.advertisement.advertisementsystem.model.criteria.CampaignCriteria;
import com.advertisement.advertisementsystem.model.dto.request.CampaignRequest;
import com.advertisement.advertisementsystem.model.dto.response.CampaignResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.entity.Advertiser;
import com.advertisement.advertisementsystem.model.entity.Campaign;
import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.repository.AdvertiserRepository;
import com.advertisement.advertisementsystem.repository.CampaignRepository;
import com.advertisement.advertisementsystem.repository.CountryRepository;
import com.advertisement.advertisementsystem.repository.LanguageRepository;
import com.advertisement.advertisementsystem.service.impl.CampaignServiceImpl;
import com.advertisement.advertisementsystem.service.searcher.CampaignSearcher;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_PAGE;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_PAGE_SIZE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    private CampaignService campaignService;

    @Mock
    private CampaignSearcher campaignSearcher;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private AdvertiserRepository advertiserRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private CampaignMapper campaignMapper;

    @Captor
    private ArgumentCaptor<Campaign> campaignCaptor;

    private final CampaignRequest campaignRequest = CampaignRequestTestBuilder.aCampaignRequest().build();
    private final CampaignResponse expectedCampaignResponse = CampaignResponseTestBuilder.aCampaignResponse().build();
    private final Campaign expectedCampaign = CampaignTestBuilder.aCampaign().build();
    private final CampaignCriteria searchCriteria = CampaignCriteria.builder().build();
    private final Advertiser advertiser = AdvertiserTestBuilder.aAdvertiser().build();
    private final Pageable pageable = PageRequest.of(TEST_PAGE, TEST_PAGE_SIZE);

    @BeforeEach
    void setUp() {
        campaignService = new CampaignServiceImpl(campaignSearcher, campaignRepository,
                advertiserRepository, countryRepository, languageRepository, campaignMapper);
    }

    @Nested
    public class SaveTest {
        @Test
        @DisplayName("Save Campaign")
        void checkSaveShouldReturnCampaignResponse() {
            doReturn(Optional.of(advertiser)).when(advertiserRepository).findById(TEST_ID);
            doReturn(expectedCampaign).when(campaignMapper).toCampaign(campaignRequest);
            doReturn(new ArrayList<>()).when(countryRepository).findAllById(new ArrayList<>());
            doReturn(new ArrayList<>()).when(languageRepository).findAllById(new ArrayList<>());
            doReturn(expectedCampaign).when(campaignRepository).save(expectedCampaign);
            doReturn(expectedCampaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

            CampaignResponse actualCampaign = campaignService.save(TEST_ID, campaignRequest);

            verify(advertiserRepository).findById(anyLong());
            verify(campaignMapper).toCampaign(any());
            verify(countryRepository).findAllById(anyList());
            verify(languageRepository).findAllById(anyList());
            verify(campaignRepository).save(any());
            verify(campaignMapper).toCampaignResponse(any());

            assertThat(actualCampaign).isEqualTo(expectedCampaignResponse);
        }

        @Test
        @DisplayName("Save Campaign with Argument Captor")
        void checkSaveWithArgumentCaptorShouldReturnCampaignResponse() {
            doReturn(Optional.of(advertiser)).when(advertiserRepository).findById(TEST_ID);
            doReturn(expectedCampaign).when(campaignMapper).toCampaign(campaignRequest);
            doReturn(new ArrayList<>()).when(countryRepository).findAllById(new ArrayList<>());
            doReturn(new ArrayList<>()).when(languageRepository).findAllById(new ArrayList<>());
            doReturn(expectedCampaign).when(campaignRepository).save(expectedCampaign);
            doReturn(expectedCampaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

            campaignService.save(TEST_ID, campaignRequest);

            verify(advertiserRepository).findById(anyLong());
            verify(campaignMapper).toCampaign(any());
            verify(countryRepository).findAllById(anyList());
            verify(languageRepository).findAllById(anyList());
            verify(campaignRepository).save(campaignCaptor.capture());
            verify(campaignMapper).toCampaignResponse(any());

            assertThat(campaignCaptor.getValue()).isEqualTo(expectedCampaign);
        }
    }

    @Test
    @DisplayName("Find all Campaigns")
    void checkFindAllShouldReturnCampaignResponsePage() {
        doReturn(new PageImpl<>(List.of(expectedCampaign))).when(campaignRepository).findAllByStatus(any(Status.class), eq(pageable));
        doReturn(expectedCampaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

        PageResponse<CampaignResponse> actualCampaigns = campaignService.findAll(pageable);

        verify(campaignRepository).findAllByStatus(any(Status.class), eq(pageable));
        verify(campaignMapper).toCampaignResponse(any());

        assertThat(actualCampaigns.getContent().stream()
                .anyMatch(actualCampaignResponse -> actualCampaignResponse.equals(expectedCampaignResponse))
        ).isTrue();
    }

    @Test
    @DisplayName("Find all Campaigns by criteria")
    void checkFindAllByCriteriaShouldReturnCampaignResponsePage() {
        doReturn(new PageImpl<>(List.of(expectedCampaign))).when(campaignSearcher).getCampaignByCriteria(searchCriteria);
        doReturn(expectedCampaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

        PageResponse<CampaignResponse> actualCampaigns = campaignService.findAllByCriteria(searchCriteria, pageable);

        verify(campaignSearcher).getCampaignByCriteria(any());
        verify(campaignMapper).toCampaignResponse(any());

        assertThat(actualCampaigns.getContent().stream()
                .anyMatch(actualCampaignResponse -> actualCampaignResponse.equals(expectedCampaignResponse))
        ).isTrue();
    }

    @Nested
    public class FindByIdTest {
        @DisplayName("Find Campaign by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkFindByIdShouldReturnCampaignResponse(Long id) {
            doReturn(Optional.of(expectedCampaign)).when(campaignRepository).findById(id);
            doReturn(expectedCampaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

            CampaignResponse actualCampaign = campaignService.findById(id);

            verify(campaignRepository).findById(anyLong());
            verify(campaignMapper).toCampaignResponse(any());

            assertThat(actualCampaign).isEqualTo(expectedCampaignResponse);
        }

        @Test
        @DisplayName("Find Campaign by ID; not found")
        void checkFindByIdShouldThrowCampaignNotFoundException() {
            doThrow(EntityNotFoundException.class).when(campaignRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> campaignService.findById(TEST_ID));

            verify(campaignRepository).findById(anyLong());
        }
    }

    @Nested
    public class UpdateTest {
        @DisplayName("Update Campaign by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateShouldReturnCampaignResponse(Long id) {
            doReturn(Optional.of(expectedCampaign)).when(campaignRepository).findById(id);
            doNothing().when(campaignMapper).updateCampaign(campaignRequest, expectedCampaign);
            doReturn(expectedCampaign).when(campaignRepository).save(expectedCampaign);
            doReturn(expectedCampaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

            CampaignResponse actualCampaign = campaignService.update(id, campaignRequest);

            verify(campaignRepository).findById(anyLong());
            verify(campaignMapper).updateCampaign(any(), any());
            verify(campaignRepository).save(any());
            verify(campaignMapper).toCampaignResponse(any());

            assertThat(actualCampaign).isEqualTo(expectedCampaignResponse);
        }

        @DisplayName("Update Campaign by ID with Argument Captor")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateWithArgumentCaptorShouldReturnCampaignResponse(Long id) {
            doReturn(Optional.of(expectedCampaign)).when(campaignRepository).findById(id);
            doNothing().when(campaignMapper).updateCampaign(campaignRequest, expectedCampaign);
            doReturn(expectedCampaign).when(campaignRepository).save(expectedCampaign);
            doReturn(expectedCampaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

            campaignService.update(id, campaignRequest);

            verify(campaignRepository).findById(anyLong());
            verify(campaignMapper).updateCampaign(any(), any());
            verify(campaignRepository).save(campaignCaptor.capture());
            verify(campaignMapper).toCampaignResponse(any());

            assertThat(campaignCaptor.getValue()).isEqualTo(expectedCampaign);
        }

        @Test
        @DisplayName("Update Campaign by ID; not found")
        void checkUpdateShouldThrowCampaignNotFoundException() {
            doThrow(EntityNotFoundException.class).when(campaignRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class,
                    () -> campaignService.update(TEST_ID, campaignRequest)
            );

            verify(campaignRepository).findById(anyLong());
        }
    }

    @Nested
    public class RestoreByIdTest {
        @DisplayName("Restore Campaign by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkRestoreByIdShouldReturnCampaignResponse(Long id) {
            doReturn(Optional.of(expectedCampaign)).when(campaignRepository).findById(id);
            doReturn(expectedCampaign).when(campaignRepository).save(expectedCampaign);
            doReturn(expectedCampaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

            CampaignResponse actualCampaign = campaignService.restoreById(id);

            verify(campaignRepository).findById(anyLong());
            verify(campaignRepository).save(any());
            verify(campaignMapper).toCampaignResponse(any());

            assertThat(actualCampaign.getStatus()).isEqualTo(Status.ACTIVE);
        }

        @Test
        @DisplayName("Restore Campaign by ID; not found")
        void checkDeleteByIdShouldThrowCampaignNotFoundException() {
            doThrow(EntityNotFoundException.class).when(campaignRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> campaignService.restoreById(TEST_ID));

            verify(campaignRepository).findById(anyLong());
        }
    }

    @Nested
    public class DeleteByIdTest {
        @DisplayName("Delete Campaign by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkDeleteByIdShouldReturnCampaignResponse(Long id) {
            CampaignResponse campaignResponse = CampaignResponseTestBuilder.aCampaignResponse().build();
            campaignResponse.setStatus(Status.DELETED);

            doReturn(Optional.of(expectedCampaign)).when(campaignRepository).findById(id);
            doReturn(expectedCampaign).when(campaignRepository).save(expectedCampaign);
            doReturn(campaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

            CampaignResponse actualCampaign = campaignService.deleteById(id);

            verify(campaignRepository).findById(anyLong());
            verify(campaignRepository).save(any());
            verify(campaignMapper).toCampaignResponse(any());

            assertThat(actualCampaign.getStatus()).isEqualTo(Status.DELETED);
        }

        @Test
        @DisplayName("Delete Campaign by ID; not found")
        void checkDeleteByIdShouldThrowCampaignNotFoundException() {
            doThrow(EntityNotFoundException.class).when(campaignRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> campaignService.deleteById(TEST_ID));

            verify(campaignRepository).findById(anyLong());
        }
    }
}
