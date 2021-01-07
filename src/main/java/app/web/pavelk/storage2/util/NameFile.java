package app.web.pavelk.storage2.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NameFile {

    public static String[] getNameStatic(String activeProfile) {
        String[] strings;
//        String pathStatic;
//
//        if (activeProfile.equals("dev")) {
//            pathStatic = "src/main/resources/static";
//        } else {
//            pathStatic = "classes/static";
//        }
//
//        try {
//            strings = Files.list(Paths.get(pathStatic)).map(f -> {
//                return ("/" + f.getName(f.getNameCount() - 1).toString());
//            }).toArray(String[]::new);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new String[0];
//        }
        return new String[0];
    }
}
