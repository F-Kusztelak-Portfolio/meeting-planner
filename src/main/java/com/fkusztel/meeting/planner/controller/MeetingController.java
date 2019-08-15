package com.fkusztel.meeting.planner.controller;

import com.fkusztel.meeting.planner.entity.Meeting;
import com.fkusztel.meeting.planner.entity.MeetingType;
import com.fkusztel.meeting.planner.entity.PriorityType;
import com.fkusztel.meeting.planner.exception.MeetingNotFoundException;
import com.fkusztel.meeting.planner.service.MeetingService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Controller
@RequestMapping(path = "meeting")
public class MeetingController {

  @Autowired
  MeetingService meetingService;

  /**
   * Create a new Meeting with the specified values.
   *
   * @param meetingType Type of the meeting.
   * @param priorityType Priority type of meeting.
   * @param date Starting date of meeting (2018-05-07).
   */
  @PostMapping(path = "/create")
  public @ResponseBody
  Meeting createMeeting(
      @RequestParam MeetingType meetingType,
      @RequestParam PriorityType priorityType,
      @RequestParam String date) {

    return meetingService.meetingCreate(meetingType, priorityType, date);
  }

  /**
   * Find meeting with the specified Id.
   *
   * @param meetingId Id of given meeting.
   * @exception MeetingNotFoundException Given meeting was not found in database
   */
  @GetMapping(path = "/read")
  public @ResponseBody Meeting readMeetingById(@RequestParam Long meetingId)
      throws MeetingNotFoundException {
    log.info("readMeetingById {}", meetingId);
    return meetingService.findMeetingById(meetingId);
  }

  /** Find all meetings in database*/
  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Meeting> getAllMeetings() {
    return meetingService.findAll();
  }

  /**
   * Update a Meeting if exists or creates a new one with the specified values.
   *
   * @param meetingId Id of the meeting.
   * @param meetingType Type of the meeting.
   * @param priorityType Priority type of meeting.
   * @param date Starting date of meeting (2018-05-07).
   */
  @PutMapping(path = "/update")
  public @ResponseBody String updateMeeting(
      @RequestParam Long meetingId,
      @RequestParam MeetingType meetingType,
      @RequestParam PriorityType priorityType,
      @RequestParam String date) {

    log.info("updateMeeting: {}", meetingId);

    // Find meeting by Id and update details
    return meetingService.updateMeeting(meetingId, meetingType, priorityType, date);
  }

  /**
   * Delete meeting from database with the specified values.
   *
   * @param meetingId Id of the meeting.
   * @exception MeetingNotFoundException Given meeting was not found in database
   */
  @DeleteMapping(path = "/delete")
  public @ResponseBody String deleteMeeting(@RequestParam Long meetingId)
      throws MeetingNotFoundException {
    log.info("deleteMeeting with Id: {}", meetingId);

    // Find meeting by Id and delete it
    meetingService.deleteMeeting(meetingId);
    return "Meeting with id: " + meetingId + " deleted successfully";
  }

  /**
   * Find a date between two given dates with the specified values.
   *
   * @param startDate Date from which the search should be smaller (2018-01-01).
   * @param endDate Date from which the search should be greater (2018-02-02).
   */
  @GetMapping(path = "/between")
  public @ResponseBody Iterable<Meeting> findMeetingByDateBetween(
      String startDate, String endDate) {

    log.info("findMeetingByDateBetween: {}{}", startDate, endDate);

    return meetingService.findMeetingByDateBetween(
        LocalDate.parse(startDate), LocalDate.parse(endDate));
  }
}
