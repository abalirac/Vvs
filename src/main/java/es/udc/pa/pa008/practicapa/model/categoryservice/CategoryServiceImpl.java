package es.udc.pa.pa008.practicapa.model.categoryservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.pa008.practicapa.model.category.Category;
import es.udc.pa.pa008.practicapa.model.category.CategoryDao;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Transactional(readOnly = true)
    public List<Category> findCategorys() {

        return categoryDao.findCategorys();
    }

}
