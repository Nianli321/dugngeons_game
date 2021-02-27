package unsw.dungeon;

public class SpikeDownState implements SpikeState {
    
    private Spikes spikes;

    /**
     * constructor for SpikeDownState
     * @param spikes spikes which state describes
     */
    public SpikeDownState(Spikes spikes) {
        this.spikes = spikes;
    }

    /**
     * turn state of spikes to up
     * @return SpikeUpState
     */
    @Override
    public SpikeState putUpSpikes() {
        return new SpikeUpState(spikes);
    }
    
}