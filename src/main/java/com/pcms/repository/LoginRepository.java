package com.pcms.repository;

import com.pcms.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface LoginRepository {

    @Select("""
        SELECT
            *
        FROM user
        WHERE
            email = #{emailAddress}
            AND
            password = SHA256(#{password}, 256)
    """)
    Optional<User> login(@Param("emailAddress") String emailAddress, @Param("password") String password);

}
