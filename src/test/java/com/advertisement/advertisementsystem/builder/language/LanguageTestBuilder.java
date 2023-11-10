package com.advertisement.advertisementsystem.builder.language;

import com.advertisement.advertisementsystem.builder.TestBuilder;
import com.advertisement.advertisementsystem.model.entity.Language;
import com.advertisement.advertisementsystem.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import static com.advertisement.advertisementsystem.util.TestConstants.TEST_ID;
import static com.advertisement.advertisementsystem.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aLanguage")
public class LanguageTestBuilder implements TestBuilder<Language> {

    private Long id = TEST_ID;

    private String name = TEST_STRING;

    private Status status = Status.ACTIVE;

    @Override
    public Language build() {
        return Language.builder()
                .id(id)
                .name(name)
                .status(status)
                .build();
    }
}
