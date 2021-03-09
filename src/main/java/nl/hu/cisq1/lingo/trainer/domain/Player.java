package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private int name;

    public Player(int name) {
        this.name = name;
    }


}
