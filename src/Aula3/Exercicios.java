package Aula3;

import java.time.*;
import java.util.Date;

public class Exercicios {
    public static void main(String[] args){
        ex01();
        ex02();
        ex03();
    }

    public static void ex01(){
        Year ano = Year.of(2017);
        DayOfWeek diaSem = ano.atDay(2).getDayOfWeek();
        System.out.println(diaSem);
    }

    public static void ex02(){
        Year ano = Year.of(2017);
        DayOfWeek diaSemUlt = ano.atDay(ano.length()).getDayOfWeek();
        System.out.println(diaSemUlt);
    }

    public static void ex03(){
        LocalDate data = LocalDate.now();
        System.out.println("Mes: " + data.getMonth() + " Nº dias: " + data.lengthOfMonth());
    }

}
