public class Usuario {
    private int id;
    private String nome;
    public String cpfCnpj;
    private String email;
    private String endereco;
    private String senha;
    private boolean flagReitor;
    /*o id sera instanciado pelo construtor na hora do cadastro 
    ou faz automaticamente no banco de dados?
     */
    public Usuario(int id ,String nome, String cpfCnpj, String email, String endereco, String senha, boolean reitor) {
        this.id = id;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.endereco = endereco;
        this.senha = senha;
        this.flagReitor = reitor;
    }

    public boolean login(String emailDigitado, String senhaDigitada) {
        return this.email.equals(emailDigitado) && this.senha.equals(senhaDigitada);
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpfCnpj; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco;}
    public String getSenha() { return senha; }
    public boolean isFlagReitor() { return flagReitor; }
    public int getId(){ return id; }
}
