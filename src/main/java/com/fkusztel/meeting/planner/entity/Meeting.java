package com.fkusztel.meeting.planner.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Filip.Kusztelak
 */
@Entity(name = "Meeting")
@Table(name = "meeting")
@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Meeting implements Serializable {

  @Id
  @Column(name = "meeting_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "meeting_type")
  @Enumerated(EnumType.STRING)
  private MeetingType meetingType;

  @Column(name = "priority_type")
  @Enumerated(EnumType.STRING)
  private PriorityType priorityType;

  @Column(name = "meeting_date", unique = true)
  private ZonedDateTime meetingDate;
}
