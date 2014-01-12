package ipower.configuration;

import ipower.utils.XmlUtil;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 模块数据解析类。
 * @author young。
 * @since 2013-09-18。
 * */
public final class ModuleParse {
	/*
	 * 将Xml文件流解析为模块系统对象集合。
	 * @param inputStream
	 * 	xml文件流。
	 * @return
	 * 	模块系统对象集合。
	 * */
	public static ModuleSystemCollection parse(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException{
		Document doc = XmlUtil.loadDocument(inputStream);
		if(doc != null){
			Element root = doc.getDocumentElement();
			if(root != null && root.getNodeName().equalsIgnoreCase("ipower") && root.hasChildNodes()){
				ModuleSystemCollection systems = new ModuleSystemCollection();
				NodeList nodes = root.getChildNodes();
				for(int i = 0; i < nodes.getLength();i++){
					Node n = nodes.item(i);
					if(n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equalsIgnoreCase("system")){
						ModuleSystem ms = parseToModuleSystem((Element)n);
						if(ms != null){
							systems.add(ms);
						}
					}
				}
				return (systems.size() > 0 ? systems : null);
			}
		}
		return null;
	}
	
	private static ModuleSystem parseToModuleSystem(Element element){
		ModuleSystem ms = new ModuleSystem();
		ms.setId(element.getAttribute("id"));
		ms.setSign(element.getAttribute("sign"));
		ms.setName(element.getAttribute("name"));
		ms.setDescription(element.getAttribute("description"));
		
		if(element.hasChildNodes()){
			NodeList nodes = element.getChildNodes();
			for(int i = 0; i < nodes.getLength();i++){
				Node n = nodes.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equalsIgnoreCase("module")){
					ModuleDefine md = parseModuleDefine((Element)n);
					if(md != null){
						ms.getModules().add(md);
					}
				}
			}
		}
		
		return ms;
	}
	
	private static ModuleDefine parseModuleDefine(Element element){
		ModuleDefine md = new ModuleDefine();
		md.setModuleID(element.getAttribute("id"));
		md.setModuleName(element.getAttribute("name"));
		md.setModuleUri(element.getAttribute("uri"));
		String order = element.getAttribute("order");
		if(order == null || order.isEmpty())
			md.setOrderNo(0);
		else
			md.setOrderNo(Integer.parseInt(order));
		if(element.hasChildNodes()){
			NodeList nodes = element.getChildNodes();
			for(int i = 0; i < nodes.getLength();i++){
				Node n = nodes.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equalsIgnoreCase("module")){
					ModuleDefine m = parseModuleDefine((Element)n);
					if(m != null){
						md.getModules().add(m);
					}
				}
			}
		}
		return md;
	}
}