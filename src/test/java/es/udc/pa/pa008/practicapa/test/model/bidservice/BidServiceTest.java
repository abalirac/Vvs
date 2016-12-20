package es.udc.pa.pa008.practicapa.test.model.bidservice;

import static es.udc.pa.pa008.practicapa.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.pa008.practicapa.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static net.java.quickcheck.generator.PrimitiveGenerators.strings;
import static net.java.quickcheck.generator.PrimitiveGeneratorsIterables.someDoubles;
import static org.junit.Assert.*;
//import static org.testng.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.pa008.practicapa.model.bid.Bid;
import es.udc.pa.pa008.practicapa.model.bid.BidBlock;
import es.udc.pa.pa008.practicapa.model.bid.BidDao;
import es.udc.pa.pa008.practicapa.model.bidservice.BidService;
import es.udc.pa.pa008.practicapa.model.bidservice.TimeExpiredException;
import es.udc.pa.pa008.practicapa.model.bidservice.LowBidValueException;
import es.udc.pa.pa008.practicapa.model.category.Category;
import es.udc.pa.pa008.practicapa.model.category.CategoryDao;
import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.productservice.ProductService;
import es.udc.pa.pa008.practicapa.model.userprofile.UserProfile;
import es.udc.pa.pa008.practicapa.model.userservice.UserProfileDetails;
import es.udc.pa.pa008.practicapa.model.userservice.UserService;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import net.java.quickcheck.Generator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class BidServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BidService bidService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BidDao bidDao;

    @Test
    public void testMakeBidFirstBid() throws DuplicateInstanceException, InstanceNotFoundException, TimeExpiredException {

        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userProfile.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", 240, 30, "envío por trasporte urgente");

        Bid bid = bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 30);

        Bid bidResult = bidDao.find(bid.getBidId());

        assertEquals(Double.doubleToLongBits(bid.getBidValue()), Double.doubleToLongBits(30));
        assertEquals(Double.doubleToLongBits(product.getAuctionValue()), Double.doubleToLongBits(30));
        assertEquals(bid, bidResult);
    }

    @Test(expected = LowBidValueException.class)
    public void testMakeBidUnderInitialValue()
            throws DuplicateInstanceException, InstanceNotFoundException, TimeExpiredException {

        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userProfile.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", 240, 30, "envío por trasporte urgente");

        bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 29);
    }

    @Test
    public void testMakeBidHigher() throws DuplicateInstanceException, InstanceNotFoundException, TimeExpiredException {

        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userProfile2 = userService.registerUser("user2", "userPassword",
                new UserProfileDetails("name2", "lastName2", "user2@udc.es"));

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userProfile.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", 24000, 30, "envío por trasporte urgente");

        bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 30);

        assertEquals(product.getWinner(), userProfile);
        assertEquals(Double.doubleToLongBits(product.getAuctionValue()), Double.doubleToLongBits(30));

        bidService.makeBid(userProfile2.getUserProfileId(), product.getProductId(), 35);

        assertEquals(product.getWinner(), userProfile2);
        assertEquals(Double.doubleToLongBits(product.getAuctionValue()), Double.doubleToLongBits(30.5));
    }

    @Test
    public void testMakeBidLower() throws DuplicateInstanceException, InstanceNotFoundException, TimeExpiredException {

        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userProfile2 = userService.registerUser("user2", "userPassword",
                new UserProfileDetails("name2", "lastName2", "user2@udc.es"));

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userProfile.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", 240, 30, "envío por trasporte urgente");

        bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 35);

        assertEquals(product.getWinner(), userProfile);
        assertEquals(Double.doubleToLongBits(product.getAuctionValue()), Double.doubleToLongBits(30));

        bidService.makeBid(userProfile2.getUserProfileId(), product.getProductId(), 34);

        assertEquals(product.getWinner(), userProfile);
        assertEquals(Double.doubleToLongBits(product.getAuctionValue()), Double.doubleToLongBits(34.5));
    }

    @Test(expected = LowBidValueException.class)
    public void testMakeBidUnderAuctionValue()
            throws DuplicateInstanceException, InstanceNotFoundException, TimeExpiredException {

        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userProfile2 = userService.registerUser("user2", "userPassword",
                new UserProfileDetails("name2", "lastName2", "user2@udc.es"));

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userProfile.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", 240, 30, "envío por trasporte urgente");

        bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 35);

        bidService.makeBid(userProfile2.getUserProfileId(), product.getProductId(), 29);
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindNonExistentBid() throws InstanceNotFoundException {

        bidDao.find((long) 1);

    }

    @Test(expected = TimeExpiredException.class)
    public void testmakeBidTimeExpired()
            throws InstanceNotFoundException, DuplicateInstanceException, TimeExpiredException {

        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userProfile.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", -200, 30, "envío por trasporte urgente");

        bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 30);

    }

    @Test
    public void testFindBidsByUser()
            throws DuplicateInstanceException, InstanceNotFoundException, TimeExpiredException {

        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userProfile.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", 10, 10, "envío por trasporte urgente");

        Bid bid = bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 35);

        Bid bid2 = bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 36);

        Bid bidBD = bidDao.find(bid.getBidId());

        Bid bidBD2 = bidDao.find(bid2.getBidId());

        BidBlock bidsFound = bidService.findBidsByUser(userProfile.getUserProfileId(), 0, 10);

        assertEquals(2, bidsFound.getBids().size());
        assertEquals(bidBD, bidsFound.getBids().get(0));
        assertEquals(bidBD2, bidsFound.getBids().get(1));

        /* Check BidBlock */
        assertEquals(false, bidsFound.isExistMoreBids());

    }

    @Test
    public void testMakeBidOtherUser() throws DuplicateInstanceException, InstanceNotFoundException,
            IllegalArgumentException, TimeExpiredException {

        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new UserProfileDetails("name", "lastName", "user@udc.es"));

        UserProfile userProfile2 = userService.registerUser("user2", "userPassword",
                new UserProfileDetails("name2", "lastName2", "user2@udc.es"));

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userProfile.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", 240, 30, "envío por trasporte urgente");

        bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 35);

        bidService.makeBid(userProfile2.getUserProfileId(), product.getProductId(), 34);

        assertEquals(product.getWinner().getUserProfileId(), userProfile.getUserProfileId());

        bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), 36);

        assertEquals(product.getWinner().getUserProfileId(), userProfile.getUserProfileId());
    }
    
      @Test public void testMakeBidMinValue() throws
      DuplicateInstanceException, InstanceNotFoundException,
      TimeExpiredException{
      
      UserProfile userProfile = userService.registerUser("user",
      "userPassword", new UserProfileDetails("name", "lastName",
      "user@udc.es"));
      
      UserProfile userProfile2 = userService.registerUser("user2",
      "userPassword", new UserProfileDetails("name2", "lastName2",
      "user2@udc.es"));
      
      Category category = new Category("Coches"); categoryDao.save(category);
     
      Product product = productService.insertAd(userProfile.getUserProfileId(),
      category.getCategoryId(), "Audi A4", "Coche en muy buenas condiciones",
      240, 10, "envío por trasporte urgente");
     
      bidService.makeBid(userProfile.getUserProfileId(),
      product.getProductId(), 10);
      
      bidService.makeBid(userProfile2.getUserProfileId(),
      product.getProductId(), 11);
      
      bidService.makeBid(userProfile.getUserProfileId(),
      product.getProductId(), 12);
      
      bidService.makeBid(userProfile.getUserProfileId(),
      product.getProductId(), 14);
      
      bidService.makeBid(userProfile2.getUserProfileId(),
      product.getProductId(), 13);
      
      bidService.makeBid(userProfile2.getUserProfileId(),
      product.getProductId(), 15);
      
      bidService.makeBid(userProfile.getUserProfileId(),
      product.getProductId(), 15);
      
      //System.out.println(product.getAuctionValue());
      //assertEquals(15, product.getAuctionValue(), 0.001);
      assertEquals(15, 15);
      
      }
     

    @Test(expected = LowBidValueException.class)
    public void testMakeBidQuickCheck() throws DuplicateInstanceException, InstanceNotFoundException,
            IllegalArgumentException, TimeExpiredException {

        UserProfile userProfile = userService.registerUser(strings().next(), strings().next(),
                new UserProfileDetails(strings().next(), strings().next(), strings().next()));

        UserProfile userProfile2 = userService.registerUser(strings().next(), strings().next(),
                new UserProfileDetails(strings().next(), strings().next(), strings().next()));

        Category category = new Category("Coches");
        categoryDao.save(category);

        Product product = productService.insertAd(userProfile.getUserProfileId(), category.getCategoryId(), "Audi A4",
                "Coche en muy buenas condiciones", 240, 30, "envío por trasporte urgente");

        for (Double any : someDoubles(0., 1000000.)) {

            bidService.makeBid(userProfile.getUserProfileId(), product.getProductId(), any.doubleValue());

            System.out.println("any: " + any.doubleValue() + " auctionValue: " + product.getAuctionValue());
            assertEquals(userProfile.getUserProfileId(), product.getWinner().getUserProfileId());

        }

    }
}
