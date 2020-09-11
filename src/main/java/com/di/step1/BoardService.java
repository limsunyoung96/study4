package com.di.step1;

public class BoardService {

	//private BoardDaoOracle boardDaoOracle = new BoardDaoOracle();
	private IBoardDao boardDao = new BoardDaoOracle();
	
	public void getBoardList() {
		boardDao.getBoardList();
	}
	
	
}
