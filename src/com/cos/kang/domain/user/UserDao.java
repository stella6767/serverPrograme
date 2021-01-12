package com.cos.kang.domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.kang.config.DB;
import com.cos.kang.domain.user.dto.JoinReqDto;
import com.cos.kang.domain.user.dto.LoginReqDto;
import com.cos.kang.domain.user.dto.UpdateReqDto;


public class UserDao {
	
	public int deleteById(int id) {
		
		String sql = "delete FROM user WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
		
	}
	
	
	
	
	public User findByUsernameAndPassword(LoginReqDto dto) {
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			rs = pstmt.executeQuery();
			
			//Persistance(영속성) API
			if(rs.next()) {
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.password(rs.getString("password"))
						.email(rs.getString("email"))
						.createDate(rs.getTimestamp("createDate"))
						.userRole(rs.getString("userRole"))
						.build();
				return user;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DB.close(conn, pstmt,rs);
		}
		
		return null;
	}
	
	
	public int findByUsername(String username) {
		String sql = "SELECT * FROM user WHERE username = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs =  pstmt.executeQuery();

			if(rs.next()) {
				return 1; // 있어
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		return -1; // 없어
	}
	
	public int save(JoinReqDto dto) { // 회원가입
		String sql = "INSERT INTO user(username, password, email , userRole, createDate) VALUES(?,?,?, 'USER', now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}
	
	
	public int update(UpdateReqDto dto) { // 회원수정
				
		String sql = "UPDATE user SET username = ?, password=?, email=? where id = ? ";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			pstmt.setInt(4, dto.getId());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}


	
	public int count() {
		String sql = "SELECT count(*) FROM user";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 커서를 이동하는 함수
				return rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}

		return -1;

	}
	
		
	public List<User> findAll(int page) {

		// SELECT 해서 Board 객체를 컬렉션에 담아서 리턴
		String sql = "SELECT * FROM user ORDER BY Id DESC LIMIT ?,4"; 
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page * 4); // 0 -> 0, 1 ->4, 2->8
			rs = pstmt.executeQuery();
			while (rs.next()) { // 커서를 이동하는 함수
				User user = User.builder().id(rs.getInt("id")).username(rs.getString("username"))
						.password(rs.getString("password")).email(rs.getString("email")).userRole(rs.getString("userRole"))						
						.createDate(rs.getTimestamp("createDate")).build();
				users.add(user);
			}

			return users;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}

		return null;
	}

}
