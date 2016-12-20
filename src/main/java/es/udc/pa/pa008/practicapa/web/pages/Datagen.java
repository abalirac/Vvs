package es.udc.pa.pa008.practicapa.web.pages;

import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa008.practicapa.model.bidservice.BidService;
import es.udc.pa.pa008.practicapa.model.bidservice.TimeExpiredException;
import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.productservice.ProductService;
import es.udc.pa.pa008.practicapa.model.userprofile.UserProfile;
import es.udc.pa.pa008.practicapa.model.userservice.IncorrectPasswordException;
import es.udc.pa.pa008.practicapa.model.userservice.UserProfileDetails;
import es.udc.pa.pa008.practicapa.model.userservice.UserService;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class Datagen {

    @Inject
    private BidService bidService;

    @Inject
    private ProductService productService;

    @Inject
    private UserService userService;

    private boolean cargado;

    public boolean getCargado() {
        return cargado;
    }

    void onActivate() throws DuplicateInstanceException, IncorrectPasswordException, InstanceNotFoundException,
            IllegalArgumentException, TimeExpiredException {
        try {
            this.cargado = false;
            userService.login("user", "user", false);

        } catch (InstanceNotFoundException e) {

            UserProfile userProfile = userService.registerUser("user", "user",
                    new UserProfileDetails("username", "lastName", "user@udc.es"));

            UserProfile userProfile2 = userService.registerUser("user2", "user2",
                    new UserProfileDetails("user2name", "lastName", "user@udc.es"));

            Product product1 = productService.insertAd(userProfile.getUserProfileId(), 1L, "Audi A1",
                    "Coche en muy buenas condiciones", 240, 30, "envío por trasporte urgente");

            productService.insertAd(userProfile.getUserProfileId(), 1L, "Audi A2", "Coche en muy buenas condiciones",
                    240, 40, "envío por trasporte urgente");

            productService.insertAd(userProfile.getUserProfileId(), 1L, "Audi A3", "Coche en muy buenas condiciones",
                    500, 1, "envío por trasporte urgente");

            productService.insertAd(userProfile.getUserProfileId(), 1L, "Audi A4", "Coche en muy buenas condiciones",
                    240, 30, "envío por trasporte urgente");

            productService.insertAd(userProfile.getUserProfileId(), 1L, "Audi A5", "Coche en muy buenas condiciones", 5,
                    10, "envío por trasporte urgente");

            productService.insertAd(userProfile2.getUserProfileId(), 2L, "PolyStation4", "Consola china cutre", 240, 2,
                    "envío por barco a remo");

            bidService.makeBid(userProfile.getUserProfileId(), product1.getProductId(), 30);

            bidService.makeBid(userProfile2.getUserProfileId(), product1.getProductId(), 35);

            bidService.makeBid(userProfile.getUserProfileId(), product1.getProductId(), 40);

            bidService.makeBid(userProfile.getUserProfileId(), product1.getProductId(), 41);

            bidService.makeBid(userProfile.getUserProfileId(), product1.getProductId(), 42);

            bidService.makeBid(userProfile.getUserProfileId(), product1.getProductId(), 43);

            this.cargado = true;

        }

    }

}
