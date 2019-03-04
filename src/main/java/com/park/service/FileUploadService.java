package com.park.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.Base64.Decoder;

import com.park.model.Constants;

public class FileUploadService {

	public static String imgBase64Upload(String base64img){
		Decoder decoder=Base64.getDecoder();		
		// Base64解码      
        byte[] imageByte = null;
        try {
            imageByte = decoder.decode(base64img);      
            for (int i = 0; i < imageByte.length; ++i) {      
                if (imageByte[i] < 0) {// 调整异常数据      
                    imageByte[i] += 256;      
                }      
            }      
        } catch (Exception e) {
             e.printStackTrace(); 
        }      
        
        // 生成文件名
        String files = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date())
                + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000)
                + ".png";
        // 生成文件路径
        String filename = Constants.UPLOADDIR  + files;     
        try {
            // 生成文件         
            File imageFile = new File(filename);
            imageFile.createNewFile();
               if(!imageFile.exists()){
                   imageFile.createNewFile();
                }
                OutputStream imageStream = new FileOutputStream(imageFile);
                imageStream.write(imageByte);
                imageStream.flush();
                imageStream.close();                    
        } catch (Exception e) {         
            e.printStackTrace();
        }
		return Constants.URL+files;
		
	}
}
