package com.hankutech.ax.centralserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hankutech.ax.centralserver.dao.UserDao;
import com.hankutech.ax.centralserver.dao.model.User;
import com.hankutech.ax.centralserver.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
