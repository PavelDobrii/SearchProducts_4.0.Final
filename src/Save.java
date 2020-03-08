import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Save {
    private static int numeFoto; // Номер сохроняемой фотографии

    //Сохранение фото
    public void SaveFoto(BufferedImage image, String item, String formatname, String addressFoto) throws IOException, InterruptedException {
        int a = 0;

        // 0 - one foto, 1 - all foto

        String nameFono = null;

        File f = new File(addressFoto); // поиск по папке и формирование масива
        String[] list = f.list();

        if (numeFoto == 1) {
            nameFono = item + ".jpeg";
        }else if (numeFoto > 1){
            nameFono = item + "-" + numeFoto + ".jpeg";
        }

        for (String file : list){
            a++;
            if (file.matches(nameFono)){
                System.out.println("Такое фото есть.");
                return;
            }
        }

        if (f.list().length == a){
//            System.out.println("address "+ addressFoto);
            ImageIO.write(image, formatname, new File(addressFoto + nameFono));
            System.out.println("Фото сохранено!");
        }
    }

    public void setNumeFoto(int numeFoto) {
        this.numeFoto = numeFoto;
    }
}

