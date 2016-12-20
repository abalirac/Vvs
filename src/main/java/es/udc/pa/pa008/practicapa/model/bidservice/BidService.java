package es.udc.pa.pa008.practicapa.model.bidservice;

import es.udc.pa.pa008.practicapa.model.bid.Bid;
import es.udc.pa.pa008.practicapa.model.bid.BidBlock;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface BidService {

    public Bid makeBid(Long userProfileId, Long productId, double amount)
            throws InstanceNotFoundException, LowBidValueException, TimeExpiredException;

    public BidBlock findBidsByUser(Long userProfileId, int startIndex, int count);

    public Bid findProductLastBid(Long productId) throws InstanceNotFoundException;

}
