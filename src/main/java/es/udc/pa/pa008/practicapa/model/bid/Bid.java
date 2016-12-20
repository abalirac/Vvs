package es.udc.pa.pa008.practicapa.model.bid;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
//import javax.persistence.Version;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.userprofile.UserProfile;

@Entity
@Immutable
public class Bid {

    private Long bidId;
    private UserProfile userProfile;
    private Product product;
    private double bidValue;
    private double auctionValue;
    private UserProfile winner;
    private Calendar date;
    // private long version; // En caso de que sea clase mutable. Optimistic
    // Locking.

    public Bid() {

    }

    public Bid(UserProfile userProfile, Product product, double bidValue, Calendar date, double auctionValue,
            UserProfile winner) {
        /**
         * NOTE: "BidId" *must* be left as "null" since its value is
         * automatically generated.
         */
        this.userProfile = userProfile;
        this.product = product;
        this.bidValue = bidValue;
        this.auctionValue = auctionValue;
        this.winner = winner;
        this.date = date;
    }

    /**
     * @return the bidId
     */
    @Column(name = "bidId")
    @SequenceGenerator(name = "BidIdGenerator", sequenceName = "BidSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BidIdGenerator")
    public Long getBidId() {
        return bidId;
    }

    /**
     * @param bidId
     *            the bidId to set
     */
    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    /**
     * @return the userProfile
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usrId")
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * @param userProfile
     *            the userProfile to set
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * @return the product
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "proId")
    public Product getProduct() {
        return product;
    }

    /**
     * @param product
     *            the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the minAmount
     */
    @Column(name = "bidVal")
    public double getBidValue() {
        return bidValue;
    }

    /**
     * @param minAmount
     *            the minAmount to set
     */
    public void setBidValue(double bidValue) {
        this.bidValue = bidValue;
    }

    /**
     * @return the maxAmount
     */
    @Column(name = "auctionVal")
    public double getAuctionValue() {
        return auctionValue;
    }

    /**
     * @param maxAmount
     *            the maxAmount to set
     */
    public void setAuctionValue(double auctionValue) {
        this.auctionValue = auctionValue;
    }

    /**
     * @return the winner
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "winnerId")
    public UserProfile getWinner() {
        return winner;
    }

    /**
     * @param winner
     *            the winner to set
     */
    public void setWinner(UserProfile winner) {
        this.winner = winner;
    }

    /**
     * @return the date
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getDate() {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

    public String dateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(this.date.getTime());
    }

    /**
     * @return the version
     */
    /*
     * @Version public long getVersion() { return version; }
     * 
     * 
     * /**
     * 
     * @param version the version to set
     */
    /*
     * public void setVersion(long version) { this.version = version; }
     */

}
