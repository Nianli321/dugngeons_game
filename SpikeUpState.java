package unsw.dungeon;

public class SpikeUpState implements SpikeState {

    private Spikes spikes;

    /**
     * constructor for SpikeUpState
     * @param spikes spikes which state describes
     */
    public SpikeUpState(Spikes spikes) {
        this.spikes = spikes;
    }

    /**
     * override to make putUpSpikes do nothing
     * @return SpikeState
     */
    @Override
    public SpikeState putUpSpikes() {
        return this;
    }
}