package com.office.agijagi_back.Mapper;


import com.office.agijagi_back.Dto.ChildNoteDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IChildNoteMapper {
    List<ChildNoteDto> selectChildNotesByEmail(String email);

    List<ChildNoteDto> selectChildNotesByNoAndEmail(String childNo, String email);
}