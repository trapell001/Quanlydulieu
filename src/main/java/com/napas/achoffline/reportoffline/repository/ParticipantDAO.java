package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantDAO extends JpaRepository<Participant, Long> {
}
