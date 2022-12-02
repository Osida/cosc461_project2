
<a name="readme-top"></a>
# Project 2: Game Playing 🎲
Implementing minimax algorithm and alpha-beta pruning via Nine Men's Morris Game - Variant D

## Table of Contents
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#documentation">Documentation</a></li>
    <li><a href="#tech">Tech</a></li>
    <li><a href="#about-the-game">About the game</a></li>
    <li><a href="#authors">Authors</a></li>
  </ol>
</details>

## Documentation 📄
[Project 2 documentation](https://pdfhost.io/v/zeJbkcLUb_COSC_461001_Project2)

## Tech 💾
**Language:** [Java 19](https://www.oracle.com/java/technologies/downloads/#java19)

## About the game 📜
<div display="flex" justify-content="space-evenly">
  <img width="240" alt="board" src="https://user-images.githubusercontent.com/51928654/204848799-0718cb30-0349-4682-92f8-fbbdaf7a293b.png">
  <img width="529" alt="image" src="https://user-images.githubusercontent.com/51928654/204849047-ffce82f9-7d66-49d1-b4ec-bc737fdaa570.png">
</div>

### Game rules
- A board game between 2 players (White & Black)
- Each player has 9 pieces
- The game has 3 phases (Opening, Midgame, and Endgame)
- The goal is to capture (remove) opponents pieces by getting 3 pieces on a single line (a mill)
- The winner is either:
  - 1st player to reduce the opponent to only 2 pieces
  - block the opponent from any further moves
- If 1 player has repeated moves (repeated moves without removing the oppoents piece) this player will be judged as loss
- **Mills** - At any stage 
  - If a player gets 3 of their pieces on the same straight line (a mill)
  - then 1 of the oppoenent's isolated pieces is removed from the board
  - unless there is no isolated piece: when a player forms a mill and there is no isolated piece from the opponent, this player can remove any piece on the board

### Move generator
#### Move generator for Black
The basic idea is both players will use the same strategy, so we can change the colors and make a move, and then change the colors back

| input | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `b` | `string` | board position|

| output | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `L` | `list` | list of all positions reachable by the input position|

1. Compute the board `tempb` by swapping the colors in `b`: replace each **W** by a **B**and vice versa 

1. Generate `L` containing all positions reachable from `tempb` by a **white** move (following the description in the next section)

1. Swap colors in all borad positions in `L`, replacing **W** with **B** and vice versa


#### Move generator for White
- `GenerateAdd`
    generates  moves created by adding a white piece (to be used in the opening phase).

- `GenerateMove`
    generates moves created by moving a whitepiece to an adjacent location (to be used in the midgame).

- `GenerateHopping`
    generates moves createdby white pieces hopping (to be used in the endgame).

- `GenerateRemove`
    a method of generating moves created by removing a black piece from the board when the White form a mill
    
    
### Game Phases
- `Opening`
Players take turns placing their 9 pieces (one at a time) on any vacant board intersection spot

- `Midgame`
Players take turns moving one piece along a board line to any adjacent vacant intersectinon spot

- `Endgame`
A player down to only 3 pieces may move (hopping) a piece to any vacant intersection spot (not just an adjacent one)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Authors ✍️
- [@Osida](https://github.com/Osida)
- [@Everett](#)

