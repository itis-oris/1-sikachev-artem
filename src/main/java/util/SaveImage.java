package util;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SaveImage {
    public static String saveImage(Part imagePart) throws IOException {
        String imageName = imagePart.getSubmittedFileName();

        File uploadDir = new File("D:\\Semestr_Work_One\\images");

        String imagePath = uploadDir.getAbsolutePath() + File.separator + imageName;
        File imageFile = new File(imagePath);

        try (InputStream inputStream = imagePart.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(imageFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return imageName;
    }
}
