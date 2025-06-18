package com.pcms.mapper;

import com.pcms.handler.repository.UserRoleTypeHandler;
import com.pcms.model.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("""
                SELECT
                    *
                FROM user
                WHERE
                    LOWER(email) = LOWER(#{email})
            """)
    @Results({
            @Result(property = "role", column = "role", typeHandler = UserRoleTypeHandler.class)
    })
    Optional<User> findByEmail(@Param("email") String emailAddress);

    @Update("""
        UPDATE user
        SET password = #{password}
        WHERE email = #{email}
    """)
    int changePassword(@Param("email") String emailAddress, @Param("password") String password);

}
