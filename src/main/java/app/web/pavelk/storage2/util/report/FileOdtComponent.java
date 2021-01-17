package app.web.pavelk.storage2.util.report;


import app.web.pavelk.storage2.entities.Client;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.odftoolkit.simple.common.navigation.TextNavigation;
import org.odftoolkit.simple.common.navigation.TextSelection;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class FileOdtComponent {

    public ByteArrayOutputStream getReportOdt(List<Client> client) throws Exception {

        //        загрузка шаблона
        ClassPathResource classPathResource = new ClassPathResource("templates/report1.odt",
                this.getClass().getClassLoader());
        InputStream inputStream = classPathResource.getInputStream();
        TextDocument textDocument = TextDocument.loadDocument(inputStream);

        //добавить дату
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        TextNavigation textNavigation = new TextNavigation("&date time", textDocument);
        while (textNavigation.hasNext()) { //поиск по ключю
            TextSelection textSelection = (TextSelection) textNavigation.nextSelection();
            try {
                textSelection.replaceWith(simpleDateFormat.format(new Date()));
            } catch (InvalidNavigationException e) {
                e.printStackTrace();
            }
        }

        //добавить строки
        Table table = textDocument.getTableList().get(0);
        for (Client row : client) {
            Row rowNew = table.appendRow();
            rowNew.getCellByIndex(0).setStringValue(row.getId().toString());
            rowNew.getCellByIndex(1).setStringValue(row.getPhone());
            rowNew.getCellByIndex(2).setStringValue(row.getName());
            rowNew.getCellByIndex(3).setStringValue(row.getTitle());
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        textDocument.save(byteArrayOutputStream);
        byteArrayOutputStream.flush();

        return byteArrayOutputStream;
    }
}
