package es.udc.pa.pa008.practicapa.web.pages.product;

import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicy;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class AdInserted {

    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    Long onPassivate() {
        return productId;
    }

    void onActivate(Long productId) {
        this.productId = productId;
    }

}
