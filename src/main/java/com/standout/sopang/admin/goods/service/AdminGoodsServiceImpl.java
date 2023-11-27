package com.standout.sopang.admin.goods.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.standout.sopang.config.ConvertList;
import com.standout.sopang.goods.dto.GoodsDTO;
import com.standout.sopang.goods.dto.ImageFileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.standout.sopang.admin.goods.dao.AdminGoodsDAO;
import com.standout.sopang.goods.vo.GoodsVO;
import com.standout.sopang.goods.vo.ImageFileVO;

@Service("adminGoodsService")
@Transactional(propagation = Propagation.REQUIRED)
public class AdminGoodsServiceImpl implements AdminGoodsService {
	@Autowired
	private AdminGoodsDAO adminGoodsDAO;
	@Autowired
	ConvertList convertList;
	@Autowired
	ModelMapper modelMapper;

	
	
	//��ǰ���� - ��ǰ����Ʈ
	@Override
	public List<GoodsDTO> listNewGoods(Map condMap) throws Exception {
//		convertList.GoodsConvertDTO(adminGoodsDAO.selectNewGoodsList(condMap));
//		return adminGoodsDAO.selectNewGoodsList(condMap);
		List<GoodsDTO> goodsDTOList	=	convertList.goodsConvertDTO(adminGoodsDAO.selectNewGoodsList(condMap));
		return goodsDTOList;
	}

	
	
	//��ǰ�߰� - ��ǰ����
	@Override
	public int addNewGoods(Map newGoodsMap) throws Exception {
		
		//��ǰ�����߰�, �������� ����Ǹ� ��ǰ id�� �߱޵Ǹ� �� ���ϰ��� goods_id�� �����Ѵ�.
		int goods_id = adminGoodsDAO.insertNewGoods(newGoodsMap);
		
		//�ش� goods_id���� img�� ������ insert�Ѵ�.
		ArrayList<ImageFileDTO> imageFileList = (ArrayList) newGoodsMap.get("imageFileList");
		for (ImageFileDTO imageFileDTO : imageFileList) {imageFileDTO.setGoods_id(goods_id);}
		List<ImageFileVO> imageListVO=convertList.imageConvertVO(imageFileList);

		adminGoodsDAO.insertGoodsImageFile(imageListVO);
		
		return goods_id;
	}

	
	//��ǰ�߰� - �̹���
	@Override
	public void addNewGoodsImage(List<ImageFileDTO> imageFileList) throws Exception {

		List<ImageFileVO> imageListVO=convertList.imageConvertVO(imageFileList);

		adminGoodsDAO.insertGoodsImageFile(imageListVO);
	}

	
	
	//��ǰ����
	@Override
	public void deleteGoods(String goods_id) throws Exception {
		adminGoodsDAO.deleteGoods(goods_id);

	}

	
	
	//��ǰ����
	@Override
	public void modifyGoods(String goods_id, Map newGoodsMap) throws Exception {
		//���޹��� goods_id�� ����ȭ, goods_id_toInt
		int goods_id_toInt = Integer.parseInt(goods_id);

		//goods_id���� �Բ� ��ǰ���� ����
		adminGoodsDAO.modifyGoods(goods_id, newGoodsMap);
		
		//����ȭ�� ��ǰid goods_id_toInt�� img�� �����Ѵ�.
		ArrayList<ImageFileDTO> imageFileList = (ArrayList) newGoodsMap.get("imageFileList");
		for (ImageFileDTO imageFileDTO : imageFileList) {
			imageFileDTO.setGoods_id(goods_id_toInt);
		}
		
		//�̹��� ����
		//�̹����� ��������� submit�� null���ܸ� �����ϰ�, �޼ҵ带 �и������ʱ����� if���� ���.
		// ������ ������������ ��쿣 modifyImages�� ��������ʴ´�.
		for (ImageFileDTO imageFileDTO : imageFileList) {
			if (imageFileDTO.getFileName() == "" || imageFileDTO.getFileName() == null) {}
			else {adminGoodsDAO.modifyImages(imageFileList);}
		}

	}

}
