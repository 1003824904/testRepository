package com.choice.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

	public class FileWorked {
		/**
		 * 删除文件
		 * @param path
		 */
		public static void deleteFile(String path){
		    File file = new File(path);
		    if(file.exists()){
		    	file.delete();
		    }
		}
		
		/**
		 * 下载文件
		 * 
		 * @param response
		 * @param request
		 * @param fileName
		 */
		public static void downloadTemplate(HttpServletResponse response,
				HttpServletRequest request, String fileName) throws IOException {
			OutputStream outp = null;
			FileInputStream in = null;
			try {
				String ctxPath = request.getSession().getServletContext()
						.getRealPath("/");
				String filedownload = ctxPath + fileName;
				fileName = URLEncoder.encode(fileName, "UTF-8");
				// 要下载的模板所在的绝对路径
				response.reset();
				response.addHeader("Content-Disposition", "attachment; filename="
						+ fileName);
				response.setContentType("application/octet-stream;charset=UTF-8");
				outp = response.getOutputStream();
				in = new FileInputStream(filedownload);
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) !=-1) {
					outp.write(b, 0, i);
				}
				outp.flush();
				
			} catch (Exception e) {
				System.out.println("Error!");
				e.printStackTrace();
			} finally {
				if (in != null) {
					in.close();
					in = null;
				}
				if (outp != null) {
					outp.close();
					outp = null;
				}
			}
		}

}
