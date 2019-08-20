package com.fkusztel.meeting.planner.exception;

/**
 * @author Filip.Kusztelak
 */
public class DirectionException extends RuntimeException {

  public DirectionException() {
    super("Wrong direction was passed");
  }
}
