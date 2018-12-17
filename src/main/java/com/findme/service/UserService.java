package com.findme.service;

import com.findme.models.User;

public interface UserService {

    User save(User user);
    User update(User user);
    void delete(Long id);

}
