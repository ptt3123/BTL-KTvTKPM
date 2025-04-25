package btl.btl.ktvtkpm.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.sql.Date;
import btl.btl.ktvtkpm.dao.PartnerPaymentDAO;
import btl.btl.ktvtkpm.dao.CarDAO;
import btl.btl.ktvtkpm.dao.PartnerDAO;
import btl.btl.ktvtkpm.model.Partner;
import btl.btl.ktvtkpm.model.PartnerPayment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

@WebServlet("/hidden/partner-payment")
public class PartnerPaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        
        if (id == null || id.isEmpty()) {
            req.setAttribute("error", "Không tìm thấy id hợp lệ.");
 
        } else {
            
            try (PartnerPaymentDAO ppdao = new PartnerPaymentDAO();
                    PartnerDAO pdao = new PartnerDAO();
                    CarDAO cdao = new CarDAO()) {
                int partnerId = Integer.parseInt(id);
                
                if (ppdao.isPartnerPaymentThisMonth(partnerId)){
                    req.setAttribute("warning", "Đã có khoản thanh toán trong tháng này cho đối tác.");
                    
                } else {
                    Partner p = pdao.read(partnerId);
                    float price = cdao.readPartnerPaymentPerMonth(partnerId);
                    LocalDate today = LocalDate.now();
                    System.out.println(3);
                    req.setAttribute("partnerName", p.getName());
                    req.setAttribute("price", price);
                    req.setAttribute("detail", "Thanh toán tháng" + today.getMonthValue());
                    req.setAttribute("createdDate", today);
                    
                }
                
            } catch (Exception e) {
                req.setAttribute("error", "Đã có lỗi xẩy ra vui lòng thử lại sau.");
                req.getRequestDispatcher("/partner_payment_form.jsp").forward(req, resp);
                
            }
            
        }
        req.getRequestDispatcher("/partner_payment_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String detail = req.getParameter("detail");
        String priceStr = req.getParameter("price").replace(",", "");
        String createdDate = req.getParameter("createdDate");
        String partnerIdStr = req.getParameter("id");
        
        try (PartnerPaymentDAO ppdao = new PartnerPaymentDAO()) {
            priceStr = priceStr.replaceAll("[.,]", "");
            double price = Double.parseDouble(priceStr);
            int partnerId = Integer.parseInt(partnerIdStr);
            int staffId = (int) req.getSession().getAttribute("uid");
            
            PartnerPayment pp = new PartnerPayment();
            pp.setPrice(price);
            pp.setDetail(detail);
            pp.setCreateDate(Date.valueOf(createdDate));
            pp.setPartnerId(partnerId);
            pp.setStaffId(staffId);
            
            // Kiểm tra Validation
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<PartnerPayment>> violations = validator.validate(pp);

            if (!violations.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder("Lỗi:<br>");
                for (ConstraintViolation<PartnerPayment> violation : violations) {
                    errorMessage.append("- ").append(violation.getMessage()).append("<br>");
                }
                req.setAttribute("error", errorMessage.toString());
                req.getRequestDispatcher("/partner_payment_form.jsp").forward(req, resp);
                return;
            }
            
            ppdao.create(pp);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã có lỗi xẩy ra vui lòng thử lại sau.");
            req.getRequestDispatcher("/partner_payment_form.jsp").forward(req, resp);
            
        }
        req.setAttribute("success", "Đã tạo thanh toán thành công, chờ Đối tác duyệt.");
        req.getRequestDispatcher("/partner_list.jsp").forward(req, resp);
    }
    
}
