package com.office.agijagi_back.Service;

import com.office.agijagi_back.Dto.ChildDto;
import com.office.agijagi_back.Dto.ChildNoteDto;
import com.office.agijagi_back.Mapper.IChildNoteMapper;
import com.office.agijagi_back.Mapper.IDiaryMapper;
import com.office.agijagi_back.Service.Interface.IChildNoteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class ChildNoteService implements IChildNoteService {
    private final IChildNoteMapper childNoteMapper;
    private final IDiaryMapper diaryMapper;

    public ChildNoteService(IChildNoteMapper childNoteMapper, IDiaryMapper diaryMapper) {
        this.childNoteMapper = childNoteMapper;
        this.diaryMapper = diaryMapper;
    }

    @Override
    public int registerChildNote(ChildNoteDto childNoteDto) {
        return 0;
    }

    @Override
    public Map<String, Object> searchChildNotes(String childNo, String email) {
        log.info("[ChildNoteService] searchChildNotes");

        Map<String, Object> result = new HashMap<>();

        List<ChildDto> childDtos = diaryMapper.selectChildrenByEmail(email);
        List<ChildNoteDto> childNoteDtos = childNoteMapper.selectChildNotesByNoAndEmail(childNo, email);
        result.put("childDtos", childDtos);
        result.put("childNoteDtos", childNoteDtos);
        return result;
    }

    @Override
    public ChildNoteDto searchChildNoteByNo(String email, int childNo) {
        return null;
    }

    @Override
    public int modifyChildNote(ChildNoteDto childNoteDto) {
        return 0;
    }

    @Override
    public int deleteChildNote(int cnNo) {
        return 0;
    }

    @Override
    public List<ChildNoteDto> searchChildrenNotes(String email) {
        log.info("[ChildNoteService] searchChildrenNotes");
        return childNoteMapper.selectChildNotesByEmail(email);
    }
}
