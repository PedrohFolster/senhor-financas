package executavel;

import model.bo.UsuarioBO;
import model.vo.UsuarioVO;

import java.util.ArrayList;

public class Executavel {

    public static void main(String[] args) {

        UsuarioBO usuarioBO = new UsuarioBO();

        // Cadastrar Usuario - (cu)

        // Cadastro OK
        UsuarioVO cu1 = new UsuarioVO(0, "Adriano", "01234567899", "a@gmail.com", "10-02-2023", "a", "a");
        cu1 = usuarioBO.cadastrarUsuarioBO(cu1);
        System.out.println(cu1.getIdUsuario() > 0 ? "Usuario cadastrado com sucesso!!!" : "Houve um problema no cadastro!!!");

        // Cadastro OK
        UsuarioVO cu2 = new UsuarioVO(0, "Maria", "01234567898", "a@gmail.com", "10-02-2023", "a", "a");
        cu2 = usuarioBO.cadastrarUsuarioBO(cu2);
        System.out.println(cu2.getIdUsuario() > 0 ? "Usuario cadastrado com sucesso!!!" : "Houve um problema no cadastro!!!");

        // Cadastro OK
        UsuarioVO cu3 = new UsuarioVO(0, "Luciano", "01234567897", "a@gmail.com", "10-02-2023", "a", "a");
        cu3 = usuarioBO.cadastrarUsuarioBO(cu3);
        System.out.println(cu3.getIdUsuario() > 0 ? "Usuario cadastrado com sucesso!!!" : "Houve um problema no cadastro!!!");

        // Menor de Idade
        UsuarioVO cu4 = new UsuarioVO(0, "Jose", "01234567896", "a@gmail.com", "10-02-2023", "a", "a");
        cu4 = usuarioBO.cadastrarUsuarioBO(cu4);
        System.out.println(cu4.getIdUsuario() > 0 ? "Usuario cadastrado com sucesso!!!" : "Houve um problema no cadastro!!!");

        // CPF nulo (validado pelo banco de dados)
        UsuarioVO cu5 = new UsuarioVO(0, "João", null, "a@gmail.com", "10-02-2023", "a", "a");
        cu5 = usuarioBO.cadastrarUsuarioBO(cu5);
        System.out.println(cu5.getIdUsuario() > 0 ? "Usuario cadastrado com sucesso!!!" : "Houve um problema no cadastro!!!");

        // Tentando cadastrar um usuario já cadastrado
        cu1 = usuarioBO.cadastrarUsuarioBO(cu1);
        System.out.println(cu1.getIdUsuario() > 0 ? "Usuario cadastrado com sucesso!!!" : "Houve um problema no cadastro!!!");

        System.out.println("\n---------------------------------------\n");

        // Atualizar Usuario (au)

        // Atualização OK
        UsuarioVO au1 = new UsuarioVO(1, "", "", "a@gmail.com", "10-02-2023", "a", "a");
        au1 = usuarioBO.consultarUsuarioBO(au1);
        au1.setNome(au1.getNome() + "a");
        boolean resultado = usuarioBO.atualizarUsuarioBO(au1);
        System.out.println(resultado ? "Usuario Atualizado com Sucesso!!!" : "Houve um problema na Atualização!!!");

        // Tentando atualizar um usuario inexistente
        UsuarioVO au2 = new UsuarioVO(100, "", "", "a@gmail.com", "10-02-2023", "a", "a");
        au2 = usuarioBO.consultarUsuarioBO(au2);
        au2.setNome(au2.getNome() + "a");
        resultado = usuarioBO.atualizarUsuarioBO(au2);
        System.out.println(resultado ? "Usuario Atualizado com Sucesso!!!" : "Houve um problema na Atualização!!!");

        // Tentando atualizar o cpf de um usuario para null
        UsuarioVO au3 = new UsuarioVO(2, "", "", "a@gmail.com", "10-02-2023", "a", "a");
        au3 = usuarioBO.consultarUsuarioBO(au3);
        au3.setCpf(null);
        resultado = usuarioBO.atualizarUsuarioBO(au3);
        System.out.println(resultado ? "Usuario Atualizado com Sucesso!!!" : "Houve um problema na Atualização!!!");

        System.out.println("\n---------------------------------------\n");

        // Excluir um Usuario - (eu)

        // Exclusão OK
        UsuarioVO eu1 = new UsuarioVO(2, "", "", "a@gmail.com", "10-02-2023", "a", "a");
        eu1 = usuarioBO.consultarUsuarioBO(eu1);
        resultado = usuarioBO.excluirUsuarioBO(eu1);
        System.out.println(resultado ? "Usuario excluído com sucesso!!!" : "Houve um problema na Exclusão!!!");

        // Tentando excluir um usuario inexistente
        UsuarioVO eu2 = new UsuarioVO(100, "", "", "a@gmail.com", "10-02-2023", "a", "a");
        eu2 = usuarioBO.consultarUsuarioBO(eu2);
        resultado = usuarioBO.excluirUsuarioBO(eu2);
        System.out.println(resultado ? "Usuario excluído com sucesso!!!" : "Houve um problema na Exclusão!!!");

        System.out.println("\n---------------------------------------\n");

        // Consultar um Usuario ou Todos - (u)

        // Consultar um usuario OK
        UsuarioVO u1 = new UsuarioVO(3, "", "", "a@gmail.com", "10-02-2023", "a", "a");
        u1 = usuarioBO.consultarUsuarioBO(u1);
        if (u1.getCpf() != null) {
            System.out.println(u1);
        } else {
            System.out.println("\nUsuario não localizado na base de dados!!!");
        }

        // Tentando consultar um usuario inexistente
        UsuarioVO u2 = new UsuarioVO(100, "", null, "a@gmail.com", "10-02-2023", "a", "a");
        u2 = usuarioBO.consultarUsuarioBO(u2);
        if (u2.getCpf() != null) {
            System.out.println(u2);
        } else {
            System.out.println("\nUsuario não localizado na base de dados!!!");
        }

        // Consultando Todos os usuarios
        ArrayList<UsuarioVO> lista = usuarioBO.consultarTodasUsuariosBO();
        if (lista.isEmpty()) {
            System.out.println("Não existem usuarios cadastrados na base de dados!!!");
        } else {
            System.out.println("\nLista de Usuarios: \n");
            for (UsuarioVO u : lista) {
                System.out.println(u + "\n");
            }
        }
    }
}
