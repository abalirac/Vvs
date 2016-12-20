package es.udc.pa.pa008.practicapa.web.pages.product;

import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.Property;

import es.udc.pa.pa008.practicapa.model.category.Category;
import es.udc.pa.pa008.practicapa.model.categoryservice.CategoryService;
import es.udc.pa.pa008.practicapa.model.productservice.ProductService;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicy;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class ViewProducts {

    @Property
    private String category;

    @Property
    private Long categoryId;

    @Property
    private String keywords;

    @Inject
    private ProductService productService;

    @Inject
    private CategoryService categoryService;

    @InjectPage
    private AllProducts allProducts;

    public String getCategorys() {

        List<Category> categorys = categoryService.findCategorys();
        String result = "";
        for (Category category : categorys) {
            result += category.getCategoryId() + "=" + category.getCategoryName() + ",";
        }
        return result;
    }

    Object onSuccess() {

        allProducts.setKeywords(keywords);
        if (category != null)
            this.categoryId = Long.valueOf(category);
        allProducts.setCategoryId(this.categoryId);
        return allProducts;
    }
}
