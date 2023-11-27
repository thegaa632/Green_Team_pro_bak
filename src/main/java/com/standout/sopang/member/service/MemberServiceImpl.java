package com.standout.sopang.member.service;

import java.util.Map;

import com.standout.sopang.member.dto.MemberDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.standout.sopang.member.dao.MemberDAO;
import com.standout.sopang.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation=Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private ModelMapper modelMapper;
	
	//�α���
	@Override
	public MemberDTO login(Map  loginMap) throws Exception{
		MemberDTO memberDTO=modelMapper.map(loginMap,MemberDTO.class);
		memberDAO.login(loginMap);
		return memberDTO;

	}
	
	//ȸ������
	@Override
	public void addMember(MemberVO memberVO) throws Exception{
		memberDAO.insertNewMember(memberVO);
	}
	
	//���̵� �ߺ�Ȯ��
	@Override
	public String overlapped(String id) throws Exception{
		return memberDAO.selectOverlappedID(id);
	}
}
