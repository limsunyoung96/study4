package com.di.step2;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TestBoardService {

	public static void main(String[] args) {
//		AbstractApplicationContext context = new GenericXmlApplicationContext("spring/step2.xml");
//		//BoardService boardService = new BoardService();
//		BoardService service = context.getBean("boardService", BoardService.class);
//		
//		BoardService boardService = BoardService.getInstance();//new BoardService();
//		System.out.println(boardService);
//		service.getBoardList();
//		
//		BoardService boardService2 = BoardService.getInstance();//new BoardService();
//		System.out.println(boardService2);
//		service.getBoardList();
//		
//		BoardService boardService3 = BoardService.getInstance();//new BoardService();
//		System.out.println(boardService3);
//		service.getBoardList();
		
		
		// Spring 설정을 읽어서 해당 빈을 받아서 실행
		AbstractApplicationContext context	= new GenericXmlApplicationContext("spring/step2.xml");
//		BoardService boardService = new BoardService();
		BoardService service = context.getBean("boardService", BoardService.class);
		System.out.println(service);
		service.getBoardList();
		
		BoardService service2 = context.getBean("boardService", BoardService.class);
		System.out.println(service2);
		service2.getBoardList();
		
		context.close();
	}
	
}
