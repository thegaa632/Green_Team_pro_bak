package com.standout.sopang.order.service;

import java.util.List;
import java.util.Map;

import com.standout.sopang.order.dto.OrderDTO;
import com.standout.sopang.order.vo.OrderVO;

public interface OrderService {

	//�ֹ��ϱ� - �����Ϸ��� �ֹ� table�� insert �ȴ�
	public void addNewOrder(List<OrderDTO> myOrderList) throws Exception;
}
