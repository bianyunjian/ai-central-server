package com.hankutech.ax.centralserver.service;

import com.hankutech.ax.centralserver.pojo.query.PersonParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.request.PersonAddRequest;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonVO;

public interface PersonService {
    PersonLibraryVO getPersonLibrary();

    PagedData<PersonVO> queryPersonTable(PagedParams pagedParams, PersonParams queryParams);

    PersonVO addPerson(PersonAddRequest request);

    void deletePerson(Integer id);
}
