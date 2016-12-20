package es.udc.pa.pa008.practicapa.web.pages.product;

import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;

import es.udc.pa.pa008.practicapa.model.category.Category;
import es.udc.pa.pa008.practicapa.model.categoryservice.CategoryService;
import es.udc.pa.pa008.practicapa.model.productservice.ProductService;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicy;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicyType;
import es.udc.pa.pa008.practicapa.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class InsertAd {

    @SessionState(create = false)
    private UserSession userSession;

    @Property
    private String category;

    @Property
    private Long categoryId;

    @Property
    private String productName;

    @Property
    private String description;

    @Property
    private int duration;

    @Property
    private double startingPrice;

    @Property
    private String shippingInfo;

    @Inject
    private ProductService productService;

    @Inject
    private CategoryService categoryService;

    @InjectPage
    private AdInserted adInserted;

    @Component
    private Form insertAdForm;

    @Component(id = "productName")
    private TextField productNameTextField;

    @Inject
    private Messages messages;

    public String getCategorys() {

        List<Category> categorys = categoryService.findCategorys();
        String result = "";
        for (Category category : categorys) {
            result += category.getCategoryId() + "=" + category.getCategoryName() + ",";
        }
        return result;
    }

    Object onSuccess() {

        try {
            if (category != null)
                this.categoryId = Long.valueOf(category);
            productService.insertAd(userSession.getUserProfileId(), this.categoryId, productName, description, duration,
                    startingPrice, shippingInfo);
        } catch (InstanceNotFoundException e) {
        }

        return adInserted;
    }
}
