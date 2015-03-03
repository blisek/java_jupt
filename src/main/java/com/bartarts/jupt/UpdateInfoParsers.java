package com.bartarts.jupt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartek on 03.03.15.
 */
public class UpdateInfoParsers {

    /**
     * Parsuje plik xml z danymi UpdateInfo. Wymagana forma:
     * <update>
     *     <update_info>
     *         <class_name></class_name>
     *         <class_package></class_package>
     *         <new_version></new_version>
     *         <new_version_description></new_version_description>
     *         <url></url>
     *     </update_info>
     *     ...
     * </update>
     * @param inputStream strumien z ktorego nastapi odczyt (wewnatrz jest opakowany w BufferedInputStream)
     * @throws com.bartarts.jupt.UpdateInfoParseException rzucany, gdy wystapi inny wyjatek w czasie parsowania
     * @return
     */
    public static List<UpdateInfo> parseXML(InputStream inputStream) {
        BufferedInputStream bis = new BufferedInputStream(inputStream);

        Document doc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(bis);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new UpdateInfoParseException(e);
        }

        NodeList nodeList = doc.getElementsByTagName("update_info");
        List<UpdateInfo> updateInfoList = new ArrayList<>(nodeList.getLength());

        for(int index = 0; index < nodeList.getLength(); ++index) {
            try {
                Node n = nodeList.item(index);

                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) n;
                    String className = element.getElementsByTagName("class_name").item(0).getTextContent();
                    String classPackage = element.getElementsByTagName("class_package").item(0).getTextContent();
                    String newVersion = element.getElementsByTagName("new_version").item(0).getTextContent();
                    String newVersionDesc = element.getElementsByTagName("new_version_description").item(0).getTextContent();
                    URL url = new URL(element.getElementsByTagName("url").item(0).getTextContent());
                    UpdateInfo ui = new UpdateInfoURL(className, classPackage, newVersion, newVersionDesc, url);
                    updateInfoList.add(ui);
                }
            } catch (MalformedURLException e) {
                throw new UpdateInfoParseException(e);
            }
        }

        return updateInfoList;
    }


}
