package com.di.step5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class TestBoardService {

	public static void main(String[] args) {
		// Spring 설정을 읽어서 해당 빈을 받아서 실행
		AbstractApplicationContext context
			= new AnnotationConfigApplicationContext(JavaConfigScan.class);

		BoardService service = context.getBean("boardService", BoardService.class);
		service.getBoardList();
		
		context.close();
	}
	
}
