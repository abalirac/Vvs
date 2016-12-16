package es.udc.pa.pa008.practicapa.model.product;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("productDao")
public class ProductDaoHibernate extends GenericDaoHibernate<Product, Long> implements ProductDao {

	@SuppressWarnings("unchecked")
	public List<Product> findByKeywords(String keywords, String categoryName, int startIndex, int count) {
		
		Query query;
		String[] words = null;
		String condition = "SELECT u FROM Product u ";
		
		if (categoryName != null) {
			
			condition = condition + " WHERE u.category.categoryName = :categoryName";
		}
		
		if (keywords != null) {
			words = keywords.split(" ");
			if (categoryName != null) 
				condition = condition + " AND u.productName LIKE CONCAT('%', :words0, '%')";
			else
				condition = condition + " WHERE u.productName LIKE CONCAT('%', :words0, '%')";
			for(int i=1; i<words.length; i++){
				condition = condition + " AND u.productName LIKE CONCAT('%', :words"+i+", '%')";
			}	
			
		}
		
		query = getSession().createQuery(condition)
				.setFirstResult(startIndex)
				.setMaxResults(count)
				.setReadOnly(true);

		if (categoryName != null) {
			
			query.setParameter("categoryName", categoryName);
		}
		
		if (keywords != null) {
			for(int i=0; i<words.length; i++){
				query.setParameter("words"+i+"", words[i]);
			}	
			
		}		

		return query.list();
    }

	@SuppressWarnings("unchecked")
	public List<Product> findProductsByUser(Long userProfileId, int startIndex, int count) {
		
		return getSession().createQuery(
				"SELECT u FROM Product u WHERE u.userProfile.userProfileId = :userProfileId"
				+ " ORDER BY u.endDate DESC")
    			.setParameter("userProfileId", userProfileId)
    			.setFirstResult(startIndex)
    			.setMaxResults(count)
    			.list();
	}
	
}