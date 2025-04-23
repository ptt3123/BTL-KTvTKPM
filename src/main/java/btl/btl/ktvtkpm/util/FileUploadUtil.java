/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl.btl.ktvtkpm.util;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import jakarta.servlet.http.Part;

public class FileUploadUtil {
    
    public static String saveFile(Part filePart, String fileName, String subDir) 
            throws Exception {
        
        if (filePart == null || filePart.getSize() == 0) {
            return null; // Không có file được tải lên
        }

        // Lấy thư mục uploads 
        String uploadPath = new File(UploadPath.UPLOAD_DIR.getPath() + subDir).getAbsolutePath();
        File uploadFolder = new File(uploadPath);
        
        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        File file = new File(uploadFolder, fileName);

        // Ghi file từ InputStream
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        
//        System.out.println("Uploading file: " + fileName);
//        System.out.println("Saving to path: " + file.getAbsolutePath());
//        System.out.println("File size: " + filePart.getSize());

        return file.getAbsolutePath(); // Trả về đường dẫn đầy đủ
    }
    
    public static boolean deleteFile(String subDir, String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }

        Path filePath = Paths.get(UploadPath.UPLOAD_DIR.getPath(), subDir, fileName);
        File file = filePath.toFile();

        if (file.exists()) {
            return file.delete();
        }

        return false;
    }


    public static String getFileExtension(Part filePart) {
        
        String fileName = filePart.getSubmittedFileName();
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return ""; // Không có phần mở rộng
        
    }

    public static boolean isValidFileType(String extension) {
        
        String[] validExtensions = {".jpg", ".jpeg", ".png", ".gif", ".mp4", ".avi", ".mov", ".mkv"};
        for (String ext : validExtensions) {
            if (extension.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }   
}
