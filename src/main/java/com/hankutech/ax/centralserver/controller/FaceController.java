package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.pojo.query.PersonParams;
import com.hankutech.ax.centralserver.pojo.request.IntIdRequest;
import com.hankutech.ax.centralserver.pojo.request.PersonAddRequest;
import com.hankutech.ax.centralserver.pojo.request.QueryRequest;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonVO;
import com.hankutech.ax.centralserver.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 人脸库接口
 */
@Validated
@Tag(name = "/face", description = "人脸库接口")
@Slf4j
@RequestMapping(path = "/face")
@RestController
public class FaceController {

    private PersonService _personService;

    public FaceController(PersonService personService) {
        this._personService = personService;
    }

    @Operation(summary = "获取所有人脸")
    @GetMapping(path = "/all")
    public BaseResponse<PersonLibraryVO> getAll() {
        BaseResponse<PersonLibraryVO> resp = new BaseResponse<>();
        PersonLibraryVO data = _personService.getPersonLibrary();
        resp.success("获取所有人脸成功", data);
        return resp;
    }

    @Operation(summary = "分页查询人脸")
    @PostMapping(path = "/table")
    public BaseResponse<PagedData<PersonVO>> queryTable(@RequestBody @Validated QueryRequest<PersonParams> request) {
        PagedData<PersonVO> data = _personService.queryPersonTable(request.getPagedParams(), request.getQueryParams());
        BaseResponse<PagedData<PersonVO>> resp = new BaseResponse<>();
        resp.success("分页查询人脸成功", data);
        return resp;
    }

    @Operation(summary = "新增人脸")
    @PostMapping(path = "/add")
    public BaseResponse<PersonVO> add(@RequestBody @Validated PersonAddRequest request) {
        BaseResponse<PersonVO> resp = new BaseResponse<PersonVO>();
        try {
            PersonVO data = _personService.addPerson(request);
            resp.success("新增人脸成功", data);
        } catch (Exception ex) {
            resp.fail(ex.getMessage());
        }

        return resp;
    }


    @Operation(summary = "删除人脸")
    @PostMapping(path = "/delete")
    public BaseResponse delete(@RequestBody @Validated IntIdRequest request) {
        Integer id = request.getId();
        _personService.deletePerson(id);
        BaseResponse response = new BaseResponse();
        response.success("删除人脸成功");
        return response;
    }


}
