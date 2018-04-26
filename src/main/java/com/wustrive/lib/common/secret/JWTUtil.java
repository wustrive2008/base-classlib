package com.wustrive.lib.common.secret;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.wustrive.lib.common.properties.PropertiesConfig;
import com.wustrive.lib.core.exception.BusinessException;
import com.wustrive.lib.util.DateUtil;
import com.wustrive.lib.util.StringUtil;
import net.minidev.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Description: jwt工具类
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/2/23 11:54
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class JWTUtil {

    private final static String JWT_SECRET;

    private final static int JWT_EXP;

    static {
        String jwtSecret = PropertiesConfig.getProperty("jwt.secret");
        if (StringUtil.isNotBlank(jwtSecret)) {
            JWT_SECRET = jwtSecret;
        } else {
            JWT_SECRET = "d4d07ded4d084c8ca9e7eff124fa73b9";
        }
        String jwtExp = PropertiesConfig.getProperty("jwt.exp");
        if (StringUtil.isNotBlank(jwtSecret) && StringUtil.isInteger(jwtExp)) {
            JWT_EXP = Integer.valueOf(jwtExp);
        } else {
            JWT_EXP = 100;
        }
    }

    /**
     * 验证jwtTemplate 是否有效
     *
     * @param jwsObject
     * @return
     * @throws BusinessException
     */
    public static boolean verify(JWSObject jwsObject) throws BusinessException, JOSEException {
        MACVerifier macVerifier = new MACVerifier(JWT_SECRET.getBytes());
        try {
            return macVerifier.verify(jwsObject.getHeader(), jwsObject.getSigningInput(), jwsObject.getSignature());
        } catch (JOSEException e) {
            throw new BusinessException("accessToken format error.");
        }
    }

    /**
     * 服务器端解密 jwtTemplate
     *
     * @param jwtTemplate
     * @return
     * @throws BusinessException
     */
    public static JWSObject decrypt(String jwtTemplate) throws BusinessException {
        try {
            return JWSObject.parse(jwtTemplate);
        } catch (ParseException e) {
            throw new BusinessException("accessToken format error.");
        }
    }

    /**
     * accessToken 加密
     *
     * @param jsonObject
     * @return
     * @throws BusinessException
     */
    public static String encrypt(JSONObject jsonObject) throws BusinessException, KeyLengthException {

        jsonObject.put("exp", DateUtil.getAfterMinuteDate(new Date(), JWT_EXP).getTime()); // 过期时间设置100分钟后过期
        Payload payload = new Payload(jsonObject);

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        // Create JWS object
        JWSObject jwsObject = new JWSObject(header, payload);

        // Create HMAC signer     
        JWSSigner signer = new MACSigner(JWT_SECRET.getBytes());

        try {
            jwsObject.sign(signer);
        } catch (JOSEException e) {
            System.err.println("Couldn't sign JWS object: " + e.getMessage());
        }

        return jwsObject.serialize();
    }

    /**
     * accessToken 加密
     *
     * @param jsonObject 用户信息
     * @param jti        终端标识
     * @return
     * @throws BusinessException
     */
    public static String encrypt(
            JSONObject jsonObject, String jti) throws BusinessException, KeyLengthException {
        jsonObject.put("jti", jti); // 终端标识
        return encrypt(jsonObject);
    }


    /**
     * 验证 exp 是否过期  过期返回 true 未过期返回 false
     *
     * @param jwsObject
     * @return
     */
    public synchronized static boolean exp(JWSObject jwsObject) {
        String exp = getValue(jwsObject, "exp").toString();
        if (StringUtil.isNotBlank(exp)) {
            if (Long.valueOf(exp) <= System.currentTimeMillis()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 重置jwt 过期时间返回新的 jwt encrypt
     *
     * @param jwtTemplate
     * @return
     */
    public synchronized static String reExp(String jwtTemplate) throws BusinessException, KeyLengthException {
        if (StringUtil.isNotBlank(jwtTemplate)) {
            return encrypt(decrypt(jwtTemplate).getPayload().toJSONObject());
        }
        return null;
    }

    /**
     * 获取 jwsObject  value
     *
     * @param jwsObject
     * @param key
     * @return
     */
    public static Object getValue(JWSObject jwsObject, String key) {
        Payload payload = jwsObject.getPayload();
        JSONObject jsonObjcet = payload.toJSONObject();
        if (jsonObjcet.containsKey(key)) {
            return jsonObjcet.get(key);
        }
        return null;
    }

}
