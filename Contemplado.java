import java.util.Date;
/*
 * talvez precise mudar a lógica da classe contemplado
 * usando herança
 */
public class Contemplado {
    private Solicitante solicitante; // referencia o solicitante 
    private Date dataContemplacao;

    public Contemplado(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public void acessarDados() {
        // a fazer
    }

    public void filtrarDados() {
        // a fazer
    }

    public void exportarDados() {
        // a fazer
    }

    public String getNome() {
        return solicitante.getNome();
    }

    public String getEmail() {
        return solicitante.getEmail();
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }
}
