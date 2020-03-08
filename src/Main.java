import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main (String [] args) throws IOException, InvalidFormatException {

        long startTime = System.currentTimeMillis();

//        //для сохронения логов
//        Date dateNow = new Date();
//        SimpleDateFormat formatForDateNow = new SimpleDateFormat(" dd.MM.yyyy 'время' hh mm ss");
//        String nameFile = "Логи программы" + formatForDateNow.format(dateNow);
//        PrintStream out = new PrintStream(new FileOutputStream("D:\\SearchProducts_4.0.Final\\Логи программы\\" + nameFile + ".txt"));
//        System.setOut(out);

        //Для определения количества сохроняемых фотографий
        Formalization form = new Formalization();// 0 - one foto, 1 - all foto
        form.setPhotoItem(1);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!
////        Очистка старой инфы в ELS
        ExselXSL exselXSL = new ExselXSL();
        exselXSL.ClineFIles();

        //Начало работы программы
        ProcessingPrices prises = new ProcessingPrices();
        prises.func("C:\\Users\\BOIH_MAPCA\\Desktop\\МагнитМ\\Прайсы");

        //Для оформления
//        Reader reader = new Reader();
//        reader.setVisible(true);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Время выполнения: " + elapsedTime/3600000);
    }
}
