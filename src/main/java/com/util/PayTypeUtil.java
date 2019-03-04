package com.util;

import java.util.ArrayList;
import java.util.List;

public class PayTypeUtil {
	//支付宝类型
	public static int payTypezfb=0;
	//微信类型
	public static int payTypewx=1;
	//现金类型
	public static int payTypexj=2;
	//工行类型
	public static int payTypegh=3;
	//app类型
	public static int payTypeapp=4;
	//银联类型
	public static int payTypeyl=5;
	//其他类型
	public static int payTypeqt=6;
	//现金2类型
	public static int payTypexj2=9;
	
	public float Zongjine = 0;
	public float weixinZongjine = 0;
	public float zfbZongjine = 0;
	public float xjZongjine = 0;
	public float qtZongjine = 0;
	public float ylZongjine = 0;
	public float ghZongjine = 0;
	public float xj2Zongjine = 0;
	public float appZongjine = 0;
	public float paidMoneyjine=0;

	public int ZongBishu = 0;
	public int weixinZongBishu = 0;
	public int zfbZongBishu = 0;
	public int xjZongBishu = 0;
	public int qtZongBishu = 0;
	public int ylZongBishu = 0;
	public int ghZongBishu = 0;
	public int xj2ZongBishu = 0;
	public int appZongBishu = 0;
	
	public int totalCount = 0;//总笔数
	public int alipayCount = 0;//支付宝笔数
	public int wechartCount = 0;//微信笔数
	public int cashCount = 0;//现金笔数
	public int otherCount = 0;//其它笔数
	public int unionPayCount = 0;//银联笔数
	public int cbcCount = 0;//工行笔数
	public int cashCount2 = 0;//现金2笔数
	public int appCount = 0;//app笔数

	public float totalAmount = 0;//总金额
	public float alipayAmount = 0;//支付宝金额
	public float wechartAmount = 0;//微信金额
	public float cashAmount = 0;//现金金额
	public float otherAmount = 0;//其它金额
	public float unionPayAmount = 0;//银联金额
	public float cbcAmount = 0;//工行金额
	public float cashAmount2 = 0;//现金2金额
	public float appAmount = 0;//app金额
	public float paidAmount=0;//实收金额
	
	
	public float getZongjine() {
		return Zongjine;
	}
	public void setZongjine(float Zongjine) {
		this.Zongjine = Zongjine;
	}
	public float getWeixinZongjine() {
		return weixinZongjine;
	}
	public void setWeixinZongjine(float weixinZongjine) {
		this.weixinZongjine = weixinZongjine;
	}
	public float getZfbZongjine() {
		return zfbZongjine;
	}
	public void setZfbZongjine(float zfbZongjine) {
		this.zfbZongjine = zfbZongjine;
	}
	public float getXjZongjine() {
		return xjZongjine;
	}
	public void setXjZongjine(float xjZongjine) {
		this.xjZongjine = xjZongjine;
	}
	public float getQtZongjine() {
		return qtZongjine;
	}
	public void setQtZongjine(float qtZongjine) {
		this.qtZongjine = qtZongjine;
	}
	public float getYlZongjine() {
		return ylZongjine;
	}
	public void setYlZongjine(float ylZongjine) {
		this.ylZongjine = ylZongjine;
	}
	public float getGhZongjine() {
		return ghZongjine;
	}
	public void setGhZongjine(float ghZongjine) {
		this.ghZongjine = ghZongjine;
	}
	public float getXj2Zongjine() {
		return xj2Zongjine;
	}
	public void setXj2Zongjine(float xj2Zongjine) {
		this.xj2Zongjine = xj2Zongjine;
	}
	public float getAppZongjine() {
		return appZongjine;
	}
	public void setAppZongjine(float appZongjine) {
		this.appZongjine = appZongjine;
	}
	public float getPaidMoneyjine() {
		return paidMoneyjine;
	}
	public void setPaidMoneyjine(float paidMoneyjine) {
		this.paidMoneyjine = paidMoneyjine;
	}
	public int getZongBishu() {
		return ZongBishu;
	}
	public void setZongBishu(int ZongBishu) {
		this.ZongBishu = ZongBishu;
	}
	public int getWeixinZongBishu() {
		return weixinZongBishu;
	}
	public void setWeixinZongBishu(int weixinZongBishu) {
		this.weixinZongBishu = weixinZongBishu;
	}
	public int getZfbZongBishu() {
		return zfbZongBishu;
	}
	public void setZfbZongBishu(int zfbZongBishu) {
		this.zfbZongBishu = zfbZongBishu;
	}
	public int getXjZongBishu() {
		return xjZongBishu;
	}
	public void setXjZongBishu(int xjZongBishu) {
		this.xjZongBishu = xjZongBishu;
	}
	public int getQtZongBishu() {
		return qtZongBishu;
	}
	public void setQtZongBishu(int qtZongBishu) {
		this.qtZongBishu = qtZongBishu;
	}
	public int getYlZongBishu() {
		return ylZongBishu;
	}
	public void setYlZongBishu(int ylZongBishu) {
		this.ylZongBishu = ylZongBishu;
	}
	public int getGhZongBishu() {
		return ghZongBishu;
	}
	public void setGhZongBishu(int ghZongBishu) {
		this.ghZongBishu = ghZongBishu;
	}
	public int getXj2ZongBishu() {
		return xj2ZongBishu;
	}
	public void setXj2ZongBishu(int xj2ZongBishu) {
		this.xj2ZongBishu = xj2ZongBishu;
	}
	public int getAppZongBishu() {
		return appZongBishu;
	}
	public void setAppZongBishu(int appZongBishu) {
		this.appZongBishu = appZongBishu;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getAlipayCount() {
		return alipayCount;
	}
	public void setAlipayCount(int alipayCount) {
		this.alipayCount = alipayCount;
	}
	public int getWechartCount() {
		return wechartCount;
	}
	public void setWechartCount(int wechartCount) {
		this.wechartCount = wechartCount;
	}
	public int getCashCount() {
		return cashCount;
	}
	public void setCashCount(int cashCount) {
		this.cashCount = cashCount;
	}
	public int getOtherCount() {
		return otherCount;
	}
	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}
	public int getUnionPayCount() {
		return unionPayCount;
	}
	public void setUnionPayCount(int unionPayCount) {
		this.unionPayCount = unionPayCount;
	}
	public int getCbcCount() {
		return cbcCount;
	}
	public void setCbcCount(int cbcCount) {
		this.cbcCount = cbcCount;
	}
	public int getCashCount2() {
		return cashCount2;
	}
	public void setCashCount2(int cashCount2) {
		this.cashCount2 = cashCount2;
	}
	public int getAppCount() {
		return appCount;
	}
	public void setAppCount(int appCount) {
		this.appCount = appCount;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public float getAlipayAmount() {
		return alipayAmount;
	}
	public void setAlipayAmount(float alipayAmount) {
		this.alipayAmount = alipayAmount;
	}
	public float getWechartAmount() {
		return wechartAmount;
	}
	public void setWechartAmount(float wechartAmount) {
		this.wechartAmount = wechartAmount;
	}
	public float getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(float cashAmount) {
		this.cashAmount = cashAmount;
	}
	public float getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(float otherAmount) {
		this.otherAmount = otherAmount;
	}
	public float getUnionPayAmount() {
		return unionPayAmount;
	}
	public void setUnionPayAmount(float unionPayAmount) {
		this.unionPayAmount = unionPayAmount;
	}
	public float getCbcAmount() {
		return cbcAmount;
	}
	public void setCbcAmount(float cbcAmount) {
		this.cbcAmount = cbcAmount;
	}
	public float getCashAmount2() {
		return cashAmount2;
	}
	public void setCashAmount2(float cashAmount2) {
		this.cashAmount2 = cashAmount2;
	}
	public float getAppAmount() {
		return appAmount;
	}
	public void setAppAmount(float appAmount) {
		this.appAmount = appAmount;
	}
	public float getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(float paidAmount) {
		this.paidAmount = paidAmount;
	}
	
	
	public static List<Integer> PayTypebsInt(String resultszbs,String resultszfbbs,String resultswxbs,String resultsxjbs,String resultsqtbs,String resultsylbs,
			String resultsghbs,String resultsxj2bs,String resultsappbs){
		List<Integer> ListPayTypeInt=new ArrayList<>();
		try{
			List<String> list=new ArrayList<>();
			list.add(resultszbs);
			list.add(resultszfbbs);
			list.add(resultswxbs);
			list.add(resultsxjbs);
			list.add(resultsqtbs);
			list.add(resultsylbs);
			list.add(resultsghbs);
			list.add(resultsxj2bs);
			list.add(resultsappbs);
			Integer result=-1;
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i) != null){
					result = Integer.parseInt(list.get(i));
					ListPayTypeInt.add(result);
				}else{
					ListPayTypeInt.add(0);
				}
			}
		}catch(Exception e){
			ListPayTypeInt=null;
		}
		System.out.println("打印--------"+ListPayTypeInt);
		return ListPayTypeInt;
	}
	
	public static List<Float> PayTypejeFloat(String resultszje,String resultszfbje,String resultswxje,String resultsxjje,
			String resultsqtje,String resultsylje,String resultsghje,String resultsxj2je,String resultsappje,String resultspaidAmount){
		List<Float> ListPayTypeFloat=new ArrayList<>();
		try{
			List<String> list=new ArrayList<>();
			list.add(resultszje);
			list.add(resultszfbje);
			list.add(resultswxje);
			list.add(resultsxjje);
			list.add(resultsqtje);
			list.add(resultsylje);
			list.add(resultsghje);
			list.add(resultsxj2je);
			list.add(resultsappje);
			list.add(resultspaidAmount);
			float result=-1.0f;
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i) != null){
					result = Float.parseFloat(list.get(i));
					ListPayTypeFloat.add(result);
				}else{
					ListPayTypeFloat.add(0.0f);
				}
			}
		}catch(Exception e){
			ListPayTypeFloat=null;
		}
		return ListPayTypeFloat;
	}
	
}
