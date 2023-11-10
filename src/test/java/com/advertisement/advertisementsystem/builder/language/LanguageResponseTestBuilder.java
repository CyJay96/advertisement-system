package com.advertisement.advertisementsystem.builder.language;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.dto.response.LanguageResponse;
import com.advertisement.advertisementsystem.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aLanguageResponse")
public class LanguageResponseTestBuilder implements TestBuilder<LanguageResponse> {

    private Long id = TEST_ID;

    private String name = TEST_STRING;

    private Status status = Status.ACTIVE;

    @Override
    public LanguageResponse build() {
        return LanguageResponse.builder()
                .id(id)
                .name(name)
                .status(status)
                .build();
    }
}
