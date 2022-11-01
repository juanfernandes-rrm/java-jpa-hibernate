package br.com.juan.loja.dao;

import br.com.juan.loja.modelo.Pedido;
import br.com.juan.loja.modelo.Produto;
import br.com.juan.loja.vo.RelatorioDeVendasVO;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO {
    private EntityManager em;

    public PedidoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido){
        this.em.persist(pedido);
    }

    public BigDecimal valorTotalVendido(){
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return em.createQuery(jpql,BigDecimal.class).getSingleResult();
    }
    
    public List<RelatorioDeVendasVO> relatorioDeVendas(){
        String jpql = "SELECT new br.com.juan.loja.vo.RelatorioDeVendasVO( " + //não é um entidade
                "produto.nome," +
                "SUM(item.quantidade)," +
                "MAX(pedido.data))" +
                "FROM Pedido pedido " +
                "JOIN pedido.itens item " +
                "JOIN item.produto produto " +
                "GROUP BY produto.nome " +
                "ORDER BY item.quantidade DESC";
        return em.createQuery(jpql,RelatorioDeVendasVO.class).getResultList();
    }

}
