package com.study.free.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.study.attach.vo.AttachVO;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.valid.ModifyType;
import com.study.common.valid.RegistType;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.exception.DaoDuplicateKeyException;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;
import com.study.util.StudyAttachUtils;

@Controller
// @RequestMapping("/free")
public class FreeBoardController {

	@Autowired
	private IFreeBoardService freeBoardService; // = new FreeBoardServiceImpl();

	@Inject
	private ICommonCodeService codeService; // = new CommonCodeServiceImpl();

	@Inject
	private StudyAttachUtils attachUtils;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	// @ModelAttribute 를 통해 공통으로 사용되는 모델객체 ( 일반적으로 공통코드 목록)
	// 요청메서드 진입 전에 호출 됩니다.
	@ModelAttribute("categoryList")
	public List<CodeVO> getCetegoryList() {
		logger.debug("getCetegoryList call");
		// System.out.println("getCetegoryList call");
		List<CodeVO> categoryList = codeService.getCodeListByParent("BC00");
		return categoryList;
	}

	@RequestMapping(value = { "/free/freeList.wow", "/free/" })
	public String freeList(@ModelAttribute("searchVO") FreeBoardSearchVO searchVO, ModelMap model) throws Exception {
		logger.debug("freeList 메서드 call");
		// System.out.println("freeList 메서드 call");
		// 파라미터에 선언한 커멘드 객체는 자동으로 모델에 저장
		// 이름은 첫글자 소문자 클래스명
		// 모델정보를 저장 할때는 request 보다는 ModelMap, Model, Map 을 활용

		List<FreeBoardVO> boards = freeBoardService.getBoardList(searchVO);
		model.addAttribute("boards", boards);

		return "free/freeList";
	}

	@RequestMapping("/free/freeView.wow")
	public String freeView(@RequestParam(value = "boNo") int no,
			@RequestParam(value = "mode", required = false, defaultValue = "HaEun") String mode, ModelMap model)
			throws Exception {
		try {
			logger.debug("mode :{} ", mode);
			// System.out.println(mode);
			FreeBoardVO free = freeBoardService.getBoard(no);
			if (free != null) {
				freeBoardService.increaseHit(no);
			}
			model.addAttribute("boardVo", free);
			return "free/freeView";
		} catch (BizNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			ResultMessageVO message = new ResultMessageVO();
			message.setResult(false).setTitle("조회실패").setMessage("해당 글이 존재하지 않습니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로");
			model.addAttribute("messageVO", message);
			return "common/message";
		}
	}

	@RequestMapping("/free/freeForm.wow")
	public void freeForm(@ModelAttribute("boardVO") FreeBoardVO free) throws Exception {
		// 스프링 폼태그 사용시 해당 모델이름으로 속성에 저장이 되어 있어야 하므로
		// @ModelAttribute를 사용하여 모델 저장. 아래로 대체할 수 있지만 위의 방법을 추천

	}

	@RequestMapping(path = "/free/freeRegist.wow"
			       , method = RequestMethod.POST )
	public ModelAndView freeRegist(@ModelAttribute("boardVO") @Validated({Default.class, RegistType.class}) FreeBoardVO board
										, BindingResult errors
										, @RequestParam(name="boFiles", required = false) MultipartFile[] boFiles
										, HttpServletRequest req) throws Exception {
		logger.debug("board={}", board);
				
		ModelAndView mav = new ModelAndView();
		
		if(errors.hasErrors()){
			mav.setViewName("/free/freeForm");
			return mav;
		}

		if (boFiles != null) {
			List<AttachVO> attaches = attachUtils.getAttachListByMultiparts(boFiles, "FREE", "free");
			board.setAttaches(attaches);
		}

		ResultMessageVO message = new ResultMessageVO();		
		try {
			board.setBoIp(req.getRemoteAddr());
			freeBoardService.registBoard(board);
			// 글 입력 성공시 메시지를 보여줄 필요 없이 바로 목록으로 가고자 한다면
			// return "redirect:/free/freeList.wow";
			mav.setViewName("redirect:/free/freeList.wow");

		} catch (DaoDuplicateKeyException e) {
			logger.error(e.getMessage(), e);
			message.setResult(false).setTitle("글 등록 실패").setMessage("해당 글번호가 존재합니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로");
			// 속성에 messageVO 로 저장
			mav.addObject("messageVO", message);
			mav.setViewName("common/message");
		}
		return mav;

	}

	// freeEdit
	@RequestMapping("/free/freeEdit.wow")
	public String freeEdit(int boNo, ModelMap model) {
		logger.debug("boNo={}", boNo);
		try {
			FreeBoardVO free = freeBoardService.getBoard(boNo);
			model.addAttribute("boardVO", free);
			model.addAttribute("categorylist", getCetegoryList());
			return "free/freeEdit";

		} catch (BizNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			ResultMessageVO message = new ResultMessageVO();
			message.setResult(false).setTitle("조회 실패").setMessage("해당 글이 존재하지 않습니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로");
			model.addAttribute("messageVO", message);
			return "common/message";
		}
	}

	// freeModify
	@RequestMapping("/free/freeModify.wow")
	// @ModelAttribute는 별칭을 주는 기능이 있고,
	// validated({ModifyType.class})만 쓰면 다른거 있어도 안하고 modifyType.class를 groups로 갖는
	// boNo만 확인함. 따라서 Defalut.calss 필요
	public String freeModify(
			@ModelAttribute("boardVO") @Validated({ Default.class, ModifyType.class }) FreeBoardVO board,
			BindingResult errors, ModelMap model) throws Exception {
		logger.debug("board={}", board);
		List<CodeVO> categoryList = codeService.getCodeListByParent("BC00");
		model.addAttribute("categorylist", categoryList);

		if (errors.hasErrors()) {
			// 검증 오류가 있으므로 입력화면으로 뷰 이동
			return "free/freeEdit";
		}

		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			freeBoardService.modifyBoard(board);
			return "redirect:/free/freeView.wow?boNo=" + board.getBoNo();

		} catch (BizNotFoundException e) {
			logger.error(e.getMessage(), e);
			messageVO.setResult(false).setTitle("글 수정 실패").setMessage("해당 글이 존재하지 않습니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로"); // Method Chaining
		} catch (BizPasswordNotMatchedException e) {
			logger.error(e.getMessage(), e);
			messageVO.setResult(false).setTitle("글 수정 실패").setMessage("해당 글 비밀번호가 일치하지 않습니다.")
					.setUrl("/free/freeList.wow").setUrlTitle("목록으로"); // Method Chaining
		}
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}

	@RequestMapping("/free/freeDelete.wow")
	public String freeDelete(FreeBoardVO board, ModelMap model) throws Exception {
		logger.debug("board={}", board);
		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			freeBoardService.removeBoard(board);

			messageVO.setResult(true).setTitle("글 삭제 성공").setMessage("선택하신 목록이 삭제 되었습니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로"); // Method Chaining

		} catch (BizNotFoundException e) {
			logger.error(e.getMessage(), e);
			messageVO.setResult(false).setTitle("글 삭제 실패").setMessage("해당 글이 존재하지 않습니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로"); // Method Chaining

		} catch (BizPasswordNotMatchedException e) {
			logger.error(e.getMessage(), e);
			messageVO.setResult(false).setTitle("글 삭제 실패").setMessage("해당 글 비밀번호가 일치하지 않습니다.")
					.setUrl("/free/freeList.wow").setUrlTitle("목록으로"); // Method Chaining
		}
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}

}
