package com.upload;

/**
 * 图片上传控制类
 * @author giscafer
 * @date 2016-6-7 上午11:48:48
 * @version V1.0
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utils.DateUtils;
import com.utils.StringUtils;
@Controller
public class PicUploadController {
	@Autowired
	private DocumentDao documentDao;
	private HttpSession session = null;

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/picture/upload.do")
	@ResponseBody
	public void fileUpload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		String  savePath= servletContext.getRealPath("/");
		System.out.println(savePath);
		String saveUrl = null;
		String saveThumbUrl = null;
		String repositoryPath = null; // 图片绝对路径
		String repositoryThumbPath = null; // 缩略图绝对路径
		//保存文件的物理路径
		savePath += "picture/";
		//相对路径（用于保存数据库）
		saveUrl = request.getContextPath() + "/picture/" ;
		saveThumbUrl = request.getContextPath() + "/picture/";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		repositoryPath = savePath;
		saveThumbUrl += ymd + "/thumb/";
		repositoryThumbPath = savePath + "thumb/";

		File dirFile = new File(repositoryPath);
		File dirThumbFile = new File(repositoryThumbPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		if (!dirThumbFile.exists()) {
			dirThumbFile.mkdirs();
		}

		response.setCharacterEncoding("UTF-8");
		Integer schunk = null;// 分割块数
		Integer schunks = null;// 总分割数
		String name = null;// 文件名
		BufferedOutputStream outputStream = null;
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024);
				factory.setRepository(new File(repositoryPath));// 设置临时目录
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");
				upload.setSizeMax(100 * 1024 * 1024);// 设置附件大小
				List<FileItem> items = upload.parseRequest(request);
				// 生成新文件名
				String newFileName = null;
				long fileSize = 0L;
				for (FileItem item : items) {
					fileSize += item.getSize();
					if (!item.isFormField()) {// 如果是文件类型
						name = item.getName();// 获得文件名
						newFileName = UUID.randomUUID().toString()
								.replace("-", "").concat(".")
								.concat(FilenameUtils.getExtension(name));
						if (name != null) {
							String nFname = newFileName;
							if (schunk != null) {
								nFname = schunk + "_" + name;
							}
							File savedFile = new File(savePath, nFname);
							item.write(savedFile);
						}
					} else {
						// 判断是否带分割信息
						if (item.getFieldName().equals("chunk")) {
							schunk = Integer.parseInt(item.getString());
						}
						if (item.getFieldName().equals("chunks")) {
							schunks = Integer.parseInt(item.getString());
						}
					}
				}

				if (schunk != null && schunk + 1 == schunks) {
					outputStream = new BufferedOutputStream(
							new FileOutputStream(
									new File(savePath, newFileName)));
					// 遍历文件合并
					for (int i = 0; i < schunks; i++) {
						File tempFile = new File(savePath, i + "_" + name);
						byte[] bytes = FileUtils.readFileToByteArray(tempFile);
						outputStream.write(bytes);
						outputStream.flush();
						tempFile.delete();
					}
					outputStream.flush();
				}
				String ip = StringUtils.getIpAddr(request);
				//
				String thumbUrlPath = repositoryThumbPath + newFileName;
				String saveUrlPath = repositoryPath + newFileName;
				ImgCompress imgCom = new ImgCompress(saveUrlPath);
				imgCom.resize(80, 80, thumbUrlPath);
				// 保存文档信息到数据库表中
				saveToDb(saveUrl + newFileName, saveThumbUrl + newFileName,
						fileSize, name, ip);

				response.getWriter().write(
						"{\"status\":true,\"newName\":\"" + saveUrl
								+ newFileName + "\",\"thumbName\":\""
								+ saveThumbUrl + newFileName + "\"}");
			} catch (FileUploadException e) {
				e.printStackTrace();
				response.getWriter().write("{\"status\":false}");
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("{\"status\":false}");
			} finally {
				try {
					if (outputStream != null)
						outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "/picture/download.do")
	@ResponseBody
	public void fileDownload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter("filePath");
		String name = request.getParameter("fileName");
		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		try {
			path = rootPath + new String(path.getBytes("ISO-8859-1"), "utf-8");
			name = new String(name.getBytes("ISO-8859-1"), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		download(path, name, request, response);
	}

	public HttpServletResponse download(String path, String name,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			name = URLDecoder.decode(name, "utf-8");
			File file = new File(path);
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			name = URLEncoder.encode(name, "utf-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ name);
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 保存信息到数据库
	 * @param newFileName
	 *            图片名称
	 * @param thumbFileName
	 *            缩略图名称
	 * @param fileSize
	 * @param fileName
	 * @param ip 
	 */
	private void saveToDb(String newFileName, String thumbFileName,
			long fileSize, String fileName, String ip) {
		FileDocument filedoc = new FileDocument();
		filedoc.setD_UPSIZE(fileSize + "");
		filedoc.setS_NAME(fileName);
		// 检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		filedoc.setS_TYPE(fileExt);
		filedoc.setS_UPTIME(DateUtils.convertDateTimeToString(null));
		filedoc.setS_URL(newFileName);
		filedoc.setS_THUMBNAIL(thumbFileName);
		filedoc.setS_UPIP(ip);
		filedoc.setS_LPATH(thumbFileName);
		filedoc.setS_PLPATH(thumbFileName);
		documentDao.insertFileDocumentUseUpdate(filedoc);
	}
}
