package es.udc.pa.pa008.practicapa.model.category;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("categoryDao")
public class CategoryDaoHibernate extends GenericDaoHibernate<Category, Long> implements CategoryDao {

    @SuppressWarnings("unchecked")
    public List<Category> findCategorys() {

        return getSession().createQuery("SELECT u FROM Category u").list();
    }

}
