package com.study.servlet;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.exception.BizNotFoundException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;

@Controller
//@RestController
public class RestTestController {

	// IMemberService memberService = new MemberServiceImpl();
	private final Logger logger = LoggerFactory.getLogger(getClass());
	IFreeBoardService boardService = new FreeBoardServiceImpl();

	// /book/12/view, .book/35/view, /book/35/delete
	@RequestMapping(value = "/book/{no}/{mode}")
	public String p1(@PathVariable("no") int boNo, @PathVariable("mode")String mode, ModelMap model) throws Exception {

		logger.debug("no={}, mode={}", boNo, mode);

		FreeBoardVO board = boardService.getBoard(boNo);
		model.addAttribute("board", board);
		return "test";
	}

	@RequestMapping(value = "/test.nhn", produces = "text/html; charset=utf-8")
	public String t1() {
		return "졸면 안됑 NoNo";
	}

	@RequestMapping("/test.daum")
	public void t2() {

	}

	@RequestMapping("/test.kakao")
	public Map<String, Object> t3() {
		Map<String, Object> syMap = new HashedMap();
		syMap.put("msg", "소뇽 최고~~");
		return syMap;
	}

//	@RequestMapping("/test.kbs")
//	@ResponseBody
//	public String t4() {
//		return "test";
//	}
//	
//	@RequestMapping("/test.mbc")
//	public @ResponseBody Map t5() {
//		Map<String, Object> syMap = new HashedMap();
//		syMap.put("msg", "소뇽 최고~~");
//		return syMap;
//	}
}
