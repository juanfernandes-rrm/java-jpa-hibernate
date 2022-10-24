package br.com.juan.loja.dao;

import br.com.juan.loja.modelo.Categoria;
import br.com.juan.loja.modelo.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDAO {
    private EntityManager em;

    public ProdutoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto){
        this.em.persist(produto);
    }

    public void atualizar(Produto produto){
        this.em.merge(produto);
    }

    public void remover(Produto produto){
        produto = em.merge(produto);
        this.em.remove(produto);
    }

    public Produto buscarPorId(Long id){
        return em.find(Produto.class,id);
    }

    public List<Produto> buscarTodos(){
        String jpql = "SELECT p FROM Produto p"; //Produto = nome da Entidade
        return em.createQuery(jpql,Produto.class).getResultList();//Java Persistence Query Language
    }

    public List<Produto> buscarTodosPorNome(String nome){
        String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome"; //p.nome = atributo da entidade
        return em.createQuery(jpql,Produto.class)
                .setParameter("nome", nome) //passando o parametro da query
                .getResultList();//Java Persistence Query Language
    }

    public List<Produto> buscarTodosPorNomeDaCategoria(String nome){
        String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome"; //p.nome.categoria = atributo do relacionamento
        return em.createQuery(jpql,Produto.class)
                .setParameter("nome", nome) //passando o parametro da query
                .getResultList();//Java Persistence Query Language
    }

    public BigDecimal buscarPrecoDoProdutoComNome(String nome){
        String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
        return em.createQuery(jpql,BigDecimal.class)
                .setParameter("nome", nome) //passando o parametro da query
                .getSingleResult();//Java Persistence Query Language
    }
}
