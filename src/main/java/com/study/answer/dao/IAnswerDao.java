package com.study.answer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.answer.vo.AnswerSearchVO;
import com.study.answer.vo.AnswerVO;

@Mapper
public interface IAnswerDao {

	public int getAnswerCountByParent(AnswerSearchVO searchVO);

	public List<AnswerVO> getAnswerListByParent(AnswerSearchVO searchVO);

	public AnswerVO getAnswer(int reNo);

	public int insertAnswer(AnswerVO answer);

	public int updateAnswer(AnswerVO answer);

	public int deleteAnswer(AnswerVO answer);
}
