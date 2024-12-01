package util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static final String PERSISTENCE_UNIT = "Motivex";
    
    private static EntityManager em;
    private static EntityManagerFactory fabrica;
    
    static {
        try {
                Dotenv dotenv = Dotenv.load();  // Carrega as variáveis do arquivo .env

                // Obtenha as variáveis do .env
                String dbUrl = dotenv.get("DB_URL");
                String dbUser = dotenv.get("DB_USER");
                String dbPassword = dotenv.get("DB_PASSWORD");
                String dbDialect = dotenv.get("APP_ENV");

                // Armazenar as variáveis no System para que o Hibernate as utilize
                System.setProperty("javax.persistence.jdbc.url", dbUrl);
                System.setProperty("javax.persistence.jdbc.user", dbUser);
                System.setProperty("javax.persistence.jdbc.password", dbPassword);
                System.setProperty("hibernate.dialect", dbDialect);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao configurar o Hibernate", e);
        }
    }
    
    public static EntityManager getEntityManager(){
        if(fabrica == null || !fabrica.isOpen()){
            // Usando configurações carregadas das variáveis de ambiente
            Map<String, String> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.url", System.getProperty("javax.persistence.jdbc.url"));
            properties.put("javax.persistence.jdbc.user", System.getProperty("javax.persistence.jdbc.user"));
            properties.put("javax.persistence.jdbc.password", System.getProperty("javax.persistence.jdbc.password"));
            properties.put("hibernate.dialect", System.getProperty("hibernate.dialect"));

            fabrica = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
        
        }
        
        if(em == null || !em.isOpen()){
            em = fabrica.createEntityManager();
        }
        return em;    
    }
    
    public static void closeEntityManager(){
        if(em.isOpen() && em != null){
            em.close();
            fabrica.close();
        }
    }
}
