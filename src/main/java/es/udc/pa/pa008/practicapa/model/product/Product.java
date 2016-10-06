package es.udc.pa.pa008.practicapa.model.product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import es.udc.pa.pa008.practicapa.model.category.Category;
import es.udc.pa.pa008.practicapa.model.userprofile.UserProfile;

@Entity
@org.hibernate.annotations.BatchSize(size = 3)
public class Product {

	private Long productId;
	private UserProfile userProfile;
	private Category category;
	private String productName;
	private String description;
	private Calendar startDate;
	private Calendar endDate;
	private double startingPrice;
	private String shippingInfo;
	private long version;
	private double auctionValue;
	private UserProfile winner;
	
	public Product() {
		
	}
	
	public Product(UserProfile userProfile, Category category, String productName, String description,
			int duration, double startingPrice, String shippingInfo) {
	/**
	 * NOTE: "productId" *must* be left as "null" since its value is automatically generated.	
	 */
	this.userProfile = userProfile;
	this.category = category;
	this.productName = productName;
	this.description = description;
	this.startDate = GregorianCalendar.getInstance();
	this.endDate = (Calendar) this.startDate.clone();
	this.endDate.add(Calendar.MINUTE, duration);
	this.startingPrice = startingPrice;
	this.shippingInfo = shippingInfo;
	this.auctionValue = startingPrice;
	
	}

	@Column(name="proId")
	@SequenceGenerator( 				// It only takes effect for
			name="ProductIdGenerator", 	// databases providing identifier
			sequenceName="ProductSeq") 	// generators.
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,
					generator="ProductIdGenerator")
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="usrId")
	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="catId")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name="proName")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name="proDesc")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="startDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	@Column(name="endDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@Column(name="strPrice")
	public double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(double startingPrice) {
		this.startingPrice = startingPrice;
	}
	
	@Column(name="shInfo")
	public String getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(String shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	
	public int giveTimeleft() {
		
		Calendar rightNow = Calendar.getInstance();
		long milis = this.endDate.getTimeInMillis() - rightNow.getTimeInMillis();
		int minuts = (((int)milis)/1000)/60; 
		
		if (minuts <= 0)
			return 0;
			
		return minuts;
	}
	
	public String dateToString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(this.startDate.getTime());
	}
	
	/**
	 * @return the version
	 */
	
	@Version
	public long getVersion() {
		return version;
	}


	/**
	 * @param version the version to set
	 */
	
	public void setVersion(long version) {
		this.version = version;
	}
	
	/**
	 * @return the maxAmount
	 */
	@Column(name="auctionVal")
	public double getAuctionValue() {
		return auctionValue;
	}
	/**
	 * @param maxAmount the maxAmount to set
	 */
	public void setAuctionValue(double auctionValue) {
		this.auctionValue = auctionValue;
	}
	
	
	/**
	 * @return the winner
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="winnerId")
	public UserProfile getWinner() {
		return winner;
	}
	/**
	 * @param winner the winner to set
	 */
	public void setWinner(UserProfile winner) {
		this.winner = winner;
	}
	
	
}
