package com.pcms.repository;

import com.pcms.model.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface LoginRepository {

    @Select("""
        SELECT
            *
        FROM user
        WHERE
            LOWER(email) = LOWER(#{email})
    """)
    @Results({
            @Result(property = "role", column = "role", typeHandler = com.pcms.handler.UserRoleTypeHandler.class)
    })
    Optional<User> findByEmail(@Param("email") String emailAddress);

}
