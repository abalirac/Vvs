package es.udc.pa.pa008.practicapa.model.bid;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface BidDao extends GenericDao<Bid, Long> {

    /**
     * 
     * @param userId
     * @param startIndex
     * @param count
     * @return the list of bids made by the user
     */
    public List<Bid> findAllByUserId(Long userId, int startIndex, int count);

    /**
     * 
     * @param productId
     * @return last bid for given product
     * @throws InstanceNotFoundException
     */
    public Bid findLastByProductId(Long productId) throws InstanceNotFoundException;

    public Bid findWinnerBid(Long productId, Long winnerId) throws InstanceNotFoundException;

}
