package com.advertisement.advertisementsystem.service;

import com.advertisement.advertisementsystem.builder.advertiser.AdvertiserRequestTestBuilder;
import com.advertisement.advertisementsystem.builder.advertiser.AdvertiserResponseTestBuilder;
import com.advertisement.advertisementsystem.builder.advertiser.AdvertiserTestBuilder;
import com.advertisement.advertisementsystem.builder.campaign.CampaignResponseTestBuilder;
import com.advertisement.advertisementsystem.builder.campaign.CampaignTestBuilder;
import com.advertisement.advertisementsystem.exception.EntityNotFoundException;
import com.advertisement.advertisementsystem.mapper.AdvertiserMapper;
import com.advertisement.advertisementsystem.mapper.CampaignMapper;
import com.advertisement.advertisementsystem.model.criteria.AdvertiserCriteria;
import com.advertisement.advertisementsystem.model.dto.request.AdvertiserRequest;
import com.advertisement.advertisementsystem.model.dto.response.AdvertiserResponse;
import com.advertisement.advertisementsystem.model.dto.response.CampaignResponse;
import com.advertisement.advertisementsystem.model.dto.response.PageResponse;
import com.advertisement.advertisementsystem.model.entity.Advertiser;
import com.advertisement.advertisementsystem.model.entity.Campaign;
import com.advertisement.advertisementsystem.model.enums.Status;
import com.advertisement.advertisementsystem.repository.AdvertiserRepository;
import com.advertisement.advertisementsystem.repository.CampaignRepository;
import com.advertisement.advertisementsystem.service.impl.AdvertiserServiceImpl;
import com.advertisement.advertisementsystem.service.searcher.AdvertiserSearcher;
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
class AdvertiserServiceTest {

    private AdvertiserService advertiserService;

    @Mock
    private AdvertiserSearcher advertiserSearcher;

    @Mock
    private AdvertiserRepository advertiserRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private AdvertiserMapper advertiserMapper;

    @Mock
    private CampaignMapper campaignMapper;

    @Captor
    private ArgumentCaptor<Advertiser> advertiserCaptor;

    private final AdvertiserRequest advertiserRequest = AdvertiserRequestTestBuilder.aAdvertiserRequest().build();
    private final AdvertiserResponse expectedAdvertiserResponse = AdvertiserResponseTestBuilder.aAdvertiserResponse().build();
    private final Advertiser expectedAdvertiser = AdvertiserTestBuilder.aAdvertiser().build();
    private final AdvertiserCriteria searchCriteria = AdvertiserCriteria.builder().build();
    private final Campaign expectedCampaign = CampaignTestBuilder.aCampaign().build();
    private final CampaignResponse expectedCampaignResponse = CampaignResponseTestBuilder.aCampaignResponse().build();
    private final Pageable pageable = PageRequest.of(TEST_PAGE, TEST_PAGE_SIZE);

    @BeforeEach
    void setUp() {
        advertiserService = new AdvertiserServiceImpl(advertiserSearcher, advertiserRepository,
                campaignRepository, advertiserMapper, campaignMapper);
    }

    @Nested
    public class SaveTest {
        @Test
        @DisplayName("Save Advertiser")
        void checkSaveShouldReturnAdvertiserResponse() {
            doReturn(expectedAdvertiser).when(advertiserMapper).toAdvertiser(advertiserRequest);
            doReturn(expectedAdvertiser).when(advertiserRepository).save(expectedAdvertiser);
            doReturn(expectedAdvertiserResponse).when(advertiserMapper).toAdvertiserResponse(expectedAdvertiser);

            AdvertiserResponse actualAdvertiser = advertiserService.save(advertiserRequest);

            verify(advertiserMapper).toAdvertiser(any());
            verify(advertiserRepository).save(any());
            verify(advertiserMapper).toAdvertiserResponse(any());

            assertThat(actualAdvertiser).isEqualTo(expectedAdvertiserResponse);
        }

        @Test
        @DisplayName("Save Advertiser with Argument Captor")
        void checkSaveWithArgumentCaptorShouldReturnAdvertiserResponse() {
            doReturn(expectedAdvertiser).when(advertiserMapper).toAdvertiser(advertiserRequest);
            doReturn(expectedAdvertiser).when(advertiserRepository).save(expectedAdvertiser);
            doReturn(expectedAdvertiserResponse).when(advertiserMapper).toAdvertiserResponse(expectedAdvertiser);

            advertiserService.save(advertiserRequest);

            verify(advertiserMapper).toAdvertiser(any());
            verify(advertiserRepository).save(advertiserCaptor.capture());
            verify(advertiserMapper).toAdvertiserResponse(any());

            assertThat(advertiserCaptor.getValue()).isEqualTo(expectedAdvertiser);
        }
    }

    @Test
    @DisplayName("Find all Advertisers")
    void checkFindAllShouldReturnAdvertiserResponsePage() {
        doReturn(new PageImpl<>(List.of(expectedAdvertiser))).when(advertiserRepository).findAllByStatus(any(Status.class), eq(pageable));
        doReturn(expectedAdvertiserResponse).when(advertiserMapper).toAdvertiserResponse(expectedAdvertiser);

        PageResponse<AdvertiserResponse> actualAdvertisers = advertiserService.findAll(pageable);

        verify(advertiserRepository).findAllByStatus(any(Status.class), eq(pageable));
        verify(advertiserMapper).toAdvertiserResponse(any());

        assertThat(actualAdvertisers.getContent().stream()
                .anyMatch(actualAdvertiserResponse -> actualAdvertiserResponse.equals(expectedAdvertiserResponse))
        ).isTrue();
    }

    @Test
    @DisplayName("Find all Advertisers by criteria")
    void checkFindAllByCriteriaShouldReturnAdvertiserResponsePage() {
        doReturn(new PageImpl<>(List.of(expectedAdvertiser))).when(advertiserSearcher).getAdvertiserByCriteria(searchCriteria);
        doReturn(expectedAdvertiserResponse).when(advertiserMapper).toAdvertiserResponse(expectedAdvertiser);

        PageResponse<AdvertiserResponse> actualAdvertisers = advertiserService.findAllByCriteria(searchCriteria, pageable);

        verify(advertiserSearcher).getAdvertiserByCriteria(any());
        verify(advertiserMapper).toAdvertiserResponse(any());

        assertThat(actualAdvertisers.getContent().stream()
                .anyMatch(actualAdvertiserResponse -> actualAdvertiserResponse.equals(expectedAdvertiserResponse))
        ).isTrue();
    }

    @Nested
    public class FindByIdTest {
        @DisplayName("Find Advertiser by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkFindByIdShouldReturnAdvertiserResponse(Long id) {
            doReturn(Optional.of(expectedAdvertiser)).when(advertiserRepository).findById(id);
            doReturn(expectedAdvertiserResponse).when(advertiserMapper).toAdvertiserResponse(expectedAdvertiser);
            doReturn(new PageImpl<>(List.of(expectedCampaign))).when(campaignRepository).findAllByAdvertiserId(id, pageable);
            doReturn(expectedCampaignResponse).when(campaignMapper).toCampaignResponse(expectedCampaign);

            AdvertiserResponse actualAdvertiser = advertiserService.findById(id, pageable);

            verify(advertiserRepository).findById(anyLong());
            verify(advertiserMapper).toAdvertiserResponse(any());
            verify(campaignRepository).findAllByAdvertiserId(anyLong(), eq(pageable));
            verify(campaignMapper).toCampaignResponse(any());

            assertThat(actualAdvertiser).isEqualTo(expectedAdvertiserResponse);
        }

        @Test
        @DisplayName("Find Advertiser by ID; not found")
        void checkFindByIdShouldThrowAdvertiserNotFoundException() {
            doThrow(EntityNotFoundException.class).when(advertiserRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> advertiserService.findById(TEST_ID, pageable));

            verify(advertiserRepository).findById(anyLong());
        }
    }

    @Nested
    public class UpdateTest {
        @DisplayName("Update Advertiser by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateShouldReturnAdvertiserResponse(Long id) {
            doReturn(Optional.of(expectedAdvertiser)).when(advertiserRepository).findById(id);
            doNothing().when(advertiserMapper).updateAdvertiser(advertiserRequest, expectedAdvertiser);
            doReturn(expectedAdvertiser).when(advertiserRepository).save(expectedAdvertiser);
            doReturn(expectedAdvertiserResponse).when(advertiserMapper).toAdvertiserResponse(expectedAdvertiser);

            AdvertiserResponse actualAdvertiser = advertiserService.update(id, advertiserRequest);

            verify(advertiserRepository).findById(anyLong());
            verify(advertiserMapper).updateAdvertiser(any(), any());
            verify(advertiserRepository).save(any());
            verify(advertiserMapper).toAdvertiserResponse(any());

            assertThat(actualAdvertiser).isEqualTo(expectedAdvertiserResponse);
        }

        @DisplayName("Update Advertiser by ID with Argument Captor")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateWithArgumentCaptorShouldReturnAdvertiserResponse(Long id) {
            doReturn(Optional.of(expectedAdvertiser)).when(advertiserRepository).findById(id);
            doNothing().when(advertiserMapper).updateAdvertiser(advertiserRequest, expectedAdvertiser);
            doReturn(expectedAdvertiser).when(advertiserRepository).save(expectedAdvertiser);
            doReturn(expectedAdvertiserResponse).when(advertiserMapper).toAdvertiserResponse(expectedAdvertiser);

            advertiserService.update(id, advertiserRequest);

            verify(advertiserRepository).findById(anyLong());
            verify(advertiserMapper).updateAdvertiser(any(), any());
            verify(advertiserRepository).save(advertiserCaptor.capture());
            verify(advertiserMapper).toAdvertiserResponse(any());

            assertThat(advertiserCaptor.getValue()).isEqualTo(expectedAdvertiser);
        }

        @Test
        @DisplayName("Update Advertiser by ID; not found")
        void checkUpdateShouldThrowAdvertiserNotFoundException() {
            doThrow(EntityNotFoundException.class).when(advertiserRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class,
                    () -> advertiserService.update(TEST_ID, advertiserRequest)
            );

            verify(advertiserRepository).findById(anyLong());
        }
    }

    @Nested
    public class RestoreByIdTest {
        @DisplayName("Restore Advertiser by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkRestoreByIdShouldReturnAdvertiserResponse(Long id) {
            doReturn(Optional.of(expectedAdvertiser)).when(advertiserRepository).findById(id);
            doReturn(expectedAdvertiser).when(advertiserRepository).save(expectedAdvertiser);
            doReturn(expectedAdvertiserResponse).when(advertiserMapper).toAdvertiserResponse(expectedAdvertiser);

            AdvertiserResponse actualAdvertiser = advertiserService.restoreById(id);

            verify(advertiserRepository).findById(anyLong());
            verify(advertiserRepository).save(any());
            verify(advertiserMapper).toAdvertiserResponse(any());

            assertThat(actualAdvertiser.getStatus()).isEqualTo(Status.ACTIVE);
        }

        @Test
        @DisplayName("Restore Advertiser by ID; not found")
        void checkDeleteByIdShouldThrowAdvertiserNotFoundException() {
            doThrow(EntityNotFoundException.class).when(advertiserRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> advertiserService.restoreById(TEST_ID));

            verify(advertiserRepository).findById(anyLong());
        }
    }

    @Nested
    public class DeleteByIdTest {
        @DisplayName("Delete Advertiser by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkDeleteByIdShouldReturnAdvertiserResponse(Long id) {
            AdvertiserResponse advertiserResponse = AdvertiserResponseTestBuilder.aAdvertiserResponse().build();
            advertiserResponse.setStatus(Status.DELETED);

            doReturn(Optional.of(expectedAdvertiser)).when(advertiserRepository).findById(id);
            doReturn(expectedAdvertiser).when(advertiserRepository).save(expectedAdvertiser);
            doReturn(advertiserResponse).when(advertiserMapper).toAdvertiserResponse(expectedAdvertiser);

            AdvertiserResponse actualAdvertiser = advertiserService.deleteById(id);

            verify(advertiserRepository).findById(anyLong());
            verify(advertiserRepository).save(any());
            verify(advertiserMapper).toAdvertiserResponse(any());

            assertThat(actualAdvertiser.getStatus()).isEqualTo(Status.DELETED);
        }

        @Test
        @DisplayName("Delete Advertiser by ID; not found")
        void checkDeleteByIdShouldThrowAdvertiserNotFoundException() {
            doThrow(EntityNotFoundException.class).when(advertiserRepository).findById(anyLong());

            assertThrows(EntityNotFoundException.class, () -> advertiserService.deleteById(TEST_ID));

            verify(advertiserRepository).findById(anyLong());
        }
    }
}
