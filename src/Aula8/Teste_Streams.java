package Aula8;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.Collection.*;
import java.util.function.DoubleBinaryOperator;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summarizingDouble;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.counting;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.naturalOrder;
import static java.util.Arrays.stream;
import java.util.concurrent.ForkJoinPool;
import static java.lang.Math.random;
import static java.lang.Math.abs;
import java.util.Collections;
import static java.lang.System.out;
import static java.util.Arrays.asList;


public class Teste_Streams {

    public static List<String> cursosExistentes(List<Aluno> alunos){
        return alunos.stream().map(Aluno::getCurso).distinct().collect(toList());
    }

    public static double mediaLEI(List<Aluno> alunos){
        return alunos.stream().filter(aluno -> aluno.getCurso().equals("LEI")).collect(averagingDouble(Aluno::getMedia));
    }

    public static Optional<Aluno> melhorAlunoLCC(List<Aluno> alunos){
        return alunos.stream().filter(aluno -> aluno.getCurso().equals("LCC")).max(Comparator.comparing(Aluno::getMedia));
    }

    public static DoubleSummaryStatistics estatisticaLEI(List<Aluno> alunos){
        return alunos.stream().filter(aluno -> aluno.getCurso().equals("LEI")).collect(summarizingDouble(Aluno::getMedia));
    }

    public static Set<String> nomesAlunos(List<Aluno> alunos){
        return alunos.stream().filter(aluno -> aluno.getMedia() > 12).map(Aluno::getNome).collect(toCollection(TreeSet::new));
    }

    public static List<Aluno> melhoresAlunosLEI(List<Aluno> alunos, int n){
        return alunos.stream().sorted(Comparator.comparing(Aluno::getMedia).reversed()).limit(n).collect(toList());
    }

    public static Map<String,Optional<Aluno>> melhorAlunoCadaCurso(List<Aluno> alunos){
        return alunos.stream().collect(groupingBy(Aluno::getCurso,maxBy(comparing(Aluno::getMedia))));
    }

    public static void main(String[] args) {

        List<Aluno> turma = Arrays.asList(
                new Aluno("10", "Rui", 21, "LEI", 12.75),
                new Aluno("2", "Ana", 21, "LM", 14.75),
                new Aluno("12", "Pedro", 22, "LEI", 14.5),
                new Aluno("20", "Rita", 23, "LM", 14.45),
                new Aluno("29", "Luis", 21, "LEI", 13.75),
                new Aluno("27", "Artur", 24, "LEE", 12.5),
                new Aluno("77", "Luisa", 25, "LEC", 13.9),
                new Aluno("18", "Laura", 24, "LEC", 11.5)
        );

        // turma.forEach(out::println);
        out.println("# Alunos " + turma.size());
        //
        List<Integer> idades = new ArrayList<>();
        for(Aluno al : turma) idades.add(al.getIdade());
        List<Double> medias = new ArrayList<>();
        for(Aluno al : turma) medias.add(al.getMedia());

        // Nº de Idades e médias
        out.println("# Idades " + idades.stream().count());
        out.println("# Medias " + medias.stream().count());

        //cursos existentes
        out.println(cursosExistentes(turma));

        //mediaLEI
        out.println(mediaLEI(turma));

        //melhorLCC
        out.println(melhorAlunoLCC(turma));

        //estatisticaLEI
        out.println(estatisticaLEI(turma));

        //nomesAlunos
        out.println(nomesAlunos(turma));

        //2 melhores alunos LEI
        out.println(melhoresAlunosLEI(turma,2));

        //melhor aluno cada curso
        out.println(melhorAlunoCadaCurso(turma));
           
           /*
           IntSummaryStatistics idadeStats =
    		turma.stream()
                     .collect(summarizingInt(Aluno::getIdade));
           //out.println(idadeStats);
            
           Set<String> nomesLei = turma.stream()
                        .filter(a -> a.getCurso().equals("LEI"))
		        .map(a -> a.getNome())
                        .collect(toSet());
           //out.println("Nomes LEI: " + nomesLei);
           
           
           Double mediaIdades = turma.stream()
                          .collect(averagingInt(Aluno::getIdade));
           //out.println("Media Idades: " + mediaIdades);
           */
           /*
           turma.stream().sorted(comparing(Aluno::getNome)).map(al -> al.getNome()).forEach(nm -> out.print(nm + "/"));
           out.println();
           turma.stream().sorted(comparing(Aluno::getMedia).reversed())
                .map(al -> al.getNome())
	        .forEach(nm -> out.print(nm + "<-"));
           out.println();
           
           turma.stream().sorted(comparing(Aluno::getIdade))
                .map(al -> al.getNome())
	        .forEach(nm -> out.print(nm + "->"));
            
           Function<Aluno, ParDeStrings> toParStrings = a -> ParDeStrings.of(a.getNome(), "" + a.getMedia());
            
            out.println();
            turma.stream()
	     .map(aluno -> toParStrings.apply(aluno))
             .sorted(comparing(ParDeStrings::getPrim))
             .forEach(out::println);
            
            out.println();
            // Os 2 melhores alunos
            turma.stream() 
                .sorted(comparing(Aluno::getMedia).reversed()) 
                .limit(2)
                 .forEach(System.out::println);
            
            turma.stream()
                 .sorted(comparing(Aluno::getIdade).thenComparing(comparing(Aluno::getNome)))
                 .limit(5)
              .forEach(out::println);
            
            turma.stream()
                 .sorted(comparing(Aluno::getNome, (n1, n2) -> - (n1.length() - n2.length())))
                 .limit(5)
                 .forEach(out::println);
           //---------------------------------------------------------
            Function<Double, Predicate<Double>> maiorQX = x -> (d -> d > x);
            double mediaX = 13.0;
            Predicate<Double> predMedMQx = maiorQX.apply(mediaX);
            Predicate<Aluno> alunoDeLei = al -> al.getCurso().equals("LEI");
            Predicate<Aluno> mediaMaiorX = al -> predMedMQx.test(al.getMedia());

            List<String> cods = 
                turma.stream()
                     .filter(alunoDeLei.and(mediaMaiorX))
                     .map(Aluno::getCodigo)
                     .sorted()
                     .collect(toList());
            //out.println("Media > " + mediaX + " : Alunos " + cods);
            //---------------------------------------------------------
            Set<String> cods1 = 
                  turma.stream()
                       .filter(alunoDeLei.and(mediaMaiorX))
                       .map(Aluno::getCodigo)
                       .collect(toCollection(TreeSet::new));
            //out.println("Media > " + mediaX + " : Alunos " + cods1);           
            //------------------------------------------------------------
            Map<String, List<Aluno>> alunosPorCurso = 
    	    turma.stream()
    	        .collect(groupingBy(Aluno::getCurso));
            //out.println(alunosPorCurso);
            //---------------------------------------------------------
            /*
            Map<String, Set<String>> nomesOrdPorCurso = 
            turma.stream()
                  .collect(groupingBy(Aluno::getCurso, TreeMap::new, 
                                      mapping(Aluno::getNome, toCollection(TreeSet::new))));
            //out.println(nomesOrdPorCurso);
            //---------------------------------------------------------
            */
            /*
             Map<String, Map<Integer, Set<String>>> tabCursoCodsPorIdade = 
                     turma.stream()
                          .collect(groupingBy(Aluno::getCurso, 
                                              groupingBy(Aluno::getIdade, mapping(Aluno::getCodigo, toCollection(TreeSet::new)))));
             //out.println(tabCursoCodsPorIdade);
             //---------------------------------------------------------
             Predicate<Aluno> alunoMenos23 = al -> al.getIdade() <= 22;
             
             Map<Boolean, List<Aluno>> menosEmaisDe23 = 
                     turma.stream()
                          .collect(partitioningBy(alunoMenos23));
           */
        //out.println(menosEmaisDe23.get(true));
        //out.println(menosEmaisDe23.get(false));
        //out.println(menosEmaisDe23.get(true).stream().collect(averagingDouble(Aluno::getMedia)));
        //out.println("Media dos menores: " +
        //menosEmaisDe23.get(true).stream()
        // .collect(averagingDouble(Aluno::getMedia)));
        //out.println("Nomes dos maiores: " +
        //menosEmaisDe23.get(false).stream()
        //   .map(Aluno::getNome)
        //  .collect(toSet()));
             /*
             Map<Boolean, Double> mediasMenosEmaisDe23 = 
                        turma.stream()
                             .collect(partitioningBy(alunoMenos23, averagingDouble(Aluno::getMedia)));
             //out.println(mediasMenosEmaisDe23);
             //---------------------------------------------------------
             Map<Boolean, Long> contaMenosEmaisDe23 = 
                        turma.stream()
                             .collect(partitioningBy(alunoMenos23, counting()));
             //out.println(contaMenosEmaisDe23);
             //---------------------------------------------------------
             Map<Boolean, String> linhasMenosEmaisDe23 = 
                        turma.stream()
                             .collect(partitioningBy(alunoMenos23, mapping(Aluno::getNome, joining("/"))));
             //out.println(linhasMenosEmaisDe23);
           //---------------------------------------------------------
             */
             /*
             Function<Double, Predicate<Double>> maiorQX = x -> (d -> d > x);
             double mediaX = 13.0;
             Predicate<Double> predMedMQx = maiorQX.apply(mediaX);
             Predicate<Aluno> mediaMaiorX = al -> predMedMQx.test(al.getMedia());
            */
            /*
             Double mediaTeste = 16.0;
             Function<Double, Predicate<Aluno>> mediaMaiorQueX = media -> al -> al.getMedia() > media;       
             boolean mediaMaiorQValor = 
                    turma.stream()
                         .anyMatch(mediaMaiorQueX.apply(mediaTeste));
             */
        //out.println("Media > " + mediaTeste + " " + mediaMaiorQValor);

        //---------------------------------------------------------
           /*
             Comparator<Aluno> porMedia = (a1,a2)  -> (int) a1.getMedia() - (int) a2.getMedia();

             Optional<Aluno> melhorAlunoLEI = 
                    turma.stream()
                         .filter(alunoDeLei)
                         .max(porMedia);
             Aluno talvezOmelhor = melhorAlunoLEI.orElseGet(Aluno::new);
             //out.println("Aluno com melhor Média em LEI " + talvezOmelhor);
             
             
             //out.println("Média = " + talvezOmelhor.getMedia());
             
             boolean existeNome = 
                    turma.stream()
                         .noneMatch(al -> al.getNome().equals("John"));
             //out.println(existeNome);
            //---------------------------------------------------------
             */
             /*
             Comparator<Aluno> porMediaDec = (a1,a2)  -> - ((int) a1.getMedia() - (int) a2.getMedia());
                     turma.stream()
                          .sorted(porMediaDec)
                          .limit(2)
                          .forEach(out::println);
             */
        //---------------------------------------------------------
             /*
             Map<String, Double> tabCodMediaLEI =
                     turma.stream()
                          .filter(alunoDeLei)
                          .collect(toMap(Aluno::getCodigo, Aluno::getMedia));
             //tabCodMediaLEI.forEach( (ca, md) -> out.println("Aluno: " + ca + " Média: " + md));
             //---------------------------------------------------------
             BinaryOperator<String> juntaNomes = (s1,s2) -> s1+ "|" + s2;
             Map<String, String> tabCursoStrNomes =
                     turma.stream()
                          .collect(toMap(Aluno::getCurso, Aluno::getNome, juntaNomes));
             //out.println(tabCursoStrNomes);
             //---------------------------------------------------------
             BinaryOperator<Aluno> alComMelhorMedia = 
                    (a1,a2) -> a1.getMedia() >= a2.getMedia() ? a1 : a2;
             Map<String,Aluno> tabCursoMelhorAluno =
                    turma.stream()
                         .collect(toMap(Aluno::getCurso, Function.identity(), alComMelhorMedia));
            //out.println(tabCursoMelhorAluno);
           //---------------------------------------------------------
            /*
            IntStream.of(4,6,2)
	             .flatMap(i -> IntStream.range(0,i))
                     .distinct()
	             .forEach(i -> out.print(i + " "));
            */
            /*
            ToDoubleFunction<Double> identDouble = d -> d;
            Stream<List<Double>> listasNotas = 
                 Stream.of(Arrays.asList(12.3, 14.5, 13.1),
                           Arrays.asList(15.2, 11.8),
	                   Arrays.asList(14.0, 13.5, 16.7, 14.8));
            //out.println(listasNotas.flatMap(Collection::stream).collect(averagingDouble(identDouble)));
            */ 
             /*
           //---------------------------------------------------------
             Map<String, Double> tabMelhores2Curso =
                     turma.stream()
                          .collect(toMap(Aluno::getCurso), )
                          .sorted(porMediaDec)
                          .limit(2)
                          .collect(toList());
             out.println(melhores2);
           //---------------------------------------------------------
             */
            /*
            List<String> linhas = 
                    Arrays.asList("Streams de Java8 em geral",
                                  "flatMap e streams de Java8",
                                  "flatMap operação complexa?",
                                  "funcionamento efectivo de flatMap");

            List<String> palavras = 
                linhas.stream()
                      .flatMap(linha -> Stream.of(linha.split("\\s* \\s*")))
                      .distinct()
                      .sorted()
                      .collect(toList());
            
            //out.println("Palavras distintas: " + palavras);
           //---------------------------------------------------------
             */
        //---------------------------------------------------------
            /*
                linhas.stream()
                      .flatMap(linha -> Stream.of(linha.split("\\s* \\s*")))
                      .flatMap(palavra -> Stream.of(palavra.chars()))
                      .flatMapToInt(Function.identity())
                      .mapToObj(i -> (char) i)
                      .forEach(s -> out.print(s + " "));
            */
            /*
           //---------------------------------------------------------
            Function<String, Stream<String>> linhaToPalavras = l -> Stream.of(l.split("\\s* \\s*"));
            Function<String, Stream<Character>> strToChar = s -> s.chars().mapToObj(i-> (char) i);
            linhas.stream()
                      .flatMap(linha -> linhaToPalavras.apply(linha))
                      .flatMap(palavra -> strToChar.apply(palavra))
		      .forEach(s -> out.print(s));
            */
            /*
           //---------------------------------------------------------
            // Nome e Curso em String do aluno com melhor média 
            String infoMelhor = 
                    turma.stream()
                         .collect(collectingAndThen(maxBy(comparing(Aluno::getMedia)),
                                         (Optional<Aluno> tvAluno) -> tvAluno.isPresent() ? tvAluno.get().getNome() + "/" + tvAluno.get().getMedia() : "Ups!"));
                              
             out.println(infoMelhor);
             //---------------------------------------------------------
             Function<Optional<Aluno>, String> getNomeMedAluno =
                     (Optional<Aluno> tvAluno) -> tvAluno.isPresent() ? tvAluno.get().getNome() + "/" + tvAluno.get().getMedia() : "Ups!";
             //---------------------------------------------------------
             String infoMelhor1 = 
                     turma.stream()
                          .collect(collectingAndThen(maxBy(comparing(Aluno::getMedia)),
	                                             (Optional<Aluno> al) -> getNomeMedAluno.apply(al)));      
             out.println(infoMelhor1);
           //---------------------------------------------------------
             */
            /*
            Function<Optional<Aluno>, ParDeStrings> getParStrAluno =
                     (Optional<Aluno> tvAluno) -> tvAluno.isPresent() ? 
                             ParDeStrings.of(tvAluno.get().getNome(), "" + tvAluno.get().getMedia()) : 
                             ParDeStrings.of("Ups!", "Ups!");
            //---------------------------------------------------------
            ParDeStrings infoMelhor2 = 
                     turma.stream()
                          .collect(collectingAndThen(maxBy(comparing(Aluno::getMedia)),
	                                             (Optional<Aluno> al) -> getParStrAluno.apply(al)));      
             out.println(infoMelhor2);
           //---------------------------------------------------------
           */
           /*
           Collector<Aluno, StringJoiner, String> collectNomesToStr = 
               Collector.of(
                             () -> new StringJoiner(" :: ", "Nomes: (", ")"),
                             (nomes, aluno) -> nomes.add(aluno.getNome().toUpperCase()),
                             (sj1, sj2) -> sj1.merge(sj2),
                             StringJoiner::toString
                            );
           String nomesTurma = 
                   turma.stream()
                        .collect(collectNomesToStr);
          out.println(nomesTurma);
           //---------------------------------------------------------
          */
          /*
          int soma = IntStream.of(6,5,7,1,2,3)
                               .peek(i -> out.print("i=" + i + ","))
                               .map(x -> x * 2)
                               //.peek(t -> out.print("t = " + t + ","))
                               .sum();
          out.println(" Soma = " + soma);
           
          int soma1 = IntStream.of(6,5,7,1,2,3)
                    .map(x -> x * 2)
                    .peek(t -> out.print("t=" + t + ","))
                    .sum();
          out.println(" Soma = " + soma1);
           
          int soma2 = IntStream.of(6,5,7,1,2,3)
                    .peek(i -> out.print("i=" + i + ","))
                    .map(x -> x * 2)
                    .peek(t -> out.print("t=" + t + ","))
                    .sum();
          out.println(" Soma = " + soma2);
           
          int soma3 = IntStream.of(6,5,7,1,2,3)
                    .peek(i -> out.print("i=" + i + ","))
                    .map(i -> i * 2)
                    .peek(t -> out.print("t=" + t + ","))
		.limit(3)
                    .sum();
          out.println(" Soma = " + soma3);
          //---------------------------------------------------------
          List<String> texto = asList("O", "método", "peek()", "deixa-nos", "ver", "a", "pipeline", "real");
          texto.stream()
            .peek(e -> out.println("Peek1: " + e ))
            .map(pal-> pal.length())
            .peek(e -> out.println("Peek2: " + e ))
            .filter(i -> i > 5)
            .forEach(e -> out.println("forEach: " + e));
          */
    }
}

class ParDeStrings {
    public static ParDeStrings of(String s1, String s2) {
        return new ParDeStrings(s1, s2);
    }

    private String str1;
    private String str2;

    private ParDeStrings(String s1, String s2) {
        str1 = s1; str2 = s2;
    }

    public String getPrim() { return str1; }
    public String getSeg() { return str2; }
    @Override
    public String toString() { return str1 + "$" + str2; }
}