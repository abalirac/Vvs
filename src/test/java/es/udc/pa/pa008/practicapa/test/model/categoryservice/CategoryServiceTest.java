package es.udc.pa.pa008.practicapa.test.model.categoryservice;

import static es.udc.pa.pa008.practicapa.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.pa008.practicapa.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.pa008.practicapa.model.category.Category;
import es.udc.pa.pa008.practicapa.model.category.CategoryDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CategoryServiceTest {
	
	@Autowired
	private CategoryDao categoryDao;
	
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

}
