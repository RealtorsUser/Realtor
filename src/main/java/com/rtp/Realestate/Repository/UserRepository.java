package com.rtp.Realestate.Repository;

import com.rtp.Realestate.Model.RealtorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<RealtorUser, Long> {
    RealtorUser findByEmail(String email);
}
