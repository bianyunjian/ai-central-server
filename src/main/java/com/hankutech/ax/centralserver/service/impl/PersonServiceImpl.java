package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankutech.ax.centralserver.dao.PersonDao;
import com.hankutech.ax.centralserver.dao.model.Person;
import com.hankutech.ax.centralserver.pojo.query.PersonParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.request.PersonAddRequest;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonVO;
import com.hankutech.ax.centralserver.service.PersonService;
import com.hankutech.ax.centralserver.support.face.FaceUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonDao _personDao;

    @Override
    public PersonLibraryVO getPersonLibrary() {
        List<Person> personList = _personDao.selectList(null);

        if (personList != null && personList.size() > 0) {
            PersonLibraryVO data = new PersonLibraryVO();
            List<PersonVO> list = personList.stream().map(t -> new PersonVO(t)).collect(Collectors.toList());
            data.setPersonList(list);
            return data;
        }
        return null;

    }

    @Override
    public PagedData<PersonVO> queryPersonTable(PagedParams pagedParams, PersonParams queryParams) {


        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(Person.COL_PERSON_NAME, queryParams.getPersonName());
        queryWrapper.orderByAsc(Person.COL_PERSON_ID);

        IPage<Person> iPage = new Page<>(pagedParams.getPageNum(), pagedParams.getPageSize());
        iPage = _personDao.selectPage(iPage, queryWrapper);

        PagedData<PersonVO> data = new PagedData<>();
        data.setTotal(iPage.getTotal());
        if (iPage.getTotal() > 0) {
            List<PersonVO> list = new ArrayList<>();
            for (Person p : iPage.getRecords()) {
                list.add(new PersonVO(p));
            }
            data.setList(list);
        }
        return data;
    }

    @Override
    public PersonVO addPerson(PersonAddRequest request) throws InvalidObjectException {
        Person newPerson = new Person();
        newPerson.setPersonName(request.getPersonName());
        newPerson.setPhoneNum(request.getPhoneNum());
        newPerson.setImage(request.getImage());
        String faceFtrArrayString = FaceUtil.getFaceFtrArrayString(newPerson.getImage());
        newPerson.setFaceFtrArray(faceFtrArrayString);
        _personDao.insert(newPerson);
        return new PersonVO(newPerson);
    }

    @Override
    public void deletePerson(Integer personId) {
        QueryWrapper<Person> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq(Person.COL_PERSON_ID, personId);
        _personDao.delete(deleteWrapper);
    }
}
