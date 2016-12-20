package es.udc.pa.pa008.practicapa.web.pages.product;

import java.util.List;
import java.util.Locale;
import java.text.Format;
import java.text.NumberFormat;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.product.ProductBlock;
import es.udc.pa.pa008.practicapa.model.productservice.ProductService;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicy;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicyType;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class AllProducts {

    private final static int PRODUCTS_PER_PAGE = 3;

    private int startIndex = 0;
    private ProductBlock productBlock;

    private Long categoryId;
    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Property
    private Product product;

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
            return new Object[] { keywords, categoryId, startIndex - PRODUCTS_PER_PAGE };
        } else {
            return null;
        }

    }

    public Object[] getNextLinkContext() {

        if (productBlock.isExistMoreProducts()) {
            return new Object[] { keywords, categoryId, startIndex + PRODUCTS_PER_PAGE };
        } else {
            return null;
        }

    }

    Object[] onPassivate() {
        return new Object[] { keywords, categoryId, startIndex };
    }

    void onActivate(String keywords, Long categoryId, int startIndex) {

        this.keywords = keywords;
        this.categoryId = categoryId;
        this.startIndex = startIndex;
        try {
            productBlock = productService.findByKeywords(keywords, categoryId, startIndex, PRODUCTS_PER_PAGE);
        } catch (InstanceNotFoundException e) {
        }
    }

}
