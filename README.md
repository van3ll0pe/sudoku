# Sudoku

## launch the game
`./gradlew run` //run with default map <br>
`./gradlew run --args="file"` //run with the map given in the file <br>

## MAP

in a file you need to have 9 lines and 9 rows with value 1 to 9 for constant in the map
For mutable element, you need to put 0

> exemple :
>  ```
>  01003209 //this is a valid line
>  ```

## How to play

When the game start, you can have many options:
- make a move with m/M and give position and value
- undo the move with `u/U`
- redo the move with `r/R`
- quit the game with `q/Q`
- use a solver with `s/S`

Have fun
