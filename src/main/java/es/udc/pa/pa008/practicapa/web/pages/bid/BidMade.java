package es.udc.pa.pa008.practicapa.web.pages.bid;

public class BidMade {

    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    void onActivate(Long productId) {

        this.productId = productId;

    }

    Long onPassivate() {
        return productId;
    }

}
