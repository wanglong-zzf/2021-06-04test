package com.nclg.service;

import com.nclg.bean.User;

public interface UserService {

    User findByUsername(String username);
}
