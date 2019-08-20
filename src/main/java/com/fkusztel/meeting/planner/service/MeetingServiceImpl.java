package com.fkusztel.meeting.planner.service;

import com.fkusztel.meeting.planner.entity.Meeting;
import com.fkusztel.meeting.planner.entity.MeetingRepository;
import com.fkusztel.meeting.planner.entity.MeetingType;
import com.fkusztel.meeting.planner.entity.PriorityType;
import com.fkusztel.meeting.planner.exception.DirectionException;
import com.fkusztel.meeting.planner.exception.MeetingNotFoundException;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Service
public class MeetingServiceImpl implements MeetingService {

  @Autowired
  MeetingRepository meetingRepository;

  /**
   * Save meeting in database
   *
   * @param meeting Given meeting entity.
   */
  @Override
  public void saveMeeting(Meeting meeting) {
    meetingRepository.save(meeting);
  }

  /**
   * Find meeting with the specified Id.
   *
   * @param meetingId Id of given meeting.
   * @exception MeetingNotFoundException Given meeting was not found in database
   */
  @Override
  public Meeting findMeetingById(Long meetingId) throws MeetingNotFoundException {

    return meetingRepository.findById(meetingId).orElseThrow(MeetingNotFoundException::new);
  }

  /** Find all meetings in database */
  @Override
  public Iterable<Meeting> findAll() {
    return meetingRepository.findAll();
  }

  /**
   * Delete meeting from database with the specified values.
   *
   * @param meetingId Id of the meeting.
   * @exception MeetingNotFoundException Given meeting was not found in database
   */
  @Override
  public void deleteMeeting(Long meetingId) throws MeetingNotFoundException {
    try {
      meetingRepository.deleteById(meetingId);
    } catch (NoSuchElementException e) {
      throw new MeetingNotFoundException();
    }
  }

  /**
   * Create a new Meeting with the specified values.
   *
   * @param meetingType Type of the meeting.
   * @param priorityType Priority type of meeting.
   * @param date Starting date of meeting (2018-02-03T12:30:30).
   * @param timeZone Preferred timezone (Europe/Paris, America/Sao_Paulo, Asia/Tokyo etc).
   */
  @Override
  public Meeting meetingCreate(
      MeetingType meetingType, PriorityType priorityType, String date, ZoneId timeZone) {

    // Create meeting from given parameters and save it to database
    Meeting meeting =
        Meeting.builder()
            .meetingType(meetingType)
            .priorityType(priorityType)
            .meetingDate(ZonedDateTime.ofLocal(LocalDateTime.parse(date), timeZone, ZoneOffset.UTC))
            .build();

    log.info("createMeeting: {}", meeting.toString());
    saveMeeting(meeting);
    return meetingRepository.save(meeting);
  }

  /**
   * Update a Meeting if exists or creates a new one with the specified values.
   *
   * @param meetingId Id of the meeting.
   * @param meetingType Type of the meeting.
   * @param priorityType Priority type of meeting.
   * @param date Starting date of meeting (2018-02-03T12:30:30).
   */
  @Override
  public String updateMeeting(Long meetingId, MeetingType meetingType, PriorityType priorityType, String date) {

    Meeting updated;
    try {
      updated = findMeetingById(meetingId);
      updated.setMeetingType(meetingType);
      updated.setPriorityType(priorityType);
      updated.setMeetingDate(ZonedDateTime.parse(date));

      saveMeeting(updated);
      return updated.toString() + " updated successfully";
    } catch (MeetingNotFoundException e) {
      Meeting created =
          Meeting.builder()
              .meetingType(meetingType)
              .priorityType(priorityType)
              .meetingDate(ZonedDateTime.parse(date))
              .build();

      saveMeeting(created);
      return "Meeting with Id "
          + meetingId
          + " was absent and it was created: "
          + created.toString();
    }
  }

  /**
   * Get all types available
   */
  @Override
  public Iterable<MeetingType> getType() {
    return Arrays.asList(MeetingType.values());
  }

  /**
   * Get all priorities available
   */
  @Override
  public Iterable<PriorityType> getPriority() {
    return Arrays.asList(PriorityType.values());
  }

  @Override
  public Iterable<Meeting> getSorted(String orderBy, Direction direction) {

    if (direction.equals(Sort.Direction.ASC)) {
      return meetingRepository.findAll(Sort.by(Sort.Direction.ASC, orderBy));
    }

    if (direction.equals(Direction.DESC)) {
      return meetingRepository.findAll(Sort.by(Direction.DESC, orderBy));
    }

    throw new DirectionException();
  }

  /**
   * Find a date between two given dates with the specified values.
   *
   * @param startDate Date from which the search should be smaller (2018-01-01T11:30:30).
   * @param endDate Date from which the search should be greater (2019-02-02T12:30:30).
   */
  @Override
  public Iterable<Meeting> findMeetingByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
    List<Meeting> meetingList = Lists.newArrayList(findAll());
    List<Meeting> datesBetween = Lists.newArrayList();

    // Check all meetings and if date is between start and end date
    for (Meeting meeting : meetingList) {
      if (meeting.getMeetingDate().toLocalDateTime().isAfter(startDate)
          && meeting.getMeetingDate().toLocalDateTime().isBefore(endDate)) {

        datesBetween.add(meeting);
      }
    }
    return datesBetween;
  }
}
