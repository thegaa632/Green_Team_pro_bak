package com.standout.sopang.goods.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.standout.sopang.goods.dto.GoodsDTO;
import com.standout.sopang.goods.vo.GoodsVO;



public interface GoodsService {
	//���������� - ���� status��
	public Map<String, List <GoodsDTO>>  listGoods() throws Exception;
	
	//header ī�װ���
	public List<GoodsDTO> menuGoods(String menuGoods) throws Exception;

	//��õŰ����
	public List<String> keywordSearch(String keyword) throws Exception;

	//�˻�
	public List<GoodsDTO> searchGoods(String searchWord) throws Exception;
	
	//��ǰ��
	public Map goodsDetail(String _goods_id) throws Exception;
}
