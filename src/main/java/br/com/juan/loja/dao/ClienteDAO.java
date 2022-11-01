package br.com.juan.loja.dao;

import br.com.juan.loja.modelo.Cliente;
import br.com.juan.loja.modelo.Pedido;
import br.com.juan.loja.modelo.Produto;

import javax.persistence.EntityManager;

public class ClienteDAO {
    private EntityManager em;

    public ClienteDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Cliente cliente){
        this.em.persist(cliente);
    }

    public Cliente buscarPorId(Long id){
        return em.find(Cliente.class,id);
    }
}
