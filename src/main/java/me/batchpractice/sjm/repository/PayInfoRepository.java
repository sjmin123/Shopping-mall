package me.batchpractice.sjm.repository;

import me.batchpractice.sjm.entity.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayInfoRepository extends JpaRepository<PayInfo,Long> {
}
