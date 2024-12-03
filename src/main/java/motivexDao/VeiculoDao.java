package motivexDao;

import jakarta.persistence.*;
import java.time.Year;
import java.util.List;
import motivex.entities.Veiculo;
import util.HibernateUtil;

/**
 *
 * @author matheus-leal
 */
public class VeiculoDao {
    public void cadastrarVeiculo(Veiculo v){
          EntityManager em = HibernateUtil.getEntityManager();
          
          try{
              em.getTransaction().begin();
              em.persist(v);
              em.getTransaction().begin();
          } catch (Exception e){
              em.getTransaction().rollback();
              throw e;
          } finally {
            HibernateUtil.closeEntityManager();
        }
    }
    public List<Veiculo> listar() {
         EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Veiculo v ORDER BY v.status", Veiculo.class).getResultList();
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
    
    public List<Veiculo> listarComParametros(String modelo, String placa, String status, Year ano) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            // Construindo a consulta dinâmica
            StringBuilder queryString = new StringBuilder("SELECT v FROM Veiculo v WHERE 1=1");

            // Adicionando os filtros de acordo com os parâmetros recebidos
            if (modelo != null && !modelo.isEmpty()) {
                queryString.append(" AND v.modelo LIKE :modelo");
            }
            if (placa != null && !placa.isEmpty()) {
                queryString.append(" AND v.placa LIKE :placa");
            }
            if (status != null && !status.isEmpty()) {
                queryString.append(" AND v.status = :status");
            }
            if (ano != null) {
                queryString.append(" AND v.ano = :ano");
            }

            // Criando a query
            TypedQuery<Veiculo> query = em.createQuery(queryString.toString(), Veiculo.class);

            // Definindo os parâmetros
            if (modelo != null && !modelo.isEmpty()) {
                query.setParameter("modelo", "%" + modelo + "%");
            }
            if (placa != null && !placa.isEmpty()) {
                query.setParameter("placa", "%" + placa + "%");
            }
            if (status != null && !status.isEmpty()) {
                query.setParameter("status", status);
            }
            if (ano != null) {
                query.setParameter("ano", ano);
            }

            // Retornando os resultados da consulta
            return query.getResultList();

        } catch (RuntimeException e) {
            throw e; // Propaga a exceção para ser tratada em outro ponto, se necessário
        } finally{
            HibernateUtil.closeEntityManager();
        }
    }
    
    public void excluirVeiculo(int id){
          EntityManager em = HibernateUtil.getEntityManager();
          try{
              Veiculo v = em.find(Veiculo.class, id);
              if(v != null){
                  em.getTransaction().begin();
                  em.remove(v);
                  em.getTransaction().commit();
              }
          } catch(Exception e){
                em.getTransaction().rollback();
                 throw e;
             } finally {
              HibernateUtil.closeEntityManager();
          }
    }
    
    public Veiculo obter(int id){
        EntityManager em = HibernateUtil.getEntityManager();
        try{
            return em.find(Veiculo.class, id);
        } finally{
            HibernateUtil.closeEntityManager();
        }
    }
    
    public void atualizar(Veiculo v){
          EntityManager em = HibernateUtil.getEntityManager();
          try{
              em.getTransaction().begin();
              em.merge(v);
              
          }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
   
}
