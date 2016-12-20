package es.udc.pa.pa008.practicapa.model.category;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Immutable
public class Category {

    private Long categoryId;
    private String categoryName;

    public Category() {

    }

    public Category(String categoryName) {
        /**
         * NOTE: "categoryId" *must* be left as "null" since its value is
         * automatically generated.
         */
        this.categoryName = categoryName;
    }

    @Column(name = "catId")
    @SequenceGenerator( // It only takes effect for
            name = "CategoryIdGenerator", // databases providing identifier
            sequenceName = "CategorySeq") // generators.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CategoryIdGenerator")

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "catName")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
