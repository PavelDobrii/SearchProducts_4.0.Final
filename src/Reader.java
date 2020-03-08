import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reader extends JFrame {

        //Кнопка
    JButton Button1 = new JButton("Запустить програму");

        //Надпись с заполняемым полем
    JLabel Label1  = new JLabel("Введите адрес папки с прайсами:");
    JTextField TextField1 = new JTextField("C:\\Users\\BOIH_MAPCA\\Desktop\\МагнитМ\\Прайсы",10);

        //Кругляшки переключения
    JRadioButton RadioButton1 = new JRadioButton("Одно фото:");
    JRadioButton RadioButton2 = new JRadioButton("Все фото:");

        //Квадратик с галоской
    JCheckBox CheckBox1 = new JCheckBox("Всё заполнено и проверено:", false);


 public Reader () {
     super("SearchProducts_4.0.Final");
     this.setBounds(100,100,450,100);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

     Container compiler = this.getContentPane();
     compiler.setLayout(new GridLayout(3,2,2,2));
     compiler.add(Label1);
     compiler.add(TextField1);

     ButtonGroup buttonGroup = new ButtonGroup();
     buttonGroup.add(RadioButton1);
     buttonGroup.add(RadioButton2);
     compiler.add(RadioButton1);
     compiler.add(RadioButton2);
     RadioButton1.setSelected(true);

     compiler.add(CheckBox1);

//     Button1.addAncestorListener(new ButtonEvent());
     compiler.add(Button1);
    }

    class ButtonEvent implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if(CheckBox1.isSelected()) {

                String messege = null;
                messege += TextField1.getText();

                if (RadioButton1.isSelected()) {

                } else if (RadioButton2.isSelected()) {

                }
            }else {
                JOptionPane.showMessageDialog(null,"Подтвердите выбор","Ok",JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}