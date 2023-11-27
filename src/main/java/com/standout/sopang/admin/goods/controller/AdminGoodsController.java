package com.standout.sopang.admin.goods.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface AdminGoodsController {
	//��ǰ����
	public String adminGoodsMain(@RequestParam Map<String, String> dateMap, HttpServletRequest request
			, Model model, HttpServletResponse response) throws Exception;

	//��ǰ�߰�
	public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception;

	//��ǰ����
	public String deleteGoods(@RequestParam("goods_id") String goods_id, HttpServletRequest request
			, Model model, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception;

	//��ǰ����
	public ResponseEntity modifyGoods(@RequestParam("goods_id") String goods_id,
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;
}
