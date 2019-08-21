package com.fkusztel.meeting.planner.config;

import com.fkusztel.meeting.planner.entity.Meeting;
import com.fkusztel.meeting.planner.entity.MeetingType;
import com.fkusztel.meeting.planner.entity.PriorityType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** @author Filip.Kusztelak */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestObjectFactory {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Meetings {

    public static List<Meeting> getMeetingList() {
      return Arrays.asList(getMeetingParis(), getMeetingSaoPaulo(), getMeetingBerlin());
    }

    public static List<Meeting> getDatesAll() {
      return Arrays.asList(
          getMeetingLosAngeles(),
          getMeetingSaoPaulo(),
          getMeetingBerlin(),
          getMeetingTokyo(),
          getMeetingParis());
    }

    public static List<Meeting> getDatesBetween() {
      return Arrays.asList(getMeetingSaoPaulo(), getMeetingBerlin(), getMeetingTokyo());
    }

    static Meeting getMeetingLosAngeles() {
      return Meeting.builder()
          .id(3L)
          .priorityType(PriorityType.MEDIUM)
          .meetingType(MeetingType.GROOMING)
          .meetingDate(ZonedDates.LOS_ANGELES)
          .build();
    }

    static Meeting getMeetingSaoPaulo() {
      return Meeting.builder()
          .id(3L)
          .priorityType(PriorityType.LOW)
          .meetingType(MeetingType.STAND_UP)
          .meetingDate(ZonedDates.SAOPAULO)
          .build();
    }

    public static Meeting getMeetingBerlin() {
      return Meeting.builder()
          .id(1L)
          .priorityType(PriorityType.MEDIUM)
          .meetingType(MeetingType.GROOMING)
          .meetingDate(ZonedDates.BERLIN)
          .build();
    }

    static Meeting getMeetingTokyo() {
      return Meeting.builder()
          .id(3L)
          .priorityType(PriorityType.HIGH)
          .meetingType(MeetingType.DEMO)
          .meetingDate(ZonedDates.TOKYO)
          .build();
    }

    public static Meeting getMeetingParis() {
      return Meeting.builder()
          .id(2L)
          .priorityType(PriorityType.HIGH)
          .meetingType(MeetingType.DEMO)
          .meetingDate(ZonedDates.PARIS)
          .build();
    }
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ZonedDates {

    static final ZonedDateTime LOS_ANGELES =
        ZonedDateTime.ofLocal(
            LocalDateTime.parse(Dates.DATE_LOS_ANGELES),
            TimeZones.TIME_ZONE_LOS_ANGELES,
            ZoneOffset.UTC);

    static final ZonedDateTime SAOPAULO =
        ZonedDateTime.ofLocal(
            LocalDateTime.parse(Dates.DATE_SAOPAULO), TimeZones.TIME_ZONE_SAOPAULO, ZoneOffset.UTC);

    static final ZonedDateTime BERLIN =
        ZonedDateTime.ofLocal(
            LocalDateTime.parse(Dates.DATE_BERLIN), TimeZones.TIME_ZONE_BERLIN, ZoneOffset.UTC);

    static final ZonedDateTime TOKYO =
        ZonedDateTime.ofLocal(
            LocalDateTime.parse(Dates.DATE_TOKYO), TimeZones.TIME_ZONE_TOKYO, ZoneOffset.UTC);

    static final ZonedDateTime PARIS =
        ZonedDateTime.ofLocal(
            LocalDateTime.parse(Dates.DATE_PARIS), TimeZones.TIME_ZONE_PARIS, ZoneOffset.UTC);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Dates {

    public static final String DATE_LOS_ANGELES = "2019-11-11T19:30:30";
    public static final String DATE_BERLIN = "2018-02-03T12:30:30";
    public static final String DATE_PARIS = "2017-01-03T12:30:30";
    static final String DATE_SAOPAULO = "2019-03-03T12:30:30";
    static final String DATE_TOKYO = "2018-11-03T09:30:30";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class TimeZones {

    public static final ZoneId TIME_ZONE_BERLIN = ZoneId.of("Europe/Berlin");
    static final ZoneId TIME_ZONE_LOS_ANGELES = ZoneId.of("America/Los_Angeles");
    static final ZoneId TIME_ZONE_SAOPAULO = ZoneId.of("America/Sao_Paulo");
    static final ZoneId TIME_ZONE_TOKYO = ZoneId.of("Asia/Tokyo");
    static final ZoneId TIME_ZONE_PARIS = ZoneId.of("Europe/Paris");
  }
}
