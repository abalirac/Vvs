package es.udc.pa.pa008.practicapa.model.productservice;

import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.product.ProductBlock;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface ProductService {

	public Product insertAd(Long userProfileId, Long categoryId, String productName, 
			String description, int duration, double startingPrice, String shippingInfo)
			throws InstanceNotFoundException;
	
	public Product findProduct(Long productId) throws InstanceNotFoundException;
	
	public ProductBlock findByKeywords(String keywords, Long categoryId, int startIndex, int count)
			throws InstanceNotFoundException;
	
	public ProductBlock findProductsByUser(Long userProfileId, int startIndex, int count);
	
}
