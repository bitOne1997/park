package com.park.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.DataUsageCard;
import com.park.model.DataUsageCardDetail;
import com.park.model.Page;
import com.park.model.Park;
import com.park.service.AuthorityService;
import com.park.service.DataUsageCardService;
import com.park.service.ExcelExportService;
import com.park.service.ParkService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;
import com.park.service.impl.ExcelServiceImpl;

@Controller
@RequestMapping("/dataUsage")
public class DataUsageCardController {

	@Autowired
	private DataUsageCardService cardService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	
	@Autowired
	private ParkService parkService;
	
	@Autowired
	private AuthorityService authService;
	
	@RequestMapping(value = "", produces = {"application/json;charset=UTF-8"})
	public String getCardPage(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		String username = (String) session.getAttribute("username");
		List<Park> parkList = parkService.getParks();
		if(username != null)
			parkList = parkService.filterPark(parkList, username);
		modelMap.addAttribute("parks", parkList);
		AuthUser user = authService.getUserByUsername(username);
		boolean isAdmin = false;
		if(user != null){
			modelMap.addAttribute("user", user);
			isAdmin = user.getRole() == AuthUserRole.ADMIN.getValue() ? true : false;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
		if(isAdmin)
			return "dataUsageCard";
		else
			return "/login";
	}
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCount(){
		Map<String, Object> body = new HashMap<String, Object>();
		int count = cardService.getCardCount();
		body.put("count", count);
		return Utility.createJsonMsg(1001, "get count success", body);
	}
	
	@RequestMapping(value = "/getCard", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCard(@RequestParam("low")int low, @RequestParam("count")int count){
		List<DataUsageCardDetail> cards = cardService.getCardDetail(low, count);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("cards", cards);
		return Utility.createJsonMsg(1001, "get card success", body);
		
	}
	
	@RequestMapping(value = "/getParkCards/{parkId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCardsByParkId(@PathVariable int parkId){
		List<DataUsageCardDetail> cards = cardService.getCardsByParkId(parkId);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("cards", cards);
		return Utility.createJsonMsg(1001, "get card success", body);
	}
	
	
	@RequestMapping(value = "/getCardById/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCardById(@PathVariable int id){
		List<DataUsageCardDetail> cards = cardService.getCardById(id);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("cards", cards);
		return Utility.createJsonMsg(1001, "get card success", body);
	}
	
	@RequestMapping(value = "/getCardByCardNumber/{number}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCardByCardNumber(@PathVariable String number){
		List<DataUsageCardDetail> cards = cardService.getCardByCardNumber(number);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("cards", cards);
		return Utility.createJsonMsg(1001, "get card success", body);
	}
	
	@RequestMapping(value="/getCardDetailByKeywords",method=RequestMethod.POST,produces={"appliction/json;charset=utf-8"})
	@ResponseBody
	public String getCardDetailByKeywords(@RequestBody Map<String, String> args){
		String keyWords=args.get("keywords");
		keyWords=keyWords.trim();
		Map<String, Object> body = new HashMap<String, Object>();
		if (keyWords.equals("")) {
			body.put("message","输入错误");
		}
		else {
			List<DataUsageCardDetail> cards=cardService.getCardDetailByKeywords(keyWords);
			body.put("cards", cards);
		}						
		return Utility.createJsonMsg(1001, "get card success", body);
	}
	@RequestMapping(value = "/getCardByPhoneNumber/{number}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCardByPhoneNumber(@PathVariable String number){
		List<DataUsageCardDetail> cards = cardService.getCardByPhoneNumber(number);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("cards", cards);
		return Utility.createJsonMsg(1001, "get card success", body);
	}
	
	@RequestMapping(value = "/insertCard", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insertCard(@RequestBody DataUsageCard card){
		if(cardService.insertCard(card) > 0)
			return Utility.createJsonMsg(1001, "insert card successfully");
		else
			return Utility.createJsonMsg(1002, "insert card fail");
	}
	
	@RequestMapping(value = "/updateCard", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String updateCard(@RequestBody DataUsageCard card){
		if(cardService.updateCard(card) > 0)
			return Utility.createJsonMsg(1001, "update card successfully");
		else
			return Utility.createJsonMsg(1002, "update card fail");		
	}
	
	@RequestMapping(value = "/updateCardFields", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String updateCardFileds(@RequestBody Map<String, Object> args){
		int id = (int)args.get("id");
		List<DataUsageCard> cards = cardService.getUsageCardById(id);
		if(cards == null || cards.size() == 0)
			return Utility.createJsonMsg("1002", "no this data usage card");
		DataUsageCard card = cards.get(0);
		
		Method[] methods = null;
		try{
			methods = Class.forName("com.park.model.DataUsageCard").getMethods();
		}catch(SecurityException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 0; i< methods.length; i++){
			String methodName = methods[i].getName();
			if(!methodName.substring(0, 3).equals("set"))
				continue;
			String fieldInMethod = methodName.substring(3).toLowerCase();
			if(args.containsKey(fieldInMethod)){
				try {
					methods[i].invoke(card, args.get(fieldInMethod));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		return updateCard(card);
	}
	
	
	@RequestMapping(value = "/deleteCard/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String deleteCard(@PathVariable int id){
		if(cardService.deleteCard(id) > 0)
			return Utility.createJsonMsg(1001, "delete card successfully");
		else
			return Utility.createJsonMsg(1002, "delete card fail");
	}
	
	@Autowired
	private ExcelServiceImpl<DataUsageCardDetail> usagecardExcel;
	@Autowired
	private ExcelExportService excelExportService;
	@RequestMapping(value="/getCardExcel")
	@ResponseBody
	public void getUsageCardExcel(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException{
		List<DataUsageCardDetail> cardDetails=cardService.getAllCardDetail();		
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	//	String[] headers={"Id","停车场名称","卡号","电话号码","类型","安装位置","经度","纬度","状态","停车场id","本月流量"};
		String[] headers1={"停车场名称","卡号","电话号码","本月流量","类型","安装位置","经纬度"};
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR+ "export2003_b.xls");
		HSSFWorkbook workbook = new HSSFWorkbook();
	//	usagecardExcel.produceSheetData("流量卡", headers, cardDetails, workbook, "yyyy-MM-dd");
		excelExportService.produceExceldataDataUsage("流量卡", headers1, cardDetails, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR+ "export2003_b.xls", response);
	}
	
}
