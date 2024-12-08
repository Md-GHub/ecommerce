package com.mdghub.project.repository;

import com.mdghub.project.model.Payment;
import org.springframework.context.annotation.ReflectiveScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    Payment findByPgPaymentId(String pgPaymentId);
}
