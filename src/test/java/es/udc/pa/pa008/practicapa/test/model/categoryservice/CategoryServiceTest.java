package es.udc.pa.pa008.practicapa.test.model.categoryservice;

import static es.udc.pa.pa008.practicapa.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.pa008.practicapa.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static net.java.quickcheck.generator.PrimitiveGeneratorsIterables.someIntegers;
import static net.java.quickcheck.generator.PrimitiveGenerators.strings;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
//import static org.testng.Assert.assertEquals;

import es.udc.pa.pa008.practicapa.model.category.Category;
import es.udc.pa.pa008.practicapa.model.category.CategoryDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryDao categoryDao;

    //PR-UN-13
    @Test
    public void testFindCategorys() {

        /* Add categorys and find categorys. */

        Category category = new Category("Móviles");
        categoryDao.save(category);

        Category category2 = new Category("Altavoces");
        categoryDao.save(category2);

        Category category3 = new Category("Motos");
        categoryDao.save(category3);

        List<Category> categorys = categoryDao.findCategorys();

        /* Check data. */
        assertEquals(3, categorys.size());
        assertEquals(category2, categorys.get(0));
        assertEquals(category3, categorys.get(1));
        assertEquals(category, categorys.get(2));
    }
    //PR-UN-32
    @Test
    public void testFindCategoriesQuickCheck() {
        int index = 0;
        for (Integer any : someIntegers()) {

            Category category = new Category(strings().next());
            categoryDao.save(category);

            List<Category> categorys = categoryDao.findCategorys();
            // System.out.println("any: "+any.intValue()+" size:
            // "+categorys.size()+"nombre1: "+category.getCategoryName()+"
            // nombre2:"+categorys.get(index).getCategoryName());
            assertEquals(index + 1, categorys.size());
            index++;

        }

    }

}
