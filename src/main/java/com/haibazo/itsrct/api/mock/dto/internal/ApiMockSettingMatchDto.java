package com.haibazo.itsrct.api.mock.dto.internal;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiMockSettingMatchDto {

    private ApiMockSettingDto mockSetting;
    private String matchedPattern;
    private Map<String, String> pathVariables;

    public ApiMockSettingMatchDto(ApiMockSettingDto mockSetting, String matchedPattern,
            Map<String, String> pathVariables) {
        this.mockSetting = mockSetting;
        this.matchedPattern = matchedPattern;
        this.pathVariables = pathVariables;
    }

}
