package es.udc.pa.pa008.practicapa.model.bidservice;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.pa008.practicapa.model.bid.Bid;
import es.udc.pa.pa008.practicapa.model.bid.BidBlock;
import es.udc.pa.pa008.practicapa.model.bid.BidDao;
import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.product.ProductDao;
import es.udc.pa.pa008.practicapa.model.userprofile.UserProfile;
import es.udc.pa.pa008.practicapa.model.userprofile.UserProfileDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Service("BidService")
@Transactional
public class BidServiceImpl implements BidService {

    @Autowired
    private BidDao bidDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserProfileDao userProfileDao;

    public Bid makeBid(Long userProfileId, Long productId, double amountI)
            throws InstanceNotFoundException, LowBidValueException, TimeExpiredException {

        double winnerBidValue;

        // amountI = redondear(amountI);

        UserProfile userProfile = userProfileDao.find(userProfileId);
        Product product = productDao.find(productId);

        if (product.getEndDate().after(Calendar.getInstance())) {
            try {

                Bid lastBid = bidDao.findLastByProductId(productId);

                if (lastBid.getUserProfile() != product.getWinner()) {

                    Bid winnerBid = bidDao.findWinnerBid(product.getProductId(),
                            product.getWinner().getUserProfileId());
                    winnerBidValue = winnerBid.getBidValue();

                } else
                    winnerBidValue = lastBid.getBidValue();

                if (userProfile == product.getWinner() && amountI <= winnerBidValue)

                    throw new LowBidValueException(amountI);

                else {

                    if (amountI > winnerBidValue) {

                        Bid bid = new Bid(userProfile, product, amountI, Calendar.getInstance(),
                                autoIncrement(winnerBidValue), userProfile);
                        product.setAuctionValue(autoIncrement(winnerBidValue));
                        product.setWinner(userProfile);
                        productDao.save(product);
                        bidDao.save(bid);
                        return bid;

                    } else if (amountI > product.getAuctionValue() && amountI <= winnerBidValue) {

                        if (amountI == winnerBidValue) {

                            Bid bid = new Bid(userProfile, product, amountI, Calendar.getInstance(), amountI,
                                    product.getWinner());
                            product.setAuctionValue(amountI);
                            productDao.save(product);
                            bidDao.save(bid);
                            return bid;

                        } else {

                            Bid bid = new Bid(userProfile, product, amountI, Calendar.getInstance(),
                                    autoIncrement(amountI), product.getWinner());
                            product.setAuctionValue(autoIncrement(amountI));
                            productDao.save(product);
                            bidDao.save(bid);
                            return bid;

                        }

                    } else
                        throw new LowBidValueException(amountI);
                }

            } catch (InstanceNotFoundException e) {

                if (amountI >= product.getStartingPrice()) {

                    Bid bid = new Bid(userProfile, product, amountI, Calendar.getInstance(), product.getAuctionValue(),
                            userProfile);
                    product.setWinner(userProfile);
                    productDao.save(product);
                    bidDao.save(bid);
                    return bid;

                } else
                    throw new LowBidValueException(amountI);

            }
            /*
             * try { Bid lastBid = bidDao.findLastByProductId(productId);
             * 
             * if (lastBid.getUserProfile() != lastBid.getWinner()){
             * 
             * Bid winnerBid =
             * bidDao.findWinnerBid(product.getProductId(),lastBid.getWinner().
             * getUserProfileId()); winnerBidValue = winnerBid.getBidValue();
             * 
             * } else winnerBidValue = lastBid.getBidValue();
             * 
             * if(amount > winnerBidValue){
             * 
             * Bid bid = new Bid(userProfile, product, amount,
             * autoIncrement(winnerBidValue), userProfile,
             * Calendar.getInstance()); bidDao.save(bid); return bid;
             * 
             * }else if(amount > lastBid.getAuctionValue() && amount <=
             * winnerBidValue){
             * 
             * if(amount == winnerBidValue) throw new
             * LowBidValueException(amount); else{ Bid bid = new
             * Bid(userProfile, product, amount, autoIncrement(amount),
             * lastBid.getWinner(), Calendar.getInstance()); bidDao.save(bid);
             * return bid; }
             * 
             * } else throw new LowBidValueException(amount);
             * 
             * } catch (InstanceNotFoundException e){
             * 
             * if(amount >= product.getStartingPrice()){
             * 
             * Bid bid = new Bid(userProfile, product, amount,
             * product.getStartingPrice(), userProfile, Calendar.getInstance());
             * bidDao.save(bid); return bid;
             * 
             * } else throw new LowBidValueException(amount);
             * 
             * }
             * 
             */

        } else
            throw new TimeExpiredException(product.getEndDate());

    }

    private double autoIncrement(double value) {
        return value + 0.5;
    }

    private double redondear(double value) {
        return (Math.round(value * 2)) / 2;
    }

    @Transactional(readOnly = true)
    public BidBlock findBidsByUser(Long userProfileId, int startIndex, int count) {
        List<Bid> bids = bidDao.findAllByUserId(userProfileId, startIndex, count + 1);

        boolean existMoreBids = bids.size() == (count + 1);

        if (existMoreBids) {
            bids.remove(bids.size() - 1);
        }

        return new BidBlock(bids, existMoreBids);
    }

    public Bid findProductLastBid(Long productId) throws InstanceNotFoundException {
        return bidDao.findLastByProductId(productId);
    }

}
