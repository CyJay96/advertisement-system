package com.advertisement.advertisementsystem.builder.language;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.dto.request.LanguageRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aLanguageRequest")
public class LanguageRequestTestBuilder implements TestBuilder<LanguageRequest> {

    private String name = TEST_STRING;

    @Override
    public LanguageRequest build() {
        return LanguageRequest.builder()
                .name(name)
                .build();
    }
}
