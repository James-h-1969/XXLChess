# XXL Chess

Chess on a 14 × 14 board with five extra piece types, written in Java with [Processing](https://processing.org/). Play against a friend or against the computer. Built for INFO1113 at the University of Sydney as an introduction to Java and object-oriented programming.

![XXL Chess board](https://github.com/James-h-1969/XXLChess/assets/102572039/b98bbdee-1154-4284-bd02-f796dc2028e0)

## Pieces

Alongside the standard king, queen, rook, bishop, knight and pawn:

| Piece | Letter | Moves like |
|-------|--------|-----------|
| Amazon | `A` / `a` | Queen + knight |
| Chancellor | `E` / `e` | Rook + knight |
| Archbishop | `H` / `h` | Bishop + knight |
| Guard | `G` / `g` | King + knight (not royal) |
| Camel | `C` / `c` | A (3, 1) leaper |

Upper-case letters are black, lower-case are white. Positions are plain text files, one row per line; `level1.txt` is the default opening position.

## Features

- Legal move generation with check, checkmate and stalemate detection, castling, and pawn promotion.
- Two-player mode, or a computer opponent that picks a random legal move.
- Chess clocks with per-move increment for both sides.
- Animated piece movement with configurable speed.
- Press `R` to reset the board.

## Configuration

`config.json`:

```json
{
  "layout": "level1.txt",
  "time_controls": {
    "player": { "seconds": 180, "increment": 2 },
    "cpu":    { "seconds": 180, "increment": 2 }
  },
  "player_colour": "white",
  "piece_movement_speed": 6.0,
  "max_movement_time": 1,
  "two_player": true
}
```

## Running

Requires Java 8+ and Gradle.

```bash
gradle run      # start the game
gradle test     # JUnit tests
```

## Code

Everything lives in `src/main/java/XXLChess/`. `App` is the Processing sketch and entry point, `Board` and `Tile` draw the board, `Pieces` loads a layout and owns every `Piece` subclass, `GameLogic` handles turns, check and game end, `Computer` is the opponent, and `Timer` and `Movement` handle the clocks and animation.
