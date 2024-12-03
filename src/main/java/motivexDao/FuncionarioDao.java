package motivexDao;

import jakarta.persistence.*;
import java.util.List;
import motivex.entities.Funcionario;
import util.HibernateUtil;

/**
 *
 * @author matheus-leal
 */
public class FuncionarioDao {
   
    public void cadastrarFuncionario(Funcionario f){
        EntityManager em = HibernateUtil.getEntityManager();
        
        try{
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
        } catch(Exception e){
            em.getTransaction().rollback();
            throw e;
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
    
     public List<Funcionario> listar() {
         EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
    
    public List<Funcionario> listarComParametros(String nome, String cargo) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            // Montando a consulta dinâmica
            StringBuilder queryString = new StringBuilder("SELECT f FROM Funcionario f WHERE 1=1");

            // Adiciona filtros se fornecidos
            if (nome != null && !nome.isEmpty()) {
                queryString.append(" AND f.nome LIKE :nome");
            }
            if (cargo != null && !cargo.isEmpty()) {
                queryString.append(" AND f.cargo LIKE :cargo");
            }
           

            // Criando a query
            TypedQuery<Funcionario> query = em.createQuery(queryString.toString(), Funcionario.class);

            // Definindo os parâmetros se forem passados
            if (nome != null && !nome.isEmpty()) {
                query.setParameter("nome", "%" + nome + "%");
            }
            if (cargo != null && !cargo.isEmpty()) {
                query.setParameter("cargo", "%" + cargo + "%");
            }
          
            // Executando a consulta
            return query.getResultList();
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
    
    public void excluirFuncionario(int id){
          EntityManager em = HibernateUtil.getEntityManager();
          try{
              Funcionario f = em.find(Funcionario.class, id);
              if(f != null){
                  em.getTransaction().begin();
                  em.remove(f);
                  em.getTransaction().commit();
              }
          } catch(Exception e){
                em.getTransaction().rollback();
                throw e;
          } finally {
              HibernateUtil.closeEntityManager();
          }
    }
    
    public Funcionario obter(int id){
          EntityManager em = HibernateUtil.getEntityManager();
          try{
              return em.find(Funcionario.class, id);
          } finally{
              HibernateUtil.closeEntityManager();
          }
    }
    
    public void atualizar(Funcionario f){
          EntityManager em = HibernateUtil.getEntityManager();
          try{
              em.getTransaction().begin();
              em.merge(f);
              
          }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
}
