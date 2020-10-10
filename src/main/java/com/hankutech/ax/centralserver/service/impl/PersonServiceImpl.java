package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hankutech.ax.centralserver.constant.Common;
import com.hankutech.ax.centralserver.constant.ErrorCode;
import com.hankutech.ax.centralserver.dao.FaceDao;
import com.hankutech.ax.centralserver.dao.FaceLibraryDao;
import com.hankutech.ax.centralserver.dao.PersonDao;
import com.hankutech.ax.centralserver.dao.model.Face;
import com.hankutech.ax.centralserver.dao.model.FaceLibrary;
import com.hankutech.ax.centralserver.dao.model.Person;
import com.hankutech.ax.centralserver.exception.InvalidDataException;
import com.hankutech.ax.centralserver.pojo.query.PersonParams;
import com.hankutech.ax.centralserver.pojo.request.PagedParams;
import com.hankutech.ax.centralserver.pojo.request.PersonAddRequest;
import com.hankutech.ax.centralserver.pojo.response.PagedData;
import com.hankutech.ax.centralserver.pojo.vo.PersonLibraryVO;
import com.hankutech.ax.centralserver.pojo.vo.PersonVO;
import com.hankutech.ax.centralserver.service.PersonService;
import com.hankutech.ax.centralserver.support.face.FaceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Resource
    private FaceLibraryDao faceLibraryMapper;
    @Resource
    private FaceDao faceMapper;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PersonVO addPerson(PersonAddRequest request) throws InvalidObjectException, InvalidDataException {
        // 检测人员是否名称重复
        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Person.COL_PERSON_NAME, request.getPersonName());
        Person repeatOne = _personDao.selectOne(queryWrapper);
        if (null != repeatOne) {
            throw new InvalidDataException(MessageFormat.format("人员名称={0}重复", request.getPersonName()))
                    .with(ErrorCode.PERSON_REPEAT_NAME);
        }
        Person newPerson = new Person();
        newPerson.setPersonName(request.getPersonName());
        newPerson.setPhoneNum(request.getPhoneNum());
        newPerson.setImage(request.getImage());
        String faceFtrArrayString = FaceUtil.getFaceFtrArrayString(newPerson.getImage());
        newPerson.setFaceFtrArray(faceFtrArrayString);

        _personDao.insert(newPerson);

        syncAddFace(getFaceLibraryId(), newPerson.getPersonName(), newPerson.getFaceFtrArray());

        return new PersonVO(newPerson);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePerson(Integer personId) throws InvalidDataException {
        // 检测人员是否存在
        Person existOne = _personDao.selectById(personId);
        if (null == existOne) {
            throw new InvalidDataException(MessageFormat.format("ID={0}的人员不存在", personId))
                    .with(ErrorCode.PERSON_NOT_EXIST);
        }
        QueryWrapper<Person> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq(Person.COL_PERSON_ID, personId);
        _personDao.delete(deleteWrapper);

        syncDeleteFace(getFaceLibraryId(), existOne.getPersonName());
    }

    public int getFaceLibraryId() {
        QueryWrapper<FaceLibrary> qw = new QueryWrapper<>();
        qw.orderByAsc(FaceLibrary.COL_ID);
        List<FaceLibrary> faceLibraryList = faceLibraryMapper.selectList(qw);
        if (faceLibraryList == null || faceLibraryList.size() == 0) {
            log.error("无法找到有效的人脸库");
            log.info("初始化默认的人脸库");
            //init default faceLibrary and face
            FaceLibrary newFaceLibrary = new FaceLibrary();
            newFaceLibrary.setProductId(1);
            newFaceLibrary.setName("demo");
            newFaceLibrary.setDescription("default demo face library");
            faceLibraryMapper.insert(newFaceLibrary);

            Integer newFaceLibraryId = newFaceLibrary.getId();

            List<Person> existPersonList = _personDao.selectList(null);
            if (existPersonList != null && existPersonList.size() > 0) {
                for (Person p :
                        existPersonList) {
                    Face newFace = new Face();
                    newFace.setFaceLibraryId(newFaceLibraryId);
                    newFace.setPersonName(p.getPersonName());
                    newFace.setFeatures(p.getFaceFtrArray().getBytes(StandardCharsets.UTF_8));
                    faceMapper.insert(newFace);
                }
                notifyFaceAIServiceReload();
            }
            return newFaceLibraryId;

        }
        return faceLibraryList.get(0).getId();
    }


    private void syncAddFace(int faceLibraryId, String personName, String faceFtrArray) {

        Face newFace = new Face();
        newFace.setFaceLibraryId(faceLibraryId);
        newFace.setPersonName(personName);
        newFace.setImageUrl("");
        newFace.setFeatures(faceFtrArray.getBytes(StandardCharsets.UTF_8));
        faceMapper.insert(newFace);
        notifyFaceAIServiceReload();
    }

    private void syncDeleteFace(int faceLibraryId, String personName) {
        QueryWrapper<Face> qw = new QueryWrapper<>();
        qw.eq(Face.COL_FACE_LIBRARY_ID, faceLibraryId);
        qw.eq(Face.COL_PERSON_NAME, personName);
        faceMapper.delete(qw);
        notifyFaceAIServiceReload();
    }

    private void notifyFaceAIServiceReload() {
        int faceLibraryId = getFaceLibraryId();
        System.out.println("notifyFaceAIServiceReload request:" + faceLibraryId);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        String path = Common.FACE_NOTIFY_SERVICE_URL + "?productId=1&apikey=1&requestFaceLibraryId=" + faceLibraryId;
        HttpGet httpGet = new HttpGet(path);
        try {
            response = httpClient.execute(httpGet);
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                System.out.println("notifyFaceAIServiceReload:" + strResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
