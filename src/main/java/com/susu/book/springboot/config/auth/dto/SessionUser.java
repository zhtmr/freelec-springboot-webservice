package com.susu.book.springboot.config.auth.dto;

import com.susu.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

// User 엔티티 값을 받을 session Dto
/*

 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
