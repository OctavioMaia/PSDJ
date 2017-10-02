package Aula2;

import java.time.*;

public class Time {
    public static void main(String args[]) {
        ex3();
    }

    public static void ex1(){
        LocalDate hoje = LocalDate.now();
        LocalDate dataEspecial = LocalDate.of(2016, 3, 21);

        LocalTime hora = LocalTime.now();
        LocalTime horaRef1 = LocalTime.of(16, 35, 55);
        LocalTime horaRef2 = LocalTime.of(16, 35, 55, 111111);
        LocalTime horaRef3 = LocalTime.parse("12:33");

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limite1 = LocalDateTime.of(2016, 3, 21, 17, 30);
        LocalDateTime limite2 = LocalDateTime.of(2016, 3, 21, 17, 30, 30);

        System.out.println(hoje);
        System.out.println(hora);
        System.out.println(agora);
        System.out.println(hoje.getMonth());
    }

    public static void ex3(){
        LocalDate DiaDeAnos = LocalDate.of(1995,7,19);

        //dia da seamana que faço anos
        System.out.println(DiaDeAnos.getDayOfWeek());

        //dia do ano que faço anos
        System.out.println(DiaDeAnos.getDayOfYear());

        //qts dias tem o ano desta data
        System.out.println(DiaDeAnos.lengthOfYear());

        //um mes e qts dias dps de fazer anos que data será?
        System.out.println(DiaDeAnos.plusDays(15).plusMonths(1));

        //e dez anos dps deste dia?
        System.out.println(DiaDeAnos.plusDays(15).plusMonths(1).plusYears(10));
    }
}