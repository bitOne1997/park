package com.park.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.Constants;
import com.park.service.Utility;

@Controller
@RequestMapping("file")
public class FileController {

	@RequestMapping(value="/upload/uploadBase64Image",method=RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
    public String base64UpLoad(@RequestBody Map<String, String> args, HttpServletRequest request, HttpServletResponse response){  
		String base64img=args.get("base64img");
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
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("status", "1001");
        ret.put("body", Constants.URL+files);
		return Utility.gson.toJson(ret);
	}
}
