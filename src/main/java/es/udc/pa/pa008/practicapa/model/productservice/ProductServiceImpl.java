package es.udc.pa.pa008.practicapa.model.productservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.pa008.practicapa.model.category.Category;
import es.udc.pa.pa008.practicapa.model.category.CategoryDao;
import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.product.ProductBlock;
import es.udc.pa.pa008.practicapa.model.product.ProductDao;
import es.udc.pa.pa008.practicapa.model.userprofile.UserProfile;
import es.udc.pa.pa008.practicapa.model.userprofile.UserProfileDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductDao productDao;
	
	@Autowired
    private UserProfileDao userProfileDao;
	
	@Autowired
    private CategoryDao categoryDao;
	
	public Product insertAd(Long userProfileId, Long categoryId, String productName, 
			String description, int duration, double startingPrice, String shippingInfo) 
			throws InstanceNotFoundException {
		
		UserProfile userProfile = userProfileDao.find(userProfileId);
		Category category = categoryDao.find(categoryId);
		
        Product product = new Product(userProfile, category, productName, description, duration, 
        		startingPrice, shippingInfo);
         
        productDao.save(product);
        return product;
	}

	@Transactional(readOnly = true)
    public Product findProduct(Long productId) throws InstanceNotFoundException {

        return productDao.find(productId);
    }
	
	@Transactional(readOnly = true)
	public ProductBlock findByKeywords(String keywords, Long categoryId, int startIndex, int count) 
			throws InstanceNotFoundException {

		/*
		 * Find count+1 products to determine if there exist more products
		 * above the specified range.
		 */
		String categoryName = null;
		if (categoryId != null)
			categoryName = categoryDao.find((long)categoryId).getCategoryName();
		
		List<Product> products = productDao.findByKeywords(keywords, categoryName, startIndex, count + 1);
		
		/* Verify timeleft */
		for (int i=0;i<products.size();i++){
			if (products.get(i).giveTimeleft() <= 0)
				products.remove(i);
		}
		
		boolean existMoreProducts = products.size() == (count + 1);
		
		/*
		 * Remove the last product from the returned list if there exist
		 * more products above the specified range.
		 */
		if (existMoreProducts) {
			products.remove(products.size() - 1);
		}
		
		/* Return ProductBlock */
		return new ProductBlock(products, existMoreProducts);
	}

	@Transactional(readOnly = true)
	public ProductBlock findProductsByUser(Long userProfileId, int startIndex, int count) {

		/*
		 * Find count+1 products to determine if there exist more products
		 * above the specified range.
		 */
		List<Product> products = productDao.findProductsByUser(userProfileId, startIndex, count + 1);
		boolean existMoreProducts = products.size() == (count + 1);
		
		/*
		 * Remove the last product from the returned list if there exist
		 * more products above the specified range.
		 */
		if (existMoreProducts) {
			products.remove(products.size() - 1);
		}
		
		/* Return ProductBlock */
		return new ProductBlock(products, existMoreProducts);
		
	}

}