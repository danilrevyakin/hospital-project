package com.example.hospitalproject.userInfo.repository;

import com.example.hospitalproject.userInfo.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

}
