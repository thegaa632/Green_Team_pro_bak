package com.standout.sopang.admin.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.standout.sopang.goods.dto.GoodsDTO;
import com.standout.sopang.goods.dto.ImageFileDTO;
import com.standout.sopang.goods.vo.GoodsVO;
import com.standout.sopang.goods.vo.ImageFileVO;
import com.standout.sopang.order.vo.OrderVO;

public interface AdminGoodsService {
	// ��ǰ����
	public List<GoodsDTO> listNewGoods(Map condMap) throws Exception;

	//��ǰ�߰�
	public int addNewGoods(Map newGoodsMap) throws Exception;
	public void addNewGoodsImage(List<ImageFileDTO> imageFileList) throws Exception;

	//��ǰ����
	public void deleteGoods(String goods_id) throws Exception;

	//��ǰ����
	public void modifyGoods(String goods_id, Map newGoodsMap) throws Exception;

}
