package main.android51;

        import android.app.Activity;
        import android.graphics.drawable.Drawable;
        import android.os.Bundle;
        import android.view.View;
        import android.util.Log;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.List;
        import java.util.stream.Collectors;
        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Collections;
        import android.content.Intent;

        import chess.Grid;
        import chess.Pieces.*;

public class ChessGame extends Activity {

    //names of bundle variables being sent to game over screen
    public static final String WINNER = "WINNER";
    public static final String GAME_MOVES = "GAME_MOVES";

    //game variables
    ImageView start_view;
    ImageView destination_view;
    char[] firstEntry;
    char[] secondEntry;
    char current_player;

    //undo logic
    Grid.Space[] most_recent_move;
    Grid.Space most_recent_check_piece_white;
    Grid.Space most_recent_check_piece_black;
    Piece most_recent_piece;
    Piece placeholder_piece;
    Drawable most_recent_img;
    ImageView[] most_recent_views;
    Boolean wasInCheck_white;
    Boolean wasInCheck_black;
    Boolean placeholder_moved;
    Boolean most_recent_piece_first_move;

    //misc
    TextView infoPanel;
    TextView turnPanel;
    ArrayList<Grid.Space[]> allmoves;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Grid.init();

        firstEntry = new char[2];
        secondEntry = new char[2];
        firstEntry[0] = '\0';
        firstEntry[1] = '\0';
        secondEntry[0] = '\0';
        secondEntry[1] = '\0';

        current_player = 'w';

        most_recent_views = new ImageView[2];
        most_recent_move = new Grid.Space[2];

        start_view = null;
        destination_view = null;
        most_recent_move[0] = null;
        most_recent_move[1] = null;
        most_recent_views[0] = null;
        most_recent_views[1] = null;
        most_recent_piece = null;
        placeholder_piece = null;
        most_recent_img = null;
        most_recent_check_piece_white = null;
        most_recent_check_piece_black = null;
        wasInCheck_white = false;
        wasInCheck_black = false;

        infoPanel = findViewById(R.id.textView_message);
        turnPanel = findViewById(R.id.textView_player_turn);
        allmoves = new ArrayList<Grid.Space[]>();
    }

    public void onSpaceClick(View space) {
        if (firstEntry[0] == '\0') {
            firstEntry[0] = space.getTag().toString().charAt(0);
            firstEntry[1] = space.getTag().toString().charAt(1);
            start_view = (ImageView) space;

            infoPanel.setText(current_player + " " + firstEntry[0] + firstEntry[1] + " -> ");
        }
        else {
            secondEntry[0] = space.getTag().toString().charAt(0);
            secondEntry[1] = space.getTag().toString().charAt(1);
            destination_view = (ImageView) space;

            Grid.Space start;
            Grid.Space destination;
            int row;
            int column;

            row = Integer.parseInt(Character.toString(firstEntry[1]));
            column = firstEntry[0];

            start = Grid.mapToSpace(row, column);

            row = Integer.parseInt(Character.toString(secondEntry[1]));
            column = secondEntry[0];

            destination = Grid.mapToSpace(row, column);

            placeholder_piece = destination.piece;
            placeholder_moved = start.piece != null ? start.piece.moved : false;

            if  (!(Grid.movePiece(start, destination, current_player, isInCheck()))) {
                infoPanel.setText("Invalid: " + " " + firstEntry[0] + firstEntry[1] + " " + secondEntry[0] +secondEntry[1]);
            }
            else {
                most_recent_img = destination_view.getDrawable();
                most_recent_move[0] = start;
                most_recent_move[1] = destination;
                most_recent_piece = placeholder_piece;
                most_recent_views[0] = start_view;
                most_recent_views[1] = destination_view;
                most_recent_piece_first_move = placeholder_moved;

                Grid.Space[] moves = {start, destination};
                allmoves.add(moves);

                infoPanel.setText(current_player + " " + firstEntry[0] + firstEntry[1] + " " + secondEntry[0] +secondEntry[1]);

                destination_view.setImageDrawable(start_view.getDrawable());
                start_view.setImageDrawable(null);

                if (current_player == 'w') {
                    Grid.wKingCheck[0] = null;
                    Grid.wKingCheck[1] = null;
                    current_player = 'b';
                    turnPanel.setText("Blacks Turn");

                    if (Grid.bKingCheck[0] != null) {
                        infoPanel.setText("bking in check");
                        wasInCheck_black = true;
                        most_recent_check_piece_black = Grid.bKingCheck[0];
                    }
                    else {
                        wasInCheck_black = false;
                    }
                }
                else {
                    Grid.bKingCheck[0] = null;
                    Grid.bKingCheck[1] = null;
                    current_player = 'w';
                    turnPanel.setText("Whites Turn");

                    if (Grid.wKingCheck[0] != null) {
                        infoPanel.setText("wking in check");
                        wasInCheck_white = true;
                        most_recent_check_piece_black = Grid.wKingCheck[0];
                    }
                    else {
                        wasInCheck_white = false;
                    }
                }

                if (isGameOver()) {
                    if (current_player == 'w') {
                        GameOver("Black Wins");
                    }
                    else {
                        GameOver("White Wins");
                    }
                }
            }

            firstEntry[0] = '\0';
            firstEntry[1] = '\0';
            secondEntry[0] = '\0';
            secondEntry[1] = '\0';
        }
    }

    private void GameOver(String winner) {
        Intent game_over_activity = new Intent(this, GameOver.class);

        //you can make your own data type here if you want for the moves
        game_over_activity.putExtra(WINNER, winner);
        game_over_activity.putExtra(GAME_MOVES, allmoves);

        startActivity(game_over_activity);
    }

    public void undo_clicked(View view) {
        if (most_recent_move[0] != null) {
            most_recent_move[0].piece = most_recent_move[1].piece;
            most_recent_move[1].piece = most_recent_piece;
            most_recent_views[0].setImageDrawable(most_recent_views[1].getDrawable());
            most_recent_views[1].setImageDrawable(most_recent_img);

            infoPanel.setText("undo success");

            most_recent_move[0].piece.moved = most_recent_piece_first_move;
            allmoves.remove(allmoves.size() - 1);

            if (current_player == 'w') {
                Grid.wKingCheck[0] = null;
                Grid.wKingCheck[1] = null;

                current_player = 'b';
                if (wasInCheck_black) {
                    Grid.bKingCheck[0] = most_recent_check_piece_black;
                }

                turnPanel.setText("Blacks Turn");
            }
            else {
                Grid.bKingCheck[0] = null;
                Grid.bKingCheck[1] = null;
                current_player = 'w';

                if (wasInCheck_white) {
                    Grid.wKingCheck[0] = most_recent_check_piece_white;
                }

                turnPanel.setText("Whites Turn");
            }

            start_view = null;
            destination_view = null;
            most_recent_move[0] = null;
            most_recent_move[1] = null;
            most_recent_views[0] = null;
            most_recent_views[1] = null;
            most_recent_piece = null;
            placeholder_piece = null;
            most_recent_img = null;
        }
        else {
            infoPanel.setText("undo failed");
        }
    }

    public void ai_clicked(View view) {

    }

    public void draw_clicked(View view) {
        GameOver("Draw");
    }

    public void resign_clicked(View view) {
        if (isGameOver()) {
            if (current_player == 'w') {
                GameOver("Black Wins");
            }
            else {
                GameOver("White Wins");
            }
        }
    }

    private boolean isInCheck() {
        if (current_player == 'b' && Grid.bKingCheck[0] != null) {
            return true;
        }
        else if (current_player == 'w' && Grid.wKingCheck[0] != null) {
            return true;
        }
        return false;
    }

    private Boolean isGameOver() {
        Grid.Space playerKing = Grid.findKing(current_player);
        /*
        Log.i("INFO", "check: " + isInCheck() + " king_moves: " + king_has_no_moves(playerKing) +
                "\npieces_can_capture : " + pieces_can_capture() + " pieces_can_block: " + pieces_can_block(playerKing) +
                "\nwKingCheck[0]: " + Grid.wKingCheck[0] + " bKingCheck[0]: " + Grid.bKingCheck[0]);
        */
        return isInCheck() && king_has_no_moves(playerKing) && !pieces_can_capture() && !pieces_can_block(playerKing);
    }

    private  boolean king_has_no_moves(Grid.Space playerKing) {
        return playerKing.piece.getMoves(playerKing).stream()
                .map(s -> Grid.illegalMove(playerKing, s))
                .reduce((a, b) -> a && b)
                .orElse(true);
    }

    private boolean pieces_can_capture() {
        if (current_player == 'w' && isInCheck()) {
            return Arrays.stream(Grid.board)
                    .flatMap(Arrays::stream)
                    .filter(s -> s.piece != null && s.piece.owner == current_player)
                    .filter(s -> s.piece.getMoves(s).contains(Grid.wKingCheck[0]))
                    .filter(s -> !(s.piece instanceof  King && Grid.illegalMove(s, Grid.wKingCheck[0])))
                    .map(s -> true)
                    .findAny()
                    .orElse(false);
        }
        else {
            return Arrays.stream(Grid.board)
                    .flatMap(Arrays::stream)
                    .filter(s -> s.piece != null && s.piece.owner == current_player)
                    .filter(s -> s.piece.getMoves(s).contains(Grid.bKingCheck[0]))
                    .filter(s -> !(s.piece instanceof  King && Grid.illegalMove(s, Grid.bKingCheck[0])))
                    .map(s -> true)
                    .findAny()
                    .orElse(false);
        }
    }

    private boolean pieces_can_block(Grid.Space playerKing) {
        if (current_player == 'w' && isInCheck()) {
            if (Grid.wKingCheck[0].piece instanceof Queen || Grid.wKingCheck[0].piece instanceof Rook
                    || Grid.wKingCheck[0].piece instanceof Bishop) {
                String[] directions = {"l", "ul", "u", "ur", "r", "dr", "d", "dl"};

                return Arrays.stream(Grid.board)
                        .flatMap(Arrays::stream)
                        .filter(s -> s.piece != null && s.piece.owner == current_player && !(s.piece instanceof King))
                        .filter(s -> {
                            ArrayList<Grid.Space> line = Arrays.stream(directions)
                                    .filter(d -> Grid.wKingCheck[0].piece.getLine(Grid.wKingCheck[0], d).contains(playerKing))
                                    .map(d -> Grid.wKingCheck[0].piece.getLine(Grid.wKingCheck[0], d))
                                    .flatMap(l -> l.stream())
                                    .collect(Collectors.toCollection(ArrayList::new));
                            return !Collections.disjoint(s.piece.getMoves(s), line);
                        })
                        .map(s -> true)
                        .findAny()
                        .orElse(false);
            }
        } else if (current_player == 'b' && isInCheck()) {
            if (Grid.bKingCheck[0].piece instanceof Queen || Grid.bKingCheck[0].piece instanceof Rook
                    || Grid.bKingCheck[0].piece instanceof Bishop) {
                String[] directions = {"l", "ul", "u", "ur", "r", "dr", "d", "dl"};

                return Arrays.stream(Grid.board)
                        .flatMap(Arrays::stream)
                        .filter(s -> s.piece != null && s.piece.owner == current_player && !(s.piece instanceof King))
                        .filter(s -> {
                            ArrayList<Grid.Space> line = Arrays.stream(directions)
                                    .filter(d -> Grid.bKingCheck[0].piece.getLine(Grid.bKingCheck[0], d).contains(playerKing))
                                    .map(d -> Grid.bKingCheck[0].piece.getLine(Grid.bKingCheck[0], d))
                                    .flatMap(l -> l.stream())
                                    .collect(Collectors.toCollection(ArrayList::new));
                            return !Collections.disjoint(s.piece.getMoves(s), line);
                        })
                        .filter(s -> {
                                boolean is_illegal_move = s.piece.getMoves(s).stream()
                                        .filter(des -> Grid.illegalMove(s, des))
                                        .map(des -> true)
                                        .findAny()
                                        .orElse(false);

                                return !is_illegal_move;
                        })
                        .map(s -> true)
                        .findAny()
                        .orElse(false);
            }
        }
        return false;
    }
}
