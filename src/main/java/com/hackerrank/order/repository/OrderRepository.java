package com.hackerrank.order.repository;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hackerrank.order.model.PurchaseOrder;



@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
	 @Transactional
	 Long deleteById(Long id);
	 
	 @Query("select po from PurchaseOrder po where po.customerId=?1")
	  List<PurchaseOrder> findBySearch(Long custId);
   
}
