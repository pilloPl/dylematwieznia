package io.pillopl;

public interface BehaviorStrategy {
    Behavior showBehavior();

    void seenBehaviorWas(Behavior card);
}

class Random implements BehaviorStrategy {

    @Override
    public Behavior showBehavior() {
        return new java.util.Random().nextBoolean() ? Behavior.COOPERATE : Behavior.BETRAY;
    }

    @Override
    public void seenBehaviorWas(Behavior reaction) {
    }

    @Override
    public String toString() {
        return "Random";
    }
}

class Wredna implements BehaviorStrategy {

    @Override
    public Behavior showBehavior() {
        return Behavior.BETRAY;
    }

    @Override
    public void seenBehaviorWas(Behavior reaction) {
    }

    @Override
    public String toString() {
        return "Wredna";
    }
}

class Frajer implements BehaviorStrategy {

    @Override
    public Behavior showBehavior() {
        return Behavior.COOPERATE;
    }

    @Override
    public void seenBehaviorWas(Behavior reaction) {
    }

    @Override
    public String toString() {
        return "Frajer";
    }
}

class WetZaWet implements BehaviorStrategy {


    private Behavior lastSeen;

    @Override
    public Behavior showBehavior() {
        if (lastSeen == null) {
            return Behavior.COOPERATE;
        }
        return lastSeen;
    }

    @Override
    public void seenBehaviorWas(Behavior reaction) {
        lastSeen = reaction;
    }

    @Override
    public String toString() {
        return "WetZaWet";
    }
}

class MyEx implements BehaviorStrategy {

    private boolean hasSeenBetray;

    @Override
    public Behavior showBehavior() {
        if (hasSeenBetray) {
            return Behavior.BETRAY;
        }
        return Behavior.COOPERATE;
    }

    @Override
    public void seenBehaviorWas(Behavior reaction) {
        if (reaction == Behavior.BETRAY) {
            hasSeenBetray = true;
         }
    }

    @Override
    public String toString() {
        return "MyEx";
    }
}


