package com.shop.market.repository;

import com.shop.market.entity.userE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<userE, Long> {


}
