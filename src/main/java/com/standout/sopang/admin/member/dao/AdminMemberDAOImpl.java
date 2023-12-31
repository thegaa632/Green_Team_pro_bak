package com.standout.sopang.admin.member.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.standout.sopang.member.vo.MemberVO;

@Repository("adminMemberDao")
public class AdminMemberDAOImpl  implements AdminMemberDAO{
	@Autowired
	private SqlSession sqlSession;
	
	//ȸ������
	public ArrayList<MemberVO> listMember(HashMap condMap) throws DataAccessException{
		ArrayList<MemberVO> memberList=(ArrayList)sqlSession.selectList("mapper.admin.member.listMember",condMap);
		return memberList;
	}

}
