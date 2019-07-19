package com.doyd.core.util;

import com.doyd.core.util.encrypt.RsaUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/**
 * 安全工具类
 *
 * @author ZhouZQ
 * @create 2019/3/21
 */
@Slf4j
public class SecurityUtils {

    /**
     * 字符编码
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * MD5 加密字符串
     *
     * @param sourceStr
     * @return
     */
    public static Optional<String> md5Encrypt(final String sourceStr) {
        return md5Encrypt(sourceStr, CHARSET_NAME);
    }

    /**
     * MD5加密字符串
     *
     * @param sourceStr 原始
     * @return 加密之后字符串
     */
    public static Optional<String> md5Encrypt(final String sourceStr, String coding) {
        if (StringUtils.isBlank(sourceStr)) {
            return Optional.empty();
        }

        byte[] sourceByte;

        try {
            sourceByte = sourceStr.getBytes(coding);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            sourceByte = sourceStr.getBytes();
        }

        // 用来将字节转换成 16 进制表示的字符
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(sourceByte);
            final byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            final char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                final byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>>
                // 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            return Optional.of(new String(str)); // 换后的结果转换为字符串
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    /**
     * 创建公钥
     */
    public static Optional<Map<String, String>> getRsaKey() {
        try {
            Map<String, Object> keyMap = RsaUtil.genKeyPair();
            Map<String, String> key = Maps.newHashMapWithExpectedSize(2);
            String publicKey = RsaUtil.getPublicKey(keyMap);
            String privateKey = RsaUtil.getPrivateKey(keyMap);
            key.put("publicKey", publicKey);
            key.put("privateKey", privateKey);
            return Optional.of(key);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * 公钥加密(注意：加密后base64加密了)
     *
     * @param source
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Optional<String> rsaEncryptByPublicKey(String source, String publicKey) {
        try {
            byte[] data = source.getBytes(CHARSET_NAME);
            byte[] encodedData = RsaUtil.encryptByPublicKey(data, publicKey);
            return Optional.of(Base64.getEncoder().encodeToString(encodedData));
        } catch (Exception e) {
            log.error("rsa encrypt by pubilc key error:{}", e);
            return Optional.empty();
        }
    }

    /**
     * 私钥解密(注意：encodedData是base64加密后的才行)
     *
     * @param encodedData
     * @param privateKey
     * @return
     */
    public static Optional<String> rsaDecodeByPrivateKey(String encodedData, String privateKey) {
        try {
            byte[] decodedData = RsaUtil.decryptByPrivateKey(Base64.getDecoder().decode(encodedData), privateKey);
            return Optional.of(new String(decodedData, CHARSET_NAME));
        } catch (Exception e) {
            log.error("rsa decode by private key error:{}", e);
            return Optional.empty();
        }
    }


    /**
     * SHA1加密
     *
     * @param sourceStr 原文
     * @return
     */
    public static Optional<String> sha1Encrypt(final String sourceStr) {

        if (StringUtils.isBlank(sourceStr)) return Optional.empty();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(sourceStr.getBytes(CHARSET_NAME));
            byte[] hash = digest.digest();
            String hexString = toHexString(hash);
            return Optional.of(hexString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * SHA加密
     *
     * @param sourceStr 原文
     * @return
     */
    public static Optional<String> shaEncrypt(final String sourceStr) {
        if (StringUtils.isBlank(sourceStr)) return Optional.empty();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.reset();
            digest.update(sourceStr.getBytes(CHARSET_NAME));
            byte[] hash = digest.digest();
            String hexString = toHexString(hash);
            return Optional.of(hexString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * 对字符串进行URL编码
     *
     * @param sourceStr 需要编码的字符串
     * @param enc       编码格式
     * @return
     */
    public static String urlEncode(String sourceStr, String enc) {
        try {
            return URLEncoder.encode(sourceStr, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串进行URL编码
     *
     * @param sourceStr 需要编码的字符串
     * @param enc       编码格式
     * @return
     */
    public static String urlDecode(String sourceStr, String enc) {
        try {
            return URLDecoder.decode(sourceStr, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * authcode解密
     *
     * @param string 密文
     * @param key    密钥
     * @return
     * @throws Exception
     */
    public static Optional<String> authCodeDecode(String string, String key) {

        Preconditions.checkNotNull(string, "authcode source str is not null");
        Preconditions.checkNotNull(key, "authcode key is not null");

        // 随机密钥长度 取值 0-32;
        // 加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，加密结果也会每次不同，增大破解难度。（实际上就是iv）
        // 取值越大，密文变动规律越大，密文变化 = 16 的 $ckey_length 次方
        // 当此值为 0 时，则不产生随机密钥
        int ckey_length = 4;

        //密匙
        Optional<String> keyOptional = md5Encrypt(key);
        Preconditions.checkArgument(keyOptional.isPresent(), "key md5 error");

        Optional<String> keyaOptional = md5Encrypt(keyOptional.get().substring(0, 16));
        Optional<String> keybOptional = md5Encrypt(keyOptional.get().substring(16, 32));

        String keyc = ckey_length > 0 ? string.substring(0, ckey_length) : "";

        Preconditions.checkArgument(keyaOptional.isPresent() && keybOptional.isPresent(), "keya md5 error or keyb md5 error");
        Optional<String> keyacOptional = md5Encrypt(keyaOptional.get() + keyc);
        Preconditions.checkArgument(keyacOptional.isPresent(), "keya+keyc md5 error");

        String cryptkey = keyaOptional.get() + keyacOptional.get();
        int cryptkeyLen = cryptkey.length();

        try {
            string = new String(Base64.getDecoder().decode(string.substring(ckey_length)), CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return Optional.empty();
        }
        StringBuffer result = authCodeEncryptionProcess(string, cryptkey, cryptkeyLen);

        String secString = result.toString();
        String substringBegin = secString.substring(0, 10);
        if ((Integer.parseInt(substringBegin) == 0 || Integer.parseInt(substringBegin) - System.currentTimeMillis() / 1000 > 0) && secString.substring(10, 26).equals(md5Encrypt(secString.substring(26) + keybOptional.get()).orElseThrow(IllegalStateException::new).substring(0, 16))) {
            return Optional.of(secString.substring(26));
        } else {
            return Optional.empty();
        }
    }


    /**
     * authCode加密方式
     *
     * @param string 元字符串
     * @param key    密钥
     * @param expiry
     * @return
     * @throws Exception
     */
    public static Optional<String> authCodeEncode(String string, String key, int expiry) {
        // 随机密钥长度 取值 0-32;
        // 加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，加密结果也会每次不同，增大破解难度。（实际上就是iv）
        // 取值越大，密文变动规律越大，密文变化 = 16 的 $ckey_length 次方
        // 当此值为 0 时，则不产生随机密钥
        int ckey_length = 4;
        Preconditions.checkNotNull(string, "authcode source str is not null");
        Preconditions.checkNotNull(key, "authcode key is not null");
        Preconditions.checkArgument(expiry > 0, "key expiry time error");

        //密匙
        Optional<String> keyOptional = md5Encrypt(key);
        Preconditions.checkArgument(keyOptional.isPresent(), "key md5 error");

        Optional<String> keyaOptional = md5Encrypt(keyOptional.get().substring(0, 16));
        Optional<String> keybOptional = md5Encrypt(keyOptional.get().substring(16));

        String keyc = ckey_length > 0 ? md5Encrypt(String.valueOf(System.currentTimeMillis() / 1000)).orElseThrow(IllegalStateException::new).substring((32 - ckey_length)) : "";

        Preconditions.checkArgument(keyaOptional.isPresent() && keybOptional.isPresent(), "keya md5 error or keyb md5 error");
        Optional<String> keyacOptional = md5Encrypt(keyaOptional.get() + keyc);
        Preconditions.checkArgument(keyacOptional.isPresent(), "keya+keyc md5 error");
        String cryptkey = keyaOptional.get() + keyacOptional.get();

        int cryptkeyLen = cryptkey.length();

        //sprintf('%010d', $expiry ? $expiry + time() : 0).substr(md5($string.$keyb), 0, 16).$string
        string = String.format("%010d", expiry > 0 ? expiry + System.currentTimeMillis() / 1000 : 0) + md5Encrypt(string + keybOptional.get()).orElseThrow(IllegalStateException::new).substring(0, 16) + string;

        StringBuffer result = authCodeEncryptionProcess(string, cryptkey, cryptkeyLen);


        byte[] bytes;
        try {
            bytes = result.toString().getBytes(CHARSET_NAME);
            return Optional.of(keyc + Base64.getEncoder().encodeToString(bytes));
        } catch (UnsupportedEncodingException e) {
            return Optional.empty();
        }
    }

    private static StringBuffer authCodeEncryptionProcess(String string, String cryptkey, int cryptkeyLen) {
        int stringLen = string.length();

        List<Integer> rndkey = new ArrayList<>();
        StringBuffer result = new StringBuffer();
        Integer[] box = new Integer[256];
        for (int i = 0; i < box.length; i++) {
            box[i] = i;
        }

        for (int i = 0; i <= 255; i++) {
            rndkey.add((int) cryptkey.charAt(i % cryptkeyLen));
        }

        for (int j = 0, i = 0; i < 256; i++) {
            j = (j + box[i] + rndkey.get(i)) % 256;
            int tmp = box[i];
            box[i] = box[j];
            box[j] = tmp;
        }

        for (int k = 0, j = 0, i = 0; i < stringLen; i++) {
            k = (k + 1) % 256;
            j = (j + box[k]) % 256;
            int tmp = box[k];
            box[k] = box[j];
            box[j] = tmp;
            int a = (int) string.charAt(i);
            int b = box[(box[k] + box[j]) % 256];
            char r = (char) (a ^ b);
            result.append(r);
        }

        return result;
    }


    private static String toHexString(final byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte digestTemp : digest) {
            String shaHex = Integer.toHexString(digestTemp & 0xFF);
            if (shaHex.length() < 2) {
                sb.append(0);
            }
            sb.append(shaHex);
        }
        return sb.toString();
    }
}
