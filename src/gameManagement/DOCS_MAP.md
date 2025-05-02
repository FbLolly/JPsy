## Map documentation
## EMPTY LINES ARE AUTOMATICALLY IGNORED

boolean (room lighting, if it is off, the game timer is automatically going to appear)
integer (offsets all components of the map by this number, making it possible to use more texture packs in different maps)

integer (map width in tiles, not pixels)
integer (map height in tiles, not pixels)

integer (number of the next map, 0 if there is no possible next map (absence of doors))

integer matrix (the map itself made out of it's components (width x height), i advise that every number inserted here has a xx format to improve readability)

# components
0       : Floor component
-1      : Player Spawn
1-10    : Wall components
10-30   : Floor Items (watch interactibles)
30-40   : Floor Effects
50      : Door

# interactibles
x, y of every item desired to be interactible


## Map example
true

0

5
5

2

01 02 02 02 03
08 -1 00 00 04
08 00 00 30 50
08 00 00 00 04
07 06 06 06 05