package btl.btl.ktvtkpm.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import btl.btl.ktvtkpm.dao.PartnerDAO;
import btl.btl.ktvtkpm.model.Partner;

@WebServlet("/hidden/partner-list")
public class PartnerListServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String pageStr = req.getParameter("page");
        String keyword = req.getParameter("keyword");
        
        int page = 1;
        int limit = 10;

        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
        // Giữ mặc định là 1
        }
        
        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }
        
        try (PartnerDAO pdao = new PartnerDAO()) {
            List<Partner> list = pdao.readPartnerByName(keyword, page, limit);
            
            int count = pdao.readCountOfPartnerByName(keyword);
            int total = (int) Math.ceil((double) count / limit);
            
            if (total == 0){
                total = 1;
            }
            
            req.setAttribute("list", list);
            req.setAttribute("limit", limit);
            req.setAttribute("page", page);
            req.setAttribute("total", total);
            req.getRequestDispatcher("/partner_list.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã có lỗi xảy ra, hãy thử lại sau.");
            req.getRequestDispatcher("/partner_list.jsp").forward(req, resp);
        }
    }
}
