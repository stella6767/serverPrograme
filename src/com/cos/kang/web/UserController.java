package com.cos.kang.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.kang.domain.user.User;
import com.cos.kang.domain.user.dto.JoinReqDto;
import com.cos.kang.domain.user.dto.LoginReqDto;
import com.cos.kang.domain.user.dto.UpdateReqDto;
import com.cos.kang.service.UserService;
import com.cos.kang.util.Script;

//http://localhost:8080/kang/board
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cmd = request.getParameter("cmd");
		// http://localhost:8080/kang/user?cmd=saveForm
		UserService userService = new UserService();

		HttpSession session = request.getSession();

		if (cmd.equals("userList")) {

			System.out.println("유저 리스트 들왔나?");

			int page = Integer.parseInt(request.getParameter("page"));
			List<User> users = userService.유저목록보기(page);

			request.setAttribute("users", users);

			int userCount = userService.유저개수();
			int lastPage = (userCount - 1) / 4;

			double currentPosition = (double) page / (lastPage) * 100;

			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPosition", currentPosition);

			RequestDispatcher dis = request.getRequestDispatcher("user/userList.jsp");
			dis.forward(request, response);

		} else if (cmd.equals("joinForm")) {

			RequestDispatcher dis = request.getRequestDispatcher("user/joinForm.jsp");
			dis.forward(request, response);

		} else if (cmd.equals("join")) {
			// 서비스 호출
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			
			JoinReqDto dto = new JoinReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			System.out.println("회원가입 : " + dto);
			
			int result = userService.회원가입(dto);
			if (result == 1) {
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "회원가입 실패");
			}

		}else if(cmd.equals("usernameCheck")) {
			BufferedReader br = request.getReader();
			String username = br.readLine();
			
			System.out.println("중복체크 : "+username);
			
			int result = userService.유저네임중복체크(username);
			System.out.println("result 값: " + result);
			PrintWriter out = response.getWriter();
			if(result == 1) {
				out.print("ok");
			}else {
				out.print("fail");
			}
			out.flush();
		}else if(cmd.equals("loginForm")) {
			RequestDispatcher dis = 
					request.getRequestDispatcher("user/loginForm.jsp");
				dis.forward(request, response);	
		}else if(cmd.equals("login")) {
			// 서비스 호출
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			LoginReqDto dto = new LoginReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			
			User userEntity = userService.로그인(dto);
			
			if(userEntity != null) {
				session.setAttribute("principal", userEntity); //인증주체
				response.sendRedirect("index.jsp"); //index는 유일한 예외
			}else {
				Script.back(response, "로그인실패");
			}
			
			
		}else if(cmd.equals("updateForm")) { //회원정보 들어가기
			
//			User principal = (User)session.getAttribute("principal");		
			
//			int id = principal.getId();
//			String username = principal.getUsername();
//			String password = principal.getPassword();
//			String email = principal.getEmail();
//			String userRole = principal.getUserRole();
//			Timestamp createDate = principal.getCreateDate();
//						
//			request.setAttribute("id", id);
//			request.setAttribute("username", username);
//			request.setAttribute("password", password);
//			request.setAttribute("email", email);
//			request.setAttribute("userRole", userRole);
//			request.setAttribute("createDate", createDate);
			
//			request.setAttribute("principal", principal);
			
			RequestDispatcher dis = 
					request.getRequestDispatcher("user/updateForm.jsp");
				dis.forward(request, response);						
		}else if(cmd.equals("update")) {
			
			int id = Integer.parseInt(request.getParameter("id"));
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			
			UpdateReqDto dto = new UpdateReqDto();
			
			dto.setUsername(username);
			dto.setId(id);
			dto.setPassword(password);
			dto.setEmail(email);
		
			int result = userService.회원수정(dto);
			
			if (result == 1) {
				session.invalidate();
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "회원수정 실패");
			}
			
			
		}else if(cmd.equals("delete")) {
				
			int id = Integer.parseInt(request.getParameter("id"));
						
			int result = userService.회원삭제(id);
			
			if (result == 1) {
				session.invalidate();
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "회원삭제 실패");
			}
						
		}else if(cmd.equals("logout")) {
			session.invalidate();
			response.sendRedirect("index.jsp");
		}else if(cmd.equals("deleteByAdmin")) {
			
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println("관리자 권한으로 삭제: " + id);
			int result = userService.회원삭제(id);
			
			if (result == 1) {
				Script.responseData(response, "성공");
			} else {
				Script.back(response, "회원삭제 실패");
			}
			
		}
		
		
		
		
		
		

	}

}
