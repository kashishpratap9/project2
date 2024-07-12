Problem Definition:
The N-Queens problem is about placing N queens on an 
𝑁
×
𝑁
N×N chessboard in such a way that no two queens can attack each other. The challenge lies in the fact that a queen can move horizontally, vertically, and diagonally.

Constraints:
Row Constraint: No two queens can be placed in the same row.
Column Constraint: No two queens can be placed in the same column.
Diagonal Constraints: No two queens can be placed on the same diagonal. This includes both:
Major Diagonal (from top-left to bottom-right)
Minor Diagonal (from top-right to bottom-left)
Approach:
The problem can be approached using several methods, with the most common being backtracking.

Backtracking Approach:
Place a Queen: Start by placing a queen in the first row.
Check Safety: For each column in the current row, check if placing a queen there is safe (i.e., it does not violate any of the constraints).
Recursive Placement: If placing the queen is safe, move on to the next row and repeat the process.
Backtrack: If no valid position is found for a queen in a particular row, backtrack to the previous row and move the previously placed queen to the next possible position.
Visualization:
Row-by-Row Placement: Place queens row by row, ensuring that each placement does not result in an attack.
Solution Representation: Each solution can be represented as an array where the index represents the row and the value at each index represents the column position of the queen in that row.
