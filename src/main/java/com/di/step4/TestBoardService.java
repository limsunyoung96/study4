package com.di.step4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class TestBoardService {

	public static void main(String[] args) {
		
		// Spring 설정을 읽어서 해당 빈을 받아서 실행
		AbstractApplicationContext context
			= new AnnotationConfigApplicationContext(JavaConfigDirect.class);

		BoardService service = context.getBean("getBoardService", BoardService.class);
		System.out.println(service);
		service.getBoardList();
		
		context.close();
	}
	
}
