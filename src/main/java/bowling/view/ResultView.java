package bowling.view;

import bowling.domain.frame.Frame;
import bowling.domain.frame.Frames;
import bowling.domain.frame.NormalFrame;
import bowling.domain.point.Point;
import bowling.domain.point.Points;

import java.util.stream.Collectors;

public class ResultView {
    private static final int FIRST_TRY_COUNT = 1;
    private static final int SECOND_TRY_COUNT = 2;
    private static final int THIRD_TRY_COUNT = 3;

    private static final int STRIKE_POINT = 10;
    private static final int GUTTER_POINT = 0;

    private static final String MARK_JOIN_DELIMETER = "|";
    private static final String MARK_STRIKE = "X";
    private static final String MARK_SPARE = "/";
    private static final String MARK_GUTTER = "-";
    private static final String MARK_NONE = "";

    private static final String FRAME_HEAD = "|  NAME  |   01   |   02   |   03   |   04   |   05   |   06   |   07   |   08   |   09   |   10   |";
    private static final String FRAME_LINE = "|";

    public static void viewResult(Frames frames) {
        viewFrameHead();
        viewFrames(frames);
        viewScores(frames);
    }

    private static void viewFrames(Frames frames) {
        viewPlayerName(frames.getPlayerName());
        String frameViews = frames.getFrames().stream()
                .map(frame -> viewFrame(frame))
                .collect(Collectors.joining(FRAME_LINE));
        System.out.println(frameViews + FRAME_LINE);
    }

    private static void viewScores(Frames frames) {
        viewPlayerName("");
        StringBuilder sb = new StringBuilder();
        Integer sumScore = 0;
        for (int i = 0; i < frames.getFrames().size(); i++) {
            sb.append(getScoures(frames.getFrame(i), sumScore));
            sumScore += getSumScore(frames.getFrame(i).getScore());
        }
        System.out.println(sb.toString());
    }

    private static int getSumScore(Integer score) {
        if(score != null){
            return score;
        }
        return 0;
    }

    private static String getScoures(Frame preframe, Integer sumScore) {
        Integer score = preframe.getScore();
        if (score != null) {
            return String.format("%5s   |", score + sumScore);
        }
        return "        |";
    }

    private static String viewFrame(Frame frame) {
        Points points = frame.getPoints();
        if (frame instanceof NormalFrame) {
            return String.format("%5s   ", getScoreMark(points));
        }
        return String.format(" %5s  ", getScoreMark(points));
    }

    private static String getScoreMark(Points points) {
        if (points.getTryCount() == FIRST_TRY_COUNT) {
            return getMark(points.getFirstPoint());
        }
        if (points.getTryCount() == SECOND_TRY_COUNT) {
            return getMark(points.getFirstPoint(), points.getSecondPoint());
        }
        if (points.getTryCount() == THIRD_TRY_COUNT) {
            return getMark(points.getFirstPoint(), points.getSecondPoint()) + MARK_JOIN_DELIMETER + getMark(points.getThirdPoint());
        }
        return MARK_NONE;
    }

    private static String getMark(Point point1, Point point2) {
        int sumPoint = point1.getPoint() + point2.getPoint();
        if (sumPoint == STRIKE_POINT) {
            return getMark(point1) + MARK_JOIN_DELIMETER + MARK_SPARE;
        }
        return getMark(point1) + MARK_JOIN_DELIMETER + getMark(point2);
    }

    private static String getMark(Point point) {
        if (point.getPoint() == STRIKE_POINT) {
            return MARK_STRIKE;
        }
        if (point.getPoint() == GUTTER_POINT) {
            return MARK_GUTTER;
        }
        return String.valueOf(point.getPoint());
    }

    private static void viewPlayerName(String name) {
        System.out.print(String.format("|%6s  |", name));
    }

    private static void viewFrameHead() {
        System.out.println(FRAME_HEAD);
    }
}
