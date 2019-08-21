package com.fkusztel.meeting.planner.service;

import com.fkusztel.meeting.planner.entity.Meeting;
import com.fkusztel.meeting.planner.entity.MeetingType;
import com.fkusztel.meeting.planner.entity.PriorityType;
import com.fkusztel.meeting.planner.exception.MeetingNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.data.domain.Sort.Direction;

/** @author Filip.Kusztelak */
public interface MeetingService {

  void saveMeeting(Meeting meeting);

  Meeting findMeetingById(Long meetingId) throws MeetingNotFoundException;

  Iterable<Meeting> findAll();

  void deleteMeeting(Long meetingId) throws MeetingNotFoundException;

  Meeting meetingCreate(
      MeetingType meetingType, PriorityType priorityType, String date, ZoneId timeZone);

  Iterable<Meeting> findMeetingByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

  Meeting updateMeeting(
      Long meetingId,
      MeetingType meetingType,
      PriorityType priorityType,
      String date,
      ZoneId timeZone);

  Iterable<MeetingType> getType();

  Iterable<PriorityType> getPriority();

  Iterable<Meeting> getSorted(String attribute, Direction direction);
}
