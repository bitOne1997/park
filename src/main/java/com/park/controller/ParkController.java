package com.park.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.park.model.AdminArgs;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Constants;
import com.park.model.Page;
import com.park.model.Park;
import com.park.model.ParkDetail;
import com.park.model.ParkNews;
import com.park.model.ParkStatusInfo;
import com.park.model.Street;
import com.park.service.AreaService;
import com.park.service.AuthorityService;
import com.park.service.ChannelService;
import com.park.service.HardwareService;
import com.park.service.ParkService;
import com.park.service.StreetService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
public class ParkController {

	@Autowired
	private ParkService parkService;
	
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private AreaService areaService;
	@Autowired
	private StreetService streetService;
	@Autowired
	ChannelService channelService;
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	HardwareService hardwareService;
	private static Log logger = LogFactory.getLog(ParkController.class);
	
	@RequestMapping(value = "/parks", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public String parks(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if(user != null){
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
			
		return "park";
	}
	
	@RequestMapping(value = "/outsideParks", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public String outsideParks(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if(user != null){
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
			
		return "outsidePark";
	}
	/*新加页面*/
	@RequestMapping(value = "/outsideParks2", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public String outsideParks2(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if(user != null){
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
			
		return "outsidePark2";
	}
	
	@RequestMapping(value = "/getPark/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkById(@PathVariable int id, ModelMap modelMap, HttpServletRequest request){
		Park park = parkService.getParkById(id);
	
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("status", 1001);
		ret.put("body", park);
		ret.put("message", "get park success");
		return Utility.gson.toJson(ret);
	}
	
	@RequestMapping(value = "/getParkStatusInfo", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkStatusInfo() {
		ParkStatusInfo parkStatusInfo=new ParkStatusInfo();
		int totalCount=0;
		int onlineCount=0;
		Map<String,Integer> online=new HashMap<>();
		online.put("0", 0);
		online.put("1", 0);
		online.put("2", 0);
		online.put("3", 0);
//		online.put("4", 0);
//		online.put("5", 0);
		Map<String,Integer> typeCount=new HashMap<>();
		typeCount.put("0", 0);
		typeCount.put("1", 0);
		typeCount.put("2", 0);
		typeCount.put("3", 0);
//		typeCount.put("4", 0);
//		typeCount.put("5", 0);
		List<Park> parks=parkService.getParks();
		for (Park park : parks) {
			totalCount++;
			boolean onlinetmp=false;
			if (new Date().getTime()-park.getDate().getTime()<1000*60*5) {
				onlineCount++;
				onlinetmp=true;
			}
			switch (park.getType()) {
			case 0:
				typeCount.put("0", typeCount.get("0")+1);
				if (onlinetmp) {
					online.put("0", online.get("0")+1);
				}
				break;
			case 1:
				typeCount.put("1", typeCount.get("1")+1);
				if (onlinetmp) {
					online.put("1", online.get("1")+1);
				}
				break;
			case 2:
				typeCount.put("2", typeCount.get("2")+1);
				if (onlinetmp) {
					online.put("2", online.get("2")+1);
				}
				break;
			case 3:
				typeCount.put("3", typeCount.get("3")+1);
				if (onlinetmp) {
					online.put("3", online.get("3")+1);
				}
				break;
//			case 4:
//				typeCount.put("4", typeCount.get("4")+1);
//				if (onlinetmp) {
//					online.put("4", online.get("4")+1);
//				}
//				break;
//			case 5:
//				typeCount.put("5", typeCount.get("5")+1);
//				if (onlinetmp) {
//					online.put("5", online.get("5")+1);
//				}
//				break;
			default:
				break;
			}
		}
		
		
		
		
		parkStatusInfo.setOnline(online);
		parkStatusInfo.setTypeCount(typeCount);
		parkStatusInfo.setTotalCount(totalCount);
		parkStatusInfo.setOnlineCount(onlineCount);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("status", 1001);
		ret.put("body", parkStatusInfo);
		return Utility.gson.toJson(ret);
	}
	@RequestMapping(value = "/getParkByMac", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkByMac(@RequestBody Map<String, String> args){		
		Map<String, Object> ret = new HashMap<String, Object>();
		String mac = args.get("mac");
		List<Map<String, Object>> infos = hardwareService.getInfoByMac(mac);
		if (infos.isEmpty()) {
			ret.put("status", 1003);
			return Utility.gson.toJson(ret);
		}
		Map<String, Object> info = infos.get(0);
		Integer parkId = (Integer) info.get("parkID");
		Park park = parkService.getParkById(parkId);			
		ret.put("status", 1001);
		ret.put("body", park);
		ret.put("message", "get park success");
		return Utility.gson.toJson(ret);
	}
	/*@RequestMapping(value = "/getOutsideParkByStreetId/{streetId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getOutsideParkByStreetId(@PathVariable("streetId")int streetId){		
		List<Park> parks=parkService.getOutsideParkByStreetId(streetId);
		return Utility.createJsonMsg(1001, "get parks successfully", parks);
	}*/
	
	@RequestMapping(value = "/getOutsideParkByStreetId/{streetId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getOutsideParkByStreetId(@PathVariable("streetId")int streetId){		
		List<Park> parks=parkService.getOutsideParkByStreetId(streetId);
		return Utility.createJsonMsg(1001, "get parks successfully", parks);
	}
	
	@RequestMapping(value = "/getOutsideParkByAreaId/{areaId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getOutsideParkByAreaId(@PathVariable("areaId")int areaId){	
	List<Street> streets=streetService.getByArea(areaId);
	List<Park> parks=new ArrayList<>();
	for (Street street : streets) {
		List<Park> parkstmp=parkService.getOutsideParkByStreetId(street.getId());
		parks.addAll(parkstmp);
	}
	return Utility.createJsonMsg(1001, "get parks successfully", parks);
	}
	@RequestMapping(value = "/getParkByIds", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkByIds(@RequestBody Map<String, Object> args, ModelMap modelMap, HttpServletRequest request){
		List<Integer> ParkIds = (List<Integer>)args.get("Ids");
		List<Park> parks = new ArrayList<Park>();
		for(Integer id : ParkIds){
			Park park = parkService.getParkById(id);
			if(park != null)
				parks.add(park);
		}
		return Utility.createJsonMsg(1001, "get parks successfully", parks);
	}
	@RequestMapping(value="/parkmap")
	public String parkmap(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if(user != null){
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
		return "parkmap";
	}
	@RequestMapping(value = "/getParkLeftPort/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkLeftPort(@PathVariable int id, ModelMap modelMap, HttpServletRequest request){
		Park park = parkService.getParkById(id);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		ret.put("status", 1001);
		if(park != null){
			body.put("leftPort", park.getPortLeftCount());
			body.put("parkId", park.getId());
			body.put("parkName", park.getName());
			body.put("portCount", park.getPortCount());
			ret.put("message", "get park port success");
		}else{
			body.put("leftPort", "null");
			ret.put("message", "park not exist");
		}
		ret.put("body", body);
		
		return Utility.gson.toJson(ret);
	}
	
	@RequestMapping(value = "/getParks", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParks(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		List<Park> parkList = parkService.getParks();
		
		String username = (String) session.getAttribute("username");
		if(username != null)
			parkList = parkService.filterPark(parkList, username);
	
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("status", 1001);
		ret.put("body", parkList);
		ret.put("message", "get park success");
		return Utility.gson.toJson(ret);
	}
	
	@RequestMapping(value = "/getNearParks", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getNearParks(@RequestBody Map<String, Object> args){
		
		double longitude = Double.parseDouble(args.get("longitude").toString());
		double latitude = Double.parseDouble(args.get("latitude").toString());
		double radius = Double.parseDouble(args.get("radius").toString());
		List<Park> parkList = parkService.getNearParks(longitude, latitude, radius);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("status", 1001);
		ret.put("body", parkList);
		ret.put("message", "get park success");
		return Utility.gson.toJson(ret);
	}
	
	@RequestMapping(value="/getParksByType", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParksByType(@RequestParam(value="type",required=false)Integer type,HttpServletRequest request, HttpSession session){
		
		List<Park> parkList = parkService.getParks();
		String username = (String) session.getAttribute("username");
		if(username != null)
			parkList = parkService.filterPark(parkList, username);
		if (type==null) {
			type=3;
		}
		List<Park> parkl = new ArrayList<>();
		for (Park park : parkList) {
			if (park.getType()==type) {
				parkl.add(park);
			}
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("status", 1001);
		ret.put("body", parkl);
		ret.put("message", "get park success");
		return Utility.gson.toJson(ret);
	};
	@RequestMapping(value = "/getParkWithName/{name}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkWithName(@PathVariable String name){
		
	//	logger.info("get park with name: " + name);
		List<ParkDetail> parks = parkService.getParkByName(name);
	
		if(parks != null){
			if( parks.size() != 0){
				return Utility.createJsonMsg(1001, "get park success", parks);
			}else{
				logger.debug("park not exist : " + name);
				return Utility.createJsonMsg(1002, "park not exist");
				
			}
				
		}
		return Utility.createJsonMsg(1002, "get park fail");
	}
	
	@RequestMapping(value = "/getParkByName", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkByName(@RequestBody Map<String, Object> args){
		String name = (String)args.get("name");
//		logger.info("get park with name: " + name);
		List<ParkDetail> parks = parkService.getParkByName(name);
	
		if(parks != null){
			if( parks.size() != 0)
				return Utility.createJsonMsg(1001, "get park success", parks);
			else{
				logger.debug("park not exist : " + name);
				return Utility.createJsonMsg(1002, "park not exist");
			}
				
				
		}
		return Utility.createJsonMsg(1002, "get park fail");
	}
	
	@RequestMapping(value = "/getParkCount", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkCount(){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		int count = parkService.getParkCount();
		body.put("count", count);
	
		ret.put("status", "1001");
		ret.put("message", "get park detail success");
		ret.put("body", Utility.gson.toJson(body));
		
		return Utility.gson.toJson(ret);					
	}
	
	@RequestMapping(value = "/getOutsideParkCount", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getOutsideParkCount(){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		int count = parkService.getOutsideParkCount();
		body.put("count", count);
	
		ret.put("status", "1001");
		ret.put("message", "get park detail success");
		ret.put("body", Utility.gson.toJson(body));
		
		return Utility.gson.toJson(ret);					
	}

	@RequestMapping(value = "/getParkDetail", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public  String  getParkDetail( @RequestBody Map<String, Object> args,HttpSession session){
		int low = (int)args.get("low");
		int count = (int)args.get("count");
		Map<String, Object> ret = new HashMap<String, Object>();
		List<ParkDetail> parkDetail = parkService.getParkDetail(low, count);
		String username = (String) session.getAttribute("username");
		
		if(username != null){
			
			AuthUser user = authService.getUserByUsername(username);
			if(user != null && user.getRole() != AuthUserRole.ADMIN.getValue())
			{
				parkDetail = parkService.filterParkDetail(parkDetail, username);
				if (parkDetail.isEmpty()) {
					parkDetail = parkService.filterParkDetail(parkService.getParkDetail(0, 1000), username);
				}
				//parkDetail = parkService.getPark();
				//parkDetail = parkService.filterPark(parkDetail, username);
			}
		}			

		ret.put("status", "1001");
		ret.put("message", "get park detail success");
		ret.put("body", parkDetail);

		return Utility.gson.toJson(ret);
		
	}
	
	@RequestMapping(value = "/getOutsideParkDetail", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public  String  getOutsideParkDetail( @RequestBody Map<String, Object> args,HttpSession session){
		int low = (int)args.get("low");
		int count = (int)args.get("count");
		Map<String, Object> ret = new HashMap<String, Object>();
		List<ParkDetail> parkDetail = parkService.getOutsideParkDetail(low, count);
		String username = (String) session.getAttribute("username");		
		if(username != null){			
			AuthUser user = authService.getUserByUsername(username);
			if(user != null && user.getRole() != AuthUserRole.ADMIN.getValue())
			{
				parkDetail = parkService.filterParkDetail(parkDetail, username);
				if (parkDetail.isEmpty()) {
					parkDetail = parkService.filterParkDetail(parkService.getParkDetail(0, 1000), username);
				}
			}
		}			

		ret.put("status", "1001");
		ret.put("message", "get park detail success");
		ret.put("body", parkDetail);

		return Utility.gson.toJson(ret);
		
	}
	
	@RequestMapping(value = "/search/parkBykeywords", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkByLocationkeywords(@RequestBody Map<String, Object> args){
		String str=(String) args.get("keywords");
		str=str.trim();
		List<Park> parkDetail=parkService.getParkDetailByKeywords(str);
		Map<String, Object> ret = new HashMap<String, Object>();
		if(parkDetail != null){
			ret.put("status", "1001");
			ret.put("message", "get park detail success");
			ret.put("body", Utility.gson.toJson(parkDetail));
		}else{
			ret.put("status", "1002");
			ret.put("message", "get park detail fail");
		}
		return Utility.gson.toJson(ret);
	}
	
	@RequestMapping(value = "/insert/park", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insertPark(@RequestBody Park park){
		Map<String, Object> ret = new HashMap<String, Object>();
		if (AdminArgs.isGuest) {
			int parkcount=parkService.getParkCount();
			if (parkcount>=AdminArgs.maxParkCount) {
				ret.put("status", "1002");
				ret.put("message", "超过最大限制数");
				return Utility.gson.toJson(ret);
			}
		}
		return parkService.insertPark(park);
	}
	
	@RequestMapping(value = "/update/park", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String updatePark(@RequestBody Park park){
		if(park.getStreetId()==1){
			Park tmPark=parkService.getParkById(park.getId());
			park.setStreetId(tmPark.getStreetId());
		}
		return parkService.updatePark(park);
	}
	
	@RequestMapping(value = "/update/parkFields", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String updatePark1(@RequestBody Map<String, Object> args){
		logger.info("更新停车场信息"+args.toString());
		if(!args.containsKey("id"))
			return Utility.createJsonMsg("1002", "need park id");
		int parkId = (int) args.get("id");
		Park park = parkService.getParkById(parkId);
		if(park == null){
			return Utility.createJsonMsg("1003", "no this park, cannot update");
		}
		
		Method[] methods = null;
		try {
			methods = Class.forName("com.park.model.Park").getMethods();
		} catch (SecurityException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		for(int i = 0; i < methods.length; i++){
			String methodName = methods[i].getName();
			if(!methodName.substring(0, 3).equals("set"))
				continue;
			String fieldInMethod = methodName.substring(3).toLowerCase();
			if(args.containsKey(fieldInMethod)){
				try {
					methods[i].invoke(park, args.get(fieldInMethod));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		return parkService.updatePark(park);
	}
	
	
	@RequestMapping(value = "/delete/park/{Id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String deletePark(@PathVariable int Id){
		channelService.deleteByParkId(Id);
		return parkService.deletePark(Id);
	}
	
	@RequestMapping(value = "/dynamicNews/queryList", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getSearchParkNews(@RequestBody Map<String, Object> args){
		int pageSize = (int)args.get("pageSize");
		int offset = (int)args.get("offset");
		double longitude = (double)args.get("longitude");
		double latitude = (double)args.get("latitude");
		double radius = (double)args.get("radius");
		List<ParkNews> parkNewsList = parkService.getSearchParkLatestNews(longitude, latitude, radius, offset, pageSize);
		
		return Utility.createJsonMsg(1001, "get news successfully", parkNewsList);
		
	}
	
	@RequestMapping(value = "/insert/parkNews", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insertParkNews(@RequestBody ParkNews parkNews){
		parkNews.setDate(new Date());
		parkService.insertParkNews(parkNews);
		return Utility.createJsonMsg(1001, "insert news successfully");
		
	}
	

	
	
	@RequestMapping(value="getLastPark",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getLastPark(){
		Map<String, Object> ret=new HashMap<>();
		Park park=parkService.getLastPark();
		ret.put("park", park);
		return Utility.gson.toJson(ret);
	}

	@RequestMapping(value = "/search/parking", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getSearchParks(@RequestBody Map<String, Object> args){
		
		if(args.containsKey("parkingName")){
			String parkName = (String)args.get("parkingName");
			List<ParkDetail> parkList = parkService.getParkByName(parkName);
			return Utility.createJsonMsg(1001, "get park successfully", parkList);
		}
		
		double longitude = (double)args.get("userLocationlongitude");
		double latitude = (double)args.get("userLocationlatitude");
		double radius = (double)args.get("distance");

		List<Park> searchPark = parkService.getNearParks(longitude, latitude, radius);
		if(!args.containsKey("portLeftCount")){
			return Utility.createJsonMsg(1001, "get parks successfully", searchPark);
			
		}else{
			List<Park> filterPark = new ArrayList<Park>();
			
			int portLeftCount = (int)args.get("portLeftCount");
			for(Park park : searchPark){
				if(park.getPortLeftCount() >=  portLeftCount)
					filterPark.add(park);
			}
			return Utility.createJsonMsg(1001, "get parks successfully", filterPark);
		}
		
	}
	

	/**
	 * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file"
	 * name="myfiles"/>
	 * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
	 */
	static public int filePrefix = 0;
	@RequestMapping(value = "/upload/parkPic", method = RequestMethod.POST)
	public String uploadParkPicture(@RequestParam(value ="parkId") int parkId, HttpServletRequest request,
			HttpServletResponse response) {
		int id = parkId;
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
			String picUri = null;
			if (iter.hasNext()) {
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
						}
						picUri = Constants.URL + fileName;
					}
				}
			}
			if(picUri != null){
				Park park = parkService.getParkById(id);
				if(park != null){
					park.setPictureUri(picUri);
					parkService.updatePark(park);
				}
									
				//return Utility.createJsonMsg(1001, "upload file success", body);
				
			}else{
				//return Utility.createJsonMsg(1002, "upload failed");
			}
		}
		//return Utility.createJsonMsg(1002, "upload failed");
		return "redirect:/parks";
	}
}
