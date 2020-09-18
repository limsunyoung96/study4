package com.study.answer.service;

import java.util.List;

import com.study.exception.BizAccessFailException;
import com.study.exception.BizException;
import com.study.exception.BizNotFoundException;
import com.study.answer.vo.AnswerSearchVO;
import com.study.answer.vo.AnswerVO;

public interface IAnswerService {
	
	/**
	 * 댓글 목록 조회 <br>
	 * <b>필수 : reCategory, reParentNo </b>
	 */
	public List<AnswerVO> getAnswerListByParent(AnswerSearchVO searchVO);

	/** 댓글등록 */
	public void registAnswer(AnswerVO answer) throws BizException;

	/**
	 * 댓글 수정 <br>
	 * 댓글이 존재하지 않으면 BizNotFoundException 
	 * 댓글 작성자와 로그인 사용자가 다른 경우 BizAccessFailException
	 */
	public void modifyAnswer(AnswerVO answer) throws BizNotFoundException, BizAccessFailException;

	/**
	 * 댓글 삭제 <br>
	 * 해당글의 존재하지 않으면 BizNotFoundException 
	 * 댓글 작성자와 로그인 사용자가 다른 경우 BizAccessFailException
	 */
	public void removeAnswer(AnswerVO answer) throws BizNotFoundException, BizAccessFailException;
	
}