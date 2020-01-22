package com.aling.core.dto.system;

import lombok.Data;

@Data
public class UserReq extends BaseReq{

    private String userId;
    private String userName;
    private String password;
    private String code;
    private String randomKey;

}
