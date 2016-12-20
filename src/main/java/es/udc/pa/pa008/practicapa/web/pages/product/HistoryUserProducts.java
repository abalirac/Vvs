package es.udc.pa.pa008.practicapa.web.pages.product;

import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.product.ProductBlock;
import es.udc.pa.pa008.practicapa.model.productservice.ProductService;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicy;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicyType;
import es.udc.pa.pa008.practicapa.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class HistoryUserProducts {

    private final static int PRODUCTS_PER_PAGE = 3;

    private int startIndex = 0;
    private ProductBlock productBlock;

    @Property
    private Product product;

    @SessionState(create = false)
    private UserSession userSession;

    @Inject
    private Locale locale;

    @Inject
    private ProductService productService;

    public List<Product> getProducts() {
        return productBlock.getProducts();
    }

    public Format getFormat() {
        return NumberFormat.getInstance(locale);
    }

    public Object[] getPreviousLinkContext() {

        if (startIndex - PRODUCTS_PER_PAGE >= 0) {
            return new Object[] { startIndex - PRODUCTS_PER_PAGE };
        } else {
            return null;
        }

    }

    public Object[] getNextLinkContext() {

        if (productBlock.isExistMoreProducts()) {
            return new Object[] { startIndex + PRODUCTS_PER_PAGE };
        } else {
            return null;
        }

    }

    Object[] onPassivate() {
        return new Object[] { startIndex };
    }

    void onActivate(int startIndex) {

        this.startIndex = startIndex;
        productBlock = productService.findProductsByUser(userSession.getUserProfileId(), startIndex, PRODUCTS_PER_PAGE);
    }
}
