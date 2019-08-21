package com.fkusztel.meeting.planner.service;

import static com.fkusztel.meeting.planner.config.TestObjectFactory.Dates.DATE_BERLIN;
import static com.fkusztel.meeting.planner.config.TestObjectFactory.TimeZones.TIME_ZONE_BERLIN;
import static org.mockito.ArgumentMatchers.any;

import com.fkusztel.meeting.planner.config.TestObjectFactory;
import com.fkusztel.meeting.planner.config.TestObjectFactory.Dates;
import com.fkusztel.meeting.planner.entity.Meeting;
import com.fkusztel.meeting.planner.entity.MeetingRepository;
import com.fkusztel.meeting.planner.entity.MeetingType;
import com.fkusztel.meeting.planner.entity.PriorityType;
import com.fkusztel.meeting.planner.exception.MeetingNotFoundException;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

/** @author Filip.Kusztelak */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MeetingServiceImplTest.class, MeetingServiceImpl.class})
public class MeetingServiceImplTest {

  @Autowired private MeetingServiceImpl meetingService;

  @MockBean private MeetingRepository meetingRepository;

  @Test
  public void missionCreate_ProperMeetingAndPriorityType() {

    Mockito.when(meetingRepository.save(any(Meeting.class)))
        .thenReturn(TestObjectFactory.Meetings.getMeetingBerlin());

    Meeting result =
        meetingService.meetingCreate(
            MeetingType.GROOMING, PriorityType.MEDIUM, DATE_BERLIN, TIME_ZONE_BERLIN);

    Assert.assertEquals(TestObjectFactory.Meetings.getMeetingBerlin(), result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void meetingCreate_WrongMeetingType() {
    meetingService.meetingCreate(
        MeetingType.valueOf("WRONG"), PriorityType.MEDIUM, DATE_BERLIN, TIME_ZONE_BERLIN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void meetingCreate_WrongPriorityType() {
    meetingService.meetingCreate(
        MeetingType.GROOMING, PriorityType.valueOf("WRONG"), DATE_BERLIN, TIME_ZONE_BERLIN);
  }

  @Test
  public void findAllMeetings_success() {
    List<Meeting> missionListExcepted = TestObjectFactory.Meetings.getMeetingList();

    Mockito.when(meetingRepository.findAll()).thenReturn(missionListExcepted);

    List<Meeting> missionListResult = (List<Meeting>) meetingService.findAll();

    Assert.assertEquals(missionListExcepted, missionListResult);
  }

  @Test
  public void findAllMissions_emptyList() {
    List<Meeting> missionListExcepted = Lists.newArrayList();

    Mockito.when(meetingRepository.findAll()).thenReturn(missionListExcepted);

    List<Meeting> missionListResult = (List<Meeting>) meetingService.findAll();

    Assert.assertEquals(missionListExcepted, missionListResult);
  }

  @Test
  public void findMissionById_success() throws MeetingNotFoundException {
    Mockito.when(meetingRepository.findById(2L))
        .thenReturn(Optional.of(TestObjectFactory.Meetings.getMeetingParis()));

    Meeting result = meetingService.findMeetingById(2L);

    Assert.assertEquals(TestObjectFactory.Meetings.getMeetingParis(), result);
  }

  @Test(expected = MeetingNotFoundException.class)
  public void findMissionByName_exception() throws MeetingNotFoundException {
    Mockito.when(meetingRepository.findById(1L)).thenReturn(Optional.empty());

    meetingService.findMeetingById(1L);
  }

  @Test
  public void getMeetingTypes() {
    List<MeetingType> result = (List<MeetingType>) meetingService.getType();

    Assert.assertEquals(Arrays.asList(MeetingType.values()), result);
  }

  @Test
  public void getMeetingPriority() {
    List<PriorityType> result = (List<PriorityType>) meetingService.getPriority();

    Assert.assertEquals(Arrays.asList(PriorityType.values()), result);
  }

  @Test
  public void missionUpdate_Success() {
    Mockito.when(meetingRepository.findById(2L))
        .thenReturn(Optional.of(TestObjectFactory.Meetings.getMeetingParis()));

    Meeting exceptedResult = TestObjectFactory.Meetings.getMeetingBerlin();
    exceptedResult.setId(2L);

    Mockito.when(meetingRepository.save(any(Meeting.class)))
        .thenReturn(TestObjectFactory.Meetings.getMeetingParis());

    Meeting result =
        meetingService.updateMeeting(
            2L, MeetingType.GROOMING, PriorityType.MEDIUM, DATE_BERLIN, TIME_ZONE_BERLIN);

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void missionUpdateFailed_createNewMission() {
    Mockito.when(meetingRepository.findById(2L)).thenReturn(Optional.empty());

    Meeting exceptedResult = TestObjectFactory.Meetings.getMeetingBerlin();
    exceptedResult.setId(2L);

    Mockito.when(meetingRepository.save(any(Meeting.class)))
        .thenReturn(TestObjectFactory.Meetings.getMeetingParis());

    Meeting result =
        meetingService.updateMeeting(
            2L, MeetingType.GROOMING, PriorityType.MEDIUM, DATE_BERLIN, TIME_ZONE_BERLIN);

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void missionSortBy_DateAsc() {
    List<Meeting> exceptedResult = TestObjectFactory.Meetings.getMeetingList();
    exceptedResult.sort(Comparator.comparing(Meeting::getMeetingDate));

    Mockito.when(meetingRepository.findAll(any(Sort.class)))
        .thenReturn(TestObjectFactory.Meetings.getMeetingList());

    List<Meeting> result = (List<Meeting>) meetingService.getSorted("meetingDate", Direction.ASC);
    result.sort(Comparator.comparing(Meeting::getMeetingDate));

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void missionSortBy_DateDesc() {
    List<Meeting> exceptedResult = TestObjectFactory.Meetings.getMeetingList();
    exceptedResult.sort(Comparator.comparing(Meeting::getMeetingDate).reversed());

    Mockito.when(meetingRepository.findAll(any(Sort.class)))
        .thenReturn(TestObjectFactory.Meetings.getMeetingList());

    List<Meeting> result = (List<Meeting>) meetingService.getSorted("meetingDate", Direction.ASC);
    result.sort(Comparator.comparing(Meeting::getMeetingDate).reversed());

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateBetween_success() {
    Mockito.when(meetingRepository.findAll()).thenReturn(TestObjectFactory.Meetings.getDatesAll());

    List<Meeting> result =
        Lists.newArrayList(
            meetingService.findMeetingByDateBetween(
                LocalDateTime.parse(Dates.DATE_PARIS),
                LocalDateTime.parse(Dates.DATE_LOS_ANGELES)));

    List<Meeting> exceptedResult = TestObjectFactory.Meetings.getDatesBetween();

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateBetween_emptyList() {
    List<Meeting> mockResult = Lists.newArrayList();

    Mockito.when(meetingRepository.findAll()).thenReturn(mockResult);

    List<Meeting> result =
        Lists.newArrayList(
            meetingService.findMeetingByDateBetween(
                LocalDateTime.parse(Dates.DATE_LOS_ANGELES),
                LocalDateTime.parse(Dates.DATE_PARIS)));

    List<Meeting> exceptedResult = Lists.newArrayList();

    Assert.assertEquals(exceptedResult, result);
  }
}
