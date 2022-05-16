package io.pillopl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        List<BehaviorStrategy> players = List.of(new Random(), new WetZaWet(), new Frajer(), new Wredna(), new MyEx());
        System.out.println(Evolution.evolve(players));
    }
}

enum Behavior {
    COOPERATE,
    BETRAY
}

class Behaviors {
    final Behavior one;
    final Behavior two;

    static Behaviors of(Behavior one, Behavior two) {
        return new Behaviors(one, two);
    }

    Behaviors(Behavior one, Behavior two) {
        this.one = one;
        this.two = two;
    }
}

class Interaction {

    static Behaviors between(BehaviorStrategy player, BehaviorStrategy anotherPlayer) {
        Behavior cardOne = player.showBehavior();
        Behavior cardTwo = anotherPlayer.showBehavior();
        player.seenBehaviorWas(cardTwo);
        anotherPlayer.seenBehaviorWas(cardOne);
        return Behaviors.of(cardOne, cardTwo);
    }

}

class Game {

    static Result between(BehaviorStrategy player, BehaviorStrategy anotherPlayer) {
        BehaviorStrategy p = newInstance(player);
        BehaviorStrategy p2 = newInstance(anotherPlayer);
        return IntStream.range(0, 200)
                .mapToObj(i -> calculatePoints(Interaction.between(p, p2)))
                .reduce(Result.zeroZero(), Result::add);

    }

    static private BehaviorStrategy newInstance(BehaviorStrategy bs) {
        try {
            return bs.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }


    static private Result calculatePoints(Behaviors played) {
        Behavior cardOne = played.one;
        Behavior cardTwo = played.two;
        if (cardOne == Behavior.COOPERATE) {
            if (cardTwo == Behavior.COOPERATE) {
                return new Result(3, 3);
            }
            return new Result(0, 5);
        } else {
            if (cardTwo == Behavior.COOPERATE) {
                return new Result(5, 0);
            }
            return new Result(1, 1);
        }
    }


}


class Result {
    int one;
    int two;

    static Result zeroZero() {
        return new Result(0, 0);
    }

    Result(int one, int two) {
        this.one = one;
        this.two = two;
    }

    Result add(Result result) {
        return new Result(one + result.one, two + result.two);
    }

    @Override
    public String toString() {
        return "Result{" +
                "one=" + one +
                ", two=" + two +
                '}';
    }
}

class Tournament {

    static Map<BehaviorStrategy, Integer> play(List<BehaviorStrategy> players) {
        Map<BehaviorStrategy, Integer> results = new HashMap<>();

        players.forEach(p -> players.forEach(p2 -> {
            Result result = Game.between(p, p2);
            results.merge(p, result.one, Integer::sum);
            results.merge(p2, result.two, Integer::sum);
        }));
        System.out.println(results);
        return results;
    }
}


class Evolution {

    static List<BehaviorStrategy> evolve(List<BehaviorStrategy> generation) {
        if (generation.size() > 1) {
            List<BehaviorStrategy> play = Tournament.play(generation)
                    .entrySet()
                    .stream().sorted(Comparator.comparing(Map.Entry::getValue))
                    .skip(1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            return evolve(play);
        }
        return generation;
    }

}















