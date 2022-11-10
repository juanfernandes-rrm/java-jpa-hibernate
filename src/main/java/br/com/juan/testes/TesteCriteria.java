package br.com.juan.testes;

import br.com.juan.loja.dao.CategoriaDAO;
import br.com.juan.loja.dao.ClienteDAO;
import br.com.juan.loja.dao.ProdutoDAO;
import br.com.juan.loja.modelo.Categoria;
import br.com.juan.loja.modelo.Cliente;
import br.com.juan.loja.modelo.Produto;
import br.com.juan.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TesteCriteria {
    public static void main(String[] args) {
        popularBancoDeDados();
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        produtoDAO.buscarPorParametrosComCriteria("Xiaomi Redmi", null, LocalDate.now());
    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("Xiaomi Redmi","Celular bom", new BigDecimal("800"), celulares);
        Cliente cliente = new Cliente("Juan","12345678");

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);

        em.getTransaction().begin();
        categoriaDAO.cadastrar(celulares);
        produtoDAO.cadastrar(celular);
        clienteDAO.cadastrar(cliente);
        em.getTransaction().commit();
        em.close();
    }
}
