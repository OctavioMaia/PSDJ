package Aula8;

/**
 *
 * @author asus
 */

public class Aluno {
    private String codigo;
    private String nome;
    private int idade;
    private String curso;
    private double media;
    
    public Aluno() {}
    public Aluno(String cod, String nm, int anos, String crs, double md) {
        codigo = cod; nome = nm; idade = anos; curso = crs; media = md;
    }
    
    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getCurso() { return curso; }
    public double getMedia() { return media; }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Aluno( ");
        s.append(codigo); s.append(", "); s.append(nome);
        s.append(", "); s.append(idade);
        s.append(", "); s.append(curso);
        s.append(", "); s.append(media); s.append(" )");
        return s.toString();
    }
}
