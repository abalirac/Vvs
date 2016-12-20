package es.udc.pa.pa008.practicapa.test.model.productservice;

import static es.udc.pa.pa008.practicapa.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.pa008.practicapa.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.pa008.practicapa.model.category.Category;
import es.udc.pa.pa008.practicapa.model.category.CategoryDao;
import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.product.ProductBlock;
import es.udc.pa.pa008.practicapa.model.productservice.ProductService;
import es.udc.pa.pa008.practicapa.model.userprofile.UserProfile;
import es.udc.pa.pa008.practicapa.model.userservice.UserProfileDetails;
import es.udc.pa.pa008.practicapa.model.userservice.UserService;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class ProductServiceTest {

    private final long NON_EXISTENT_PRODUCT_ID = -1;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void testInsertAdAndFindProduct() throws DuplicateInstanceException, InstanceNotFoundException {

        /* Register user, insert ad and find product. */
        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userFound = userService.findUserProfile(userProfile.getUserProfileId());

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userFound.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", 240, 3000.00, "envío por trasporte urgente");

        Product productFound = productService.findProduct(product.getProductId());

        /* Check data. */
        assertEquals(product, productFound);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindNonExistentProduct() throws InstanceNotFoundException {

        productService.findProduct(NON_EXISTENT_PRODUCT_ID);

    }

    @Test // keywords=0, category=0
    public void testFindByKeywords1() throws DuplicateInstanceException, InstanceNotFoundException {

        /* Register user, insert ads and find by keywords and category name. */
        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userFound = userService.findUserProfile(userProfile.getUserProfileId());

        UserProfile userProfile2 = userService.registerUser("anonimo", "anonimoPassword",
                new UserProfileDetails("jaja", "ultimo", "anonimo@udc.es"));

        UserProfile userFound2 = userService.findUserProfile(userProfile2.getUserProfileId());

        Category category = new Category("Coches");
        categoryDao.save(category);

        Category category2 = new Category("Televisores");
        categoryDao.save(category2);

        Product product = productService.insertAd(userFound.getUserProfileId(), category.getCategoryId(),
                "Audi A4 Turbo", "Coche en muy buenas condiciones", 240, 3000.00, "envío por trasporte urgente");

        Product product2 = productService.insertAd(userFound2.getUserProfileId(), category.getCategoryId(),
                "Audi A6 Gasolina", "Coche con muchos quilometros", 160, 1000.00, "venir a buscar");

        Product product3 = productService.insertAd(userFound2.getUserProfileId(), category2.getCategoryId(),
                "Sony Bravia KDL50", "Plasma espectacular", 640, 0500.00, "envío por trasporte urgente");

        ProductBlock productsFound = productService.findByKeywords(null, null, 0, 10);

        /* Check data. */
        assertEquals(3, productsFound.getProducts().size());
        assertEquals(product, productsFound.getProducts().get(0));
        assertEquals(product2, productsFound.getProducts().get(1));
        assertEquals(product3, productsFound.getProducts().get(2));

        /* Check ProductBlock */
        assertEquals(false, productsFound.isExistMoreProducts());
    }

    @Test // keywords=1 (desordenadas y con mayúsculas), category=0
    public void testFindByKeywords2() throws DuplicateInstanceException, InstanceNotFoundException {

        /* Register user, insert ads and find by keywords and category name. */
        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userFound = userService.findUserProfile(userProfile.getUserProfileId());

        UserProfile userProfile2 = userService.registerUser("anonimo", "anonimoPassword",
                new UserProfileDetails("jaja", "ultimo", "anonimo@udc.es"));

        UserProfile userFound2 = userService.findUserProfile(userProfile2.getUserProfileId());

        Category category = new Category("Coches");
        categoryDao.save(category);

        Category category2 = new Category("Televisores");
        categoryDao.save(category2);

        Product product = productService.insertAd(userFound.getUserProfileId(), category.getCategoryId(),
                "Audi A4 Turbo", "Coche en muy buenas condiciones", 240, 3000.00, "envío por trasporte urgente");

        Product product2 = productService.insertAd(userFound2.getUserProfileId(), category.getCategoryId(),
                "Audi A6 Turbo", "Coche con muchos quilometros", 160, 1000.00, "venir a buscar");

        productService.insertAd(userFound2.getUserProfileId(), category2.getCategoryId(), "Sony Bravia KDL50",
                "Plasma espectacular", 640, 0500.00, "envío por trasporte urgente");

        ProductBlock productsFound = productService.findByKeywords("TURBO audi", null, 0, 10);

        /* Check data. */
        assertEquals(2, productsFound.getProducts().size());
        assertEquals(product, productsFound.getProducts().get(0));
        assertEquals(product2, productsFound.getProducts().get(1));

        /* Check ProductBlock */
        assertEquals(false, productsFound.isExistMoreProducts());
    }

    @Test // keywords=0, category=1
    public void testFindByKeywords3() throws DuplicateInstanceException, InstanceNotFoundException {

        /* Register user, insert ads and find by keywords and category name. */
        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userFound = userService.findUserProfile(userProfile.getUserProfileId());

        UserProfile userProfile2 = userService.registerUser("anonimo", "anonimoPassword",
                new UserProfileDetails("jaja", "ultimo", "anonimo@udc.es"));

        UserProfile userFound2 = userService.findUserProfile(userProfile2.getUserProfileId());

        Category category = new Category("Coches");
        categoryDao.save(category);

        Category category2 = new Category("Televisores");
        categoryDao.save(category2);

        Product product = productService.insertAd(userFound.getUserProfileId(), category.getCategoryId(),
                "Audi A4 Turbo", "Coche en muy buenas condiciones", 240, 3000.00, "envío por trasporte urgente");

        Product product2 = productService.insertAd(userFound2.getUserProfileId(), category.getCategoryId(),
                "Audi A6 Gasolina", "Coche con muchos quilometros", 160, 1000.00, "venir a buscar");

        productService.insertAd(userFound2.getUserProfileId(), category2.getCategoryId(), "Sony Bravia KDL50",
                "Plasma espectacular", 640, 0500.00, "envío por trasporte urgente");

        ProductBlock productsFound = productService.findByKeywords(null, category.getCategoryId(), 0, 10);

        /* Check data. */
        assertEquals(2, productsFound.getProducts().size());
        assertEquals(product, productsFound.getProducts().get(0));
        assertEquals(product2, productsFound.getProducts().get(1));

        /* Check ProductBlock */
        assertEquals(false, productsFound.isExistMoreProducts());
    }

    @Test // keywords=1, category=1
    public void testFindByKeywords4() throws DuplicateInstanceException, InstanceNotFoundException {

        /* Register user, insert ads and find by keywords and category name. */
        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userFound = userService.findUserProfile(userProfile.getUserProfileId());

        UserProfile userProfile2 = userService.registerUser("anonimo", "anonimoPassword",
                new UserProfileDetails("jaja", "ultimo", "anonimo@udc.es"));

        UserProfile userFound2 = userService.findUserProfile(userProfile2.getUserProfileId());

        Category category = new Category("Coches");
        categoryDao.save(category);

        Category category2 = new Category("Televisores");
        categoryDao.save(category2);

        productService.insertAd(userFound.getUserProfileId(), category.getCategoryId(), "Audi A4 Turbo",
                "Coche en muy buenas condiciones", 240, 3000.00, "envío por trasporte urgente");

        Product product = productService.insertAd(userFound2.getUserProfileId(), category.getCategoryId(),
                "Audi A6 Gasolina", "Coche con muchos quilometros", 160, 1000.00, "venir a buscar");

        productService.insertAd(userFound2.getUserProfileId(), category2.getCategoryId(), "Sony Bravia KDL50",
                "Plasma espectacular", 640, 0500.00, "envío por trasporte urgente");

        ProductBlock productsFound = productService.findByKeywords("a6 AU", category.getCategoryId(), 0, 10);

        /* Check data. */
        assertEquals(1, productsFound.getProducts().size());
        assertEquals(product, productsFound.getProducts().get(0));

        /* Check ProductBlock */
        assertEquals(false, productsFound.isExistMoreProducts());
    }

    @Test
    public void testFindProductsByUser() throws DuplicateInstanceException, InstanceNotFoundException {

        /* Register user, insert ad and find products. */
        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userFound = userService.findUserProfile(userProfile.getUserProfileId());

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userFound.getUserProfileId(), category.getCategoryId(),
                "Audi A4 Turbo", "Coche en muy buenas condiciones", 240, 3000.00, "envío por trasporte urgente");

        Product product2 = productService.insertAd(userFound.getUserProfileId(), category.getCategoryId(),
                "Audi A6 Gasolina", "Coche con muchos quilometros", 160, 1000.00, "venir a buscar");

        Product product3 = productService.insertAd(userFound.getUserProfileId(), category.getCategoryId(),
                "Audi A8 Gasoil", "Coche muy seguro", 640, 9500.00, "envío por trasporte urgente");

        ProductBlock productsFound = productService.findProductsByUser(userFound.getUserProfileId(), 0, 10);

        /* Check data. */
        assertEquals(3, productsFound.getProducts().size());
        assertEquals(product3, productsFound.getProducts().get(0));
        assertEquals(product, productsFound.getProducts().get(1));
        assertEquals(product2, productsFound.getProducts().get(2));

        /* Check ProductBlock */
        assertEquals(false, productsFound.isExistMoreProducts());
    }

}
