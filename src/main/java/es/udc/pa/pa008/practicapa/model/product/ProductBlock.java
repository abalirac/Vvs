package es.udc.pa.pa008.practicapa.model.product;

import java.util.List;

public class ProductBlock {

    private List<Product> products;
    private boolean existMoreProducts;

    public ProductBlock(List<Product> products, boolean existMoreProducts) {

        this.products = products;
        this.existMoreProducts = existMoreProducts;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isExistMoreProducts() {
        return existMoreProducts;
    }

    public void setExistMoreProducts(boolean existMoreProducts) {
        this.existMoreProducts = existMoreProducts;
    }

}
