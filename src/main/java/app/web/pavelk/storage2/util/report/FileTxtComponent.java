package app.web.pavelk.storage2.util.report;

import app.web.pavelk.storage2.entities.Client;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class FileTxtComponent {
    public ByteArrayOutputStream getReportTxt(List<Client> client) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String separator = " ";
        byteArrayOutputStream.write(("Список клиентов" + "\n").getBytes());
        byteArrayOutputStream.write(("id" + separator + "phone" + separator + "name" + separator + "title" + "\n").getBytes());
        for (Client line : client) {
            byteArrayOutputStream.write((line.getId().toString() + separator +
                    line.getPhone() + separator + line.getName() + separator + line.getTitle() + "\n").getBytes());
        }
        byteArrayOutputStream.flush();
        return byteArrayOutputStream;
    }
}
