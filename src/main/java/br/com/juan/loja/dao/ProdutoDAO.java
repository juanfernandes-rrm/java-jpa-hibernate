package br.com.juan.loja.dao;

import br.com.juan.loja.modelo.Categoria;
import br.com.juan.loja.modelo.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
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
        return em.createNamedQuery("Produto.produtosPorCategoria",Produto.class)
                .setParameter("nome", nome) //passando o parametro da query
                .getResultList();//Java Persistence Query Language
    }

    public BigDecimal buscarPrecoDoProdutoComNome(String nome){
        String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
        return em.createQuery(jpql,BigDecimal.class)
                .setParameter("nome", nome) //passando o parametro da query
                .getSingleResult();//Java Persistence Query Language
    }

    public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro){
        String jpql = "SELECT p FROM Produto.p WHERE 1=1";
        if(nome != null && nome.trim().isEmpty()){
            jpql += "AND p.nome = :nome";
        }
        if(preco != null){
            jpql += "AND p.preco = :preco";
        }
        if(dataCadastro != null){
            jpql += "AND p.dataCadastro = :dataCadastro";
        }

        TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
        if(nome != null && nome.trim().isEmpty()){
            query.setParameter("nome",nome);
        }
        if(preco != null){
            query.setParameter("preco",preco);
        }
        if(dataCadastro != null){
            query.setParameter("dataCadastro",dataCadastro);
        }
        return query.getResultList();
    }

    public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);
        //query.select(Produto.class); se o select for diferente do from, precisa setar

        Predicate filtros = builder.and(); //contatena os ands
        if(nome != null){
            filtros = builder.and(filtros, builder.equal(from.get("nome"),nome));
        }
        if(preco != null){
            filtros = builder.and(filtros, builder.equal(from.get("preco"),preco));
        }
        if(dataCadastro != null){
            filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"),dataCadastro));
        }
        query.where(filtros);
        return em.createQuery(query).getResultList();
    }
}
