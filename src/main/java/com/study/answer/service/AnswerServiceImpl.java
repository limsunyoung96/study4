package com.study.answer.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.study.exception.BizAccessFailException;
import com.study.exception.BizException;
import com.study.exception.BizNotFoundException;
import com.study.answer.dao.IAnswerDao;
import com.study.answer.vo.AnswerSearchVO;
import com.study.answer.vo.AnswerVO;

@Service
public class AnswerServiceImpl implements IAnswerService {

	@Inject
	private IAnswerDao answerDao;

	@Override
	public List<AnswerVO> getAnswerListByParent(AnswerSearchVO searchVO) {
		int rowCount = answerDao.getAnswerCountByParent(searchVO);
		searchVO.setTotalRowCount(rowCount);
		searchVO.pageSetting();
		return answerDao.getAnswerListByParent(searchVO);
	}

	@Override
	public void registAnswer(AnswerVO answer) throws BizException {
		answerDao.insertAnswer(answer);
	}

	@Override
	public void modifyAnswer(AnswerVO answer) throws BizNotFoundException, BizAccessFailException {
		AnswerVO vo = answerDao.getAnswer(answer.getReNo());
		if (vo == null) {
			throw new BizNotFoundException("글번호[" + answer.getReNo() + "]을 조회하지 못했습니다.");
		}
		if (!vo.getReMemId().equals(answer.getReMemId())) {
			throw new BizAccessFailException("해당 글의 작성자가 아닙니다.");
		}
		answerDao.updateAnswer(answer);
	}

	@Override
	public void removeAnswer(AnswerVO answer) throws BizNotFoundException, BizAccessFailException {
		AnswerVO vo = answerDao.getAnswer(answer.getReNo());
		if (vo == null) {
			throw new BizNotFoundException("글번호[" + answer.getReNo() + "]을 조회하지 못했습니다.");
		}
		if (!vo.getReMemId().equals(answer.getReMemId())) {
			throw new BizAccessFailException("해당 글의 작성자가 아닙니다.");
		}
		answerDao.deleteAnswer(answer);
	}

}