package com.standout.sopang.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.standout.sopang.member.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.standout.sopang.common.base.BaseController;
import com.standout.sopang.member.service.MemberService;
import com.standout.sopang.member.vo.MemberVO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller("memberController")
@RequestMapping(value = "/member")
public class MemberControllerImpl extends BaseController implements MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberDTO memberDTO;

	@RequestMapping(value="/login" ,method = RequestMethod.GET)
	public String login(){
		return "/member/login";
	}
	//�α���
	@Override
	@RequestMapping(value="/login" ,method = RequestMethod.POST)
	public String login(@RequestParam Map<String, String> loginMap, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		memberDTO = memberService.login(loginMap);
		
		//memberVO�� ������ ���
		if (memberDTO != null && memberDTO.getMember_id() != null) {
			HttpSession session = request.getSession();
			session = request.getSession();

			
			//�α��� ���� isLogOn�� ȸ������ memberInfo�� ���ǿ� �����Ѵ�.
			session.setAttribute("isLogOn", true);
			session.setAttribute("memberInfo", memberDTO);
			return "/main/main";
			//������������ �̵�.

		} else { //memberVO�� ������������ ��� message�� ��� return + login�������� �̵�
			String message = "���̵�  ��й�ȣ�� Ʋ���ϴ�. �ٽ� �α������ּ���";
			model.addAttribute("message", message);
		}
		return "/member/login";
	}


	@RequestMapping(value="/join" ,method = RequestMethod.GET)
	public String join(){
		return "/member/join";
	}


	//ȸ������
	@Override
	@RequestMapping(value="/join" ,method = RequestMethod.POST)
	public ResponseEntity addMember(@ModelAttribute("memberVO") MemberVO _memberVO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			//ȸ�������� try, addMember ������ �ȳ������� �Բ�  login�������� �̵��Ѵ�.
		    memberService.addMember(_memberVO);
		    message  = "<script>";
		    message +=" alert('sopang�� ���Ű� ȯ���մϴ�!');";
		    message += " location.href='"+request.getContextPath()+"/member/login';";
		    message += " </script>";
		    
		}catch(Exception e) {
			//�����߻���, ȸ�������������� ���̵�
			message  = "<script>";
		    message += " location.href='"+request.getContextPath()+"/member/join';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		
		//�� ���̽��� ���� �� ������ return
		return resEntity;
	}

	
	
	
	//���̵� �ߺ�Ȯ��
	@Override
	@RequestMapping(value="/overlapped" ,method = RequestMethod.POST)
	public ResponseEntity overlapped(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseEntity resEntity = null;
		
		//overlapped�� ����� ������ return �Ѵ�.
		String result = memberService.overlapped(id);
		resEntity = new ResponseEntity(result, HttpStatus.OK);
		return resEntity;
	}
	
	
	
	//�α׾ƿ�

	//�ѹ� �� üũ�ϱ�
	@Override
	@RequestMapping(value="/logout" ,method = RequestMethod.GET)
	public String logout(Model model, RedirectAttributes redirectAttributes,
						 HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		//���� ���� �ʱ�ȭ �� ���������� �̵�.
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		return "redirect:/main/main";
	}

}
