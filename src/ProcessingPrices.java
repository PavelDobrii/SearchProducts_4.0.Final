import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;

public class ProcessingPrices {
    private String domotexnica = "Домотехника";
    private String neolinc = "Неолинк";
    private String electroservis = "Электросервис";
    private String patio = "ПАТИО";

    private String prise;

    public void func(String path) throws IOException, InvalidFormatException {

        File f = new File(path); // поиск по папке и формирование масива
        String[] list = f.list();

        for (String file : list) {

            // Начало выполнения основного метода программы
            if (!file.contains("~$")) { // проверка на недопущение резервный файлов


                if (file.matches(".*\\b" + domotexnica + "\\b.*")) {// Домотехника
                    String domotexnicaPath = path + "\\" + file;

                    prise = domotexnica;
                    PriseAll priseAll = new PriseAll();
                    priseAll.PriseAll(1,2,3,5, 4,"Домотехника",domotexnicaPath);

                } else if (file.matches(".*\\b" + neolinc + "\\b.*")) {// Неолинк
                    String neolincPath = path + "\\" + file;


                } else if (file.matches(".*\\b" + electroservis + "\\b.*")) {// Электросервис
                    String electroservisPath = path + "\\" + file;

                    prise = electroservis;
                    PriseAll priseAll = new PriseAll();
                    priseAll.PriseAll(2,3,4,23, 9,"Электросервис", electroservisPath);

                } else if (file.matches(".*\\b" + patio + "\\b.*")) {// ПАТИО

                    String patioPath = path + "\\" + file;

                    prise = patio;
                    PriseAll priseAll = new PriseAll();
                    priseAll.PriseAll(0,1,2,6, 8,"ПАТИО", patioPath);

                } else break;
            }
        }
    }

}

