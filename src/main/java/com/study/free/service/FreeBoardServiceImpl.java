package com.study.free.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.attach.dao.IAttachDao;
import com.study.attach.vo.AttachVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.dao.IFreeBoardDao;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Service
public class FreeBoardServiceImpl implements IFreeBoardService {

	@Inject
	private IFreeBoardDao freeBoardDao;

	@Autowired
	private IAttachDao attachDao;

	@Override
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) {
		// 건수를 구해서 searchVO 설정 -> searchVO.pageSetting() -> list 호출
		int cnt = freeBoardDao.getBoardCount(searchVO);
		searchVO.setTotalRowCount(cnt);
		searchVO.pageSetting();
		List<FreeBoardVO> list = freeBoardDao.getBoardList(searchVO);
		return list;
	}

	@Override
	public FreeBoardVO getBoard(int boNo) throws BizNotFoundException {
		FreeBoardVO vo = freeBoardDao.getBoard(boNo);
		if (vo == null) {
			throw new BizNotFoundException("[" + boNo + "] 조회 실패");
		}
		
//		// 해당 게시물의 첨부파일 가져오기
//		AttachVO attach = new AttachVO();
//		attach.setAtchCategory("FREE");
//		attach.setAtchParentNo(boNo);
//		
//		vo.setAttaches(attachDao.getAttachByParentNoList(attach));

		return vo;
	}

	@Override
	public void registBoard(FreeBoardVO board) {
		freeBoardDao.insertBoard(board); // 여기 구문에서 freeboard.xml의 insert 내의 selectKey 구문에서 boNo에 값이 담긴다
		
		List<AttachVO> attaches = board.getAttaches();
		if (attaches != null) {
			for (AttachVO vo : attaches) {
				vo.setAtchParentNo(board.getBoNo());
				attachDao.insertAttach(vo);
			}
		}
	}

	@Override
	public void modifyBoard(FreeBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {

		FreeBoardVO vo = freeBoardDao.getBoard(board.getBoNo());
		if (vo == null) {
			throw new BizNotFoundException();
		}
		if (!(vo.getBoPass().equals(board.getBoPass()))) {
			throw new BizPasswordNotMatchedException();
		}
		int cnt = freeBoardDao.updateBoard(board);
		if (cnt < 1) {
			throw new BizNotEffectedException();
		}
//			BizPasswordNotMatchedException 이거 던지려면 !=대신 !( .equals)
	}

	@Override
	public void removeBoard(FreeBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {

		FreeBoardVO vo = freeBoardDao.getBoard(board.getBoNo());
		if (vo == null) {
			throw new BizNotFoundException();
		}
		if (!(vo.getBoPass().equals(board.getBoPass()))) {
			throw new BizPasswordNotMatchedException();
		}
		int cnt = freeBoardDao.deleteBoard(board);
		if (cnt < 1) {
			throw new BizNotEffectedException();
		}
//			BizPasswordNotMatchedException 이거 던지려면 !=대신 !( .equals)
	}

	@Override
	public void increaseHit(int boNo) {
		freeBoardDao.increaseHit(boNo);
	}

}
