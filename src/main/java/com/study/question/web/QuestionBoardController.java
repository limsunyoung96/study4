package com.study.question.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.valid.ModifyType;
import com.study.common.valid.RegistType;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.exception.DaoDuplicateKeyException;
import com.study.login.vo.UserVO;
import com.study.question.service.IQuestionBoardService;
import com.study.question.vo.QuestionBoardSearchVO;
import com.study.question.vo.QuestionBoardVO;

@Controller
// @RequestMapping("/question")
public class QuestionBoardController {

	@Autowired
	IQuestionBoardService questionBoardService; // = new QuestionBoardServiceImpl();

	@Inject
	ICommonCodeService codeService; // = new CommonCodeServiceImpl();

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// @ModelAttribute 를 통해 공통으로 사용되는 모델객체 ( 일반적으로 공통코드 목록)
	// 요청메서드 진입 전에 호출 됩니다.
	@ModelAttribute("categoryList")
	public List<CodeVO> getCategoryList() {
		logger.debug("getCategoryList call");
		// System.out.println("getCategoryList call");
		List<CodeVO> categoryList = codeService.getCodeListByParent("QA00");
		return categoryList;
	}

	@RequestMapping(value = { "/question/questionList.wow", "/question/" })
	public String questionList(@ModelAttribute("searchVO") QuestionBoardSearchVO searchVO, ModelMap model)
			throws Exception {
		logger.debug("questionList 메서드 call");
		// System.out.println("questionList 메서드 call");
		// 파라미터에 선언한 커멘드 객체는 자동으로 모델에 저장
		// 이름은 첫글자 소문자 클래스명
		// 모델정보를 저장 할때는 request 보다는 ModelMap, Model, Map 을 활용

		List<QuestionBoardVO> boards = questionBoardService.getBoardList(searchVO);
		model.addAttribute("boards", boards);
		model.addAttribute("cateList", getCategoryList());
  
		return "question/questionList";
	}

	@RequestMapping("/question/questionView.wow")
	public String questionView(@RequestParam(value = "boNo") int no,
			@RequestParam(value = "mode", required = false, defaultValue = "sunyoung") String mode, ModelMap model)
			throws Exception {
		try {
			logger.debug("mode :{} ", mode);
			// System.out.println(mode);
			QuestionBoardVO question = questionBoardService.getBoard(no);
			if (question != null) {
				//questionBoardService.increaseHit(no);
			}
			model.addAttribute("boardVo", question);
			return "question/questionView";
		} catch (BizNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			ResultMessageVO message = new ResultMessageVO();
			message.setResult(false).setTitle("조회실패").setMessage("해당 글이 존재하지 않습니다.")
					.setUrl("/question/questionList.wow").setUrlTitle("목록으로");
			model.addAttribute("messageVO", message);
			return "common/message";
		}
	}

	@RequestMapping("/question/questionForm.wow")
	public void questionForm(@ModelAttribute("boardVO") QuestionBoardVO question) throws Exception {
		// 스프링 폼태그 사용시 해당 모델이름으로 속성에 저장이 되어 있어야 하므로
		// @ModelAttribute를 사용하여 모델 저장. 아래로 대체할 수 있지만 위의 방법을 추천

	}

	@RequestMapping(path = "/question/questionRegist.wow", method = RequestMethod.POST)
	public ModelAndView questionRegist(
			@ModelAttribute("boardVO") @Validated({ Default.class, RegistType.class }) QuestionBoardVO board,
			BindingResult errors, HttpServletRequest req, HttpSession session) throws Exception {
		logger.debug("board={}", board);
		ModelAndView mav = new ModelAndView();
		ResultMessageVO message = new ResultMessageVO();
		UserVO user = (UserVO) session.getAttribute("USER_INFO");
		
		if (errors.hasErrors()) {
			mav.setViewName("/question/questionForm");
			return mav;
		}
		try {
			// 여기IP였어 
			// board.setBoID(); // (req.getRemoteAddr());
			
			// 사용자 아이디
			board.setBoId(user.getUserId());
			
			// 사용자 이름
			board.setBoWriter(user.getUserName());
			
			// 게시글 비밀번호
			board.setBoPass(user.getUserPass());
			
			questionBoardService.registBoard(board);
			// 글 입력 성공시 메시지를 보여줄 필요 없이 바로 목록으로 가고자 한다면
			// return "redirect:/question/questionList.wow";
			mav.setViewName("redirect:/question/questionList.wow");

		} catch (DaoDuplicateKeyException e) {
			logger.error(e.getMessage(), e);
			message.setResult(false).setTitle("글 등록 실패").setMessage("해당 글번호가 존재합니다.")
					.setUrl("/question/questionList.wow").setUrlTitle("목록으로");
			// 속성에 messageVO 로 저장
			mav.addObject("messageVO", message);
			mav.setViewName("common/message");
		}
		return mav;

	}

	// questionEdit
	@RequestMapping("/question/questionEdit.wow")
	public String questionEdit(int boNo, ModelMap model) {
		logger.debug("boNo={}", boNo);
		try {
			QuestionBoardVO question = questionBoardService.getBoard(boNo);
			model.addAttribute("boardVO", question);
			model.addAttribute("categorylist", getCategoryList());
			return "question/questionEdit";

		} catch (BizNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			ResultMessageVO message = new ResultMessageVO();
			message.setResult(false).setTitle("조회 실패").setMessage("해당 글이 존재하지 않습니다.")
					.setUrl("/question/questionList.wow").setUrlTitle("목록으로");
			model.addAttribute("messageVO", message);
			return "common/message";
		}
	}

	// questionModify
	@RequestMapping("/question/questionModify.wow")
	// @ModelAttribute는 별칭을 주는 기능이 있고,
	// validated({ModifyType.class})만 쓰면 다른거 있어도 안하고 modifyType.class를 groups로 갖는
	// boNo만 확인함. 따라서 Defalut.calss 필요
	public String questionModify(
			@ModelAttribute("boardVO") @Validated({ Default.class, ModifyType.class }) QuestionBoardVO board,
			BindingResult errors, ModelMap model) throws Exception {
		logger.debug("board={}", board);

		model.addAttribute("categorylist", getCategoryList());

		if (errors.hasErrors()) {
			// 검증 오류가 있으므로 입력화면으로 뷰 이동
			return "question/questionEdit";
		}

		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			questionBoardService.modifyBoard(board);
			return "redirect:/question/questionView.wow?boNo=" + board.getBoNo();

		} catch (BizNotFoundException e) {
			logger.error(e.getMessage(), e);
			messageVO.setResult(false).setTitle("글 수정 실패").setMessage("해당 글이 존재하지 않습니다.")
					.setUrl("/question/questionList.wow").setUrlTitle("목록으로"); // Method Chaining
		} catch (BizPasswordNotMatchedException e) {
			logger.error(e.getMessage(), e);
			messageVO.setResult(false).setTitle("글 수정 실패").setMessage("해당 글 비밀번호가 일치하지 않습니다.")
					.setUrl("/question/questionList.wow").setUrlTitle("목록으로"); // Method Chaining
		}
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}

	@RequestMapping("/question/questionDelete.wow")
	public String questionDelete(QuestionBoardVO board, ModelMap model) throws Exception {
		logger.debug("board={}", board);
		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			questionBoardService.removeBoard(board);

			messageVO.setResult(true).setTitle("글 삭제 성공").setMessage("선택하신 목록이 삭제 되었습니다.")
					.setUrl("/question/questionList.wow").setUrlTitle("목록으로"); // Method Chaining

		} catch (BizNotFoundException e) {
			logger.error(e.getMessage(), e);
			messageVO.setResult(false).setTitle("글 삭제 실패").setMessage("해당 글이 존재하지 않습니다.")
					.setUrl("/question/questionList.wow").setUrlTitle("목록으로"); // Method Chaining

		} catch (BizPasswordNotMatchedException e) {
			logger.error(e.getMessage(), e);
			messageVO.setResult(false).setTitle("글 삭제 실패").setMessage("해당 글 비밀번호가 일치하지 않습니다.")
					.setUrl("/question/questionList.wow").setUrlTitle("목록으로"); // Method Chaining
		}
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}

}
