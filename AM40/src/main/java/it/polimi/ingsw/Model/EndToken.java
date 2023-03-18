package it.polimi.ingsw.Model;

public class EndToken {
    private boolean end;
    private Player player;

    public EndToken() {
        end = false;
        player = null;
    }
    public boolean check (Player p) {
        return p.getBookshelf().isFull();
    }
    public void updateScore (Player p) {
        if (player == null && !end && check(p)) {
            p.addCurrentScore(1);
            player = p;
            end = true;
        }
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
