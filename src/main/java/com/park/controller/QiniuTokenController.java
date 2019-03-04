package com.park.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.service.Utility;
import com.qiniu.util.Auth;

@Controller
@RequestMapping("/qiniu")
public class QiniuTokenController {
@RequestMapping(value="getToken",produces={"application/json;charset=UTF-8"})
@ResponseBody
public String getToken(){
	  //设置好账号的ACCESS_KEY和SECRET_KEY
	  String ACCESS_KEY = "7erPCrBonMkzzZc1wFZNJOSOe7mo6n_5dwB5fr0I";
	  String SECRET_KEY = "lCwqANYjG_v76t8Yo0Edffw-ne_mXsVKSp1pj3vv";
	  //要上传的空间
	  String bucketname = "carpicture";
	  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	  String token= auth.uploadToken(bucketname);
	  Map<String, Object> result=new HashMap<>();
	  result.put("status", 1001);
	  result.put("token", token);
	  return Utility.gson.toJson(result);
}
}
