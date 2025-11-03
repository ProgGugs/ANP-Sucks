import java.util.Date;

public class Pedido {
    private String descricao;
    private Date dataPedido;
    private String universidade;
    private Solicitante solicitante;
    private boolean aprovado;

    public Pedido(String descricao, String universidade, Solicitante solicitante) {
        this.descricao = descricao;
        this.universidade = universidade;
        this.solicitante = solicitante;
        this.dataPedido = new Date(); 
        this.aprovado = false;       
    }

    // Aprovar pedido
    public void aprovar() {
        this.aprovado = true;
        System.out.println("Pedido aprovado!");
    }


    public String getStatus() {
        return aprovado ? "Aprovado" : "Pendente";
    }
    
    public String getDescricao() { return descricao; }
    public Date getDataPedido() { return dataPedido; }
    public String getUniversidade() { return universidade; }
    public Solicitante getSolicitante() { return solicitante; }

    public boolean isAprovado() { return aprovado; }
}
