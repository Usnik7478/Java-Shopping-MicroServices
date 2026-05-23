package com.example.inventory_service.repository;

import com.example.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

	@Query("Select i from Inventory i where i.quantity >= :quan and i.skuCode = :sku")
	Inventory checkInventory(@Param("sku") String sku, @Param("quan") Integer quan);

	@Transactional
	@Modifying
	@Query("UPDATE Inventory i SET i.quantity = i.quantity - :quan WHERE i.skuCode = :sku")
	int updateInventoryByForOrderPlacing(@Param("sku") String sku, @Param("quan") Integer quan);
}
