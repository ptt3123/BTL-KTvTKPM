package btl.btl.ktvtkpm.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import btl.btl.ktvtkpm.dao.PartnerStatisticsDAO;
import btl.btl.ktvtkpm.dao.PartnerDAO;
import btl.btl.ktvtkpm.model.PartnerStatistics;

@WebServlet("/hidden/partner-statistics")
public class PartnerStatisticsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pageStr = request.getParameter("page");
        if (pageStr == null || pageStr.isEmpty()) {
            
            request.getRequestDispatcher("/partner_statistics_form.jsp").forward(request, response);
        } else {
            
            String start = request.getParameter("startDate");
            String end = request.getParameter("endDate");
            handleRequest(start, end, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");
        handleRequest(start, end, request, response);
    }
    
    private void handleRequest(String start, String end, 
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Date startDate = null;
        Date endDate = null;

        try {
            if (start != null && !start.isBlank()) {
                startDate = Date.valueOf(start);
            }

            if (end != null && !end.isBlank()) {
                endDate = Date.valueOf(end);
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Định dạng ngày không hợp lệ. Vui lòng chọn lại.");
            request.getRequestDispatcher("/partner_statistics_form.jsp").forward(request, response);
            return;
        }
        
        if(startDate != null && endDate != null){
            if(startDate.compareTo(endDate) > 0){
                request.setAttribute("error", "Ngày bắt đầu phải trước ngày kết thúc.");
                request.getRequestDispatcher("/partner_statistics_form.jsp").forward(request, response);
            }
        }
        
        String keyword = request.getParameter("keyword");
        
        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }
         
        int page = 1;
        int limit = 10;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
        // Giữ mặc định là 1
        }
            
        try (PartnerStatisticsDAO psdao = new PartnerStatisticsDAO();
                PartnerDAO pdao = new PartnerDAO();) {
            List<PartnerStatistics> list = psdao.readList(keyword, startDate, endDate, page, limit);
            int count = pdao.readCountOfPartnerByName(keyword);
            int total = (int) Math.ceil((double) count / limit);
            
            if (total == 0){
                total = 1;
            }
                
            request.setAttribute("list", list);
            request.setAttribute("page", page);
            request.setAttribute("total", total);
            request.setAttribute("limit", limit);
            request.getRequestDispatcher("/partner_statistics.jsp").forward(request, response);
                
        } catch (Exception e) {
            request.setAttribute("error", "Đã có lỗi xảy ra, hãy thử lại sau.");
            request.getRequestDispatcher("/partner_statistics_form.jsp").forward(request, response);
        }  
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
