<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thống kê đối tác</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            justify-content: center; /* căn giữa theo chiều ngang */
            align-items: center;     /* căn giữa theo chiều dọc */
            background-color: #f4f4f4;
            font-family: Arial, sans-serif;
        }

        .form-container {
            background-color: white;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .form-container h2 {
            margin-bottom: 20px;
        }

        .form-container label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }

        .form-container input[type="date"] {
            padding: 8px;
            margin-top: 5px;
            width: 100%;
            max-width: 250px;
            box-sizing: border-box;
        }

        .form-container button {
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            background-color: #007bff;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }

        .form-container button:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Thống kê doanh thu đối tác</h2>
        <% 
            String errorMessage = (String) request.getAttribute("error"); 
            if (errorMessage != null) { 
        %>
            <div class="error-message">
                <%= errorMessage %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/hidden/partner-statistics" method="post">
            <label for="startDate">Từ ngày:</label>
            <input type="date" id="startDate" name="startDate">

            <label for="endDate">Đến ngày:</label>
            <input type="date" id="endDate" name="endDate">

            <button type="submit">Thống kê</button>
        </form>
    </div>
</body>
</html>
