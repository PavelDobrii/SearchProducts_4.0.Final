import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class PriseAll {

    private String Address;
    private String FileAddress;
    private String Answer;
    private int Index;
    private String NameItem;
    private String NameURL;
    private String ItemDescription;
    private String Manufacturer;


    public void PriseAll(int Категория, int Производитель, int Наименование, int Цена, int Страна, String prise, String path) throws IOException {

        //Открытие Exel для чтения наименований
        Workbook wb1;
        try {
            wb1 = new XSSFWorkbook(new FileInputStream(path));
        }catch (NoClassDefFoundError e) {
            wb1 = new HSSFWorkbook(new FileInputStream(path));
        }

        //String result;

        String ПервыйСтолбецXLS = null;
        int a = 0;

        for (;;) {
            try {

                //Категория товара
                try {
                    ПервыйСтолбецXLS = wb1.getSheetAt(0).getRow(a).getCell(Категория).getStringCellValue();
                }catch (IllegalStateException e){ }

                //Производитель товара
                String ВторойСтолбецXLS = wb1.getSheetAt(0).getRow(a).getCell(Производитель).getStringCellValue();


                //Наименование товара
                String ТретийСтолбецXLS = null;
                try {
                    ТретийСтолбецXLS = wb1.getSheetAt(0).getRow(a).getCell(Наименование).getStringCellValue();
                }catch (IllegalStateException e) {
                    int ТретийСтолбецXLS_int = (int) wb1.getSheetAt(0).getRow(a).getCell(Цена).getNumericCellValue();
                    ТретийСтолбецXLS = ТретийСтолбецXLS + "";
                }

                //Цена товара
                String ЦенаСтолбецXLS = null;

                try {
                    ЦенаСтолбецXLS = wb1.getSheetAt(0).getRow(a).getCell(Цена).getStringCellValue();
                }catch (IllegalStateException e){
                    int ЦенаСтолбецXLS_int = (int) wb1.getSheetAt(0).getRow(a).getCell(Цена).getNumericCellValue();
                    ЦенаСтолбецXLS = ЦенаСтолбецXLS_int + "";
                }catch (NullPointerException e){
                    break;
                }

                //Страна товара
                String СтранаСтолбецXLS = wb1.getSheetAt(0).getRow(a).getCell(Страна).getStringCellValue();


                ProductCategory productCategory = new ProductCategory();
                productCategory.CategorySearch(ПервыйСтолбецXLS);
                int index = productCategory.getIdex();
                Address = productCategory.getAddress();
                FileAddress = productCategory.getFileAddress();

                a++;

                if (index == 1) {

                    Net net = new Net();
                    String st = ВторойСтолбецXLS + " " + ТретийСтолбецXLS;
                    net.setQuery(st);
                    net.setParam(0);
                    net.Net();
                    Answer = net.getAnswer();

                    Formalization form = new Formalization();
                    form.setForm(Answer);
                    form.setComparison(ТретийСтолбецXLS);
                    form.Formalization();
                    Index = form.getIndex();
                    if (Index == 1) {
                        NameItem = form.getNameItem();
                        NameURL = form.getNameURL();

                        form.AllFormalization(NameURL);
                        ItemDescription = form.getItemDescription();

                        form.setAddressFoto(Address);
                        form.PhotoURL();

                        form.Manufacturer();
                        Manufacturer = form.getManufacturer();


                        ExselXSL exselXSL = new ExselXSL();

                        exselXSL.setManufacturer(Manufacturer);
                        String [] categoru = productCategory.getCategoru();
                        exselXSL.setItemDescription(ItemDescription);
                        exselXSL.setCategoru(categoru);

                        exselXSL.setIzgotov(ВторойСтолбецXLS);
                        exselXSL.setProizvod_strana(СтранаСтолбецXLS);
                        exselXSL.setImporter(prise);

                        exselXSL.setFileAddress(FileAddress);
                        exselXSL.setNameItem(NameItem);
                        exselXSL.CopyingFile();//Копирование XML файла
                        exselXSL.WorkFile(ЦенаСтолбецXLS, ВторойСтолбецXLS);//Работа с XML файлом // wb1 -- Для работы с инфой


                        exselXSL.SavingFile(); //Сохранение XML файла


                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        System.out.println();
                    }
                }


            } catch (NullPointerException e) {
                System.out.println("No PriseALL " + prise + " " + e);
                return;
            }
        }
    }
}

