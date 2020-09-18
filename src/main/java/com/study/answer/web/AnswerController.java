package com.study.answer.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.study.exception.BizAccessFailException;
import com.study.exception.BizNotFoundException;
import com.study.login.vo.UserVO;
import com.study.answer.service.IAnswerService;
import com.study.answer.vo.AnswerSearchVO;
import com.study.answer.vo.AnswerVO;

// @Controller
@RestController
public class AnswerController {

	@Autowired
	private IAnswerService answerService; // = new AnswerServiceImpl();

	@RequestMapping(value = "/answer/answerList", produces = "application/json;charset=UTF-8")
	// @ResponseBody // @Controller로 되어있으면 붙여야하고 ResponseController 하면 안붙여도 된다
	public Map<String, Object> answerList(AnswerSearchVO searchVO) throws Exception {
		List<AnswerVO> list = answerService.getAnswerListByParent(searchVO);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("data", list);
		map.put("count", list.size());
		return map;
	}

	// @PostMapping ("/answer/answerRegist")
	@RequestMapping(value = "/answer/answerRegist", method = RequestMethod.POST)
	public Map<String, Object> answerRegist(AnswerVO answer, HttpServletRequest req, HttpSession session)
			throws Exception {
		answer.setReIp(req.getRemoteAddr());
		UserVO user = (UserVO) session.getAttribute("USER_INFO");
		answer.setReMemId(user.getUserId());
		answerService.registAnswer(answer);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("msg", "정상 등록 되었습니다.");
		return map;
	}

	@RequestMapping(value = "/answer/answerModify")
	public Map<String, Object> answerModify(AnswerVO answer, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("USER_INFO");
		answer.setReMemId(user.getUserId());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			answerService.modifyAnswer(answer);
			map.put("result", true);
			map.put("msg", "수정 되었습니다.");
			return map;
		} catch (BizNotFoundException e) {
			map.put("result", false);
			map.put("msg", "글이 존재하지 않습니다.");
			return map;
		} catch (BizAccessFailException e) {
			map.put("result", false);
			map.put("msg", "접근에 실패했습니다.");
			return map;
		}
	}
	
	@RequestMapping(value = "/answer/answerDelete")
	public Map<String, Object> answerDelete(AnswerVO answer, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("USER_INFO");
		answer.setReMemId(user.getUserId());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			answerService.removeAnswer(answer);
			map.put("result", true);
			map.put("msg", "삭제 되었습니다.");
			return map;
		} catch (BizNotFoundException e) {
			map.put("result", false);
			map.put("msg", "글이 존재하지 않습니다.");
			return map;
		} catch (BizAccessFailException e) {
			map.put("result", false);
			map.put("msg", "접근에 실패했습니다.");
			return map;
		}
	}
}
