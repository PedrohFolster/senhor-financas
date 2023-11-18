package model.vo;

public class PessoaVO {

    private int idPessoa;
    private String nome;
    private String cpf;
    private int idade;

    public PessoaVO(int idPessoa, String nome, String cpf, int idade) {
        super();
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    public PessoaVO() {
        super();
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Código da Pessoa:" + idPessoa +
                "\n Nome: " + nome +
                "\n CPF: " + cpf +
                "\n Idade:" + idade;
    }
}
