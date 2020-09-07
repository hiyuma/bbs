package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import domain.Message;

/**
 * Servlet implementation class BBSServlet
 */
@WebServlet("/bbs")
public class BBSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BBSServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bbs");
			con = ds.getConnection();
			String sql = "SELECT * FROM messages order by posted desc";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<Message> messageList = new ArrayList<>();
			while (rs.next()) {
				Message message = new Message();
				message.setId((Integer) rs.getObject("id"));
				message.setUser(rs.getString("user"));
				message.setBody(rs.getString("body"));
				message.setPosted(rs.getTimestamp("posted"));
				messageList.add(message);
			}
			request.setAttribute("messageList", messageList);
			request.getRequestDispatcher("WEB-INF/view/bbs.jsp")
					.forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String user = request.getParameter("user");
		String body = request.getParameter("body");
		Connection con = null;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bbs");
			con = ds.getConnection();
			String sql2 = "insert into messages (user,body) values(?,?)";
			PreparedStatement stmt = con.prepareStatement(sql2);
			stmt.setString(1, user);
			stmt.setString(2, body);
			stmt.executeUpdate();
			String sql = "SELECT * FROM messages order by posted desc";
			stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			List<Message> messageList = new ArrayList<>();
			while (rs.next()) {
				Message message = new Message();
				message.setId((Integer) rs.getObject("id"));
				message.setUser(rs.getString("user"));
				message.setBody(rs.getString("body"));
				message.setPosted(rs.getTimestamp("posted"));
				messageList.add(message);
			}
			request.setAttribute("messageList", messageList);
			request.getRequestDispatcher("WEB-INF/view/bbs.jsp")
					.forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				throw new ServletException(e);

			}

		}

	}

}
