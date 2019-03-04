package com.park.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.formula.functions.Index;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Constants;
import com.park.model.Page;
import com.park.model.SingleParkInfo;
import com.park.model.SingleParkInfoCompare;
import com.park.model.Udpconnectorinfo;
import com.park.model.Udpconnectors;
import com.park.service.AuthorityService;
import com.park.service.JavaBeanXml;
import com.park.service.ParkServletDelegate;
import com.park.service.ParkServletService;
import com.park.service.UdpConnectorInfoService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@RequestMapping("udp")
@Controller
public class UdpController {

	@Autowired
	private AuthorityService authService;

	@Autowired
	private UserPagePermissionService pageService;

	@Autowired
	private JavaBeanXml javaBeanXml;

	@Autowired
	private UdpConnectorInfoService udpConnectorInfoService;

	@RequestMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	public String Index(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if (user != null) {
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if (user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin = true;
			modelMap.addAttribute("isAdmin", isAdmin);

			Set<Page> pages = pageService.getUserPage(user.getId());
			for (Page page : pages) {
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
		return "udpTest";
	}

	@RequestMapping(value = "send", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String send(@RequestBody Map<String, String> args) throws IOException, InterruptedException {
		String message = args.get("message");
		byte[] msToSend = message.getBytes();		
		for (Udpconnectors udpconnector : Constants.udpconnectors) {
			InetAddress address = InetAddress.getByName(udpconnector.getIp());
			int port = Integer.parseInt(udpconnector.getPort());
			System.out.println(address + "::" + port);
			DatagramPacket packet2 = new DatagramPacket(msToSend, msToSend.length, address, port);
			Constants.socket.send(packet2);
			Thread.sleep(1000);
		}
		return Utility.createJsonMsg(1001, "ok");
	}

	@RequestMapping(value = "sendByMac", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String sendByMac(@RequestBody Map<String, String> args) throws IOException, InterruptedException {
		String message = args.get("message");
		String mac = args.get("mac");
		Udpconnectors udpconnectornow=null;
		for (Udpconnectors udpconnector : Constants.udpconnectors) {
			if (udpconnector.getMac().equals(mac)) {
				udpconnectornow=udpconnector;
			}
		}
		if (udpconnectornow==null) {
			return Utility.createJsonMsg(1002, "failed!");
		}				
		byte[] msToSend = message.getBytes();
		InetAddress address = InetAddress.getByName(udpconnectornow.getIp());
		int port = Integer.parseInt(udpconnectornow.getPort());
		System.out.println(address + "::" + port);
		DatagramPacket packet2 = new DatagramPacket(msToSend, msToSend.length, address, port);
		Constants.socket.send(packet2);
		Thread.sleep(1000);

		return Utility.createJsonMsg(1001, "ok");
	}

	@RequestMapping(value = "getConnectors", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getConnectors() throws IOException, InterruptedException {

		return Utility.createJsonMsg(1001, "ok", Constants.udpconnectors);
	}

	@RequestMapping(value = "getReceiveData", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getReceiveData() throws IOException, InterruptedException {

		return Utility.createJsonMsg(1001, "ok", Constants.dataReceived.toString());
	}

	@RequestMapping(value = "testForSingnalParkInfo", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String testForSingnalParkInfo() throws DocumentException {
		ParkServletService ParkWS = new ParkServletService();
		ParkServletDelegate ParkDelegate = ParkWS.getParkServletPort();
		List<String> parkid = new ArrayList<>();
		for (int i = 0; i < 233; i++) {
			String searchStr = "pt31010" + String.valueOf(700004 + i);
			parkid.add(searchStr);
		}

		List<SingleParkInfo> singleParkInfos = new ArrayList<>();
		for (String string : parkid) {
			String xml = ParkDelegate.singleParkInfo("admin_pt", "cadre_pt", string);
			if (xml == null || xml.equals("")) {
				continue;
			}
			SingleParkInfo singleParkInfo = (SingleParkInfo) javaBeanXml.SingleParkInfoFromXml(xml);
			String tmp = singleParkInfo.getUpdatetime();
			String updateTime = tmp.substring(0, 4) + "-" + tmp.substring(4, 6) + "-" + tmp.substring(6, 8) + " "
					+ tmp.substring(8, 10) + ":" + tmp.substring(10, 12) + ":" + tmp.substring(12, 14);
			singleParkInfo.setUpdatetime(updateTime);
			singleParkInfos.add(singleParkInfo);
		}
		Collections.sort(singleParkInfos, new SingleParkInfoCompare());
		// javaBeanXml.updateParkFromXml();
		// String xml=ParkDelegate.singleParkInfo("admin_pt", "cadre_pt",
		// "pt31010700079");
		return Utility.createJsonMsg(1001, "ok", singleParkInfos);
	}

	@RequestMapping(value = "getForSingnalParkInfo", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getForSingnalParkInfo(@RequestBody Map<String, String> args) throws DocumentException {
		String parkId = args.get("parkId");
		ParkServletService ParkWS = new ParkServletService();
		ParkServletDelegate ParkDelegate = ParkWS.getParkServletPort();
		String xml = ParkDelegate.singleParkInfo("admin_pt", "cadre_pt", parkId);
		if (xml == null) {
			return Utility.createJsonMsg(1001, "ok", "无数据!");
		}
		SingleParkInfo singleParkInfo = (SingleParkInfo) javaBeanXml.SingleParkInfoFromXml(xml);
		// Collections.sort(singleParkInfos,new SingleParkInfoCompare());
		// javaBeanXml.updateParkFromXml();
		// String xml=ParkDelegate.singleParkInfo("admin_pt", "cadre_pt",
		// "pt31010700079");
		return Utility.createJsonMsg(1001, "ok", singleParkInfo);
	}

	@RequestMapping(value = "getForSingnalIOInfo", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getForSingnalIOInfo(@RequestBody Map<String, String> args) throws DocumentException {
		String parkId = args.get("parkId");
		ParkServletService ParkWS = new ParkServletService();
		ParkServletDelegate ParkDelegate = ParkWS.getParkServletPort();
		String xml = ParkDelegate.getio("admin_pt", "cadre_pt", parkId);
		if (xml == null) {
			return Utility.createJsonMsg(1001, "ok", "无数据!");
		}
		// SingleParkInfo singleParkInfo=(SingleParkInfo)
		// javaBeanXml.SingleParkInfoFromXml(xml);
		// Collections.sort(singleParkInfos,new SingleParkInfoCompare());
		// javaBeanXml.updateParkFromXml();
		// String xml=ParkDelegate.singleParkInfo("admin_pt", "cadre_pt",
		// "pt31010700079");
		return Utility.createJsonMsg(1001, "ok", xml);
	}

	@RequestMapping(value = "getAreaInfo", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getAreaInfo() throws DocumentException {
		// String parkId=args.get("parkId");
		ParkServletService ParkWS = new ParkServletService();
		ParkServletDelegate ParkDelegate = ParkWS.getParkServletPort();
		String xml = ParkDelegate.areainfo("admin_pt", "cadre_pt");
		if (xml == null) {
			return Utility.createJsonMsg(1001, "ok", "无数据!");
		}
		// SingleParkInfo singleParkInfo=(SingleParkInfo)
		// javaBeanXml.SingleParkInfoFromXml(xml);
		// Collections.sort(singleParkInfos,new SingleParkInfoCompare());
		// javaBeanXml.updateParkFromXml();
		// String xml=ParkDelegate.singleParkInfo("admin_pt", "cadre_pt",
		// "pt31010700079");
		return Utility.createJsonMsg(1001, "ok", xml);
	}
}
