package app.web.pavelk.storage2.util.report;


import app.web.pavelk.storage2.entities.Client;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class FileXmlComponent {

    // Функция для сохранения DOM в файл
    private static ByteArrayOutputStream writeDocument(Document document) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(byteArrayOutputStream);
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream;
    }

    public ByteArrayOutputStream getReportXml(List<Client> client) throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();     // создаем пустой объект Document, в котором будем  создавать наш xml-фай
        Element element = document.createElementNS("http//Client", "Clients");           // создаем корневой элемент
        document.appendChild(element);

        for (Client client2 : client) {
            Element element2 = document.createElement("Client");
            element2.setAttribute("id", client2.getId().toString());    // устанавливаем атрибут id

            // создаем элемент
            Element phone = document.createElement("phone");
            phone.appendChild(document.createTextNode(client2.getPhone()));
            element2.appendChild(phone);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(client2.getName()));
            element2.appendChild(name);

            Element title = document.createElement("title");
            title.appendChild(document.createTextNode(client2.getTitle()));
            element2.appendChild(title);

            //добавляем дочерний элемент к корневому
            element.appendChild(element2);
        }

        return writeDocument(document);
    }
}
