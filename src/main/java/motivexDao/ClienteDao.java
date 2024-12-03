package motivexDao;

import jakarta.persistence.*;
import java.util.List;
import motivex.entities.Cliente;
import util.HibernateUtil;

/**
 *
 * @author matheus-leal
 */
public class ClienteDao {
    public void cadastrarFuncionario(Cliente c){
        EntityManager em = HibernateUtil.getEntityManager();
        
        try{
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        } catch(Exception e){
            em.getTransaction().rollback();
            throw e;
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
    
    public List<Cliente> listar() {
         EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
    
     // Método para listar clientes com parâmetros opcionais
    public List<Cliente> listarComParametros(String nome, String cpf, String email) {
       EntityManager em = HibernateUtil.getEntityManager();
        try {
            // Construindo a consulta dinâmica
            StringBuilder queryString = new StringBuilder("SELECT c FROM Cliente c WHERE 1=1");

            if (nome != null && !nome.isEmpty()) {
                queryString.append(" AND c.nome LIKE :nome");
            }
            if (cpf != null && !cpf.isEmpty()) {
                queryString.append(" AND c.cpf = :cpf");
            }
            if (email != null && !email.isEmpty()) {
                queryString.append(" AND c.email LIKE :email");
            }

            // Criando a query
            TypedQuery<Cliente> query = em.createQuery(queryString.toString(), Cliente.class);

            // Definindo os parâmetros
            if (nome != null && !nome.isEmpty()) {
                query.setParameter("nome", "%" + nome + "%");
            }
            if (cpf != null && !cpf.isEmpty()) {
                query.setParameter("cpf", cpf);
            }
            if (email != null && !email.isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }

            // Retornando os resultados
            return query.getResultList();

        } catch (RuntimeException e) {
            throw e; // Propaga a exceção para ser tratada em outro ponto
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
    
}
