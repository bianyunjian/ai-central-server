package com.hankutech.ax.centralserver.controller;

import com.hankutech.ax.centralserver.dao.model.Person;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.exception.InvalidParamException;
import com.hankutech.ax.centralserver.pojo.query.PersonParams;
import com.hankutech.ax.centralserver.pojo.request.AbstractObjectRequest;
import com.hankutech.ax.centralserver.pojo.request.IntIdRequest;
import com.hankutech.ax.centralserver.pojo.request.PersonAddRequest;
import com.hankutech.ax.centralserver.pojo.request.QueryRequest;
import com.hankutech.ax.centralserver.pojo.response.BaseResponse;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonVO;
import com.hankutech.ax.centralserver.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * 人脸库接口
 */
@Validated
@Tag(name = "/face", description = "人脸库接口")
@Slf4j
@RequestMapping(path = "/face")
@RestController
public class FaceController {

    private final PersonService _personService;

    @Autowired
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


        PersonVO data = null;
        try {
            data = _personService.addPerson(request);
            if (data != null) {
                resp.success("新增人脸成功", data);
            } else {
                resp.fail("新增人脸失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.fail("新增人脸失败:" + e.getMessage());
        }

        return resp;
    }


    @Operation(summary = "删除人脸")
    @PostMapping(path = "/delete")
    public BaseResponse delete(@RequestBody @Validated IntIdRequest request) {
        BaseResponse response = new BaseResponse();
        Integer id = request.getId();
        try {
            _personService.deletePerson(id);
            response.success("删除人脸成功");
        } catch (InvalidDataException e) {
            e.printStackTrace();
            response.fail("删除人脸失败:" + e.getMessage());
        }


        return response;
    }


    //==================================================================================================================

    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class AddPersonRequest extends AbstractObjectRequest<Person> {
        @NotBlank
        @Schema(description = "人员姓名", example = "王军", required = true)
        private String personName;

        @NotBlank
        @Schema(description = "图片base64字符串", example = "", required = true)
        private String image;

        @Schema(description = "手机号码", example = "15677886767")
        private String phoneNum;

        @Override
        protected void validate() throws InvalidParamException {
        }

        @Override
        protected void format() throws InvalidParamException {
            //todo 剔除base64头部信息?
        }

        @Override
        protected Person buildData() {
            Person model = new Person();

            return null;
        }
    }


}
