package com.fkusztel.meeting.planner.entity;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Filip.Kusztelak
 */
public interface MeetingRepository extends CrudRepository<Meeting, Long> {

  void deleteById(Long id);
}
