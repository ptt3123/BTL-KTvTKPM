<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="btl.btl.ktvtkpm.model.PartnerStatistics" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Kết quả thống kê</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f6f8;
                display: flex;
                flex-direction: column;
                align-items: center;
                padding-top: 40px;
            }

            h2 {
                margin-bottom: 20px;
            }

            table {
                border-collapse: collapse;
                width: 80%;
                background-color: #fff;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }

            th, td {
                border: 1px solid #ddd;
                padding: 12px;
                text-align: center;
            }

            th {
                background-color: #3498db;
                color: white;
            }

            tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            .pagination {
                margin-top: 20px;
            }

            .pagination a {
                margin: 0 5px;
                text-decoration: none;
                color: #3498db;
                font-weight: bold;
            }

            .pagination a.active {
                color: white;
                background-color: #3498db;
                padding: 5px 10px;
                border-radius: 4px;
            }

            .error {
                color: red;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        
    <% 
        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
    %>
    
    <form method="post" action="${pageContext.request.contextPath}/hidden/partner-statistics">
        <input type="text" name="keyword" placeholder="Tìm kiếm theo tên đối tác..." value="<%= keyword %>">
        <input type="submit" value="Tìm kiếm">
    </form>

    <h2>Danh sách đối tác theo thống kê doanh thu</h2>

    <table>
        <tr>
            <th>STT</th>
            <th>Tên đối tác</th>
            <th>Mô tả</th>
            <th>Tổng doanh thu(vnd)</th>
        </tr>
        <%
            List<PartnerStatistics> list = (List<PartnerStatistics>) request.getAttribute("list");
            int currentPage = request.getAttribute("page") != null ? (Integer) request.getAttribute("page") : 1;
            int limit = request.getAttribute("limit") != null ? (Integer) request.getAttribute("limit") : 10;
            DecimalFormat df = new DecimalFormat("#,###");
            
            int stt = (currentPage - 1) * limit;
            for (PartnerStatistics item : list) {
                stt++;
        %>
                <tr>
                    <td><%= stt %></td>
                    <td><%= item.getName() %></td>
                    <td><%= item.getDetail() %></td>
                    <td><%= df.format(item.getRevenue()) %></td>
                </tr>
        <%
            }
        %>
    </table>

    <!-- Phân trang -->
    <div class="pagination">
        <%
            int totalPages = request.getAttribute("total") != null ? (Integer) request.getAttribute("total") : 1;
            String startDate = request.getParameter("startDate") != null ? request.getParameter("startDate") : "";
            String endDate = request.getParameter("endDate") != null ? request.getParameter("endDate") : "";

            int prevPage = Math.max(1, currentPage - 1);
            int nextPage = Math.min(totalPages, currentPage + 1);
        %>

        <a href="${pageContext.request.contextPath}/hidden/partner-statistics?page=<%= prevPage %>&keyword=<%= keyword %>&startDate=<%= startDate %>&endDate=<%= endDate %>">&laquo;</a>
        <span><%= currentPage %> / <%= totalPages %></span>
        <a href="${pageContext.request.contextPath}/hidden/partner-statistics?page=<%= nextPage %>&keyword=<%= keyword %>&startDate=<%= startDate %>&endDate=<%= endDate %>">&raquo;</a>
    </div>

    </body>
</html>
