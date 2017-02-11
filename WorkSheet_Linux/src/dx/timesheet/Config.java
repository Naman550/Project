/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dx.timesheet;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * This class set the Configuration of data like HTTP,Domain,version,URL.. in timeSheet
 */
public class Config {
    public static  String HTTP = "";
    public static  String DOMAIN=""; // = "pms.trimonks.com/";*/
    public static  String VERSION=""; // = "pms.trimonks.com/";*/
    public static  String URL=""; // = "pms.trimonks.com/";*/
    public static  String COPYRIGHT=""; // = "pms.trimonks.com/";*/
    public static  String COMPANYNAME=""; 
    public static  String LOGOPATH=""; 
    public static  String COMPANYURL=""; 
    
    static String hostName = "";

    public static void setDomain(){
    
        
        try {
            
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
            String name[] = hostName.split("-");
            hostName=name[0];
            System.out.println(hostName);
        
            
            /**
            * set the path of XML file
            */
            
//         File fXmlFile = new File("/home/developer/Downloads/WorkSheet/src/dx/timesheet/domain.xml");
           File fXmlFile = new File("domain.xml");
            
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            
            
            NodeList nList = doc.getElementsByTagName("domain");
            
            for(int i=0; i<nList.getLength(); i++){
                
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                   // NodeList nodelist = element.getElementsByTagName("domain_url");
                    
                    HTTP = getElements(eElement, "http");
                    DOMAIN = getElements(eElement, "domain_url");
                    VERSION = getElements(eElement, "version");
                    URL = getElements(eElement, "url");
                    COPYRIGHT = getElements(eElement, "copyright");
                    COMPANYNAME = getElements(eElement, "company");
                    LOGOPATH = getElements(eElement, "logo_path");
                    COMPANYURL = getElements(eElement, "company_url");
                }
            }
            
            }
            catch (IOException ex) {
                Logger.getLogger(xml.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
           Logger.getLogger(xml.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SAXException ex) {
           Logger.getLogger(xml.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public static String getElements(Element element, String tag) {
        NodeList nodelist = element.getElementsByTagName(tag);
        String value = "";
        if (nodelist.getLength() > 0) {
            Element elm = (Element) nodelist.item(0);
            value = elm.getChildNodes().item(0).getNodeValue();
            System.out.println(value);
        }
        return value;
    }
    

}
