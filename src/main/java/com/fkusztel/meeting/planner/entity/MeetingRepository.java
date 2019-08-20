package com.fkusztel.meeting.planner.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/** @author Filip.Kusztelak */
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

  void deleteById(Long id);
}
