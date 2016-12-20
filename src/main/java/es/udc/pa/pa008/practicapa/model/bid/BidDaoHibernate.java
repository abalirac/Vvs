package es.udc.pa.pa008.practicapa.model.bid;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("BidDao")
public class BidDaoHibernate extends GenericDaoHibernate<Bid, Long> implements BidDao {

    @SuppressWarnings("unchecked")
    public List<Bid> findAllByUserId(Long userId, int startIndex, int count) {
        return getSession()
                .createQuery("SELECT u FROM Bid u WHERE u.userProfile.userProfileId = :userId ORDER BY u.date DESC")
                .setParameter("userId", userId).setFirstResult(startIndex).setMaxResults(count).list();
    }

    public Bid findLastByProductId(Long productId) throws InstanceNotFoundException {
        Bid bid = (Bid) getSession()
                .createQuery("SELECT u FROM Bid u WHERE u.product.productId = :productId ORDER BY u.date DESC")
                .setParameter("productId", productId).setMaxResults(1).uniqueResult();
        if (bid == null)
            throw new InstanceNotFoundException(productId, Bid.class.getName());
        else
            return bid;
    }

    public Bid findWinnerBid(Long productId, Long userId) throws InstanceNotFoundException {
        Bid bid = (Bid) getSession()
                .createQuery(
                        "SELECT u FROM Bid u WHERE u.product.productId = :productId AND u.userProfile.userProfileId = :userId ORDER BY u.date DESC")
                .setParameter("productId", productId).setParameter("userId", userId).setMaxResults(1).uniqueResult();
        if (bid == null)
            throw new InstanceNotFoundException(productId, Bid.class.getName());
        else
            return bid;
    }

}
