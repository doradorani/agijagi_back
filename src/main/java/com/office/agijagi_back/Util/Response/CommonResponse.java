package com.office.agijagi_back.Util.Response;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {
    SUCCESS(200, "성공하였습니다."),
    FAIL(401, "실패하였습니다.");

    private @SQLInjectionSafe int code;
    private @SQLInjectionSafe String msg;
}