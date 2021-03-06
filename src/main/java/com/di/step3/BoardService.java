package com.di.step3;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoardService {

	//private BoardDaoOracle boardDaoOracle = new BoardDaoOracle();
	
	// IBoardDao의 객체를 생성자/setter 통해 받도록 변경
	private IBoardDao boardDao; // = new BoardDaoOracle();

	@Autowired // 실제로는 필드단에 사용하고, setter는 생성안함
	public void setBoardDao(IBoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	@PostConstruct
	public void myInit() {
		// 객체에서 필요한 초기화 작업
		System.out.println("초기화 작업을 하였습니다.");
	}

	@PreDestroy
	public void myDestroy() {
		// 객체가 소멸될 때 불필요한 자원 정리 작업
		System.out.println("자원정리를 했습니다.");
		System.out.println("이제 안뇽");
	}
	
	public void getBoardList() {
		boardDao.getBoardList();
	}
	
	
}
