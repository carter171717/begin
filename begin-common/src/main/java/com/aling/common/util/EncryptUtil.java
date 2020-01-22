package com.aling.common.util;

import com.aling.common.entity.User;

public class EncryptUtil {

    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 1024;

    public static void encryptPassword(User user, String password) throws Exception {
        byte[] salt = DigestUtils.generateSalt(SALT_SIZE);
        user.setSalt(EncodesUtils.encodeHex(salt));
        byte[] hashLoginPwd = DigestUtils.sha1(password.getBytes("UTF-8"), salt,
                HASH_INTERATIONS);
        user.setPassword(EncodesUtils.encodeHex(hashLoginPwd));
    }

    public static String encodeToPasswordHex(User user, String password) throws Exception {
        byte[] salt = EncodesUtils.decodeHex(user.getSalt());
        byte[] hashLoginPwd = DigestUtils.sha1(password.getBytes("UTF-8"), salt,
                HASH_INTERATIONS);
        return EncodesUtils.encodeHex(hashLoginPwd);
    }
}
