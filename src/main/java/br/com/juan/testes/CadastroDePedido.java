package br.com.juan.testes;

import br.com.juan.loja.dao.CategoriaDAO;
import br.com.juan.loja.dao.ClienteDAO;
import br.com.juan.loja.dao.PedidoDAO;
import br.com.juan.loja.dao.ProdutoDAO;
import br.com.juan.loja.modelo.*;
import br.com.juan.loja.vo.RelatorioDeVendasVO;
import br.com.juan.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {
    public static void main(String[] args) {
        popularBancoDeDados();
        EntityManager em = JPAUtil.getEntityManager();
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        Pedido pedido = pedidoDAO.buscarPedidoComCliente(1l);
        em.close();
        System.out.println(pedido.getCliente().getDadosPessoais().getNome());

//        ProdutoDAO produtoDAO = new ProdutoDAO(em);
//        Produto produto = produtoDAO.buscarPorId(1l);
//
//        ClienteDAO clienteDAO = new ClienteDAO(em);
//        Cliente cliente = clienteDAO.buscarPorId(1l);
//
//        em.getTransaction().begin();
//        Pedido pedido = new Pedido(cliente);
//        pedido.adicionarItem(new ItemPedido(10,pedido,produto));
//        PedidoDAO pedidoDAO = new PedidoDAO(em);
//        pedidoDAO.cadastrar(pedido);
//        em.getTransaction().commit();
//
//        BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
//        System.out.println("Valor total vendido: "+totalVendido);
//
//        List<RelatorioDeVendasVO> relatorio = pedidoDAO.relatorioDeVendas();
//        relatorio.forEach(System.out::println);
        em.close();


    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("Xiaomi Redmi","Celular bom", new BigDecimal("800"), celulares);
        Cliente cliente = new Cliente("Juan","12345678");
        Pedido pedido = new Pedido(cliente);
        ItemPedido itemPedido = new ItemPedido(2, pedido,celular);
        pedido.adicionarItem(itemPedido);

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);
        PedidoDAO pedidoDAO = new PedidoDAO(em);

        em.getTransaction().begin();
        categoriaDAO.cadastrar(celulares);
        produtoDAO.cadastrar(celular);
        clienteDAO.cadastrar(cliente);
        pedidoDAO.cadastrar(pedido);
        em.getTransaction().commit();
        em.close();
    }
}
