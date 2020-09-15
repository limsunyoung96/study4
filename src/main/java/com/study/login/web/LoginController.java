package com.study.login.web;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.common.util.CookieUtils;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.login.service.ILoginService;
import com.study.login.vo.UserVO;

@Controller
public class LoginController{

	@Inject
	private ILoginService loginService;// = new LoginServiceImpl();
	
	// Common logging: private final Log logger = LogFactory.getLog(getClass());
	// SLF4J 조금 더 향상된 Log 퍼시드 객체
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(path ="/login/login.wow", method = RequestMethod.GET)
	public String loginGet() throws Exception {
		return "login/login";
	}

	@RequestMapping(path ="/login/login.wow", method = RequestMethod.POST)
	public String loginPostt(UserVO vo
							, HttpSession session
							, @RequestParam(name = "rememberMe", required = false) String remember
							, HttpServletResponse resp
							, ModelMap model) throws Exception {
		// Common Logging: logger.debug("UserVo="+vo+", remember="+remember);
		// 여러 값을 콤마로 구분하여 처리
		// {}를 사용하는 것도 가능
		// logger.debug("UserVo=", vo, ", remember=", remember);
		logger.debug("UserVo={}, remember={}",vo ,remember);
		
		try {
			UserVO userVO = loginService.loginCheck(vo);
			// if(remember != null && remember.equals("Y")){
			if("Y".equals(remember)) {
			 	Cookie cookie = CookieUtils.createCookie("SAVE_ID", vo.getUserId(), "/", 60*60*24*31);
			 	resp.addCookie(cookie);
			}else{
				resp.addCookie(CookieUtils.createCookie("SAVE_ID", "", "/", 0));
			}
			// 현재 사용자정보(UserVO)를 세션에 저장 
			logger.debug("세션에 정보저장 :{} ", userVO);
			session.setAttribute("USER_INFO", userVO);
			return "redirect:/";
		} catch (BizNotFoundException | BizPasswordNotMatchedException e) {
			logger.error(e.getMessage(),e);
			ResultMessageVO message = new ResultMessageVO();
			message.setResult(false)
					.setTitle("로그인 실패")
					.setMessage("회원존재하지 않거나 비밀번호가 틀립니다.");
			model.addAttribute("messageVO", message);
			return "common/message";
		}
	} 

	@RequestMapping(path ="/login/logout.wow")
	public String logout(HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("USER_INFO");
		loginService.logout(user);
		logger.debug("로그아웃={}",user);
		session.invalidate();
		return "redirect:/";
	}
	
	
	/*public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 요청 메서드가  GET : login 화면  , POST : loginCheck 를 할 거예요
		if("GET".equals(req.getMethod())) {
			return "login/login";
		}else if("POST".equals(req.getMethod())) {
			// 로그인 체크 
			UserVO vo = new UserVO();
			vo.setUserId(req.getParameter("userId"));
			vo.setUserPass(req.getParameter("userPass"));			
			try {
				UserVO userVO = loginService.loginCheck(vo);
				HttpSession session = req.getSession();
				
				String remember = req.getParameter("rememberMe");
				// if(remember != null && remember.equals("Y")){
				if("Y".equals(remember)) {
				 	Cookie cookie = CookieUtils.createCookie("SAVE_ID", vo.getUserId(), "/", 60*60*24*31);
				 	resp.addCookie(cookie);
				}else{
					resp.addCookie(CookieUtils.createCookie("SAVE_ID", "", "/", 0));
				}
				// 현재 사용자정보(UserVO)를 세션에 저장 
				System.out.println("세션에 정보저장 : " + userVO);
				session.setAttribute("USER_INFO", userVO);
				return "redirect:/";
			} catch (BizNotFoundException | BizPasswordNotMatchedException e) {
				e.printStackTrace();
				ResultMessageVO message = new ResultMessageVO();
				message.setResult(false)
						.setTitle("로그인 실패")
						.setMessage("회원존재하지 않거나 비밀번호가 틀립니다.");
				req.setAttribute("messageVO", message);
				return "common/message";
			}
		}else {
			// GET/POST 아니면 에러 
			throw new ServletException("지원하지 않는 메서드 요청입니다.");
		}
	} // process
	*/
}
