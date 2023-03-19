package it.polimi.ingsw.am40;

public class EndToken {
    private boolean end;
    private Player player;

    public EndToken() {
        end = false;
        player = null;
    }
    public boolean check (Player p) {
        return player.getBookshelf().isFull();
    }
    public void updateScore (Player p) {
        if (player == null && !end && check(p)) {
            p.addCurrentScore(1);
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
