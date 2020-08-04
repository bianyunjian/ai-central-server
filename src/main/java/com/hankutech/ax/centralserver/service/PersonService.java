package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.PersonParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.request.PersonAddRequest;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonVO;

import java.io.InvalidObjectException;

public interface PersonService {
    PersonLibraryVO getPersonLibrary();

    PagedData<PersonVO> queryPersonTable(PagedParams pagedParams, PersonParams queryParams);

    PersonVO addPerson(PersonAddRequest request) throws InvalidObjectException, InvalidDataException;

    void deletePerson(Integer id) throws InvalidDataException;
}
