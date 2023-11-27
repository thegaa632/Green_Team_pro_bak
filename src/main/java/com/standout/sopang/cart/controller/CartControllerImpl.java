package com.standout.sopang.cart.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.cart.dto.CartDTO;
import com.standout.sopang.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.standout.sopang.cart.service.CartService;
import com.standout.sopang.cart.vo.CartVO;
import com.standout.sopang.common.base.BaseController;
import com.standout.sopang.goods.vo.GoodsVO;
import com.standout.sopang.member.vo.MemberVO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("cartController")
@RequestMapping(value="/cart")
public class CartControllerImpl extends BaseController implements CartController{
	@Autowired
	private CartService cartService;
	@Autowired
	private CartDTO cartDTO;
	@Autowired
	private MemberDTO memberDTO;
	
	//��ٱ���

	@Override
	@RequestMapping(value="/myCartList" ,method = RequestMethod.GET)
	public String myCartMain(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberInfo");
		//ȸ�������� �´� ��ٱ��� ����Ʈ�� �ҷ��´�.
		if (memberDTO != null) {
			String member_id = memberDTO.getMember_id();

			cartDTO.setMember_id(member_id);
			Map<String, List> cartMap = cartService.myCartList(cartDTO);
			session.setAttribute("cartMap", cartMap);
			return "/cart/myCartList";
		} else {
			return "redirect:/member/login";
		}
	}

//	@RequestMapping(value="/myCartList" ,method = RequestMethod.GET)
//	public String myCartMain(HttpServletRequest request, HttpServletResponse response, Model model,
//	RedirectAttributes redirectAttributes)  throws Exception {
//
//		HttpSession session=request.getSession();
//		MemberVO memberVO=(MemberVO)session.getAttribute("memberInfo");
//
//		//ȸ�������� �´� ��ٱ��� ����Ʈ�� �ҷ��´�.
//		if (memberVO != null) {
//			String member_id = memberVO.getMember_id();
//
//			cartVO.setMember_id(member_id);
//			Map<String, List> cartMap = cartService.myCartList(cartVO);
//			session.setAttribute("cartMap", cartMap);
//			return "/cart/myCartList";
//		} else {
//			return "redirect:/member/login.do";
//		}
//	}


	//��ٱ��� �߰�
	@RequestMapping(value="/addGoodsInCart" ,method = RequestMethod.POST,produces = "application/text; charset=utf8")
	public  @ResponseBody String addGoodsInCart(@RequestParam("goods_id") int goods_id,
			                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		HttpSession session=request.getSession();
		memberDTO=(MemberDTO)session.getAttribute("memberInfo");
		String member_id=memberDTO.getMember_id();
		
		//ȸ�������� �߰��ϰ����ϴ� ��ǰid�� ��ٱ��� �ߺ�üũ �� boolean�� isAreadyExisted�� ���ϰ� ����
		cartDTO.setMember_id(member_id);
		cartDTO.setGoods_id(goods_id);
		boolean isAreadyExisted=cartService.findCartGoods(cartDTO);
		
		//�ߺ��ɰ�� already_existed return, ���� ��� add_success return
		if(isAreadyExisted==true){return "already_existed";}
		else{cartService.addGoodsInCart(cartDTO);return "add_success";}
		// ���ڿ��� �����µ� ��� ó������?

	}
	
	
	
	//��ٱ��� ����
	@RequestMapping(value="/removeCartGoods" ,method = RequestMethod.POST)
	public String removeCartGoods(@RequestParam("cart_id") int cart_id,
			                          HttpServletRequest request, HttpServletResponse response,
								  Model model,RedirectAttributes redirectAttributes)  throws Exception{

		//@RequestParam���� cart_id ��ǰ�� ���� �� myCartList�� redirect
		cartService.removeCartGoods(cart_id);
		return "redirect:/cart/myCartList.do";
	}

	//��ٱ��� ����
	//���߿� @RequestParam Map���� ó���� �� �ִ��� Ȯ��
	@RequestMapping(value="/modifyCartQty" ,method = RequestMethod.POST)
	public @ResponseBody String  modifyCartQty(@RequestParam("goods_id") int goods_id,
			                                   @RequestParam("cart_goods_qty") int cart_goods_qty,
			                                    HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HttpSession session=request.getSession();
		memberDTO=(MemberDTO)session.getAttribute("memberInfo");
		String member_id=memberDTO.getMember_id();
		
		//member_id�� @RequestParam���� goods_id�� cart_goods_qty�� ���������� �ݿ��ϰ� ������� ���Ϲ޾� result�� ����
		cartDTO.setMember_id(member_id);
		cartDTO.setGoods_id(goods_id);
		cartDTO.setCart_goods_qty(cart_goods_qty);
		boolean result=cartService.modifyCartQty(cartDTO);
		
		//�Ϸ�� modify_success, ���� ��� modify_failed�� ����.
		if(result==true){return "modify_success";}
		else{return "modify_failed";	}
		
	}
	
	
}
