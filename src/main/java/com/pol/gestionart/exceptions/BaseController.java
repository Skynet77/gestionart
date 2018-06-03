package com.pol.gestionart.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pol.gestionart.controller.HomeController;

//@ControllerAdvice
public class BaseController {

	private static final Log baseLogger = LogFactory.getLog(HomeController.class);
	//Mapping
	public static final String ERROR_PAGE_URL = "common/errorPage";
	public static final String REDIRECT_TO = "redirect:";
	public static final String GO_TO_LOGIN_PAGE = "redirect:/login";
	//VIEWS
	public static final String ERROR_AJAX_VIEW = "common/errorPageAjax";

	public BaseController() {

	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String handleHttpRequestMethodNotSupportedException(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session, Exception exception) {
		baseLogger.debug(exception);

		return REDIRECT_TO + "/home";
	}

	

	@ExceptionHandler(WebAppException.class)
	public String handleWebAppException(Model model,WebAppException pe, HttpServletResponse response,HttpSession session){
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		/*if (ErrorConstants.AUTH_SESSION_EXPIRED.getCodigo().equals(errorBody.getCode())) {
			session.invalidate();
			return AuthenticationController.GO_TO_SESSION_INVALID;
		}*/
		ErrorData errorData = new ErrorData();
		errorData.setType("WEB");
		errorData.setCode("200518");
		errorData.setDescription(pe.getMessage());
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			model.addAttribute("error",pe.getMessage());
		
		return ERROR_PAGE_URL;
	}


	@ExceptionHandler(AjaxException.class)
	public @ResponseBody ErrorData handleWebAppException(Model model,AjaxException pe, HttpServletResponse response,HttpSession session){
		ErrorData errorData = new ErrorData();
		errorData.setType("WEB");
		errorData.setCode("200518");
		errorData.setDescription(pe.getMessage());
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		baseLogger.error(errorData);
		return errorData;
	}


	

}
