package com.office.agijagi_back.Controller;

import com.office.agijagi_back.Dto.ChildNoteDto;
import com.office.agijagi_back.Service.ChildNoteService;
import com.office.agijagi_back.Service.ResponseService;
import com.office.agijagi_back.Util.Response.ListResult;
import com.office.agijagi_back.Util.Response.SingleResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/childHealth")
public class ChildNoteController {

    private final ChildNoteService childNoteService;
    private final ResponseService responseService;

    public ChildNoteController(ChildNoteService childNoteService, ResponseService responseService) {
        this.childNoteService = childNoteService;
        this.responseService = responseService;
    }

    private String getUserName() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
    @ApiOperation(httpMethod = "POST"
            , value = "해당 자녀의 건강기록 등록"
            , notes = "register child Note"
            , response = Integer.class
            , responseContainer = "SingleResult")
    @PostMapping("/childNote/{childNo}")
    public SingleResult<Integer> registerChildNote(@ApiParam ChildNoteDto childNoteDto,
                                                   @PathVariable String childNo){
        log.info("[ChildNoteController] registerChildNote");
        childNoteDto.setU_email(getUserName());

        return responseService.getSingleResult(childNoteService.registerChildNote(childNoteDto));
    }

    @ApiOperation(httpMethod = "GET"
            , value = "해당 자녀의 건강기록 조회"
            , notes = "search child Note"
            , response = ChildNoteDto.class
            , responseContainer = "ListResult")
    @GetMapping("/childNotes/{childNo}")
    public SingleResult<Map<String, Object>> searchChildNotes(@PathVariable String childNo){
        log.info("[ChildNoteController] searchChildNotes");

        return responseService.getSingleResult(childNoteService.searchChildNotes(childNo,getUserName()));
    }

    @ApiOperation(httpMethod = "GET"
            , value = "해당 자녀의 특정 건강 기록 조회"
            , notes = "search child Note"
            , response = ChildNoteDto.class
            , responseContainer = "ListResult")
    @GetMapping("/childNotes/{childNo}/{healthNo}")
    public SingleResult<ChildNoteDto> searchChildHealthNote(@PathVariable int childNo, @PathVariable int healthNo){
        log.info("[ChildNoteController] searchChildHealthNote");
        ChildNoteDto childNoteDto = new ChildNoteDto(healthNo, childNo,getUserName());

        return responseService.getSingleResult(childNoteService.searchChildHealthNote(childNoteDto));
    }

    @ApiOperation(httpMethod = "GET"
            , value = "모든 자녀의 접종내역 조회"
            , notes = "search children Inoculation Notes"
            , response = ChildNoteDto.class
            , responseContainer = "ListResult")
    @GetMapping("/inoculationNotes")
    public ListResult<ChildNoteDto> searchChildrenInoculationNotes(){
        log.info("[ChildNoteController] searchChildrenInoculationNotes");

        return responseService.getListResult(childNoteService.searchChildrenInoculationNotes(getUserName()));
    }

    @ApiOperation(httpMethod = "DELETE"
            , value = "해당 자녀 해당 접종내역 삭제"
            , notes = "delete child Inoculation NOTE"
            , response = ChildNoteDto.class
            , responseContainer = "SingleResult")
    @DeleteMapping("/childNote/{childNo}/{healthNo}")
    public SingleResult<Integer> deleteChildInoculationNote(@PathVariable int childNo, @PathVariable int healthNo){
        log.info("[ChildNoteController] deleteChildInoculationNote");
        ChildNoteDto childNoteDto = new ChildNoteDto(healthNo, childNo, getUserName());

        return responseService.getSingleResult(childNoteService.deleteChildNote(childNoteDto));
    }
  @ApiOperation(httpMethod = "PUT"
            , value = "해당 자녀 해당 접종 내역 수정"
            , notes = "update child Inoculation NOTE"
            , response = ChildNoteDto.class
            , responseContainer = "SingleResult")
    @PutMapping("/childNote/{childNo}/{healthNo}")
    public SingleResult<Integer> modifyChildNote(@ApiParam ChildNoteDto childNoteDto, @PathVariable int childNo, @PathVariable int healthNo){
        log.info("[ChildNoteController] modifyChildNote");
        childNoteDto.setU_email(getUserName());

        return responseService.getSingleResult(childNoteService.modifyChildNote(childNoteDto));
    }


}
