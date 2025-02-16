package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    private String IMAGE_DIR;

    @Override
    public void init() throws ServletException {
        super.init();
        // Получаем путь из параметра контекста
        IMAGE_DIR = getServletContext().getInitParameter("imageDirectory");
        System.out.println(IMAGE_DIR);

        // Если параметр не задан, проверяем переменную окружения
        if (IMAGE_DIR == null || IMAGE_DIR.trim().isEmpty()) {
            IMAGE_DIR = System.getenv("IMAGE_DIR");
        }

        // Если всё ещё не задан, используем путь внутри приложения
        if (IMAGE_DIR == null || IMAGE_DIR.trim().isEmpty()) {
            String appPath = getServletContext().getRealPath("");
            if (appPath != null) {
                IMAGE_DIR = appPath + File.separator + "images";
            } else {
                throw new ServletException("Не удалось определить путь к папке изображений");
            }
        }

        File imageDir = new File(IMAGE_DIR);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getPathInfo().substring(1); // Получаем имя файла из URL
        File imageFile = new File(IMAGE_DIR, imageName);

        if (imageFile.exists()) {
            response.setContentType(getServletContext().getMimeType(imageFile.getName()));
            response.setContentLength((int) imageFile.length());
            try (InputStream inputStream = new FileInputStream(imageFile);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

