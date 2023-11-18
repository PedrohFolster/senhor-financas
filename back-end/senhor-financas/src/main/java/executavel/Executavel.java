package executavel;

import model.bo.PessoaBO;
import model.vo.PessoaVO;

public class Executavel {

    public static void main(String[] args) {

        PessoaBO pessoaBO = new PessoaBO();

        // Cadastrar Pessoa - (cp)

        // Cadastro OK
        PessoaVO cp1 = new PessoaVO(0, "Adriano", "01234567899", 45);
        cp1 = pessoaBO.cadastrarPessoaBO(cp1);
        System.out.println(cp1.getIdPessoa() > 0 ? "Pessoa cadastrada com sucesso!!!" : "Houve um problema no cadastro!!!");

        // Cadastro OK
        PessoaVO cp2 = new PessoaVO(0, "Maria", "01234567898", 20);
        cp2 = pessoaBO.cadastrarPessoaBO(cp2);
        System.out.println(cp2.getIdPessoa() > 0 ? "Pessoa cadastrada com sucesso!!!" : "Houve um problema no cadastro!!!");

        // Cadastro OK
        PessoaVO cp3 = new PessoaVO(0, "Luciano", "01234567897", 40);
        cp3 = pessoaBO.cadastrarPessoaBO(cp3);
        System.out.println(cp3.getIdPessoa() > 0 ? "Pessoa cadastrada com sucesso!!!" : "Houve um problema no cadastro!!!");

        // Menor de Idade
        PessoaVO cp4 = new PessoaVO(0, "Jose", "01234567896", 15);
        cp4 = pessoaBO.cadastrarPessoaBO(cp4);
        System.out.println(cp4.getIdPessoa() > 0 ? "Pessoa cadastrada com sucesso!!!" : "Houve um problema no cadastro!!!");

        // CPF nulo (validado pelo banco de dados)
        PessoaVO cp5 = new PessoaVO(0, "João", null, 25);
        cp5 = pessoaBO.cadastrarPessoaBO(cp5);
        System.out.println(cp5.getIdPessoa() > 0 ? "Pessoa cadastrada com sucesso!!!" : "Houve um problema no cadastro!!!");

        // Tentando cadastrar uma pessoa já cadastrada
        cp1 = pessoaBO.cadastrarPessoaBO(cp1);
        System.out.println(cp1.getIdPessoa() > 0 ? "Pessoa cadastrada com sucesso!!!" : "Houve um problema no cadastro!!!");

        System.out.println("\n---------------------------------------\n");

        // Atualizar Pessoa (ap)

        // Atualização OK
        PessoaVO ap1 = new PessoaVO(1, "", "", 0);
        ap1 = pessoaBO.consultarPessoaBO(ap1);
        ap1.setIdade(ap1.getIdade() + 1);
        boolean resultado = pessoaBO.atualizarPessoaBO(ap1);
        System.out.println(resultado ? "Pessoa Atualizada com Sucesso!!!" : "Houve um problema na Atualização!!!");

        // Tentando atualizar uma pessoa inexistente
        PessoaVO ap2 = new PessoaVO(100, "", "", 0);
        ap2 = pessoaBO.consultarPessoaBO(ap2);
        ap2.setIdade(ap2.getIdade() + 1);
        resultado = pessoaBO.atualizarPessoaBO(ap2);
        System.out.println(resultado ? "Pessoa Atualizada com Sucesso!!!" : "Houve um problema na Atualização!!!");

        // Tentando atualizar o cpf de uma pessoa para null
        PessoaVO ap3 = new PessoaVO(2, "", "", 0);
        ap3 = pessoaBO.consultarPessoaBO(ap3);
        ap3.setCpf(null);
        resultado = pessoaBO.atualizarPessoaBO(ap3);
        System.out.println(resultado ? "Pessoa Atualizada com Sucesso!!!" : "Houve um problema na Atualização!!!");

        // Tentando transformar uma pessoa em menor de idade
        PessoaVO ap4 = new PessoaVO(2, "", "", 0);
        ap4 = pessoaBO.consultarPessoaBO(ap4);
        ap4.setIdade(10);
        resultado = pessoaBO.atualizarPessoaBO(ap4);
        System.out.println(resultado ? "Pessoa Atualizada com Sucesso!!!" : "Houve um problema na Atualização!!!");

        System.out.println("\n---------------------------------------\n");
    }
}
