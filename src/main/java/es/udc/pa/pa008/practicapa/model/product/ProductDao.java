package es.udc.pa.pa008.practicapa.model.product;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface ProductDao extends GenericDao<Product, Long> {

    /**
     * Returns a list of Products by keywords and / or category
     *
     * @param keywords
     *            the words to search
     * @param categoryName
     *            the category to search
     * @param startIndex
     *            the number to start index
     * @param count
     *            the number of elements
     * @return the list of Products
     */
    public List<Product> findByKeywords(String keywords, String categoryName, int startIndex, int count);

    /**
     * Returns a list of Products by user id
     *
     * @param userProfileId
     *            the user id to search
     * @param startIndex
     *            the number to start index
     * @param count
     *            the number of elements
     * @return the list of Products
     */
    public List<Product> findProductsByUser(Long userProfileId, int startIndex, int count);

}
