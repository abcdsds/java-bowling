package bowling.domain.state.finish;

import bowling.domain.pin.Pins;
import bowling.domain.state.StateExpression;
import bowling.exception.CannotMatchStateException;

public class Spare extends Finished {

    private final Pins firstPins;
    private final Pins secondPins;

    private Spare(final Pins firstPins, final Pins secondPins) {
        if (!firstPins.isSpare(secondPins)) {
            throw new CannotMatchStateException(this.getClass().getName());
        }
        this.firstPins = firstPins;
        this.secondPins = secondPins;
    }

    public static Spare of(final Pins firstPins, final Pins secondPins) {
        return new Spare(firstPins, secondPins);
    }

    @Override
    public boolean isMiss() {
        return false;
    }

    @Override
    public String getDesc() {
        return firstPins.getHitCount() + "|" + StateExpression.SPARE;
    }
}
