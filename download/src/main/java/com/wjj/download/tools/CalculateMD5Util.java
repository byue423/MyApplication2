package com.wjj.download.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalculateMD5Util {
    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符
     */
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 生成文件的MD5校验值
     *
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @author wjj
     * @date 2014年8月26日
     */
    public static String getFileMD5String(String fileName) throws IOException,
            NoSuchAlgorithmException {
        InputStream fis;
        File file = null;
        MessageDigest messageDigest = null;
        byte[] buffer = null;
        int numRead = 0;
        String md5Message = "";
        if (fileName != null && !fileName.equals("")) {
            fileName = fileName.replace("\\\\", "/").replace("/",
                    File.separator);
            file = new File(fileName);
            if (file.exists()) {
                fis = new FileInputStream(file);
                buffer = new byte[1024];
                messageDigest = MessageDigest.getInstance("MD5");
                while ((numRead = fis.read(buffer)) > 0) {
                    messageDigest.update(buffer, 0, numRead);
                }
                fis.close();
                md5Message = bufferToHex(messageDigest.digest());
            } else {
                throw new FileNotFoundException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return md5Message;
    }

    /**
     * 字节转换成十六进制
     *
     * @param bytes
     * @return
     * @author wjj
     * @date 2014年8月26日
     */
    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        // 取字节中高 4 位的数字转换
        // >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        // 取字节中低 4 位的数字转换
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static void main(String[] args) throws IOException {
//		try {
//			String md5 = getFileMD5String("E:\\yidongOA.apk");
//			System.out.println("MD5 Message: " + md5);
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}
