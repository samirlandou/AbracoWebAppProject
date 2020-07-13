package br.com.abracowebmanagement.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet(description = "Servlet For Image", urlPatterns = { "/ImageServlet" })
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		response.setContentType("image/jpeg");

		ServletOutputStream out;

		out = response.getOutputStream();

		FileInputStream fileInpStream = new FileInputStream("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/joelaronson/Login/userImage.png");
		
		BufferedInputStream bufferInputStream = new BufferedInputStream(fileInpStream);

		BufferedOutputStream bufferOutputStream = new BufferedOutputStream(out);

		int ch = 0;

		while ((ch = bufferInputStream.read()) != -1) {

			bufferOutputStream.write(ch);

		}

		bufferInputStream.close();

		fileInpStream.close();

		bufferOutputStream.close();

		out.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
