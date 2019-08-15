package com.fkusztel.meeting.planner.service;

import com.fkusztel.meeting.planner.entity.Meeting;
import com.fkusztel.meeting.planner.entity.MeetingType;
import com.fkusztel.meeting.planner.entity.PriorityType;
import com.fkusztel.meeting.planner.exception.MeetingNotFoundException;

import java.time.LocalDate;

/**
 * @author Filip.Kusztelak
 */
public interface MeetingService {

  void saveMeeting(Meeting meeting);

  Meeting findMeetingById(Long meetingId) throws MeetingNotFoundException;

  Iterable<Meeting> findAll();

  void deleteMeeting(Long meetingId) throws MeetingNotFoundException;

  Meeting meetingCreate(
          MeetingType meetingType, PriorityType priorityType, String date);

  Iterable<Meeting> findMeetingByDateBetween(LocalDate startDate, LocalDate endDate);

  String updateMeeting(Long meetingId, MeetingType meetingType, PriorityType priorityType, String date);
}
