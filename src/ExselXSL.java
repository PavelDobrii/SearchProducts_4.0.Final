import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExselXSL {
    private static String nameItem; // Имя товара
    private static String fileAddress; // Адрес папки

    private Workbook wb1; // для сохранения // Для работы с инфой
    private Sheet sheet;
    private Workbook wb; // открытия для копирования
    private String[] categoru;
    private String ItemDescription;
    private int param = 0;

    private String Izgotov;
    private String Proizvod_strana;
    private String Importer;
    private String manufacturer;


    //Копирование XML файла
    public void CopyingFile() throws IOException {

        wb1 = new HSSFWorkbook(new FileInputStream(fileAddress));
        wb = new HSSFWorkbook();
        sheet = wb.createSheet();

        for (int a = 0;; a++) {
            try {
                String red = wb1.getSheetAt(0).getRow(a).getCell(0).getStringCellValue();
                Row row = sheet.createRow(a);

                for (int b = 0; b < 255 ;b++) {
                    try {
                        try {
                            String result1 = wb1.getSheetAt(0).getRow(a).getCell(b).getStringCellValue();
                            Cell cell_1 = row.createCell(b);
                            cell_1.setCellValue(result1);
                        } catch (IllegalStateException e) {
                            int result1 = (int) wb1.getSheetAt(0).getRow(a).getCell(b).getNumericCellValue();
                            Cell cell_1 = row.createCell(b);
                            cell_1.setCellValue(result1);
                        }
                    } catch (NullPointerException e) {
                    }
                }
            } catch (NullPointerException e) {
                break;
            }
            if (param == 1){
                param = 0;
                return;
            }
        }
    }

    //Работа с XML файлом // wb1 -- Для работы с инфой
    public void WorkFile(String Prise, String Manufacturer) throws IOException {



//        System.out.println(fileAddress);

        Workbook serwis = new HSSFWorkbook(new FileInputStream("D:\\SearchProducts_4.0.Final\\База данных.xls"));

        int a = 0;

        for(;;) {
            try {
//            Row row = sheet.createRow(a);
                String result1 = wb.getSheetAt(0).getRow(a).getCell(0).getStringCellValue();
                boolean retVal;
                retVal = result1.equals(nameItem);
                if (retVal == true) {
                    System.out.println("Есть такой товар");

////                    Проверка цены.
//                    if (Prise == result2){
//                        System.out.println("Цены совподают");
//                        return;
//                    }else {
//                        System.out.println("Цена обновлена");
//                        Row row = sheet.createRow(a);
//                        Cell cell_1 = row.createCell(1);
//                        cell_1.setCellValue(Prise);
//                        return;
//                    }

                    return;
                }
                a++;
            }catch (NullPointerException e){
                Row row = sheet.createRow(a++);

                //nameItem
                Cell cell_1 = row.createCell(0);
                cell_1.setCellValue(nameItem);

                //Foto
                Cell cell_Foto = row.createCell(2);
                cell_Foto.setCellValue("[{\"MIGX_id\":\"1\",\"set\":\"\",\"description\":\"\",\"image\":\"" + nameItem.replaceAll(" ", "_") + "\"}]");

                //Prise
                Cell cell_Prise = row.createCell(3);
                cell_Prise.setCellValue(Prise);

                //nameItem
//                Cell cell_nameItem = row.createCell(2);
//                cell_nameItem.setCellValue(NamePrise);

                //Состовление описания
                BufferedReader reader = new BufferedReader(new StringReader(ItemDescription));
                String line;
                List<String> lines = new ArrayList<String>();
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }

                String [] linesAsArray = lines.toArray(new String[lines.size()]);

                int i = 0;

                for (; i < categoru.length; i++){

                    for (int in = 0; in < linesAsArray.length; in++) {

//                        boolean retVal = linesAsArray[in].equals(categoru[i]);


//                        if( categoru[i].matches(linesAsArray[in]+"\\b.*")){
                        if(linesAsArray[in].indexOf(categoru[i]) != -1){

                            String Category = linesAsArray[in].replace(categoru[i] + ":", "");
                            Category = Category.trim();

                            Cell cell_Categoru = row.createCell(i + 5);
                            cell_Categoru.setCellValue(Category);
                        }
                    }
                }

                //Гарантия
                Cell cell_Garantia = row.createCell(i + 0 + 5);
                cell_Garantia.setCellValue("12");

                //Изготовитель
                Cell cell_Izgotov= row.createCell(i + 1 + 5);
                String Izgotov = manufacturer + " " + " Страна производства " + Proizvod_strana;
                cell_Izgotov.setCellValue(Izgotov.trim());

                //Импортер
                Cell cell_Importer= row.createCell(i + 2 + 5);
                String importer = Importer;

                switch (importer) {
                    case "Домотехника":
                        importer = "ООО \"Домотехника\", 220092, г. Минск, ул. Берута, 3Б, пом. № 108";
                        break;

                    case "Неолинк":
                        importer = "";
                        break;

                    case "Электросервис":
                        importer = "ООО \"ЭЛЕКТРОСЕРВИС и Ко\" 220012, Республика Беларусь, г. Минск, ул.Чернышевского, 10А, к.412АЗ";
                        break;

                    case "ПАТИО":
                        importer = "ЗАО \"Патио\", 220005, г. Минск, пр. Независимости, дом № 58, офис 302";
                        break;

                    default:
                        importer = "";
                        break;
                }
                cell_Importer.setCellValue(importer);

                //Сервисные центры
                Cell cell_Servis= row.createCell(i + 3 + 5);

                //Производитель
                //Manufacturer


                String manf = null;
                for (int q = 0;; q++) {
                    try {
                        String red = serwis.getSheetAt(0).getRow(q).getCell(0).getStringCellValue();

                        boolean retVal;
                        red = red.toUpperCase().trim();
                        red = red.replaceAll("\\s", "");
                        Manufacturer = Manufacturer.toUpperCase().trim();
                        retVal = red.equals(Manufacturer);

//                        if (red.toUpperCase().trim() == Manufacturer.toUpperCase().trim()) {
                        if (retVal == true) {
                            manf = serwis.getSheetAt(0).getRow(q).getCell(2).getStringCellValue();
                            System.out.println("Сервис добавлен.");
                            break;
                        }
                    } catch (NullPointerException aq) {
                        System.out.println("Нет сервистного центра.");
                        break;
                    }
                }
                cell_Servis.setCellValue(manf);


                System.out.println("***************************");
                System.out.println(nameItem);
                System.out.println("Цена: " + Prise);
                System.out.println(importer);
                System.out.println("Товар добавлен");

                return;
            }
        }
    }

    //Сохранение XML файла
    public void SavingFile() throws IOException {
        //Запись книги в файл
        FileOutputStream fos = new FileOutputStream(fileAddress);
        wb.write(fos);
        fos.close();

    }

    //Дополнение базы данных Производителей
    public void DataBase(){
        int a = 0;

        for(;;) {
            try {
                String result1 = wb.getSheetAt(0).getRow(a).getCell(0).getStringCellValue();
                a++;
            } catch (NullPointerException e) {
                Row row = sheet.createRow(a++);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                try {
                    java.io.BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Введите Производителя!");
                    String proizvod_name = in.readLine();
                    System.out.println("Введите Адрес Производителя!");
                    String proizvod_adres = in.readLine();
                    System.out.println("Введите Сервистный центр!");
                    String servis_name = in.readLine();

                    Cell cell_1 = row.createCell(0);
                    cell_1.setCellValue(proizvod_name);

                    Cell cell_2 = row.createCell(1);
                    cell_2.setCellValue(proizvod_adres);

                    Cell cell_3 = row.createCell(2);
                    cell_3.setCellValue(servis_name);

                }catch (IOException x){
                    System.out.println( "DataBase " + x);
                }

                System.out.println("Добавленно в базу.");
                return;
            }
        }

    }

    public void ClineFIles() throws IOException {
        String[] product = {"водонагревател", "плит", "холодильник", "посудомоечн", "стиральные машин",
                "кофеварк", "комбайн", "микроволнов", "мультивар", "мясоруб", "пылесос", "швейные",
                "блендер", "стайлер", "кофемолк", "машинк", "миксер", "утюг", "фен", "электробритв",
                "электрочайник", "телевизоры", "cмартфоны", "ноутбук", "планшет"};

        ProductCategory productCategory = new ProductCategory();
        for (int i = 0; i < product.length; i++) {
            productCategory.CategorySearch(product[i]);

            wb1 = new HSSFWorkbook(new FileInputStream(productCategory.getFileAddress()));
            wb = new HSSFWorkbook();
            sheet = wb.createSheet();

            for (int a = 0; a < 1; a++) {
                try {
                    String red = wb1.getSheetAt(0).getRow(a).getCell(0).getStringCellValue();
                    Row row = sheet.createRow(a);

                    for (int b = 0; b < 255; b++) {
                        try {
                            try {
                                String result1 = wb1.getSheetAt(0).getRow(a).getCell(b).getStringCellValue();
                                Cell cell_1 = row.createCell(b);
                                cell_1.setCellValue(result1);
                            } catch (IllegalStateException e) {
                                int result1 = (int) wb1.getSheetAt(0).getRow(a).getCell(b).getNumericCellValue();
                                Cell cell_1 = row.createCell(b);
                                cell_1.setCellValue(result1);
                            }
                        } catch (NullPointerException e) {
                        }
                    }
                } catch (NullPointerException e) {
                    break;
                }

            }

            FileOutputStream fos = new FileOutputStream(productCategory.getFileAddress());
            wb.write(fos);
            fos.close();

        }
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public static void setNameItem(String nameItem) {
        ExselXSL.nameItem = nameItem;
    }

    public void setCategoru(String[] categoru) {
        this.categoru = categoru;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public void setIzgotov(String izgotov) {
        Izgotov = izgotov;
    }

    public void setProizvod_strana(String proizvod_strana) {
        Proizvod_strana = proizvod_strana;
    }

    public void setImporter(String importer) {
        Importer = importer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
