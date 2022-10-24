package br.com.juan.testes;

import br.com.juan.loja.dao.CategoriaDAO;
import br.com.juan.loja.dao.ProdutoDAO;
import br.com.juan.loja.modelo.Categoria;
import br.com.juan.loja.modelo.Produto;
import br.com.juan.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {
    public static void main(String[] args) {
        cadastrarProduto();
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        Produto p = produtoDAO.buscarPorId(1l);
        System.out.println(p.getPreco());

        BigDecimal preco = produtoDAO.buscarPrecoDoProdutoComNome("Xiaomi Redmi");
        System.out.println(preco);
    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("Xiaomi Redmi","Celular bom", new BigDecimal("800"), celulares);

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);

        em.getTransaction().begin();
        categoriaDAO.cadastrar(celulares);
        produtoDAO.cadastrar(celular);
        em.getTransaction().commit();
        em.close();
    }
}
