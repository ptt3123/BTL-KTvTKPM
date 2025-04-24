package btl.btl.ktvtkpm.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import btl.btl.ktvtkpm.dao.UserDAO;
import btl.btl.ktvtkpm.model.User;
import btl.btl.ktvtkpm.util.PasswordUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // Lấy dữ liệu từ form đăng nhập
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Kiểm tra dữ liệu không rỗng
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorMessage", "Tên đăng nhập và mật khẩu không được để trống!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // Check Login
        try (UserDAO userDAO = new UserDAO();) {
            User user = userDAO.readByUsername(username);
            
            if((user == null) || (!PasswordUtil.checkPassword(password, user.getPassword()))){
                request.setAttribute("errorMessage", "Thông tin đăng nhập không chính xác!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                
            } else {
                // Lưu thông tin 
                HttpSession session = request.getSession();
                session.setAttribute("uid", user.getId());
                boolean ist = user.isIsStaff();
                session.setAttribute("ist", ist);

                // Chuyển hướng đến trang chủ hoặc trang dashboard
                if (!ist) {
                    response.sendRedirect("index.jsp");
                } else {
                    response.sendRedirect("dashboard.jsp");
                }
            }
            
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Đã có lỗi xảy ra. Hãy thử lại sau!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
