package com.standout.sopang.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.standout.sopang.config.ConvertList;
import com.standout.sopang.order.dto.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.standout.sopang.order.dao.OrderDAO;
import com.standout.sopang.order.vo.OrderVO;
import org.springframework.ui.ModelMap;


@Service("orderService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	ConvertList convertList;
	@Autowired
	ModelMapper modelMapper;


	//�ֹ��ϱ�
	public void addNewOrder(List<OrderDTO> myOrderList) throws Exception{
		//�ֹ��ϱ�

		List<OrderVO> orderVOList =myOrderList.stream().map
						((order)->modelMapper.map(order,OrderVO.class))
				.collect(Collectors.toList());


		orderDAO.insertNewOrder(orderVOList);

		//īƮ���� �ֹ� ��ǰ �����Ѵ�.
		orderDAO.removeGoodsFromCart(orderVOList);
		System.out.println("��ٱ��Ͽ��� �ش� ��ǰ�� �����߽��ϴ�.");
	}	
	
}
