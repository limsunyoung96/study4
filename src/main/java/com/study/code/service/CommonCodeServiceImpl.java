package com.study.code.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.study.code.dao.ICommonCodeDao;
import com.study.code.vo.CodeVO;
 
@Service
//@Repository()
public class CommonCodeServiceImpl implements ICommonCodeService {

	@Inject
	//@Autowired 도 가능
	private ICommonCodeDao codeDao;

	@Override
	public List<CodeVO> getCodeListByParent(String parentCode) {

		List<CodeVO> list = codeDao.getCodeListByParent(parentCode);
		return list;
	}

}
