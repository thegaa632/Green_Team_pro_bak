package com.standout.sopang.goods.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public interface GoodsController {
	//����Ʈ������
	public String menuGoods(@RequestParam("menuGoods") String menuGoods, Model model
							, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//��õŰ����
	//��, @Responsebody ������̼��� ����ϸ� http��û body�� �ڹ� ��ü�� ���޹��� �� �ִ�.
	//��ó: https://cheershennah.tistory.com/179 [Today I Learned. @cheers_hena ġ���쳪:Ƽ���丮]
	public @ResponseBody String keywordSearch(@RequestParam("keyword") String keyword,Model model,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//�˻�
	public String searchGoods(@RequestParam("searchWord") String searchWord,Model model,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//��ǰ��
	public String goodsDetail(@RequestParam("goods_id") String goods_id,Model model,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
