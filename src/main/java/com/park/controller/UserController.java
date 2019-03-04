package com.park.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Constants;
import com.park.model.Page;
import com.park.model.User;
import com.park.model.UserDetail;
import com.park.service.AuthorityService;
import com.park.service.UserPagePermissionService;
import com.park.service.UserService;
import com.park.service.Utility;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private UserPagePermissionService pageService;

	private static Log logger = LogFactory.getLog(UserController.class);

	@RequestMapping(value = "/users", produces = { "application/json;charset=UTF-8" })
	public String getUsers(ModelMap modelMap, HttpServletRequest request,
			HttpSession session) {
		List<User> userList = userService.getUsers();
		modelMap.put("users", userList);
		logger.info("info test");

		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		boolean isAdmin = false;
		if (user != null) {
			modelMap.addAttribute("user", user);
			if (user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin = true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}

		if (isAdmin)
			return "user";
		else
			return "/login";

	}
	

	@RequestMapping(value = "/getUserCount", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getuserCount() {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		int count = userService.getUserCount();
		body.put("count", count);

		ret.put("status", "1001");
		ret.put("message", "get user detail success");
		ret.put("body", Utility.gson.toJson(body));

		return Utility.gson.toJson(ret);
	}

	@RequestMapping(value = "/getUserDetail", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String accessIndex(@RequestParam("low") int low,
			@RequestParam("count") int count) {
		Map<String, Object> ret = new HashMap<String, Object>();
		List<UserDetail> userDetail = userService.getUserDetail(low, count);
		if (userDetail != null) {
			ret.put("status", "1001");
			ret.put("message", "get user detail success");
			ret.put("body", Utility.gson.toJson(userDetail));
		} else {
			ret.put("status", "1002");
			ret.put("message", "get user detail fail");
		}
		return Utility.gson.toJson(ret);

	}

	@RequestMapping(value = "/insert/user", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insertUser(@RequestBody User user,HttpServletResponse response) {
		logger.info("userName: " + user.getUserName() + " number: "
				+ user.getNumber());
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		return userService.insertUser(user);
	}

	@RequestMapping(value = "/update/user", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateUser(@RequestBody Map<String, Object> args) {
		String username = (String) args.get("userName");
		User user = userService.getUserByUsername(username);
		Utility.updateObjectField(user, "com.park.model.User", args);
		if (userService.updateUser(user) > 0)
			return Utility.createJsonMsg(1001, "update successful");
		else
			return Utility.createJsonMsg(1002, "update failed");

	}

	@RequestMapping(value = "/user/passwd", method = RequestMethod.POST)
	@ResponseBody
	public String getUserPasswd(@RequestParam("userName") String userName) {
		return userService.getUserPassword(userName);
	}

	@RequestMapping(value = "/register/user")
	public String registerUser(User user) {
		return "registerUser";
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String appUserLogin(@RequestBody Map<String, Object> args) {
		String username = (String) args.get("userName");
		String password = (String) args.get("password");
		User user = userService.getUserByUsername(username);
		if (user == null)
			return Utility.createJsonMsg(1002, "no this user");
		if (!user.getPasswd().equals(password))
			return Utility.createJsonMsg(1003, "password is incorrect");
		else
			return Utility.createJsonMsg(1001, "login successfully", user);

	}

	@RequestMapping(value = "/user/changePassword", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String changeUserPassword(@RequestBody Map<String, Object> args) {
		String username = (String) args.get("userName");
		String password = (String) args.get("newPassword");
		if (userService.changeUserPassword(username, password) > 0)
			return Utility.createJsonMsg(1001, "change password successfully");
		else
			return Utility.createJsonMsg(1002, "user no exists");
	}


	/**
	 * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file"
	 * name="myfiles"/>
	 * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
	 */
	static public int filePrefix = 0;
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadPicture(HttpServletRequest request,
			HttpServletResponse response) {
		
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			
			Map<String, Object> body = new HashMap<String, Object>();
			List<String> uriList = new ArrayList<String>();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						// 重命名上传后的文件名
						UserController.filePrefix++;
						//String fileName = "" + new Date().getTime() + file.getOriginalFilename();
						String fileName = "" + new Date().getTime() + UserController.filePrefix;
						// 定义上传路径
						String path = Constants.UPLOADDIR + fileName;
						File localFile = new File(path);
						try {
							file.transferTo(localFile);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
							break;
						}
						uriList.add(Constants.URL + fileName);
					}
				}
			}

			body.put("uri", uriList);
			return Utility.createJsonMsg(1001, "upload file success", body);

		}
		return Utility.createJsonMsg(1002, "upload failed");
	}

	/**
	 * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file"
	 * name="myfiles"/>
	 * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
	 */
	@RequestMapping(value = "/upload1", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(HttpServletRequest request, HttpServletResponse response) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try{
			Map<String, Object> body = new HashMap<String, Object>();
			List<String> uriList = new ArrayList<String>();
			
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			while(iter.hasNext()){
				FileItem item = (FileItem)iter.next();
				if(item.isFormField()){
					String orgName = item.getFieldName();
				}else{
					String orgName = item.getFieldName();
					String newName = new Date().getTime() + orgName;
					File saveFile = new File(Constants.UPLOADDIR + newName);
					item.write(saveFile);
					
					uriList.add(Constants.URL + newName);
				}
			}
			body.put("uri", uriList);
			return Utility.createJsonMsg(1001, "upload file success", body);
			
		}catch(Exception ex){
			
		}
		return Utility.createJsonMsg(1002, "upload failed");
		
	}

}
