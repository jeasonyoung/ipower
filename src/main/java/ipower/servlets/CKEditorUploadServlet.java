package ipower.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
/**
 * CKEditor附件上传。
 * @author young。
 * @since 2013-09-18。
 * */
public class CKEditorUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CKEditorUploadServlet.class);
	private static String baseDir;//CKEditor的上传根目录。
	private static boolean debug = false;//是否debug模式。
	private static boolean enabled = false;//是否开启CKEditor上传。
	private static Hashtable<String,List<String>> allowedExtensions;//允许的上传文件扩展名。
	private static Hashtable<String,List<String>> deniedExtensions;//阻止的上传文件扩展名。
	private static SimpleDateFormat dirFormatter;//目录命名格式：yyyyMM
	private static SimpleDateFormat fileFormatter;//文件命名格式：yyyyMMddHHmmssSSS;
	
	/**
	 * Servlet初始化方法。
	 * */
	@Override
	public void init() throws ServletException{
		//从web.xml读取debug模式。
		debug = (new Boolean(this.getInitParameter("debug"))).booleanValue();
		if(debug){
			logger.debug("\r\n---CKEditorUploadServlet initialization started----");
		}
		//格式化目录和文件命名方式。
		dirFormatter = new SimpleDateFormat("yyyyMMdd");
		fileFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		//从web.xml中获取上传根目录。
		baseDir = this.getInitParameter("baseDir");
		if(baseDir == null || baseDir.isEmpty()){
			baseDir = File.separator + "uploadFiles" + File.separator;;
		}
		if((baseDir.charAt(baseDir.length() - 1) != '/') && (baseDir.charAt(baseDir.length() - 1) != File.separatorChar)){
			baseDir += File.separator;
		}
		if(debug){
			logger.debug("baseDir:"+baseDir);
		}
		//从web.xml
		enabled = (new Boolean(this.getInitParameter("enabled"))).booleanValue();
		String realBaseDir = this.getServletContext().getRealPath(baseDir);
		File baseFile = new File(realBaseDir);
		if(!baseFile.exists()){
			baseFile.mkdirs();
		}
		if(debug){
			logger.debug("realBaseDir:"+realBaseDir);
		}
		//实例化允许的扩展名何阻止的扩展名。
		allowedExtensions = new Hashtable<String,List<String>>();
		deniedExtensions = new Hashtable<String,List<String>>();
		//从web.xml读取配置信息。
		List<String> allowList = this.stringToArrayList(this.getInitParameter("AllowedExtensionsFile"));
		List<String> denyList =this.stringToArrayList(this.getInitParameter("DeniedExtensionsFile"));
		allowedExtensions.put("File", allowList);
		deniedExtensions.put("File", denyList);
		allowedExtensions.put("Image", allowList);
		deniedExtensions.put("Image", denyList);
		allowedExtensions.put("Flash", allowList);
		deniedExtensions.put("Flash", denyList);
		
		if(debug){
			logger.debug("---CKEditorUploadServlet initialization completed---\r\n");
		}
	}
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		this.doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		if(debug){
			logger.debug("---Begin DOPOST ---");
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		//从请求参数中获取上传文件的类型：File/Image/Flash
		String typeStr= request.getParameter("Type");
		if(typeStr == null || typeStr.isEmpty()){
			typeStr = "File";
		}
		if(debug){
			logger.debug("Type:"+typeStr);
		}
		Date now = new Date();
		//设定上传文件路径。
		String currentPath = baseDir + typeStr + "/" + dirFormatter.format(now);
		//获取Web应用的上传路径。
		String currentDirPath = this.getServletContext().getRealPath(currentPath);
		if(debug){
			logger.debug("currentDirPath:"+currentDirPath);
		}
		//判断文件夹是否存在，不存在则创建。
		File dirFile = new File(currentDirPath);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		//将路径前加上Web应用名称。
		currentPath = request.getContextPath()+ currentPath;
		String newName = "",fileUrl = "";
		if(enabled){
			//使用Apache Common组件中的fileupload进行文件上传。
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try{
				List<?> items = upload.parseRequest(request);
				Map<String,Object> fields = new Hashtable<>();
				Iterator<?> iter = items.iterator();
				while(iter.hasNext()){
					FileItem item = (FileItem)iter.next(); 
					if(item.isFormField()){
						fields.put(item.getFieldName(), item.getString());
					}else{
						fields.put(item.getFieldName(), item);
					}
				}
				//CKEditor中file域的name值是upload.
				FileItem uploadFileItem = (FileItem)fields.get("upload");
				//获取文件名并做处理。
				String fileNameLong = uploadFileItem.getName();
				fileNameLong = fileNameLong.replace('\\', '/');
				String[] pathParts = fileNameLong.split("/");
				String fileName = pathParts[pathParts.length - 1];
				//获取文件扩展名。
				String ext = this.getExtension(fileName);
				//设置上传文件名。
				fileName = fileFormatter.format(now) + "."+ ext;
				//获取文件名（无扩展名）。
				String nameWithoutExt = this.getNameWithoutExtension(fileName);
				File pathToSave = new File(currentDirPath, fileName);
				fileUrl = currentPath+ "/"+ fileName;
				if(this.extIsAllowed(typeStr, ext)){
					int counter = 1;
					while(pathToSave.exists()){
						newName = nameWithoutExt+"_"+ counter+"."+ ext;
						fileUrl = currentPath + "/" + newName;
						pathToSave = new File(currentDirPath, newName);
						counter++;
					}
					uploadFileItem.write(pathToSave);
				}else if(debug){
					logger.debug("无效的文件类型："+ ext);
				}
			}catch (Exception e) {
				if(debug){
					logger.error(e);
					e.printStackTrace();
				}
			}
		}else if(debug){
			logger.debug("未开启CKEditor上传功能Servlet的控制开关。");
		}
		String ckEditor = request.getParameter("CKEditor");
		//CKEditorFuncName是回调时显示的位置，这个参数必须有。
		String callback = request.getParameter("CKEditorFuncNum");
		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.CKEDITOR.tools.callFunction("+callback+",'"+fileUrl+"','');");
		out.println("</script>");
		out.flush();
		out.close();
		if(debug){
			logger.debug("CKEditor:"+ckEditor);
			logger.debug("callback:"+callback);
			logger.debug("fileUrl:"+fileUrl);
			logger.debug("---End DOPOST---");
		}
	}
	/**
	 * 判断扩展名是否允许的方法。
	 * @param fileType
	 * 	 文件类型。
	 * @param ext
	 * 	扩展名。
	 * */
	private boolean extIsAllowed(String fileType,String ext){
		ext = ext.toLowerCase();
		List<String> allowList = allowedExtensions.get(fileType);
		List<String> denyList = deniedExtensions.get(fileType);
		if(allowList.size() == 0){
			return !denyList.contains(ext);
		}
		if(denyList.size() == 0){
			return allowList.contains(ext);
		}
		return false;
	}
	/**
	 * 获取文件名的方法。
	 * */
	private String getNameWithoutExtension(String fileName){
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	/**
	 * 获取扩展名的方法。
	 * */
	private String getExtension(String fileName){
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	/**
	 * 
	 * */
	private List<String> stringToArrayList(String str){
		List<String> tmp = new ArrayList<>();
		if(str != null && !str.isEmpty()){
			if(debug){
				logger.debug(str);
			}
			String[] strArr = str.split("\\|");   
	        if (str.length() > 0) {  
	            for (int i = 0; i < strArr.length; ++i) {  
	                if (debug){
	                	logger.debug(i+"--"+strArr[i]);
	                }
	                tmp.add(strArr[i].toLowerCase());  
	            }
	        }  
		}
        return tmp;  
	}
}