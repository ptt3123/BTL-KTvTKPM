<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Đăng ký tài khoản</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: flex-start; 
            min-height: 100vh; 
            background-color: #f4f4f4;
            font-family: Arial, sans-serif;
            margin: 0;
        }

        .register-container {
            max-width: 400px;
            width: 100%;
            text-align: center;
            overflow: auto; /* Cho phép cuộn khi nội dung dài */
            word-wrap: break-word;
            overflow-wrap: break-word;
            white-space: normal; /* Chắc chắn chữ xuống dòng */
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }

        .error-message {
            color: red;
            font-weight: bold;
            margin-bottom: 10px;
            white-space: pre-line; /* Đảm bảo xuống dòng khi có \n */
            word-wrap: break-word;
            overflow-wrap: break-word;
            text-align: left; /* Căn trái lỗi cho dễ đọc */
            max-width: 100%; /* Đảm bảo không bị tràn ra ngoài */
        }


        h2 {
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        label {
            align-self: flex-start;
            margin: 10px 0 5px;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            margin-top: 10px;
        }

        button:hover {
            background-color: #0056b3;
        }
        
    </style>
</head>
<body>
    <div class="register-container">
        <h2>Đăng ký tài khoản</h2>
        
        <% 
            String errorMessage = (String) request.getAttribute("errorMessage"); 
            if (errorMessage != null) { 
        %>
            <div class="error-message">
                <%= errorMessage %>
            </div>
        <% } %>
        
        <form action="register" method="post" enctype="multipart/form-data">
            <label for="username">Tên đăng nhập:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password" required>

            <label for="name">Tên:</label>
            <input type="text" id="name" name="name" required>

            <label for="familyName">Họ:</label>
            <input type="text" id="familyName" name="familyName">

            <label for="phoneNumber">Số điện thoại:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" required>

            <label for="address">Địa chỉ:</label>
            <input type="text" id="address" name="address" required>

            <label for="avatar">Ảnh đại diện:</label>
            <input type="file" id="avatar" name="avatar" accept="image/*">

            <label for="dateOfBirth">Ngày sinh:</label>
            <input type="date" id="dateOfBirth" name="dateOfBirth" required>

            <button type="submit">Đăng ký</button>
        </form>
    </div>
</body>
</html>
