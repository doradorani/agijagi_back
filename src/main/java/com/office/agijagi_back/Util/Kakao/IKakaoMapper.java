package com.office.agijagi_back.Util.Kakao;


import com.office.agijagi_back.Dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IKakaoMapper {

    int isUser(String email);
    int insertUser(KakaoTokenDto kakaoTokenDto);
    int getStatusByEmail(String email);
    String getLoginNicknameByEmail(String email);
    String getLoginProfileByEmail(String email);
    int returnUser(String email);
    int comeBackUser(String email);
}
