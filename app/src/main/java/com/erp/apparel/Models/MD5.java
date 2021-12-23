package com.erp.apparel.Models;


import java.security.MessageDigest;

public class MD5 {

   public static byte[] encrpytMD5(byte[] data) throws Exception{
       MessageDigest md5=MessageDigest.getInstance("MD5");
       md5.update(data);
       return md5.digest();
   }
}