import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args)throws FileNotFoundException, XMLStreamException {
        Fereastra f=new Fereastra( );
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        Carte.addInArrayList();
        Carte.addInArrayListXML();
    }
}
class Fereastra extends JFrame{
    private JButton jbAdaugare,jbSortare,jbCautareTitlu,jbCautareAutor,jbClear, jbExit;
    private JTextField jtf1, jtf2,jtf3, jtf4,jtf5;
    private JToggleButton jtbSwitch;

    public Fereastra( ){
        this.setTitle("Biblioteca");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jl1=new JLabel("Titlu:");
        jtf1=new JTextField(10);
        JPanel jp1=new JPanel( );
        jp1.add(jl1);
        jp1.add(jtf1);

        JLabel jl2=new JLabel("Autor:");
        jtf2=new JTextField(10);
        JPanel jp2=new JPanel( );
        jp2.add(jl2);
        jp2.add(jtf2);

        JLabel jl3=new JLabel("Nr pagini:");
        jtf3=new JTextField(10);
        JPanel jp3=new JPanel( );
        jp3.add(jl3);
        jp3.add(jtf3);

        JLabel jl4=new JLabel("Status:");
        jtf4=new JTextField(20);
        jtf4.setEditable(false);
        JPanel jp4=new JPanel();
        jp4.add(jl4);
        jp4.add(jtf4);

        JLabel jl5=new JLabel("Mod:");
        jtf5=new JTextField(10);
        jtf5.setText("TXT");
        jtf5.setEditable(false);
        JPanel jp5=new JPanel();
        jp5.add(jl5);
        jp5.add(jtf5);

        JPanel jpA=new JPanel();
        jpA.setLayout(new GridLayout(5,1));
        jpA.add(jp1);jpA.add(jp2);jpA.add(jp3);jpA.add(jp4);jpA.add(jp5);

        AscultaButonAdaugare add=new AscultaButonAdaugare( );
        AscultaButonSortare sort=new AscultaButonSortare( );
        AscultaButonCautareTitlu cautTitlu=new AscultaButonCautareTitlu( );
        AscultaButonCautareAutor cautAutor=new AscultaButonCautareAutor();
        AscultaButonClear clear=new AscultaButonClear();
        AscultaButonExit exit=new AscultaButonExit();
        AscultaButonSwitch schimba=new AscultaButonSwitch();

        jbAdaugare=new JButton("Adaugare");
        jbAdaugare.addActionListener(add);

        jbSortare=new JButton("Sortare");
        jbSortare.addActionListener(sort);

        jbCautareTitlu=new JButton("Cautare titlu");
        jbCautareTitlu.addActionListener(cautTitlu);

        jbCautareAutor=new JButton("Cautare autor");
        jbCautareAutor.addActionListener(cautAutor);

        jbClear=new JButton("Clear");
        jbClear.addActionListener(clear);

        jbExit=new JButton("Exit");
        jbExit.addActionListener(exit);

        jtbSwitch=new JToggleButton("TXT/XML");
        jtbSwitch.addActionListener(schimba);

        JPanel jpB=new JPanel( );
        jpB.add(jbAdaugare);
        jpB.add(jbSortare);
        jpB.add(jbCautareTitlu);
        jpB.add(jbCautareAutor);
        jpB.add(jbClear);
        jpB.add(jbExit);
        jpB.add(jtbSwitch);

        JPanel jp=new JPanel( );
        jp.setLayout(new GridLayout(2,2));
        jp.add(jpA); jp.add(jpB);

        Container cFinal=this.getContentPane( );
        cFinal.add(jp,"South");
    }
    class AscultaButonAdaugare implements ActionListener{
        public void actionPerformed(ActionEvent ev) {
            String s1=jtf1.getText();
            String s2=jtf2.getText();
            String s3=jtf3.getText();
            if(jtf5.getText().equals("TXT"))
                Carte.adaugareTXT(s1,s2,s3);
            else if(jtf5.getText().equals("XML")) {
                try {
                    Carte.adaugareXML(s1,s2,s3);
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            }
            jtf4.setText("Carte adaugata");
        }
    }
    class AscultaButonSortare implements ActionListener{
        public void actionPerformed(ActionEvent ev) {
            if(jtf5.getText().equals("TXT"))
                Carte.sortareTXT();
            else if(jtf5.getText().equals("XML"))
                    Carte.sortareXML();
            jtf4.setText("Cartile au fost sortate");
        }
    }
    class AscultaButonCautareTitlu implements ActionListener{
        public void actionPerformed(ActionEvent ev) {
            String s1=jtf1.getText();
            if(jtf5.getText().equals("TXT")){
                int nr1=Carte.cautareTitluTXT(s1);
                if(nr1>0)
                    jtf4.setText("S-au gasit "+nr1+" carti cu acest titlu");
                else
                    jtf4.setText("Nu s-a gasit cartea cautata");
            }
            else if(jtf5.getText().equals("XML")){
                int nr4=Carte.cautareTitluXML(s1);
                if(nr4>0)
                    jtf4.setText("S-au gasit "+nr4+" carti cu acest titlu");
                else
                    jtf4.setText("Nu s-a gasit cartea cautata");
            }
        }
    }
    class AscultaButonCautareAutor implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            String s2=jtf2.getText();
            if(jtf5.getText().equals("TXT") ) {
                int nr2=Carte.cautareAutorTXT(s2);
                if(nr2>0)
                    jtf4.setText("S-au gasit "+nr2+" carti de la acest autor");
                else
                    jtf4.setText("Nu s-a gasit acest autor");
            }
            else if(jtf5.getText().equals("XML")){
                int nr3=Carte.cautareAutorXML(s2);
                if(nr3>0)
                    jtf4.setText("S-au gasit "+nr3+" carti de la acest autor");
                else
                    jtf4.setText("Nu s-a gasit acest autor");
            }
        }
    }
    class AscultaButonClear implements ActionListener{
        public void actionPerformed(ActionEvent ev) {
            jtf1.setText("");
            jtf2.setText("");
            jtf3.setText("");
            jtf4.setText("");
        }
    }
    class AscultaButonExit implements ActionListener {
        public void actionPerformed(ActionEvent ev){
            System.exit(0);
        }
    }
    class AscultaButonSwitch implements ActionListener {
        public void actionPerformed(ActionEvent ev){
            if(jtf5.getText().equals("TXT"))
                jtf5.setText("XML");
            else if(jtf5.getText().equals("XML"))
                jtf5.setText("TXT");
        }
    }
}
