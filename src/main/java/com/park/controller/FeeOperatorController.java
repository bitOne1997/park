package com.park.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Constants;
import com.park.model.FeeCriterion;
import com.park.model.Feeoperator;
import com.park.model.Page;
import com.park.model.Park;
import com.park.model.Pos;
import com.park.model.Street;
import com.park.service.AuthorityService;
import com.park.service.FeeCriterionService;
import com.park.service.FeeOperatorService;
import com.park.service.ParkService;
import com.park.service.PosService;
import com.park.service.StreetService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;


@Controller
@RequestMapping("feeOperator")
public class FeeOperatorController {
	@Autowired
	private AuthorityService authService;
	@Autowired
	private FeeOperatorService feeOperatorService;
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	private PosService posService;
	@Autowired
	private ParkService parkService;
	@Autowired 
	private FeeCriterionService feeCriterionService;
	
	private static Log logger = LogFactory.getLog(FeeOperatorController.class);
	
	
	@RequestMapping(value="")
	public String index(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "feeOperator";
	}
	
	@RequestMapping(value="2")
	public String index2(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "feeOperator2";
	}
	
	@RequestMapping(value="validationid",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String Validationid(@RequestBody Map<String, Object> args) throws ParseException{
		Map<String, Object> result=new HashMap<>();
		int id=0;
		id=Integer.parseInt(args.get("id").toString());
		Double money=args.get("money")!=null?(double) args.get("money"):null;
		Boolean needMoney=args.get("needMoney")!=null?(boolean)args.get("needMoney"):false;
		List<FeeCriterion> feeCriterionid=feeCriterionService.operatorValidationid(id);
				if (feeCriterionid.isEmpty()) {
					result.put("status", 1002);
				} else {
					result.put("status", 1001);
					result.put("message", "success");
					result.put("body", feeCriterionid);
				}
		return Utility.gson.toJson(result);
	}
	
	@RequestMapping(value="validation",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String Validation(@RequestBody Map<String, Object> args) throws ParseException{
		Map<String, Object> result=new HashMap<>();
		String account=(String) args.get("account");
		String passwd=(String) args.get("passwd");
		String posNum=(String) args.get("posNum");
		Double money=args.get("money")!=null?(double) args.get("money"):null;
		Boolean needMoney=args.get("needMoney")!=null?(boolean)args.get("needMoney"):false;
		logger.info("validation:"+account+"-"+passwd+"-"+posNum+"-"+money);
		List<Feeoperator> feeoperators=feeOperatorService.operatorValidation(account, passwd);
		result.put("parkName", "");
		result.put("carportsCount", 0);
		result.put("feeCriterion", "");
		if (feeoperators.isEmpty()) {
			result.put("status", 1002);
			result.put("message", "错误的用户名或密码");	
			result.put("parkName", "");
		}
		else {		
			List<Pos> pos=posService.getByNum(posNum);
			if (pos.isEmpty()) {
				result.put("status", 1002);
				result.put("message", "无pos机");
				result.put("parkName", "");
			}
			else{
				result.put("status", 1001);
				Feeoperator operator=feeoperators.get(0);
				operator.setLastsigndate(new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
				Pos tmpPos=pos.get(0);		
				tmpPos.setLasttime(new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
				posService.updateByPrimaryKeySelective(tmpPos);
				Park park=parkService.getParkById(tmpPos.getParkid());
				FeeCriterion feeCriterion=feeCriterionService.getById(park.getFeeCriterionId());
				result.put("feeCriterion", feeCriterion);
				operator.setSignstatus(true);
				if (money!=null) {
					logger.info(account+"设置金额"+money);
					operator.setMoney((int) (money*100));
				}
				feeOperatorService.updateByPrimaryKeySelective(operator);
				if (needMoney) {
					result.put("money", (double)operator.getMoney()/100);
				}
				result.put("parkName", park.getName());
				result.put("parkId", park.getId());
				result.put("carportsCount", park.getPortCount());
				result.put("message", "success");	
			}
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="logout",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String logout(@RequestBody Map<String, String> args) throws ParseException{
		Map<String, Object> result=new HashMap<>();
		String account=args.get("account");
		String passwd=args.get("passwd");
		String posNum=args.get("posNum");
		List<Feeoperator> feeoperators=feeOperatorService.operatorValidation(account, passwd);
		if (feeoperators.isEmpty()) {
			result.put("status", 1002);
			result.put("message", "错误的用户名或密码");				
		}
		else {		
			List<Pos> pos=posService.getByNum(posNum);
			if (pos.isEmpty()) {
				result.put("status", 1002);
				result.put("message", "无pos机");
			}
			else{
				result.put("status", 1001);
				Feeoperator operator=feeoperators.get(0);				
				Pos tmpPos=pos.get(0);				
				Park park=parkService.getParkById(tmpPos.getParkid());
				Integer streetId=park.getStreetId();				
				operator.setLaststreetid(streetId);	
				operator.setLastparkid(tmpPos.getParkid());
				operator.setSignstatus(false);
				operator.setLastposnum(tmpPos.getNum());
				feeOperatorService.updateByPrimaryKeySelective(operator);		
				result.put("message", "success");	
			}
		}
		return Utility.gson.toJson(result);
	}
	
	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@RequestBody Feeoperator feeOperator){
		Map<String, Object> result=new HashMap<>();
		int num=feeOperatorService.insertSelective(feeOperator);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="delete/{id}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String delete(@PathVariable("id")int id){
		Map<String, Object> result=new HashMap<>();
		int num=feeOperatorService.deleteByPrimaryKey(id);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String update(@RequestBody Feeoperator feeOperator){
		Map<String, Object> result=new HashMap<>();
		int num=feeOperatorService.updateByPrimaryKeySelective(feeOperator);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="selectByPrimaryKey/{id}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String selectByPrimaryKey(@PathVariable("id")int id){
		Map<String, Object> result=new HashMap<>();
		Feeoperator feeOperator=feeOperatorService.selectByPrimaryKey(id);
		if (feeOperator!=null) {
			result.put("status", 1001);
			result.put("body", feeOperator);
		}
		else{
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="getCount",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getCount(){
		Map<String, Object> result=new HashMap<>();
		int count=feeOperatorService.getCount();
		result.put("count", count);
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="/getByStartAndCount",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByStartAndCount(@RequestParam("start")int start,@RequestParam("count")int count){
		Map<String, Object> result=new HashMap<>();
		List<Feeoperator> feeOperatores=feeOperatorService.getByStartAndCount(start, count);
		if (feeOperatores!=null) {
			result.put("status", 1001);
			result.put("body", feeOperatores);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="/getByNameAndPhoneParkName",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByNameAndPhoneParkName(@RequestBody Map<String, String> args){
		Map<String, Object> result=new HashMap<>();
		String name=args.get("name");
		String phone=args.get("phone");
		String parkName=args.get("parkName");
		List<Feeoperator> feeOperatores=feeOperatorService.getByNameAndPhoneParkName(name, phone, parkName);
		if (feeOperatores!=null) {
			result.put("status", 1001);
			result.put("body", feeOperatores);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
}
