import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Carte implements Comparable {
    public String titlu;
    public String autor;
    public int nr_pag;

    public Carte(String titlu, String autor, int nr_pag) {
        this.titlu = titlu;
        this.autor = autor;
        this.nr_pag = nr_pag;
    }

    @Override
    public int compareTo(Object obj) {
        String titluComp = ((Carte) obj).titlu;
        if (titluComp.compareTo(titlu) < 0)
            return 1;
        else if (titluComp.compareTo(titlu) > 0)
            return -1;
        else
            return 0;
    }

    static ArrayList<Carte> al = new ArrayList<>();

    public static void addInArrayList() {
        try {
            FileReader fr = new FileReader("Carti.txt");
            BufferedReader bfr = new BufferedReader(fr);
            for (; ; ) {
                String titlu = bfr.readLine();
                if (titlu == null) break;
                String autor;
                autor = bfr.readLine();
                String spagini = bfr.readLine();
                int nr_pag = Integer.parseInt(spagini);
                Carte c = new Carte(titlu, autor, nr_pag);
                al.add(c);
            }
            bfr.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void adaugareTXT(String titlu, String autor, String nr_pag) {
        Carte c = new Carte(titlu, autor, Integer.parseInt(nr_pag));
        al.add(c);
        try {
            File f1 = new File("E:\\An 3 sem 1\\POO\\Proiect\\Carti.txt");
            FileWriter fw = new FileWriter(f1.getName(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(titlu);
            bw.newLine();
            bw.write((autor));
            bw.newLine();
            bw.write(nr_pag);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sortareTXT() {
        int nrCarti = al.size();
        Collections.sort(al);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Carti.txt"));
            for (int i = 0; i < nrCarti; i++) {
                bw.write(al.get(i).titlu);
                bw.newLine();
                bw.write(al.get(i).autor);
                bw.newLine();
                bw.write(String.valueOf(al.get(i).nr_pag));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int cautareBinara(ArrayList<String> al, String deCautat, int stanga, int dreapta) {
        int mijloc = (stanga + dreapta) / 2;
        if (stanga > dreapta)
            return -1;
        if (deCautat.equals(al.get(mijloc)))
            return mijloc;
        if (deCautat.compareTo(al.get(mijloc)) < 0)
            return cautareBinara(al, deCautat, stanga, mijloc - 1);
        else
            return cautareBinara(al, deCautat, mijloc + 1, dreapta);
    }

    public static int cautareTitluTXT(String string) {
        ArrayList<String> titluri = new ArrayList<>();
        for (int i = 0; i < al.size(); i++)
            titluri.add(al.get(i).titlu);
        int contor1 = 0;
        for (; ; ) {
            int x = cautareBinara(titluri, string, 0, titluri.size() - 1);
            if (x == -1)
                break;
            else {
                contor1++;
                titluri.remove(x);
            }
        }
        return contor1;
    }

    public static int cautareAutorTXT(String string) {
        ArrayList<String> autori = new ArrayList<>();
        for (int i = 0; i < al.size(); i++)
            autori.add(al.get(i).autor);
        int contor2 = 0;
        for (; ; ) {
            int y = cautareBinara(autori, string, 0, autori.size() - 1);
            if (y == -1)
                break;
            else {
                contor2++;
                autori.remove(y);
            }
        }
        return contor2;
    }


    static ArrayList<Carte> xml = null;
    public static void addInArrayListXML() throws FileNotFoundException, XMLStreamException {
        Carte carteCrt = null;
        String continutTag = null;
        String titluCrt = null, autorCrt = null;
        int nr_pagCrt = 0;
        File file = new File("Carti.xml");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(file));
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String s = reader.getLocalName();
                if (s.equals("Biblioteca"))
                    xml = new ArrayList<Carte>();
                else if (s.equals("carte"))
                    carteCrt = new Carte(titluCrt, autorCrt, nr_pagCrt);
            } else if (event == XMLStreamConstants.CHARACTERS)
                continutTag = reader.getText().trim();
            else if (event == XMLStreamConstants.END_ELEMENT) {
                String s = reader.getLocalName();
                if (s.equals("carte")) xml.add(carteCrt);
                else if (s.equals("titlu")) carteCrt.titlu = continutTag;
                else if (s.equals("autor")) carteCrt.autor = continutTag;
                else if (s.equals("nr_pag")) carteCrt.nr_pag = Integer.parseInt(continutTag);
            }//if END_ELEMENT
        }//end while reader.hasNext()
        for (int i = 0; i < xml.size(); i++)
            System.out.println(xml.get(i).nr_pag);
    }

    public static void adaugareXML(String titlu, String autor, String nr_pag)throws XMLStreamException{
        Carte c = new Carte(titlu, autor, Integer.parseInt(nr_pag));
        xml.add(c);
        for (int i = 0; i < xml.size(); i++)
            System.out.println(xml.get(i).titlu);
        try (FileOutputStream out = new FileOutputStream("E:\\An 3 sem 1\\POO\\Proiect\\Carti.xml")) {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(out);

            writer.writeStartElement("Biblioteca");
            for (int i = 0; i < xml.size(); i++) {
                writer.writeStartElement("carte");

                writer.writeStartElement("titlu");
                writer.writeCharacters(xml.get(i).titlu);
                writer.writeEndElement();

                writer.writeStartElement("autor");
                writer.writeCharacters(xml.get(i).autor);
                writer.writeEndElement();

                writer.writeStartElement("nr_pag");
                writer.writeCharacters(String.valueOf(xml.get(i).nr_pag));
                writer.writeEndElement();

                writer.writeEndElement();
            }
            writer.writeEndElement();

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public static int cautareTitluXML(String string) {
        ArrayList<String> titluriXML = new ArrayList<>();
        for (int i = 0; i < xml.size(); i++)
            titluriXML.add(xml.get(i).titlu);
        int contor1 = 0;
        for (; ; ) {
            int x = cautareBinara(titluriXML, string, 0, titluriXML.size() - 1);
            if (x == -1)
                break;
            else {
                contor1++;
                titluriXML.remove(x);
            }
        }
        return contor1;
    }

    public static int cautareAutorXML(String string) {
        ArrayList<String> autoriXML = new ArrayList<>();
        for (int i = 0; i < xml.size(); i++)
            autoriXML.add(xml.get(i).autor);
        int contor2 = 0;
        for (; ; ) {
            int y = cautareBinara(autoriXML, string, 0, autoriXML.size() - 1);
            if (y == -1)
                break;
            else {
                contor2++;
                autoriXML.remove(y);
            }
        }
        return contor2;
    }

    public static void sortareXML() {
        Collections.sort(xml);
        try (FileOutputStream out = new FileOutputStream("E:\\An 3 sem 1\\POO\\Proiect\\Carti.xml")) {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(out);

            writer.writeStartElement("Biblioteca");
            for (int i = 0; i < xml.size(); i++) {
                writer.writeStartElement("carte");

                writer.writeStartElement("titlu");
                writer.writeCharacters(xml.get(i).titlu);
                writer.writeEndElement();

                writer.writeStartElement("autor");
                writer.writeCharacters(xml.get(i).autor);
                writer.writeEndElement();

                writer.writeStartElement("nr_pag");
                writer.writeCharacters(String.valueOf(xml.get(i).nr_pag));
                writer.writeEndElement();

                writer.writeEndElement();
            }
            writer.writeEndElement();

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}






