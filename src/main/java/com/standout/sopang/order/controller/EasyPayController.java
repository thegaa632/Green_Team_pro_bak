package com.standout.sopang.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.order.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.standout.sopang.member.vo.MemberVO;
import com.standout.sopang.order.service.ApiService01;
import com.standout.sopang.order.service.OrderService;
import com.standout.sopang.order.vo.OrderVO;

@Controller
public class EasyPayController {
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private ApiService01 apiService01;
	
	//EasyPayPopup(RestController)�� view����� ���� Controller�� �߰���.
	@RequestMapping(value="/test/kakaoPay.do")
	public ModelAndView kakaoPay(@RequestParam Map<String, String> map , HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
		ModelAndView mav  = new ModelAndView();
		System.out.println(map.toString());
		
		//�ֹ������� �����´�.
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("orderer");
		String member_id = memberVO.getMember_id();
		String orderer_name = memberVO.getMember_name();
		String orderer_hp = memberVO.getHp1();
		List<OrderDTO> myOrderList = (List<OrderDTO>) session.getAttribute("myOrderList");
		
		//�ֹ������� for�� ������ myOrderList�� ������������ ��´�.
		for (int i = 0; i < myOrderList.size(); i++) {
			OrderDTO orderDTO = (OrderDTO) myOrderList.get(i);
			orderDTO.setMember_id(member_id);
			orderDTO.setReceiver_name(map.get("receiver_name"));
			orderDTO.setReceiver_hp1(map.get("receiver_hp1"));
			orderDTO.setDelivery_address(map.get("delivery_address"));

			//���� ������ �ʿ��� �� ������ �ּ����� ���ܵд�.
			orderDTO.setPay_method(map.get("pay_method"));
			orderDTO.setCard_com_name(map.get("card_com_name"));
			orderDTO.setCard_pay_month(map.get("card_pay_month"));
			orderDTO.setPay_orderer_hp_num(map.get("pay_orderer_hp_num"));
			orderDTO.setOrderer_hp(orderer_hp);
			
			myOrderList.set(i, orderDTO);
		}
		
		
		//���������ͷ� ������û�ϱ�
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String res_cd = (String) map.get("res_cd");
		String enc_info = (String) map.get("enc_info");
		String enc_data = (String) map.get("enc_data");
		String card_pay_method = (String) map.get("card_pay_method");
		String ordr_idxx = (String) map.get("ordr_idxx");
		String merchantId = "himedia";
		String url = "https://api.testpayup.co.kr/ep/api/kakao/"+merchantId+"/pay";
		
		returnMap = apiService01.restApi(map, url);
		
		
		
//		String responseCode = "0000";
		
		if("0000".equals(res_cd)) {
			//����������, �ֹ������� �ֹ����̺� �ݿ��Ѵ�.
			orderService.addNewOrder(myOrderList);
			System.out.println(map.get("res_cd"));
			System.out.println(map.get("res_msg"));
			mav.setViewName("redirect:/mypage/listMyOrderHistory.do");	
		}else {
			mav.addObject("responseMsg", "īī�� ��������");
			mav.setViewName("/order/payFail");	
		}
		
		
		
		return mav;
	}
	
	
	
	
	
	
	@RequestMapping(value="/test/naverPay.do")
	public ModelAndView naverPay(@RequestParam Map<String, String> map , HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
		
		ModelAndView mav  = new ModelAndView();
		System.out.println(map.toString());
		
		//�ֹ������� �����´�.
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("orderer");
		String member_id = memberVO.getMember_id();
		String orderer_name = memberVO.getMember_name();
		String orderer_hp = memberVO.getHp1();
		List<OrderDTO> myOrderList = (List<OrderDTO>) session.getAttribute("myOrderList");
		
		//�ֹ������� for�� ������ myOrderList�� ������������ ��´�.
		for (int i = 0; i < myOrderList.size(); i++) {
			OrderDTO orderDTO = (OrderDTO) myOrderList.get(i);
			orderDTO.setMember_id(member_id);
			orderDTO.setReceiver_name(map.get("receiver_name"));
			orderDTO.setReceiver_hp1(map.get("receiver_hp1"));
			orderDTO.setDelivery_address(map.get("delivery_address"));

			//���� ������ �ʿ��� �� ������ �ּ����� ���ܵд�.
			orderDTO.setPay_method(map.get("pay_method"));
			orderDTO.setCard_com_name(map.get("card_com_name"));
			orderDTO.setCard_pay_month(map.get("card_pay_month"));
			orderDTO.setPay_orderer_hp_num(map.get("pay_orderer_hp_num"));
			orderDTO.setOrderer_hp(orderer_hp);
			
			myOrderList.set(i, orderDTO);
		}
		
		
		//���������ͷ� ������û�ϱ�
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String res_cd = (String) map.get("res_cd");
		String enc_info = (String) map.get("enc_info");
		String enc_data = (String) map.get("enc_data");
		String card_pay_method = (String) map.get("card_pay_method");
		String ordr_idxx = (String) map.get("ordr_idxx");
		String merchantId = "himedia";
		String url = "https://api.testpayup.co.kr/ep/api/naver/"+merchantId+"/pay";
		
		System.out.println(map.toString());
		returnMap = apiService01.restApi(map, url);
		
		
		System.out.println(returnMap.toString());
		String responseCode = (String) returnMap.get("responseCode");
		
		if("0000".equals(responseCode)) {
			//����������, �ֹ������� �ֹ����̺� �ݿ��Ѵ�.
			orderService.addNewOrder(myOrderList);
			System.out.println(map.get("res_cd"));
			System.out.println(map.get("res_msg"));
			mav.setViewName("redirect:/mypage/listMyOrderHistory.do");	
		}else {
			mav.addObject("responseMsg", "���̹� ��������");
			mav.setViewName("/order/payFail");	
		}
		
		
		
		return mav;
	}
}
