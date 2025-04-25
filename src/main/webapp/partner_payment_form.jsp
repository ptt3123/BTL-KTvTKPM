<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thêm Thanh Toán Theo Tháng Cho Đối Tác</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f6f8;
                display: flex;
                flex-direction: column;
                align-items: center;
                padding-top: 50px;
            }

            .form-container {
                background-color: #fff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                width: 400px;
            }

            h2 {
                text-align: center;
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
            }

            input[type="text"], input[type="number"], input[type="date"], textarea {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border-radius: 5px;
                border: 1px solid #ccc;
                box-sizing: border-box;
            }

            textarea {
                resize: vertical;
            }

            input[type="submit"] {
                width: 100%;
                background-color: #3498db;
                color: white;
                border: none;
                padding: 12px;
                font-size: 16px;
                border-radius: 5px;
                cursor: pointer;
            }

            input[type="submit"]:hover {
                background-color: #2980b9;
            }

            .message {
                width: 400px;
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 6px;
                font-weight: bold;
                text-align: center;
            }

            .success {
                background-color: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
            }

            .error {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
            }

            .warning {
                background-color: #fff3cd;
                color: #856404;
                border: 1px solid #ffeeba;
            }
        </style>
    </head>
    <body>

    <div class="form-container">
        
        <h2>Thêm Thanh Toán Mới Mới</h2>
        
        <% 
            String errorMessage = (String) request.getAttribute("error"); 
            if (errorMessage != null) { 
        %>
            <div class="error">
                <%= errorMessage %>
            </div>
        <% } %>

        <% 
            String warningMessage = (String) request.getAttribute("warning"); 
            if (warningMessage != null) { 
        %>
            <div class="warning">
                <%= warningMessage %>
            </div>
        <% } %>

        <% 
            String successMessage = (String) request.getAttribute("success"); 
            if (successMessage != null) { 
        %>
            <div class="error">
                <%= successMessage %>
            </div>
        <% } %>
        
        <form method="post" action="${pageContext.request.contextPath}/hidden/partner-payment?id=<%= request.getParameter("id") %>" style="width: 400px; margin: auto;">
            
            <label for="partnerName">Tên đối tác</label>
            <input type="text" id="partnerName" name="partnerName" 
            value="<%= request.getAttribute("partnerName") != null ? request.getAttribute("partnerName") : "" %>" 
            readonly required>
            
            <% 
                DecimalFormat df = new DecimalFormat("#,###");
                Object priceObj = request.getAttribute("price");
                float price = 0.0f;
                if (priceObj != null && priceObj instanceof Number) {
                    price = ((Number) priceObj).floatValue();
                }
            %>
            <label for="price">Giá cả</label>
            <input type="text" id="price" name="price" 
                   step="0.01" required readonly 
                   value="<%= df.format(price) %>">

            <label for="detail">Mô tả</label>
            <textarea id="detail" name="detail" rows="4" required><%= request.getAttribute("detail") != null ? request.getAttribute("detail") : "" %></textarea>

            <label for="date">Ngày tạo</label>
            <input type="date" id="date" name="createdDate" 
                   required readonly 
                   value="<%= request.getAttribute("createdDate") != null ? request.getAttribute("createdDate") : "" %>">

            <br><br>
            
            <button type="submit">Thanh toán</button>
            
        </form>
    </div>

    </body>
</html>
