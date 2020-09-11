package com.study.code.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.study.code.dao.ICommonCodeDao;
import com.study.code.vo.CodeVO;
import com.study.common.util.MybatisSqlSessionFactory;

@Service
public class CommonCodeServiceImpl implements ICommonCodeService {
	
	SqlSessionFactory Factory = MybatisSqlSessionFactory.getSqlSessionFactory();
	
	@Override
	public List<CodeVO> getCodeListByParent(String parentCode) {
		try (SqlSession sqlSession = Factory.openSession()) {
			ICommonCodeDao codeDao = sqlSession.getMapper(ICommonCodeDao.class);
			
			List<CodeVO> list = codeDao.getCodeListByParent(parentCode);
			return list;
		} 
	}
	

}
