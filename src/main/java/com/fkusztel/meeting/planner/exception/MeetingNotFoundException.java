package com.fkusztel.meeting.planner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** @author Filip.Kusztelak */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This Meeting does not exist in the system.")
public class MeetingNotFoundException extends Exception {}
