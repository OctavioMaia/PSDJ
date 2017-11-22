package Aula4;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalField;
import java.util.TimeZone;

public class TimeDateAPI {

    public static void main(String[] args) {
        ex13();
    }

    public static void ex1(){
        LocalDateTime nasc = LocalDateTime.of(1995,7,19,0,0);
        Instant nasc2 = nasc.toInstant(ZoneOffset.UTC);
        LocalDateTime nasc3 = LocalDateTime.ofInstant(nasc2,ZoneOffset.UTC);

        System.out.println("LocalDateTime: " + nasc);
        System.out.println("Instant: " + nasc2);
        System.out.println("Inverso: " + nasc3);
    }

    public static void ex2(){
        Duration oneHr = Duration.ofHours(1);
        Duration oneHr2 = ChronoUnit.HOURS.getDuration();

        System.out.println("Minutos: " + oneHr.toMinutes() +" Segundos: "+ oneHr.getSeconds() +" Millis: "+ oneHr.toMillis());
        System.out.println("Minutos: " + oneHr2.toMinutes() +" Segundos: "+ oneHr2.getSeconds() +" Millis: " +oneHr2.toMillis());
    }

    public static void ex3(){
        LocalDate eu = LocalDate.of(1995,7,19);
        LocalDate irma = eu.plusDays(25).plusMonths(1).plusYears(3);
        LocalDate pai = eu.minusMonths(6).minusYears(33);
        LocalDate mae = eu.minusMonths(9).minusYears(31);
        Integer idadeIrma = LocalDate.now().getYear()-irma.getYear();

        System.out.println("Eu: " + eu.toString() + " Pai: " + pai.toString() + " Mae: " + mae.toString());
        System.out.println("Irma: " + irma.toString() + " Idade: " +idadeIrma);
    }

    public static void ex4(){
        LocalDateTime dataNascimento = LocalDateTime.of(1995,7,19,0,0);
        Duration duracao = Duration.between(LocalDateTime.now(), dataNascimento);
        Period periodo = Period.between(LocalDate.now(), dataNascimento.toLocalDate());

        for (ChronoUnit cu : ChronoUnit.values()) {
            try {
                System.out.println(cu + " " + duracao.get(cu) + "; ");
            } catch (Exception e) {}
        }

        for (ChronoUnit cu : ChronoUnit.values()) {
            try {
                System.out.println(cu + " " + periodo.get(cu) + "; ");
            } catch (Exception e) {}
        }
    }

    public static void ex5(){
        LocalDate hoje = LocalDate.now();
        System.out.println(hoje.get(ChronoField.ERA));
    }

    public static void ex6(){
        LocalDateTime dataNascimento = LocalDateTime.of(1995,7,19,0,0);
        LocalDateTime agora = LocalDateTime.now();

        Duration diferenca = Duration.between(dataNascimento, agora);

        for (ChronoUnit cu : ChronoUnit.values()) {
            try {
                System.out.println(cu + ": " + diferenca.get(cu));
            } catch (Exception e) {}
        }

        LocalDateTime somaData = dataNascimento.plus(diferenca);

        System.out.println(somaData + "\n" + agora);
    }

    public static void ex7(){
        LocalDate seguro = LocalDate.of(2018,6,11);

        long semanas = ChronoUnit.WEEKS.between(LocalDate.now(),seguro);
        long dias = ChronoUnit.DAYS.between(LocalDate.now(),seguro);

        System.out.println("Semanas: "+semanas);
        System.out.println("Dias: "+dias);
    }

    public static void ex8(){
        LocalDate seguro = LocalDate.of(2017,6,17);
        long semanas = ChronoUnit.WEEKS.between(LocalDate.now(),seguro);
        LocalDate endSeguro = seguro.plusWeeks(semanas);

        int dia = endSeguro.getDayOfWeek().getValue()-1;
        LocalDate diaRenovar = endSeguro.minusDays(dia);

        System.out.println("Dia: " + diaRenovar.getDayOfWeek() + " / " + diaRenovar.getDayOfMonth());
    }

    public static void ex9(){
        LocalTime agora = LocalTime.now();
        System.out.println("Agora : " + agora);
        ChronoField[] campos = ChronoField.values();
        System.out.println("----- ChronoFields suportados por LocalTime -----");
        for(ChronoField cf : campos)
            if(agora.isSupported(cf))
                System.out.println(cf);

        System.out.println(agora.get(ChronoField.MINUTE_OF_DAY));
        System.out.println("Não existe ChronoUnit correspondente a MINUTE_OF_DAY !");
        System.out.println("Gama : " + ChronoField.MINUTE_OF_DAY.range() +
                " Data tem MINUTE_OF_DAY ? " + LocalDate.now().isSupported(ChronoField.MINUTE_OF_DAY));
    }

    public static void ex10(){
        Instant agora = Instant.now();
        ZoneOffset zoffPortugal = OffsetDateTime.now().getOffset();
        ZonedDateTime agoraNossaZona = ZonedDateTime.ofInstant(agora, zoffPortugal);
        System.out.println(agoraNossaZona);
        //
        ZoneId zonaAustralia = ZoneId.of("Australia/Canberra" );
        ZonedDateTime zdtAust = agora.atZone(zonaAustralia);
        System.out.println(zdtAust);
        //
        System.out.println("Instante cf. Portugal : " + agora);
        System.out.println("Instante cf. Austrália : " + zdtAust.toInstant());
        System.out.println("---   TIRE AS CONCLUSÕES ---");
    }

    public static void ex12(){
        LocalDate agora = LocalDate.of(2017,3,31);
        LocalDate[] pagamentos = new LocalDate[3];

        for(int i=0; i<3;i++){
            pagamentos[i] = agora.plus(i+1,IsoFields.QUARTER_YEARS);
            System.out.println("Pagamento nº"+(i+1)+" : "+pagamentos[i]);
        }
    }

    public static void ex13(){
        LocalDateTime atual = LocalDateTime.of(2017,12,31,23,30,0);
        ZoneId chile = ZoneId.of("America/Santiago");
        ZonedDateTime zdtchile = ZonedDateTime.of(atual,chile);

        OffsetDateTime odtChile = zdtchile.toOffsetDateTime();

        System.out.println(odtChile.atZoneSameInstant(ZoneId.of("Portugal")));
        System.out.println(zdtchile.withZoneSameInstant(ZoneId.of("Portugal")));
        System.out.println(odtChile.atZoneSameInstant(ZoneId.of("Asia/Macau")));
        System.out.println(zdtchile.withZoneSameInstant(ZoneId.of("Asia/Macau")));
    }

    public static void ex16(){
        int precoGym = 5;
        LocalDate diaGym = LocalDate.now();
        LocalDate fimGym = diaGym.plusYears(1);

        while (diaGym.isBefore(fimGym)) {
            if (diaGym.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                precoGym += 5;
                diaGym = diaGym.plusWeeks(1);
            } else {
                diaGym = diaGym.plusDays(1);
            }
        }
        System.out.println(precoGym);
    }

}
