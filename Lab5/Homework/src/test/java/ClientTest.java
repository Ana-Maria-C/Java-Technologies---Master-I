import com.example.homework.model.Client;
import com.example.homework.repository.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private ClientRepository clientRepository;

    @BeforeAll
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("MyDataBasePU");
    }

    @BeforeEach
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        clientRepository = new ClientRepository();
        clientRepository.setEntityManager(entityManager);
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testCreateClient() {
        entityManager.getTransaction().begin();

        Client client = new Client();
        client.setName("Test Create Client");
        client.setEmail("test@gmail.com");
        client.setPhone("123456789");
        client.setAddress("Home create");
        client.setDateOfBirth(new Date());
        client.setGender("Other");

        Client createdClient = clientRepository.create(client);
        entityManager.getTransaction().commit();

        assertNotNull(createdClient.getId());
    }

    @Test
    public void testFindById() {
        entityManager.getTransaction().begin();

        Client client = new Client();
        client.setName("Test Find");
        client.setEmail("find@gmail.com");
        client.setPhone("987654321");
        client.setAddress("Home find");
        client.setDateOfBirth(new Date());
        client.setGender("Female");

        clientRepository.create(client);
        entityManager.getTransaction().commit();

        Client foundClient = clientRepository.findById(client.getId());
        assertNotNull(foundClient);
        assertEquals("Test Find", foundClient.getName());
    }

    @Test
    public void testFindAll() {
        entityManager.getTransaction().begin();
        List<Client> clients = clientRepository.findAll();
        assertTrue(clients.size() >= 1);
    }

    @Test
    public void testUpdateClient() {
        entityManager.getTransaction().begin();

        Client client = new Client();
        client.setName("Old name");
        client.setEmail("client@gmail.com");
        client.setPhone("123456789");
        client.setAddress("Home c");
        client.setDateOfBirth(new Date());
        client.setGender("Female");
        clientRepository.create(client);

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        client.setName("New Name");
        Client updatedClient = clientRepository.update(client);
        entityManager.getTransaction().commit();

        assertEquals("New Name", updatedClient.getName());
    }

    @Test
    public void testDeleteClient() {
        entityManager.getTransaction().begin();

        Client client = new Client();
        client.setName("Client delete");
        client.setEmail("client@gmail.com");
        client.setPhone("123456789");
        client.setAddress("Home c");
        client.setDateOfBirth(new Date());
        client.setGender("Female");
        clientRepository.create(client);

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        clientRepository.delete(client.getId());
        entityManager.getTransaction().commit();

        Client deletedClient = clientRepository.findById(client.getId());
        assertNull(deletedClient);
    }
}
