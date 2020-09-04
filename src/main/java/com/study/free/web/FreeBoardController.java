package com.study.free.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.exception.DaoDuplicateKeyException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Controller
// @RequestMapping("/free")
public class FreeBoardController {

	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();
		
	// @ModelAttribute 를 통해 공통으로 사용되는 모델객체 ( 일반적으로 공통코드 목록)
	// 요청메서드 진입 전에 호출 됩니다.
	@ModelAttribute("cateList")
	public List<CodeVO> getCetegoryList() {
		System.out.println("getCetegoryList call");
	 	List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
		return cateList;
	}
	
	
	@RequestMapping(value= {"/free/freeList.wow", "/free/"})
	public String freeList(@ModelAttribute("searchVO") FreeBoardSearchVO searchVO
			             , ModelMap model ) throws Exception {
		System.out.println("freeList 메서드 call");
		// 파라미터에 선언한 커멘드 객체는 자동으로 모델에 저장 
		// 이름은 첫글자 소문자 클래스명 
		// 모델정보를 저장 할때는 request 보다는 ModelMap, Model, Map 을 활용 
		
		List<FreeBoardVO> boards = freeBoardService.getBoardList(searchVO);
		model.addAttribute("boards", boards);
	 	
	 	return "free/freeList";	
	}
	
	@RequestMapping("/free/freeView.wow")
	public String freeView(@RequestParam(value = "boNo")  int no 
		  , @RequestParam(value = "mode" , required = false, defaultValue = "HaEun") String mode
		  , ModelMap model) throws Exception {
		try {			
			System.out.println(mode);
			FreeBoardVO free = freeBoardService.getBoard(no);
			if(free != null){
				freeBoardService.increaseHit(no);
			}
			model.addAttribute("free", free);			
			return "free/freeView";	
		} catch (BizNotFoundException ex) {
			ex.printStackTrace();
			ResultMessageVO message = new ResultMessageVO();
			message.setResult(false)
			       .setTitle("조회실패")
			       .setMessage("해당 글이 존재하지 않습니다.")
			       .setUrl("/free/freeList.wow")
			       .setUrlTitle("목록으로");
			model.addAttribute("messageVO", message);
			return "common/message";			
		}
	}
	

	@RequestMapping("/free/freeForm.wow")
	public void freeForm(ModelMap model) throws Exception {
		// @ModelAttribute 를 통해 처리 되었슴
	 	// List<CodeVO> categoryList = codeService.getCodeListByParent("BC00");
	 	// model.addAttribute("cateList", categoryList);
		
	}	
	
	@RequestMapping(path = "/free/freeRegist.wow"
			       , method = RequestMethod.POST )
	public ModelAndView freeRegist(FreeBoardVO board, HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		ResultMessageVO message = new ResultMessageVO();		
		try {
			board.setBoIp(req.getRemoteAddr());
			freeBoardService.registBoard(board);	
			// 글 입력 성공시 메시지를 보여줄 필요 없이 바로 목록으로 가고자 한다면 
			// return "redirect:/free/freeList.wow";
			mav.setViewName("redirect:/free/freeList.wow");
			
		} catch (DaoDuplicateKeyException e) {
			e.printStackTrace();
			message.setResult(false)
			         .setTitle("글 등록 실패")
			         .setMessage("해당 글번호가 존재합니다.")
			         .setUrl("/free/freeList.wow")
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

		try {
			FreeBoardVO free = freeBoardService.getBoard(boNo);
			model.addAttribute("free", free);
			return "free/freeEdit";

		} catch (BizNotFoundException ex) {
			ex.printStackTrace();
			ResultMessageVO message = new ResultMessageVO();
			message.setResult(false).setTitle("조회 실패").setMessage("해당 글이 존재하지 않습니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로");
			model.addAttribute("messageVO", message);
			return "common/message";
		}
	}
	
	// freeModify
	@RequestMapping("/free/freeModify.wow")
	public String freeModify(FreeBoardVO board, ModelMap model) throws Exception {
		ResultMessageVO messageVO = new ResultMessageVO();
		
		try {
			freeBoardService.modifyBoard(board);
			return "redirect:/free/freeView.wow?boNo=" + board.getBoNo();  
			
		} catch (BizNotFoundException e) {
			e.printStackTrace();
			messageVO.setResult(false)  
					 .setTitle("글 수정 실패")
					 .setMessage("해당 글이 존재하지 않습니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로"); // Method Chaining
		} catch (BizPasswordNotMatchedException e) {
			e.printStackTrace();
			messageVO.setResult(false)  
		     		 .setTitle("글 수정 실패")
		     		 .setMessage("해당 글 비밀번호가 일치하지 않습니다.")
		     		 .setUrl("/free/freeList.wow")
		     		 .setUrlTitle("목록으로"); // Method Chaining
		} 
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}
	
	@RequestMapping("/free/freeDelete.wow")
	public String freeDelete(FreeBoardVO board, ModelMap model) throws Exception {
		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			freeBoardService.removeBoard(board);
			
			messageVO.setResult(true)  
				     .setTitle("글 삭제 성공")
				     .setMessage("선택하신 목록이 삭제 되었습니다.")
				     .setUrl("/free/freeList.wow")
				     .setUrlTitle("목록으로"); // Method Chaining
			
		} catch (BizNotFoundException e) {
			e.printStackTrace();
			messageVO.setResult(false)  
					 .setTitle("글 삭제 실패")
					 .setMessage("해당 글이 존재하지 않습니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로"); // Method Chaining
			
		} catch (BizPasswordNotMatchedException e) {
			e.printStackTrace();
			messageVO.setResult(false)  
					 .setTitle("글 삭제 실패")
					 .setMessage("해당 글 비밀번호가 일치하지 않습니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로"); // Method Chaining
		}
		// 속성에 messageVO로 저장
		model.addAttribute("messageVO", messageVO);
		return "common/message";
	}
	
}




