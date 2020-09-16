package com.study.question.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.question.dao.IQuestionBoardDao;
import com.study.question.vo.QuestionBoardSearchVO;
import com.study.question.vo.QuestionBoardVO;

@Service
public class QuestionBoardServiceImpl implements IQuestionBoardService {
	
	@Inject
	private IQuestionBoardDao questionBoardDao;

	@Override
	public List<QuestionBoardVO> getBoardList(QuestionBoardSearchVO searchVO) {
		// 건수를 구해서 searchVO 설정 -> searchVO.pageSetting() -> list 호출
		int cnt = questionBoardDao.getBoardCount(searchVO);
		searchVO.setTotalRowCount(cnt);
		searchVO.pageSetting();
		List<QuestionBoardVO> list = questionBoardDao.getBoardList(searchVO);
		return list;
	}

	@Override
	public QuestionBoardVO getBoard(int boNo) throws BizNotFoundException {
		QuestionBoardVO vo = questionBoardDao.getBoard(boNo);
		if (vo == null) {
			throw new BizNotFoundException("[" + boNo + "] 조회 실패");
		}
//			System.out.println(vo);
		return vo;
	}

	@Override
	public void registBoard(QuestionBoardVO board) {
		System.out.println("이거 되닝");
		questionBoardDao.insertBoard(board);

	}

	@Override
	public void modifyBoard(QuestionBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {

		QuestionBoardVO vo = questionBoardDao.getBoard(board.getBoNo());
		if (vo == null) {
			throw new BizNotFoundException();
		}
		if (!(vo.getBoPass().equals(board.getBoPass()))) {
			throw new BizPasswordNotMatchedException();
		}
		int cnt = questionBoardDao.updateBoard(board);
		if (cnt < 1) {
			throw new BizNotEffectedException();
		}
//			BizPasswordNotMatchedException 이거 던지려면 !=대신 !( .equals)
	}

	@Override
	public void removeBoard(QuestionBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {

		QuestionBoardVO vo = questionBoardDao.getBoard(board.getBoNo());
		if (vo == null) {
			throw new BizNotFoundException();
		}
		if (!(vo.getBoPass().equals(board.getBoPass()))) {
			throw new BizPasswordNotMatchedException();
		}
		int cnt = questionBoardDao.deleteBoard(board);
		if (cnt < 1) {
			throw new BizNotEffectedException();
		}
//			BizPasswordNotMatchedException 이거 던지려면 !=대신 !( .equals)
	}

	@Override
	public void increaseHit(int boNo) {
		questionBoardDao.increaseHit(boNo);
	}

}
