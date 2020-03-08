import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formalization {

    private static String form; //Полученный URL запрос с прараметрами 1-го круга
    private static String name; // краткое Имя товара С сайта
    private static String comparison; //краткое Имя товара С прайса
    private static int photoItem; // Переключатеть количества сохранненных фото
    private static String NameItem; // Полное имя товара с сайта
    private static String NameURL; // URL на страницу товара
    private static String ItemDescription;
    private static String addressFoto;
    private  int index = 0;
    private static String All_Formalization;
    private static String manufacturer;


    //Поиск на совподения
    //Нужно доработать
    public void Formalization(){
        if (form != null)
            
            try {

            String[] part = form.split("\"second\":");

            for (int a = 0; a < part.length; a++) {

                name = part[a].substring(part[a].indexOf("\"name\":"), part[a].indexOf("\",\"full_name\""));
                name = name.replaceAll("\"name\":\"", "");
                comparison = comparison.replaceAll("-", " ");

                comparison = comparison.replaceAll("ПГ", "");
                comparison = comparison.replaceAll("Э", "");
                comparison = comparison.replaceAll("ПНД", "");

                boolean retVal;
                retVal = name.equals(comparison);

                System.out.println(name);
                System.out.println(comparison);

                if (retVal == true) {
                    System.out.println("Полное совполение");
                    //Место для обработки нужного раздела
                    index = 1;

                    //Не трогать!! Это нужно.
                    //Отправка на обработку для получения URL и Имя
                    ProcessingGoods(part[a]);

                    return;
                } else {
                    name = name.replaceAll("\\s", "");
                    name = name.replace("-", "");
                    name = name.replace("\\", "");
                    name = name.replace("ПГ", "");
                    comparison = comparison.replaceAll("\\s", "");

                    name = name.toUpperCase();
                    comparison = comparison.toUpperCase();

                    retVal = name.equals(comparison);

                    System.out.println(name);
                    System.out.println(comparison);

                    if (retVal == true) {
                        System.out.println("Полное совполение");
                        //Место для обработки нужного раздела
                        index = 1;

                        //Не трогать!! Это нужно.
                        //Отправка на обработку для получения URL и Имя
                        ProcessingGoods(part[a]);

                        return;
                    } else {
                        System.out.println("!!!!!!!!!!!!!!!Нет совподения!!!!!!!!!!!!!");
                    }
                }

                //System.out.println(name);
            }
        } catch (StringIndexOutOfBoundsException e) {
//            System.out.println("No Formalization!!! " + e);
        }
    }

    //Нахождение URL // имени товара
    public void ProcessingGoods(String url_Goods){
        // URL
        String html_url = url_Goods.substring(url_Goods.indexOf("html_url\":\""), url_Goods.indexOf("\",\"reviews\""));
        html_url = html_url.replaceAll("html_url\":\"", "");
        html_url= html_url.replace("\\", "");

        NameURL = html_url;

        System.out.println(html_url);

        //Name
        String name_prefix = url_Goods.substring(url_Goods.indexOf("name_prefix\":"), url_Goods.indexOf("\",\"extended_name"));
        String full_name = url_Goods.substring(url_Goods.indexOf("full_name\":\""), url_Goods.indexOf("\",\"name_prefix"));

        name_prefix = name_prefix.replaceAll("name_prefix\":\"", "");
        full_name = full_name.replaceAll("full_name\":\"", "");

        String fulNameItem = name_prefix + " " + full_name;

        fulNameItem = fulNameItem.replace("\\", "");

//        System.out.println(fulNameItem);

        NameItem = fulNameItem;
    }

    // Полное описание товара
    public void AllFormalization(String url) {
        String All_Product = null;

        Net net = new Net();
        net.setQuery(url);
        net.setParam(1);
        net.Net();
        All_Formalization = net.getAnswer();

        String text = All_Formalization;

        String Описание = "";
        String Значение = "";
        String Заглавие = "";


        if (text != null) {


            //Полное описание товара
            try {
                String Таблица = text.substring(text.indexOf("class=\"product-specs__table\""), text.indexOf("</table>"));
                String[] part = Таблица.split("<tr>");

                int a;

                for (a = 0; a < part.length; a++) {

                    try {
                        try {
                            Заглавие = part[a].substring(part[a].indexOf("<div class=\"product-specs__table-title-inner\">"));
                            Заглавие = Заглавие.substring(Заглавие.indexOf("<div class=\"product-specs__table-title-inner\">\n"), Заглавие.indexOf("</div>\n"));
                            Заглавие = Заглавие.replaceAll("<div class=\"product-specs__table-title-inner\">", "");
                            Заглавие = Заглавие.replaceAll("\\s\\s", "");
                            Заглавие = Заглавие.replaceAll("</div>", "");
                            Заглавие = Заглавие.replaceAll("</td>", "");
                            Заглавие = Заглавие.replaceAll("</tr>", "");
                            Заглавие = Заглавие.trim();

                            //System.out.println(st3);
                            if (Заглавие != null) {
                                All_Product = All_Product + "\n";
                                All_Product = All_Product + Заглавие + "\n";
                                All_Product = All_Product + "\n";
                            }
                        }catch (StringIndexOutOfBoundsException e){
                            //}catch (IllegalStateException e){
                            //System.out.println("Упс..." + e);
                        }

                        try {
                            Описание = part[a].substring(part[a].indexOf("<p class=\"product-tip__term\">"), part[a].indexOf("</p>"));

                            Описание = Описание.replace("<p class=\"product-tip__term\">", "");

                        }catch (StringIndexOutOfBoundsException e) {
                            Описание = part[a].substring(part[a].indexOf("<td>"), part[a].indexOf("</td>"));

                            Описание = Описание.replace("<td>", "");
                            Описание = Описание.replace("</td>", "");
                        }

                            Pattern p1 = Pattern.compile("class=\"value__text.+");
                            Matcher m1 = p1.matcher(part[a]);

                            Описание = Описание.replace("<p class=\"product-tip__term\">", "");
                            Описание = Описание.replaceAll("\\s\\s", "");
                            Описание = Описание.replaceAll("\\s\\z", "");
                            Описание = Описание.replace("&laquo;", "«");
                            Описание = Описание.replace("&raquo;", "»");
                            Описание = Описание.replace("&quot;", "\"");
                            Описание = Описание.trim();

                            Описание = Описание + ": ";

                            if (m1.find()) {
                                Значение = m1.group();
                                Значение = Значение.replace("class=\"value__text\">", "");
                                Значение = Значение.replace("</span>", "");
                                Значение = Значение.replaceAll("\\s\\s", "");
                                Значение = Значение.replace("&nbsp;", " ");
                                Значение = Значение.replace("&quot;", "\"");
                                Значение = Значение.replace("\n", "");
                                Значение = Значение.replace("\r", "");
                                Значение = Значение.replace("i-tip", "есть");
                                Значение = Значение.replace("i-x", "нет");

                            } else {
                                Pattern p2 = Pattern.compile("<span class= \".+");
                                Matcher m2 = p2.matcher(part[a]);
                                if (m2.find()) {

                                    Значение = m2.group();
                                    Значение = Значение.replace("<span class= \"", "");
                                    Значение = Значение.replace("\" ></span>", "");
                                    Значение = Значение.replace("i-tip", "Есть");
                                    Значение = Значение.replace("i-x", "Нет");

                                }else {
                                    //System.out.println(" Нет данных.");
                                }
                            }

                        All_Product = All_Product + Описание + Значение + "\n";

                    }catch (StringIndexOutOfBoundsException e){
                        //System.out.println(" Нет данных.");
                    }
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Жопа..!! в AllFormalizationAll " + e);
            }

            // Место для отправки на обработку.!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            ItemDescription = All_Product;
//            System.out.println(All_Product);

            System.out.println("Описание сохранено!");

        } else {
            System.out.println(" No ");
            return;
        }


    }


    //Нахождение Производителя
    public void Manufacturer(){
//        String MF = All_Formalization;
//        System.out.println(All_Formalization);
        String st1 = All_Formalization.substring(All_Formalization.indexOf("<div class=\"offers-form__description offers-form__description_primary offers-form__description_small\">"), All_Formalization.indexOf("<div id=\"product-prices-container\""));
        try {
            st1 = st1.substring(st1.indexOf("<div class=\"offers-form__description offers-form__description_primary offers-form__description_small\">"), st1.indexOf("<div id=\"product-facets-container\""));
        }catch (StringIndexOutOfBoundsException e){}

        String st2 = null;
        try {
        st1 = st1.replaceAll("<div class=\"offers-form__description offers-form__description_primary offers-form__description_small\">", "");
        st2 = st1.substring(st1.indexOf("<script src=\""), st1.indexOf("</script>"));
        st1 = st1.replaceAll(st2, "");
        }catch (StringIndexOutOfBoundsException e){}


        st1 = st1.replaceAll("</script>", "");
        st1 = st1.replaceAll("\\s\\s", "");
        st1 = st1.replaceAll("<br>", "");
        st1 = st1.replaceAll("<p class=\"complementary\">", "");
        st1 = st1.replaceAll("</p>", "");
        st1 = st1.replaceAll("</div>", "");
        st1 = st1.replaceAll("\\s\\s", "");
        st1 = st1.replace("&quot;", "\"");
        manufacturer = st1;
    }
// СП ОАО «Брестгазоаппарат». РБ, 224016, г. Брест, ул. Орджоникидзе, 22. Товар подлежит обязательной сертификации или декларированию соответствия. <div id="product-facets-container" class="offers-description-filter"> <div class="offers-description-filter__row"> <div class="offers-description-filter__label"> <div class="offers-description-filter__sign"> Все варианты: <div class="offers-description-filter__field"> <div class="popover-style__handle offers-description__popover-handle"> <div class="popover-style popover-style_primary popover-style_small popover-style_bottom-left offers-description__popover offers-description__popover_width_full"> <div class="popover-style__content"> <div class="offers-description-configurations"> <div class="offers-description-configurations__list"><a href="https://catalog.onliner.by/tabletop_cooker/gefest/gef70002"class="offers-description-configurations__item offers-description-configurations__item_active"> <div class="offers-description-configurations__image" style="background-image: url(https://content2.onliner.by/catalog/device/header/e5aeb76dba6b417f63f76449dc4fbaf1.jpg);"> <div class="offers-description-configurations__title"> GEFEST 700-02<div class="offers-description-configurations__description"><div class="offers-description-configurations__line"> <div class="offers-description-configurations__price"> <div class="offers-description-configurations__price-value offers-description-configurations__price-value_primary"> от 49,91 р. <div class="offers-description-configurations__proposal"> <div class="offers-description-configurations__proposal-value offers-description-configurations__proposal-value_primary"> 10 предложений</a> <a href="https://catalog.onliner.by/tabletop_cooker/gefest/gef70003"class="offers-description-configurations__item "> <div class="offers-description-configurations__image" style="background-image: url(https://content2.onliner.by/catalog/device/header/ea6cddd7682419b29d2a3ea5a9305bc2.jpg);"> <div class="offers-description-configurations__title"> GEFEST 700-03<div class="offers-description-configurations__description"><div class="offers-description-configurations__line"> <div class="offers-description-configurations__price"> <div class="offers-description-configurations__price-value offers-description-configurations__price-value_primary"> от 51,10 р. <div class="offers-description-configurations__proposal"> <div class="offers-description-configurations__proposal-value offers-description-configurations__proposal-value_primary"> 10 предложений</a><div class="offers-description-filter-control offers-description-filter-control_huge offers-description-filter-control_select"> <div class="offers-description-filter-control__image" style="background-image: url(https://content2.onliner.by/catalog/device/header/e5aeb76dba6b417f63f76449dc4fbaf1.jpg);"> <div class="offers-description-filter-control__item"> GEFEST 700-02

    // Нахождение  URL фото товара// Отправка на сохранение
    public void PhotoURL(){
        String text = All_Formalization;

        try {
            //Обрезка страницы, деление её на части и последуюцие нахождение фото товаров
            if (text != null) {

                String st1 = text.substring(text.indexOf("class=\"product-gallery\""), text.indexOf("<!--/ Gallery-->"));
                String[] photo_part = st1.split("</div>");

                int b = 1;
                int a;
                for (a = 0; a < 6; a++) {
                    try {
                        String photo = photo_part[a].substring(photo_part[a].indexOf("data-original=\""), photo_part[a].indexOf("\" role=\"button\""));
                        photo = photo.replaceAll("data-original=\"", "");


                        ///Включить!!
                        URL url1 = new URL(photo);
                        BufferedImage image = ImageIO.read(url1);

                        if (image != null) {
                            // Обработка и отправка на сохранение фото

                            //Сохранение фото jpeg
                            if (photo.contains(".jpeg") == true) {
                                String Item = NameItem.replaceAll("/", "-");
                                Item = Item.replaceAll(" ", "_");
                                String formatname = "jpeg";

                                Save save = new Save();
                                save.setNumeFoto(b);
                                save.SaveFoto(image, Item, formatname, addressFoto);
                                b++;
                                //Значение для переключения между кол-вом скачиваемых фото
                                if (photoItem == 0)return;
                            }
                            //Сохранение фото png
                            if (photo.contains(".png") == true) {
                                String Item = NameItem.replaceAll("/", "-");
                                Item = Item.replaceAll(" ", "_");
                                String formatname = "png";

                                Save save = new Save();
                                save.setNumeFoto(b);
                                save.SaveFoto(image, Item, formatname, addressFoto);
                                b++;
                                //Значение для переключения между кол-вом скачиваемых фото
                                if (photoItem == 0)return;
                            }

                            //Сохранение фото jpg
                            if (photo.contains(".jpg") == true) {
                                String Item = NameItem.replaceAll("/", "-");
                                Item = Item.replaceAll(" ", "_");
                                String formatname = "jpg";

                                Save save = new Save();
                                save.setNumeFoto(b);
                                save.SaveFoto(image, Item, formatname, addressFoto);
                                b++;
                                //Значение для переключения между кол-вом скачиваемых фото
                                if (photoItem == 0)return;
                            }
                        }
                    } catch (StringIndexOutOfBoundsException e) {
//                        System.out.println("PhotoURL "+e);
                    } catch (IOException e) {
//                        System.out.println("PhotoURL "+e);
                    }catch (NullPointerException e){
//                        System.out.println("PhotoURL "+e);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                    }
                }

            } else {
                System.out.println(" No photos ");
                return;
            }
        }catch (StringIndexOutOfBoundsException e){
            System.out.println("Error "+ e);
        }
    }


    /// Set and Get

    public void setPhotoItem(int photoItem) {
        this.photoItem = photoItem;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public static String getNameItem() {
        return NameItem;
    }

    public static String getNameURL() {
        return NameURL;
    }

    public int getIndex() {
        return index;
    }

    public static void setAddressFoto(String addressFoto) {
        Formalization.addressFoto = addressFoto;
    }

    public static String getItemDescription() {
        return ItemDescription;
    }

    public static String getManufacturer() {
        return manufacturer;
    }
}
