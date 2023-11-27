package com.standout.sopang.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.standout.sopang.order.vo.OrderVO;

@Repository("orderDAO")
public class OrderDAOImpl implements OrderDAO {
	@Autowired
	private SqlSession sqlSession;
	
	//�ֹ��ϱ�
	public void insertNewOrder(List<OrderVO> myOrderList) throws DataAccessException{
		//���ϵ� �ֹ���ȣ�� �Բ� �ֹ� table�� �ֹ������� insert�Ѵ�.

			sqlSession.insert("mapper.order.insertNewOrder");

	}	
	
	public int selectOrderID() throws DataAccessException{
		//�ֹ���ȣ �������� �����Ͽ� ������� ��ȯ�Ѵ�.
		int result = sqlSession.selectOne("mapper.order.selectOrderID");
		return result;
	}

	//�ֹ��Ϸ�� ��ٱ��Ͽ��� ��ǰ ����
	public void removeGoodsFromCart(List<OrderVO> myOrderList)throws DataAccessException{
		for(int i=0; i<myOrderList.size();i++){
			//�ֹ���ǰ����Ʈ�� ������ ������ delete���� myOrderList��ŭ, for���� ���� �����Ѵ�.
			OrderVO orderVO =(OrderVO)myOrderList.get(i);
			sqlSession.delete("mapper.order.deleteGoodsFromCart",orderVO);	
		}
	}	
}
