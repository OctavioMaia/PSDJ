package TransCaixa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class Teste {
    public static TransCaixa strToTransCaixa(String linha) {
        //
        double preco = 0.0;
        int ano = 0;
        int mes = 0;
        int dia = 0;
        int horas = 0;
        int min = 0;
        int seg = 0;
        String codTrans, codCaixa;

        // split()
        String[] campos = linha.split("/");

        codTrans = campos[0].trim();
        codCaixa = campos[1].trim();

        try {
            preco = Double.parseDouble(campos[2]);
        } catch (InputMismatchException | NumberFormatException e) {
            return null;
        }

        String[] diaMesAnoHMS = campos[3].split("T");
        String[] diaMesAno = diaMesAnoHMS[0].split(":");
        String[] horasMin = diaMesAnoHMS[1].split(":");
        try {
            dia = Integer.parseInt(diaMesAno[0]);
            mes = Integer.parseInt(diaMesAno[1]);
            ano = Integer.parseInt(diaMesAno[2]);
            horas = Integer.parseInt(horasMin[0]);
            min = Integer.parseInt(horasMin[1]);
        } catch (InputMismatchException | NumberFormatException e) {
            return null;
        }

        return TransCaixa.of(codTrans, codCaixa, preco, LocalDateTime.of(ano, mes, dia, horas, min, 0));
    }

    public static void main(String[] args) {

        Comparator<TransCaixa> transPorData =
                (TransCaixa tc1, TransCaixa tc2) -> {
                    LocalDateTime ldt1 = tc1.getData();
                    LocalDateTime ldt2 = tc2.getData();
                    if (ldt1.equals(ldt2)) return 0;
                    else if (ldt1.isBefore(ldt2)) return -1;
                    else return 1;
                };

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("transCaixa.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<TransCaixa> lTrans = new ArrayList<>();

        for(String line: lines){
            lTrans.add(strToTransCaixa(line));
        }

        TreeSet<TransCaixa> tcaixaPorData = new TreeSet<TransCaixa>(transPorData);
        tcaixaPorData.addAll(lTrans);

        System.out.println("Transações OK: " + tcaixaPorData);
    }
}
