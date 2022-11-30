<h1 align="center" display="flex" justify-content="" align-items="center">
  Project 2: Nine Men's Morris Game - Variant D
</h1>

## Table of Contents
- [Tech](#tech)
- [Instructions](#instructions)
- [Team Members](#team-members)

## Tech
- **Language**: [Java 19](https://www.oracle.com/java/technologies/downloads/#java19)

## Instructions
<a href="https://pdfhost.io/v/zeJbkcLUb_COSC_461001_Project2" target="_blank" rel="noopener noreferrer">Project 2 instructions</a>

## Game Rules: Morris Game, Variant-D
<div display="flex" justify-content="space-evenly">
  <img width="240" alt="board" src="https://user-images.githubusercontent.com/51928654/204848799-0718cb30-0349-4682-92f8-fbbdaf7a293b.png">
  <img width="529" alt="image" src="https://user-images.githubusercontent.com/51928654/204849047-ffce82f9-7d66-49d1-b4ec-bc737fdaa570.png">
</div>

### Game concepts
- A board game between 2 players - White & Black
- Each player has 9 pieces (a total of 18 pieces between the 2 players)
- The game has 3 phases: Opening, Midgame, and Endgame
- The goal is to capture (remove) opponents pieces by getting 3 pieces on a single line (a mill)
- The winner is:
  - the 1st player to reduce the opponent to only 2 pieces
  - or block the opponent from any further moves
- If 1 player has repeated moves (repeated moves without removing the oppoents piece) --> this player will be judged as lost
- **Mills**: At any stage 
  - If a player gets 3 of their pieces on the same straight line (a mill)
  - then 1 of the oppoenent's isolated pieces is removed from the board
  - unless there is no isolated pieces
  when a player forms a mill and there is no isolated piece from the opponent, this player can remove any piece on the board
- An isolated piece is a piece that is not part of a mill
  
### Game phases
#### Opening:
Players take turns placing their 9 pieces (one at a time) on any vacant board intersection spot

#### Midgame:
Players take turns moving one piece along a board line to any adjacent vacant intersectinon spot

#### Endgame:
A player down to only 3 pieces may move (hopping) a piece to any vacant intersection spot (not just an adjacent one)

## Team Members [⬆️](#table-of-contents)
- [@Osida](https://github.com/Osida)
- [@Everett](https://github.com/Osida/cosc461_project1_search)
