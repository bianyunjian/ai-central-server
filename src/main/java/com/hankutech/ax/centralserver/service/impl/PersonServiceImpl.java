package com.hankutech.ax.centralserver.service.impl;

import com.hankutech.ax.centralserver.dao.PersonDao;
import com.hankutech.ax.centralserver.dao.model.Person;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonVO;
import com.hankutech.ax.centralserver.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
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
            List<PersonVO> list = personList.stream().map(t -> {
                PersonVO vo = new PersonVO();
                vo.setId(t.getPersonId());
                vo.setName(t.getPersonName());

                String faceFTRStr = (StringUtils.isEmpty(t.getFaceFtrArray()) ? "" : t.getFaceFtrArray()).replace('[', ' ').replace(']', ' ').trim();

                if (StringUtils.isEmpty(faceFTRStr) == false) {

                    Float[] ftrArray = (Arrays.stream(faceFTRStr
                            .split(","))
                            .map(s -> Float.parseFloat(s))
                            .collect(Collectors.toList())).toArray(new Float[0]);
                    vo.setFaceFtrArray(ftrArray);
                }

                return vo;
            }).collect(Collectors.toList());
            data.setPersonList(list);
            return data;
        }
        return null;

    }
}
