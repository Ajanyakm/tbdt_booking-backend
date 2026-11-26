package com.tbdt.booking.repository;

import com.tbdt.booking.entity.DarshanSlot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DarshanSlotRepository extends JpaRepository<DarshanSlot, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM DarshanSlot s WHERE s.id = :id")
    Optional<DarshanSlot> findByIdWithLock(@Param("id") Long id);
}
