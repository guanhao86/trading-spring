package com.spring.free.config;

import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * Description:Token生成工具
 * 第一部分我们称它为头部（header),第二部分我们称其为载荷（payload, 类似于飞机上承载的物品)，第三部分是签证（signature).
 */
@Slf4j
public class TokenUtil {

    public static final String AUTH_HEADER_KEY = "Authorization";

    public static final String TOKEN_PREFIX = "TOKEN";

    public static final String clientId = "";
    public static final String base64Secret = "maizi";
    public static final String name = "";
    public static final int expiresSecond = 3600 * 1000;

    /**
     * 解析jwt
     * @param jsonWebToken
     * @return
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (ExpiredJwtException eje) {
            log.error("===== Token过期 =====", eje);
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "token已过期！", "", null);
        } catch (Exception e){
            log.error("===== token解析异常 =====", e);
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "token解析异常！", "", null);
        }
    }

    /**
     * 构建jwt
     * @param userId
     * @param username
     * @param role
     * @return
     */
    public static String createJWT(String userId, String username, String role) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            //userId是重要信息，进行加密下
            String encryId = Base64Util.encode(userId);

            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("role", role)
                    .claim("userId", userId)
                    .setSubject(username)           // 代表这个JWT的主体，即它的所有人
                    .setIssuer(clientId)              // 代表这个JWT的签发主体；
                    .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
                    .setAudience(name)          // 代表这个JWT的接收对象；
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            int TTLMillis =expiresSecond;
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
                        .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
            }

            //生成JWT
            return builder.compact();
        } catch (Exception e) {
            log.error("签名失败", e);
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "签名失败！", "", null);
        }
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token){
        return parseJWT(token).getSubject();
    }

    /**
     * 从token中获取用户ID
     * @param token
     * @return
     */
    public static String getUserId(String token){
        String userId = parseJWT(token).get("userId", String.class);
        return Base64Util.decode(userId);
    }

    /**
     * 是否已过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        return parseJWT(token).getExpiration().before(new Date());
    }

    public static void main(String[] args) {
        System.out.println(createJWT("1234567890", "王鑫", ""));
    }
}
